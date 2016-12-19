/** @Description 
 * @project 	poss-withdraw
 * @file 		WithdrawCallBack.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-27		Rick_lv			Create 
 */
package com.pay.fundout.withdraw.service.order.ordercallback.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.service.order.WithdrawOrderBusiType;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.jms.notification.request.RequestType;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl;
import com.pay.poss.service.inf.input.BankInfoFacadeService;
import com.pay.poss.service.ma.member.WithdrawMemberFacadeService;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.poss.service.withdraw.orderhandler.app.output.OrderProcessHandler;
import com.pay.util.StringUtil;


/**
 * <p>
 * 提现订单回调服务
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-27
 * @see
 */
public class WithdrawOrderCallBack extends AbstractOrderCallBackServiceImpl {

	private BankInfoFacadeService bankInfoFacadeService;
	private WithdrawMemberFacadeService withdrawMemberFacadeService;
	
	private NotifyFacadeService notifyFacadeService;

	// 提现订单服务
	private WithdrawOrderService withdrawOrderService;
	// 订单后处理服务
	private OrderProcessHandler orderProcessHandler;
	private Long smsTemplateId;
	
	private Map<Long,Long> successNotifyEmailTemplateIds;
	private Map<Long,Long> successNotifySmsTemplateIds;
	private Map<Long,Long> failNotifyEmailTemplateIds;
	private Map<Long,Long> failNotifySmsTemplateIds;
	
	public void setBankInfoFacadeService(BankInfoFacadeService bankInfoFacadeService) {
		this.bankInfoFacadeService = bankInfoFacadeService;
	}

	public void setSuccessNotifyEmailTemplateIds(
			Map<Long, Long> successNotifyEmailTemplateIds) {
		this.successNotifyEmailTemplateIds = successNotifyEmailTemplateIds;
	}


	public void setSuccessNotifySmsTemplateIds(
			Map<Long, Long> successNotifySmsTemplateIds) {
		this.successNotifySmsTemplateIds = successNotifySmsTemplateIds;
	}


	public void setFailNotifyEmailTemplateIds(
			Map<Long, Long> failNotifyEmailTemplateIds) {
		this.failNotifyEmailTemplateIds = failNotifyEmailTemplateIds;
	}


	public void setFailNotifySmsTemplateIds(Map<Long, Long> failNotifySmsTemplateIds) {
		this.failNotifySmsTemplateIds = failNotifySmsTemplateIds;
	}


	@Override
	protected OrderFailProcAlertModel buildAlertInfo(HandlerParam param) {
		WithdrawOrderAppDTO order = (WithdrawOrderAppDTO) param.getBaseOrderDto();
		OrderFailProcAlertModel result = new OrderFailProcAlertModel();
		result.setOrderSeq(order.getSequenceId());
		result.setOrderStatus(order.getStatus().intValue());
		result.setFailReason(order.getErrorMessage());
		
		String appFrom = "withdraw";
		if(WithdrawOrderBusiType.PAY2BANK.getCode()==order.getBusiType().intValue()){
			appFrom = "Pay2Bank";
		}
		result.setAppFrom(appFrom);
		result.setUpdateTime(new Date());
		return result;
	}

	@Override
	protected BackFundmentOrder buildBackOrder(HandlerParam param) {
		// 退款采用红冲方式
		WithdrawOrderAppDTO order = (WithdrawOrderAppDTO) param.getBaseOrderDto();
		BackFundmentOrder backOrder = new BackFundmentOrder();

		backOrder.setUpdateTime(new Date());
		backOrder.setSequenceSrc(order.getSequenceId());
		backOrder.setTimeSrc(order.getUpdateTime());
		backOrder.setAmountSrc(new BigDecimal(order.getAmount()));
		backOrder.setFeeSrc(new BigDecimal(order.getFee()));
		

		backOrder.setPayerMember(order.getMemberCode());
		backOrder.setPayerAcctType(order.getMemberAccType().intValue());
		backOrder.setPayerAcctCode(order.getMemberAcc());
		String fromCode = "withdraw";
		String payeeAcctCode = "2181010010001";
		if(WithdrawOrderBusiType.PAY2BANK.getCode()==order.getBusiType().intValue()){
			fromCode = "Pay2Bank";
			payeeAcctCode = "2181010010002";
		}
		backOrder.setFromCode(fromCode);
		backOrder.setPayeeAcctCode(payeeAcctCode);
		// FIXME 有溢出问题？
		backOrder.setAppAmount(new BigDecimal(order.getAmount() * (-1)));
		backOrder.setAppFee(new BigDecimal(Math.abs(order.getFee())*-1));

		backOrder.setAppType(param.getWithdrawBusinessType());

		return backOrder;
	}

