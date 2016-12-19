/**
 * 
 */
package com.pay.fo.order.service.pay2acct.callback;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pay.fo.order.common.OrderProcessType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.dto.Order;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.fo.order.service.AbstractOrderCallbackService;
import com.pay.fo.order.service.base.PayToAcctOrderProcessService;
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
public class Pay2AcctOrderCallbackServiceImpl extends
		AbstractOrderCallbackService {
	
	/**
	 * 付款到账户订单处理服务类
	 */
	private PayToAcctOrderProcessService payToAcctOrderProcessService;
	
	/**
	 * 短信模板
	 */
	private Long payeeSmsTemplateId;
	/**
	 * 邮件模板
	 */
	private Long payeeEmailTemplateId;
	
	
	
	

	/**
	 * @param payToAcctOrderProcessService the payToAcctOrderProcessService to set
	 */
	public void setPayToAcctOrderProcessService(
			PayToAcctOrderProcessService payToAcctOrderProcessService) {
		this.payToAcctOrderProcessService = payToAcctOrderProcessService;
	}



	/**
	 * @param payeeSmsTemplateId the payeeSmsTemplateId to set
	 */
	public void setPayeeSmsTemplateId(Long payeeSmsTemplateId) {
		this.payeeSmsTemplateId = payeeSmsTemplateId;
	}



	/**
	 * @param payeeEmailTemplateId the payeeEmailTemplateId to set
	 */
	public void setPayeeEmailTemplateId(Long payeeEmailTemplateId) {
		this.payeeEmailTemplateId = payeeEmailTemplateId;
	}



	private PayToAcctOrderDTO init(Order order){
		if(!(order instanceof PayToAcctOrderDTO)){
			 throw new RuntimeException("传入订单对象不匹配");
		}
		return (PayToAcctOrderDTO)order;
	}

	@Override
	public boolean updateOrderStatus(Order order) throws AppException {
		PayToAcctOrderDTO dto = init(order);
		payToAcctOrderProcessService.updateOrderStatusRdTx(dto, OrderStatus.INIT.getValue());
		return true;
	}

	@Override
	public void notify(Order order) {
		PayToAcctOrderDTO dto = init(order);
		
		if(StringUtil.isNull(dto.getPayeeLoginName())){//收款方用户名为空时不发送消息
			log.error("payee loginName is null,can't send message");
			return;
		}
		if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_PAY2ACCT_SUCCESS.getValue())){
			if(isEmail(dto.getPayeeLoginName())){
				notifyPayeeEmail(dto, payeeEmailTemplateId, "您的付款到账户已经处理成功");
			}else{
				notifyPayeeSMS(dto, payeeSmsTemplateId,"付款到账户处理成功");
			}
		}

	}

	@Override
	protected AccountingDto buildAccountingDto(Order order) {
		PayToAcctOrderDTO dto = init(order);
		
		AccountingDto acctDto = null;
		
		if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.COMMON_PAY2ACCT_SUCCESS.getValue())){
			acctDto = new AccountingDto();
			acctDto.setAmount(dto.getOrderAmount());
			acctDto.setOrderAmount(dto.getOrderAmount());
			acctDto.setOrderId(dto.getOrderId());
			acctDto.setPayer(dto.getPayerMemberCode());
			acctDto.setPayee(dto.getPayeeMemberCode());
		}
		
		return acctDto;
	}
	
	
	
	
	/**
	 * 给付款方发送邮件
	 * @param dto
	 */
	private void notifyPayeeEmail(PayToAcctOrderDTO dto,long tempId,String subject){
		Map<String, String> data = new HashMap<String, String>();
		
		data.put("payeeName", dto.getPayeeName());
		data.put("payerName", dto.getPayerName());
		data.put("payeeMember", dto.getPayeeMemberCode().toString());
		data.put("amount", AmountUtils.numberFormat(dto.getOrderAmount()));
		data.put("fee", AmountUtils.numberFormat(0L));
		data.put("remarks", dto.getPaymentReason());
		data.put("sequenceId",String.valueOf(dto.getOrderId()));
		data.put("appDate", formatEmailDate(dto.getCreateDate()));
		data.put("memberType", String.valueOf(dto.getPayeeMemberType()));
		
		String mail = dto.getPayeeLoginName();
		StringBuffer sb = new StringBuffer();
		int pa = mail.indexOf("@");
		int pb = mail.indexOf(".", pa);
		sb.append(mail.substring(0, (pa < 3 ? pa : 3))).append("*").append(mail.substring(pa, pb));
		data.put("payeeLoginName", sb.toString());
		
		NotifyTargetRequest request = new NotifyTargetRequest();
		request.setData(data);
		request.setSubject(subject);
		request.setNotifyType(RequestType.EMAIL.value());
		request.setFromAddress(Constants.SYSTEM_MAIL);
		request.getRecAddress().add(dto.getPayeeLoginName());
		request.setTemplateId(tempId);
		request.setRequestTime(new Date());
		notifyFacadeService.notifyRequest(request);
		
	}
	
	/**
	 * 给付款方发送短信
	 * @param dto
	 */
	private void notifyPayeeSMS(PayToAcctOrderDTO dto,long tmpId,String subject){
		
		Map<String, String> data = new HashMap<String, String>();
		
		data.put("payeeName", dto.getPayeeName());
		data.put("payerName", dto.getPayerName());
		data.put("payeeMember", dto.getPayeeMemberCode().toString());
		data.put("amount", AmountUtils.numberFormat(dto.getOrderAmount()));
		data.put("fee", AmountUtils.numberFormat(0L));
		data.put("remarks", dto.getPaymentReason());
		data.put("sequenceId",String.valueOf(dto.getOrderId()));
		data.put("appDate", formatEmailDate(dto.getCreateDate()));
		
		String phone = dto.getPayeeLoginName();
		StringBuffer sb = new StringBuffer();
		sb.append(phone.substring(0, 2)).append("*").append(phone.substring(phone.length() - 3, phone.length()));
		data.put("payeeLoginName", sb.toString());
		
		NotifyTargetRequest request = new NotifyTargetRequest();
		request.getMobiles().add(dto.getPayeeLoginName());
		request.setNotifyType(RequestType.SMS.value());
		request.setSubject(subject);
		request.setData(data);
		request.setTemplateId(tmpId);
		request.setRequestTime(new Date());
		notifyFacadeService.notifyRequest(request);
	}
	
	

	

}
