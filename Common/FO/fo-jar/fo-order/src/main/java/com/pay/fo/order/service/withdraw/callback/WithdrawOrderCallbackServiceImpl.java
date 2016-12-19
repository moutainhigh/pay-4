/**
 * 
 */
package com.pay.fo.order.service.withdraw.callback;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.fo.order.common.OrderProcessType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.dto.Order;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.AbstractOrderCallbackService;
import com.pay.fo.order.service.base.FundoutOrderProcessService;
import com.pay.fundout.util.AmountUtils;
import com.pay.inf.exception.AppException;
import com.pay.jms.notification.request.RequestType;
import com.pay.pe.dto.AccountingDto;
import com.pay.poss.base.common.Constants;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class WithdrawOrderCallbackServiceImpl extends
		AbstractOrderCallbackService {

	/**
	 * 出款订单处理服务类
	 */
	private FundoutOrderProcessService fundoutOrderProcessService;

	/**
	 * 短信模板
	 */
	private Map<Integer, Long> payerSmsTemplateMap;
	/**
	 * 邮件模板
	 */
	private Map<Integer, Long> payerEmailTemplateMap;

	/**
	 * 通知生成工单队列名称
	 */
	private String queueName;

	/**
	 * @param fundoutOrderProcessService
	 *            the fundoutOrderProcessService to set
	 */
	public void setFundoutOrderProcessService(
			final FundoutOrderProcessService fundoutOrderProcessService) {
		this.fundoutOrderProcessService = fundoutOrderProcessService;
	}

	private FundoutOrderDTO init(final Order order) {
		if (!(order instanceof FundoutOrderDTO)) {
			throw new RuntimeException("传入订单对象不匹配");
		}
		return (FundoutOrderDTO) order;
	}

	@Override
	public boolean updateOrderStatus(final Order order) throws AppException {
		FundoutOrderDTO dto = init(order);
		if (dto.getProcessType()
				.getValue()
				.equalsIgnoreCase(
						OrderProcessType.COMMON_WITHDRAW_REQ.getValue())) {
			fundoutOrderProcessService.updateOrderStatusRdTx(dto,
					OrderStatus.INIT.getValue());
		} else if (dto
				.getProcessType()
				.getValue()
				.equalsIgnoreCase(
						OrderProcessType.COMMON_WITHDRAW_REFUND.getValue())) {
			fundoutOrderProcessService.updateOrderStatusRdTx(dto,
					OrderStatus.PROCESSED_SUCCESS.getValue());
		} else {
			fundoutOrderProcessService.updateOrderStatusRdTx(dto,
					OrderStatus.PROCESSING.getValue());
		}
		return true;
	}

	@Override
	public void notify(final Order order) {
		FundoutOrderDTO dto = init(order);

		if (StringUtil.isNull(dto.getPayerLoginName())) {// 付款方用户名为空时不发送消息
			log.error("payer loginName is null,can't send message");
			return;
		}
		if (dto.getProcessType()
				.getValue()
				.equalsIgnoreCase(
						OrderProcessType.COMMON_WITHDRAW_REQ.getValue())) {

			notifyMessage(dto);

			if (isEmail(dto.getPayerLoginName())) {
				notifyPayerEmail(dto,
						payerEmailTemplateMap.get(dto.getOrderStatus()),
						"您已经申请提现,等待支付向银行账户打款");
			} else {
				notifyPayerSMS(dto,
						payerSmsTemplateMap.get(dto.getOrderStatus()),
						"提现申请提交成功");
			}
		} else if (dto
				.getProcessType()
				.getValue()
				.equalsIgnoreCase(
						OrderProcessType.COMMON_WITHDRAW_FAIL.getValue())) {
			if (isEmail(dto.getPayerLoginName())) {
				notifyPayerEmail(dto,
						payerEmailTemplateMap.get(dto.getOrderStatus()),
						"您的申请提现处理失败");
			} else {
				notifyPayerSMS(dto,
						payerSmsTemplateMap.get(dto.getOrderStatus()), "提现失败");
			}
		} else if (dto
				.getProcessType()
				.getValue()
				.equalsIgnoreCase(
						OrderProcessType.COMMON_WITHDRAW_SUCCESS.getValue())) {
			if (isEmail(dto.getPayerLoginName())) {
				notifyPayerEmail(dto,
						payerEmailTemplateMap.get(dto.getOrderStatus()),
						"您的申请提现处理成功");
			} else {
				notifyPayerSMS(dto,
						payerSmsTemplateMap.get(dto.getOrderStatus()), "提现成功");
			}
		}

	}

	@Override
	protected AccountingDto buildAccountingDto(final Order order) {

		AccountingDto acctDto = new AccountingDto();
		
		FundoutOrderDTO dto = init(order);
		
		String acctCode = dto.getPayerAcctCode();

		try {
			AcctAttribDto acctAttribDto = acctAttribService
					.queryAcctAttribWithAcctCode(acctCode);
			acctDto.setPayerCurrencyCode(acctAttribDto.getCurCode());
			acctDto.setPayeeCurrencyCode(acctAttribDto.getCurCode());
			acctDto.setPayerAcctType(acctAttribDto.getAcctType());
			
		} catch (Exception e) {
			log.error("error:", e);
		}

		acctDto.setAmount(dto.getRealoutAmount());
		//acctDto.setBankCode(String.valueOf(dto.getFundoutBankCode()));
		acctDto.setOrderAmount(dto.getOrderAmount());
		acctDto.setOrderId(dto.getOrderId());
		acctDto.setHasCaculatedPrice(true);
		if (dto.getIsPayerPayFee() == 1) {
			acctDto.setPayerFee(dto.getFee());
		} else {
			acctDto.setPayeeFee(dto.getFee());
		}

		if (dto.getProcessType()
				.getValue()
				.equalsIgnoreCase(
						OrderProcessType.COMMON_WITHDRAW_REQ.getValue())) {
			//acctDto.setBusinessType(PayForEnum.FO_WITHDRAW.getCode());
			acctDto.setPayer(dto.getPayerMemberCode());
		} else if (dto
				.getProcessType()
				.getValue()
				.equalsIgnoreCase(
						OrderProcessType.COMMON_WITHDRAW_FAIL.getValue())) {
			acctDto.setPayee(dto.getPayerMemberCode());
			acctDto.setPayeeAcctType(dto.getPayerAcctType());
			//acctDto.setBusinessType(PayForEnum.FO_WITHDRAW_BACK.getCode());
		} else if (dto
				.getProcessType()
				.getValue()
				.equalsIgnoreCase(
						OrderProcessType.COMMON_WITHDRAW_SUCCESS.getValue())) {
			//acctDto.setBusinessType(PayForEnum.FO_WITHDRAW.getCode());
		} else if (dto
				.getProcessType()
				.getValue()
				.equalsIgnoreCase(
						OrderProcessType.COMMON_WITHDRAW_REFUND.getValue())) {
			acctDto.setPayee(dto.getPayerMemberCode());
			acctDto.setOrderId(dto.getRefundOrderId());
			acctDto.setPayeeAcctType(dto.getPayerAcctType());
			//acctDto.setBusinessType(PayForEnum.FO_WITHDRAW_REFUND_TICKET.getCode());
		}

		return acctDto;
	}

	/**
	 * 发送生成工单通知
	 * 
	 * @param dto
	 */
	private void notifyMessage(final FundoutOrderDTO dto) {
		String jsonStr = JSonUtil.toJSonString(dto.getOrderId());
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(jsonStr);
		// 通知后台处理
		notifyFacadeService.sendRequest(request);
	}

	/**
	 * 给付款方发送邮件
	 * 
	 * @param dto
	 */
	private void notifyPayerEmail(final FundoutOrderDTO dto, final long tempId,
			final String subject) {

		Map<String, String> data = new HashMap<String, String>();
		// 用户名称
		data.put("name", dto.getPayerName());
		// 会员类型
		data.put("memberType", String.valueOf(dto.getPayerMemberType()));
		// 申请日期
		data.put("date", formatEmailDate(new Date()));
		// 提现金额
		data.put("amount", AmountUtils.numberFormat(dto.getOrderAmount()));

		data.put("paymentAmount",
				AmountUtils.numberFormat(dto.getOrderAmount()));

		data.put("payerName", dto.getPayerName());

		data.put("appDate", formatEmailDate(new Date()));

		data.put("bankName", dto.getPayeeBankName());

		data.put("bankAcc", getRequireBankAccount(dto.getPayeeBankAcctCode()));

		NotifyTargetRequest request = new NotifyTargetRequest();
		request.setData(data);
		request.setNotifyType(RequestType.EMAIL.value());
		request.setFromAddress(Constants.SYSTEM_MAIL);
		request.getRecAddress().add(dto.getPayerLoginName());
		request.setTemplateId(tempId);
		request.setRequestTime(new Date());
		request.setSubject(subject);
		notifyFacadeService.notifyRequest(request);

	}

	/**
	 * 给付款方发送短信
	 * 
	 * @param dto
	 */
	private void notifyPayerSMS(final FundoutOrderDTO dto, final long tmpId, final String subject) {

		Map<String, String> data = new HashMap<String, String>();
		// 用户名称
		data.put("name", dto.getPayerName());
		// 申请日期
		data.put("date", formatSMSDate(dto.getCreateDate()));
		// 提现金额
		data.put("amount", AmountUtils.numberFormat(dto.getOrderAmount()));

		NotifyTargetRequest request = new NotifyTargetRequest();
		request.getMobiles().add(dto.getPayerLoginName());
		request.setNotifyType(RequestType.SMS.value());
		request.setSubject(subject);
		request.setData(data);
		request.setTemplateId(tmpId);
		request.setRequestTime(new Date());
		notifyFacadeService.notifyRequest(request);
	}

	/**
	 * @param payerSmsTemplateMap
	 *            the payerSmsTemplateMap to set
	 */
	public void setPayerSmsTemplateMap(final Map<Integer, Long> payerSmsTemplateMap) {
		this.payerSmsTemplateMap = payerSmsTemplateMap;
	}

	/**
	 * @param payerEmailTemplateMap
	 *            the payerEmailTemplateMap to set
	 */
	public void setPayerEmailTemplateMap(
			final Map<Integer, Long> payerEmailTemplateMap) {
		this.payerEmailTemplateMap = payerEmailTemplateMap;
	}

	/**
	 * @param queueName
	 *            the queueName to set
	 */
	public void setQueueName(final String queueName) {
		this.queueName = queueName;
	}

}