	@Override
	protected boolean changeOrderStatus(HandlerParam param) {
		WithdrawOrderAppDTO order = (WithdrawOrderAppDTO) param.getBaseOrderDto();
		order.setUpdateTime(new Date());

		// 更新订单状态
		try {
			return withdrawOrderService.updateWithdrawOrder(order);
		} catch (Exception e) {
			log.error("更新订单状态失败,原订单信息 [" + order + "]", e);
			return false;
		}

	}

	@Override
	protected void doCancelAccounting(BackFundmentOrder backFundOrder) throws PossException {
		backFundingInnerService.processCancelOrderRnTx(backFundOrder);
	}

	@Override
	public void notify(HandlerParam param) {
		WithdrawOrderAppDTO withdrawOrderAppDto = (WithdrawOrderAppDTO) param.getBaseOrderDto();
			
		withdrawOrderAppDto = withdrawOrderService.getWithdrawOrder(withdrawOrderAppDto.getSequenceId());
		HashMap<String, String> paramMap = new HashMap<String, String>(2);
		if(withdrawOrderAppDto.getOrderSeqId()!=null){
			paramMap.put("orderSeqId", withdrawOrderAppDto.getOrderSeqId().toString());
			paramMap.put("status", withdrawOrderAppDto.getStatus().toString());
			orderProcessHandler.processHandler(paramMap);
		}else{
			
			try{
	
					/**
					 * 发送邮件/短信通知
					 */
					notifyPayerMessage(withdrawOrderAppDto);
		
			}catch(Exception e){
				log.error("发送邮件/短信失败");
			}

		}
		
	}

	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	public void setOrderProcessHandler(OrderProcessHandler orderProcessHandler) {
		this.orderProcessHandler = orderProcessHandler;
	}
	
	public void setSmsTemplateId(Long smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}

