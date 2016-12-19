/**
 * 
 */
package com.pay.fo.order.service.withholding.callback;

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
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class AcctWithholdingOrderCallbackServiceImpl extends
		AbstractOrderCallbackService {
	
	/**
	 * 付款到账户订单处理服务类
	 */
	private PayToAcctOrderProcessService payToAcctOrderProcessService;
	
	
	/**
	 * 付款方短信通知模板
	 */
	private Long payerSmsTemplateId;
	
	
	
	
	

	/**
	 * @param payToAcctOrderProcessService the payToAcctOrderProcessService to set
	 */
	public void setPayToAcctOrderProcessService(
			PayToAcctOrderProcessService payToAcctOrderProcessService) {
		this.payToAcctOrderProcessService = payToAcctOrderProcessService;
	}




	public void setPayerSmsTemplateId(Long payerSmsTemplateId) {
		this.payerSmsTemplateId = payerSmsTemplateId;
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
		
		if(StringUtil.isNull(dto.getPayerMobile())){//付款方手机号为空时不发送消息
			log.error("payer mobile is null,can't send message");
			return;
		}
		if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.WITHHOLDING_PAY2ACCT_SUCCESS.getValue())){
			notifyPayerSMS(dto, payerSmsTemplateId, "账户代扣成功");
		}

	}

	@Override
	protected AccountingDto buildAccountingDto(Order order) {
		PayToAcctOrderDTO dto = init(order);
		
		AccountingDto acctDto = null;
		
		if(dto.getProcessType().getValue().equalsIgnoreCase(OrderProcessType.WITHHOLDING_PAY2ACCT_SUCCESS.getValue())){
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
	 * 给付款方发送短信
	 * @param dto
	 */
	private void notifyPayerSMS(PayToAcctOrderDTO dto,long tmpId,String subject){
		
		Map<String, String> data = new HashMap<String, String>();
		
		data.put("amount", AmountUtils.numberFormat(dto.getOrderAmount()));
		data.put("commplateDate", formatEmailDate(dto.getUpdateDate()));
		
		data.put("payerLoginName", dto.getPayerLoginName());
		
		NotifyTargetRequest request = new NotifyTargetRequest();
		request.getMobiles().add(String.valueOf(dto.getPayerMobile()));
		request.setNotifyType(RequestType.SMS.value());
		request.setSubject(subject);
		request.setData(data);
		request.setTemplateId(tmpId);
		request.setRequestTime(new Date());
		notifyFacadeService.notifyRequest(request);
	}
	
	

	

}
