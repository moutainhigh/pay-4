/**
 * 
 */
package com.pay.fo.order.service.autofundout.callback;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pay.acc.service.account.constantenum.PayForEnum;
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
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class AutoFundoutOrderCallbackServiceImpl extends
		AbstractOrderCallbackService {
	
	/**
	 * 出款订单处理服务类
	 */
	private FundoutOrderProcessService fundoutOrderProcessService;
	
	/**
	 * 邮件模板
	 */
	private Map<Integer, Long> payerEmailTemplateMap;
	
	
	/**
	 * @param fundoutOrderProcessService the fundoutOrderProcessService to set
	 */
	public void setFundoutOrderProcessService(
			FundoutOrderProcessService fundoutOrderProcessService) {
		this.fundoutOrderProcessService = fundoutOrderProcessService;
	}

	private FundoutOrderDTO init(Order order){
		if(!(order instanceof FundoutOrderDTO)){
			 throw new RuntimeException("传入订单对象不匹配");
		}
		return (FundoutOrderDTO)order;
	}

	@Override
	public boolean updateOrderStatus(Order order) throws AppException {
		FundoutOrderDTO dto = init(order);
		if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.TRUST_WITHDRAW_REQ.getValue())){
			fundoutOrderProcessService.updateOrderStatusRdTx(dto, OrderStatus.INIT.getValue());
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.TRUST_WITHDRAW_REFUND.getValue())){
			fundoutOrderProcessService.updateOrderStatusRdTx(dto, OrderStatus.PROCESSED_SUCCESS.getValue());
		}else{
			fundoutOrderProcessService.updateOrderStatusRdTx(dto, OrderStatus.PROCESSING.getValue());
		}
		return true;
	}

	@Override
	public void notify(Order order) {
		FundoutOrderDTO dto = init(order);
		if(StringUtil.isNull(dto.getPayerLoginName())){//付款方用户名为空时不发送消息
			log.error("payer loginName is null,can't send message");
			return;
		}
	   if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.TRUST_WITHDRAW_FAIL.getValue())){
				notifyPayerEmail(dto, payerEmailTemplateMap.get(dto.getOrderStatus()), "您的申请委托提现处理失败");
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.TRUST_WITHDRAW_SUCCESS.getValue())){
				notifyPayerEmail(dto, payerEmailTemplateMap.get(dto.getOrderStatus()), "您的申请委托提现处理成功");
		}
	}

	@Override
	protected AccountingDto buildAccountingDto(Order order) {
		FundoutOrderDTO dto = init(order);
		
		AccountingDto acctDto = new AccountingDto();
		acctDto.setAmount(dto.getOrderAmount());
		acctDto.setBankCode(String.valueOf(dto.getFundoutBankCode()));
		acctDto.setOrderAmount(dto.getOrderAmount());
		acctDto.setOrderId(dto.getOrderId());
		acctDto.setHasCaculatedPrice(true);
		if(dto.getIsPayerPayFee()==1){
			acctDto.setPayerFee(dto.getFee());
		}else{
			acctDto.setPayeeFee(dto.getFee());
		}
		
		if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.TRUST_WITHDRAW_REQ.getValue())){
			acctDto.setPayer(dto.getPayerMemberCode());
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.TRUST_WITHDRAW_FAIL.getValue())){
			acctDto.setPayee(dto.getPayerMemberCode());
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.TRUST_WITHDRAW_SUCCESS.getValue())){
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.TRUST_WITHDRAW_REFUND.getValue())){
			acctDto.setPayee(dto.getPayerMemberCode());
			acctDto.setOrderId(dto.getRefundOrderId());
		}
		
		return acctDto;
	}
	
	
	/**
	 * 给付款方发送邮件
	 * @param dto
	 */
	private void notifyPayerEmail(FundoutOrderDTO dto,long tempId,String subject){
		
		Map<String, String> data = new HashMap<String, String>();
		//用户名称
		data.put("name",dto.getPayerName());
		//会员类型
		data.put("memberType",String.valueOf(dto.getPayerMemberType()));
		//申请日期
		data.put("date",formatEmailDate(new Date()));
		//提现金额
		data.put("amount",AmountUtils.numberFormat(dto.getOrderAmount()));
		
		data.put("paymentAmount",AmountUtils.numberFormat(dto.getOrderAmount()));
		
		data.put("payerName",dto.getPayerName());
		
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
	 * @param payerEmailTemplateMap the payerEmailTemplateMap to set
	 */
	public void setPayerEmailTemplateMap(Map<Integer, Long> payerEmailTemplateMap) {
		this.payerEmailTemplateMap = payerEmailTemplateMap;
	}

}