	/**
	 * 给付款方发送消息
	 * @param dto
	 * @throws MaMemberQueryException 
	 */
	private void notifyPayerMessage(WithdrawOrderAppDTO dto)  {
		String msg = "";
		MemberInfoDto memberInfoDto = withdrawMemberFacadeService.qyeryMember(dto.getMemberCode());
		

		String userId=withdrawMemberFacadeService.qyeryMember(dto.getMemberCode()).getLoginName();
		if(StringUtil.isNull(userId)){//收款方用户名为空时不发送消息
			log.info("payer userId is null,can't send message");
			return;
		}
		
		long tmpId = 0;
		if(WithdrawOrderBusiType.WITHDRAW.getCode()==dto.getBusiType().intValue()){
			//发提现的通知
			if(isEmail(userId)){
				
				if(dto.getStatus()==112){
					//tmpId =103;
					tmpId = failNotifyEmailTemplateIds.get(dto.getBusiType());
					msg = "您的申请提现处理失败！";
				}else if(dto.getStatus()==111){
					//tmpId =102;
					tmpId = successNotifyEmailTemplateIds.get(dto.getBusiType());
					msg = "您的申请提现处理成功！";
				}
				notifyPayerEmail(dto,userId,msg,tmpId,memberInfoDto);
			}else{
				
				if(dto.getStatus()==112){
					//tmpId =105;
					tmpId = failNotifySmsTemplateIds.get(dto.getBusiType());
					msg = "提现失败";
				}else if(dto.getStatus()==111){
					//tmpId =104;
					tmpId = successNotifySmsTemplateIds.get(dto.getBusiType());
					msg = "提现成功";
				}
				notifyPayerSMS(dto,userId,msg,tmpId);
			}
		}else if(WithdrawOrderBusiType.PAY2BANK.getCode()==dto.getBusiType().intValue()){
			
			if(dto.getStatus()==111 && !StringUtil.isNull(dto.getPayeeMobile())){
				Map<String, String> data = new HashMap<String, String>();
				String bankName = bankInfoFacadeService.getBankNameById(dto.getBankKy());
				data.put("payerName",memberInfoDto.getMemberName());
				data.put("bankName",bankName);
				data.put("amount", numberFormat(toBigDecimalAmount(dto.getAmount())));
				String bankAcc = getRequireBankAccount(dto.getBankAcct());
				data.put("bankAcc", bankAcc);
				NotifyTargetRequest request = new NotifyTargetRequest();
				request.getMobiles().add(dto.getPayeeMobile());
				request.setNotifyType(RequestType.SMS.value());
				request.setSubject("付款到银行收款方短息通知");
				request.setData(data);
				request.setTemplateId(smsTemplateId);
				request.setRequestTime(new Date());
				notifyFacadeService.notifyRequest(request);
			}
			
			//发送付款到银行
			if(isEmail(userId)){
				if(dto.getStatus()==111){
					//tmpId =31;
					tmpId = successNotifyEmailTemplateIds.get(dto.getBusiType());
					msg="您的申请付款到银行处理成功！";
					
				}else if(dto.getStatus()==112){
					//tmpId =1014;
					tmpId = failNotifyEmailTemplateIds.get(dto.getBusiType());
					msg="您的申请付款到银行处理失败！";
				}
				notifyPayerEmail(dto,userId,msg,tmpId,memberInfoDto);
				
			}else{
				if(dto.getStatus()==111){
					//tmpId =21;
					tmpId = successNotifySmsTemplateIds.get(dto.getBusiType());
					msg="付款到银行卡-付款成功";	
				}else if(dto.getStatus()==112){
					//tmpId =1008;
					tmpId = failNotifySmsTemplateIds.get(dto.getBusiType());
					msg="付款到银行卡-付款失败";
				}
				notifyPayerSMS(dto,userId,msg,tmpId);
				
			}
		}
	}
	
	
	/**
	 * 验证是否是Email
	 * @param userId
	 * @return
	 */
	private boolean isEmail(String userId){
		if(StringUtil.null2String(userId).indexOf("@")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 付款申请提交成功(给付款方发送短信)
	 * @param dto
	 */
	private void notifyPayerSMS(WithdrawOrderAppDTO dto,String mobile,String msg,long tmpId){
		
		
		Map<String, String> data = new HashMap<String, String>();
		String bankName = bankInfoFacadeService.getBankNameById(dto.getBankKy());
		String accountName = dto.getAccountName();
		data.put("bankUserName",accountName);
		data.put("bankName",bankName);
		data.put("appDate", formatSMSDate(dto.getCreateTime()));
		data.put("paymentAmount", numberFormat(toBigDecimalAmount(dto.getAmount())));
		String bankAcc = getRequireBankAccount(dto.getBankAcct());
		data.put("bankAcc", bankAcc);
		NotifyTargetRequest request = new NotifyTargetRequest();
		request.getMobiles().add(mobile);
		request.setNotifyType(RequestType.SMS.value());
		request.setSubject(msg);
		request.setData(data);
		request.setTemplateId(tmpId);
		request.setRequestTime(new Date());
		notifyFacadeService.notifyRequest(request);
	}
	
	
	/**
	 * 付款申请提交成功(给付款方发送Email)
	 * @param dto
	 */
	private void notifyPayerEmail(WithdrawOrderAppDTO dto,String email,String msg,long tmpId,MemberInfoDto memberInfoDto){
		
		Map<String, String> data = new HashMap<String, String>();
		String accountName = dto.getAccountName();
		String bankName = bankInfoFacadeService.getBankNameById(dto.getBankKy());
		
		data.put("bankUserName",accountName);
		data.put("bankName", bankName);
		data.put("payerName", memberInfoDto.getMemberName());
		data.put("appDate", formatEmailDate(new Date()));
		data.put("paymentAmount", numberFormat(toBigDecimalAmount(dto.getAmount())));
		/**
		 * 增加会员类型1个人2企业
		 */
		data.put("memberType", String.valueOf(memberInfoDto.getMemberType()));
		
		String bankAcc = getRequireBankAccount(dto.getBankAcct());
		data.put("bankAcc", bankAcc);
		NotifyTargetRequest request = new NotifyTargetRequest();
		request.setData(data);
		request.setNotifyType(RequestType.EMAIL.value());
		request.setSubject(msg);
		request.setFromAddress(Constants.SYSTEM_MAIL);
		request.getRecAddress().add(email);
		request.setTemplateId(tmpId);
		request.setRequestTime(new Date());
		notifyFacadeService.notifyRequest(request);
		
	}
	
	/**
	 * 金额转BigDecimal
	 * @param amount
	 * @return
	 */
	protected BigDecimal toBigDecimalAmount(Long amount){
		Long tmpAmount = amount;
		if (tmpAmount == null) {
			tmpAmount = 0L;
		}
		return new BigDecimal(tmpAmount).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP);

	}
	
	private static String numberFormat(BigDecimal num){
		 NumberFormat formatter   =   new   DecimalFormat( "#,###,###.##"); 
		 if(num==null){
			 return "NULL";
		 }
		 return formatter.format(num.doubleValue());
	}
	
	
	private String getRequireBankAccount(String bankAccount){
		if(bankAccount.length()<=4){
			return bankAccount;
		}
		return  bankAccount.substring(bankAccount.length()-4);
	}
	/**
	 * 格式化SMS日期
	 * @param date
	 * @return
	 */
	private static String formatSMSDate(Date date){
		DateFormat df = new SimpleDateFormat("MM月dd日HH:mm:ss");
		return df.format(date);
	}
	/**
	 * 格式化Email日期
	 * @param date
	 * @return
	 */
	private static String formatEmailDate(Date date){
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
		return df.format(date);
	}

	public void setWithdrawMemberFacadeService(
			WithdrawMemberFacadeService withdrawMemberFacadeService) {
		this.withdrawMemberFacadeService = withdrawMemberFacadeService;
	}

	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

}
