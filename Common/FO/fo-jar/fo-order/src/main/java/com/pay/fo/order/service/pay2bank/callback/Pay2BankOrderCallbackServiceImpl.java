/**
 * 
 */
package com.pay.fo.order.service.pay2bank.callback;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
public class Pay2BankOrderCallbackServiceImpl extends
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
	 * 收款方短信通知模板
	 */
	private Long payeeSmsTemplateId;
	
	/**
	 * 通知生成工单队列名称
	 */
	private String queueName;
	
	
	
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
		if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_PAY2BANK_REQ.getValue())){
			fundoutOrderProcessService.updateOrderStatusRdTx(dto, OrderStatus.INIT.getValue());
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_PAY2BANK_REFUND.getValue())){
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
		if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_PAY2BANK_REQ.getValue())){
			
			notifyMessage(dto);
			
			if(isEmail(dto.getPayerLoginName())){
				notifyPayerEmail(dto, payerEmailTemplateMap.get(dto.getOrderStatus()), "您已经申请付款到银行,等待支付向银行账户打款");
			}else{
				notifyPayerSMS(dto, payerSmsTemplateMap.get(dto.getOrderStatus()),"付款申请提交成功");
			}
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_PAY2BANK_FAIL.getValue())){
			if(isEmail(dto.getPayerLoginName())){
				notifyPayerEmail(dto, payerEmailTemplateMap.get(dto.getOrderStatus()), "您的申请付款到银行处理失败");
			}else{
				notifyPayerSMS(dto, payerSmsTemplateMap.get(dto.getOrderStatus()),"付款到银行处理失败");
			}
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_PAY2BANK_SUCCESS.getValue())){
			if(isEmail(dto.getPayerLoginName())){
				notifyPayerEmail(dto, payerEmailTemplateMap.get(dto.getOrderStatus()), "您的申请付款到银行处理成功");
			}else{
				notifyPayerSMS(dto, payerSmsTemplateMap.get(dto.getOrderStatus()),"付款到银行处理成功");
			}
			if(!StringUtil.isNull(dto.getPayeeMobile())){
				notifyPayeeSMS(dto,payeeSmsTemplateId,"付款到银行收款方短息通知");
			}
		}

	}

	@Override
	protected AccountingDto buildAccountingDto(Order order) {
		FundoutOrderDTO dto = init(order);
		
		AccountingDto acctDto = new AccountingDto();
		acctDto.setAmount(dto.getRealoutAmount());
		acctDto.setBankCode(String.valueOf(dto.getFundoutBankCode()));
		acctDto.setOrderAmount(dto.getOrderAmount());
		acctDto.setOrderId(dto.getOrderId());
		acctDto.setHasCaculatedPrice(true);
		if(dto.getIsPayerPayFee()==1){
			acctDto.setPayerFee(dto.getFee());
		}else{
			acctDto.setPayeeFee(dto.getFee());
		}
		
		if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_PAY2BANK_REQ.getValue())){
			acctDto.setPayer(dto.getPayerMemberCode());
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_PAY2BANK_FAIL.getValue())){
			acctDto.setPayee(dto.getPayerMemberCode());
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_PAY2BANK_SUCCESS.getValue())){
		}else if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_PAY2BANK_REFUND.getValue())){
			acctDto.setOrderId(dto.getRefundOrderId());
			acctDto.setPayee(dto.getPayerMemberCode());
		}
		
		return acctDto;
	}
	
	/**
	 * 发送生成工单通知
	 * @param dto
	 */
	private void notifyMessage(FundoutOrderDTO dto){
		String jsonStr = JSonUtil.toJSonString(dto.getOrderId());
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(jsonStr);
		//通知后台处理
		notifyFacadeService.sendRequest(request);
	}
	
	
	
	/**
	 * 给收款方发送短信
	 * @param dto
	 */
	private void notifyPayeeSMS(FundoutOrderDTO dto,long tempId,String subject){
		Map<String, String> data = new HashMap<String, String>();
		data.put("payerName",dto.getPayerName());
		data.put("bankName",dto.getPayeeBankName());
		data.put("amount", AmountUtils.numberFormat(dto.getOrderAmount()));
		String bankAcc = getRequireBankAccount(dto.getPayeeBankAcctCode());
		data.put("bankAcc", bankAcc);
		NotifyTargetRequest request = new NotifyTargetRequest();
		request.getMobiles().add(dto.getPayeeMobile());
		request.setNotifyType(RequestType.SMS.value());
		request.setSubject(subject);
		request.setData(data);
		request.setTemplateId(tempId);
		request.setRequestTime(new Date());
		notifyFacadeService.notifyRequest(request);
	}
	
	
	/**
	 * 给付款方发送邮件
	 * @param dto
	 */
	private void notifyPayerEmail(FundoutOrderDTO dto,long tempId,String subject){
		Map<String, String> data = new HashMap<String, String>();
		//收款方用户类型
		String payeeMemberType = "2";
		if(dto.getTradeType()==0){
			payeeMemberType= "1";
		}
		data.put("payeeMemberType",payeeMemberType);
		//申请日期
		data.put("appDate", formatEmailDate(dto.getCreateDate()));
		//付款金额
		data.put("paymentAmount", AmountUtils.numberFormat(dto.getOrderAmount()));
		//交易号
		data.put("dealId", String.valueOf(dto.getOrderId()));
		//付款方会员号
		data.put("memberType", String.valueOf(dto.getPayerMemberType()));
		//收款方名称
		data.put("bankUserName",dto.getPayeeName());
		//收款行名称
		data.put("bankName",dto.getPayeeBankName());
		//付款方名称
		data.put("payerName", dto.getPayerName());
		String bankAcc = getRequireBankAccount(dto.getPayeeBankAcctCode());
		//收款方账号
		data.put("bankAcc", bankAcc);
		
		
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
	 * @param dto
	 */
	private void notifyPayerSMS(FundoutOrderDTO dto,long tmpId,String subject){
		
		Map<String, String> data = new HashMap<String, String>();
		
		
		data.put("dealId", String.valueOf(dto.getOrderId()));
		data.put("bankUserName",dto.getPayeeName());
		data.put("bankName",dto.getPayeeBankName());
		data.put("appDate", formatSMSDate(dto.getCreateDate()));
		data.put("paymentAmount", AmountUtils.numberFormat(dto.getOrderAmount()));
		String bankAcc = getRequireBankAccount(dto.getPayeeBankAcctCode());
		data.put("bankAcc", bankAcc);
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
	 * @param payerSmsTemplateMap the payerSmsTemplateMap to set
	 */
	public void setPayerSmsTemplateMap(Map<Integer, Long> payerSmsTemplateMap) {
		this.payerSmsTemplateMap = payerSmsTemplateMap;
	}

	/**
	 * @param payerEmailTemplateMap the payerEmailTemplateMap to set
	 */
	public void setPayerEmailTemplateMap(Map<Integer, Long> payerEmailTemplateMap) {
		this.payerEmailTemplateMap = payerEmailTemplateMap;
	}

	/**
	 * @param payeeSmsTemplateId the payeeSmsTemplateId to set
	 */
	public void setPayeeSmsTemplateId(Long payeeSmsTemplateId) {
		this.payeeSmsTemplateId = payeeSmsTemplateId;
	}

	/**
	 * @param queueName the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	

}
