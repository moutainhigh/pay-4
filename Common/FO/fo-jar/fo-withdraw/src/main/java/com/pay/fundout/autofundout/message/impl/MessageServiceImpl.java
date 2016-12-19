package com.pay.fundout.autofundout.message.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fundout.autofundout.buildorder.service.impl.BulidEnterpriseWithdrawServiceImpl;
import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.fundout.autofundout.custom.model.AutofundoutMail;
import com.pay.fundout.autofundout.message.MessageService;
import com.pay.fundout.autofundout.processor.util.AutoFundoutType;
import com.pay.jms.notification.request.RequestType;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;

public class MessageServiceImpl implements MessageService {
	
	NotifyFacadeService  notifyFacadeService;
	
	private MemberQueryService memberQueryService;
	
	/**
	 * @param memberQueryService the memberQueryService to set
	 */
	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}



	private Long failEmailTemplateId;
	
	private Long successTemplateId;
	
	private String queueName;
	
	/**
	 * @param queueName the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	/**
	 * @param successTemplateId the successTemplateId to set
	 */
	public void setSuccessTemplateId(Long successTemplateId) {
		this.successTemplateId = successTemplateId;
	}
	/**
	 * @param failEmailTemplateId the failEmailTemplateId to set
	 */
	public void setFailEmailTemplateId(Long failEmailTemplateId) {
		this.failEmailTemplateId = failEmailTemplateId;
	}
	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	/**
	 * 发送邮件
	 * @param autofundoutMail
	 */
	private void sendMail(AutofundoutMail  autofundoutMail){
		Map<String, String> data = new HashMap<String, String>();
		data.put("payeeName",autofundoutMail.getPayeeName());
		data.put("autoType",autofundoutMail.getAutoType());
		data.put("createTime",autofundoutMail.getCreateTime());
		data.put("amount",autofundoutMail.getAmount());
		data.put("failReason",autofundoutMail.getFailReason());
		NotifyTargetRequest req = new NotifyTargetRequest();
		req.setData(data);
		req.setNotifyType(RequestType.EMAIL.value());
		req.setFromAddress(Constants.SYSTEM_MAIL);
		req.setSubject(autofundoutMail.getSubject());
		req.getRecAddress().add(autofundoutMail.getEmailAddress());
		req.setTemplateId(autofundoutMail.getTemplateId().longValue());
		req.setRequestTime(new Date());
		notifyFacadeService.notifyRequest(req);
	}

	

	@Override
	public void sendFailEmail(AutoFundoutResult param) {

		AutofundoutMail mail = new AutofundoutMail();
		if(param.getAmount()!=null){
		String amountStr = new BigDecimal(param.getAmount()).divide(new BigDecimal(1000),
				2, BigDecimal.ROUND_DOWN).toString();
		mail.setAmount(amountStr);
		}else{
			mail.setAmount("0.00");
		}
		if (AutoFundoutType.AUTO_TIME.getCode() == param.getAutoType()
				.intValue()) {
			mail.setAutoType(AutoFundoutType.AUTO_TIME.getDesc());
		} else if (AutoFundoutType.AUTO_QUOTA.getCode() == param.getAutoType()
				.intValue()) {
			mail.setAutoType(AutoFundoutType.AUTO_QUOTA.getDesc());
		} else {
			mail.setAutoType("未知");
		}
		mail.setCreateTime(DateUtil
				.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
		mail.setFailReason(param.getErrorDesc());
		mail.setSubject(param.getTitle());
		mail.setTemplateId(failEmailTemplateId);

		try {
			MemberInfoDto memberInfo = this.memberQueryService
					.doQueryMemberInfoNsTx(null, param.getAutoFundoutConfig()
							.getMemberCode(), null, null);
			mail.setEmailAddress(memberInfo.getLoginName());
			mail.setPayeeName(memberInfo.getMemberName());
		} catch (MaMemberQueryException e) {
			LogUtil.error(MessageServiceImpl.class,
					"获取会员邮箱地址失败！邮件发送失败!", OPSTATUS.FAIL, param
							.getAutoFundoutConfig().getMemberCode().toString(),
					"[memberCode="
							+ param.getAutoFundoutConfig().getMemberCode()
							+ "&memberType="
							+ param.getAutoFundoutConfig().getMemberType()
							+ "&bankName="
							+ param.getAutoFundoutConfig().getBankName()
							+ "&bankCode="
							+ param.getAutoFundoutConfig().getBankCode(), e
							.getMessage(), "", e.getMessage());
		}

		sendMail(mail);
	}
	@Override
	public void sendScuEmail(AutoFundoutResult param){
		AutofundoutMail mail = new AutofundoutMail();
		String amountStr = new BigDecimal(param.getAmount()).divide(new BigDecimal(1000),2,BigDecimal.ROUND_DOWN).toString();
		mail.setAmount(amountStr);
		if(AutoFundoutType.AUTO_TIME.getCode() == param.getAutoType().intValue()){
			mail.setAutoType(AutoFundoutType.AUTO_TIME.getDesc());
		}else if(AutoFundoutType.AUTO_QUOTA.getCode() == param.getAutoType().intValue()){
			mail.setAutoType(AutoFundoutType.AUTO_QUOTA.getDesc());
		}else{
			mail.setAutoType("未知");
		}
		mail.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
		mail.setFailReason(null);
		mail.setSubject("委托提现成功");
		mail.setTemplateId(successTemplateId);
		
		try {
			MemberInfoDto memberInfo = this.memberQueryService.doQueryMemberInfoNsTx(null,param.getAutoFundoutConfig().getMemberCode(),null,null);
			mail.setEmailAddress(memberInfo.getLoginName());
			mail.setPayeeName(memberInfo.getMemberName());
		} catch (MaMemberQueryException e) {
			LogUtil.error(this.getClass(),"获取会员邮箱地址失败！邮件发送失败!",OPSTATUS.FAIL,param.getAutoFundoutConfig().getMemberCode().toString(),
					"[memberCode=" + param.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
					param.getAutoFundoutConfig().getMemberType() + "&bankName=" + param.getAutoFundoutConfig().getBankName() + 
					"&bankCode=" + param.getAutoFundoutConfig().getBankCode(),e.getMessage(),"",e.getMessage());
		}
		
		sendMail(mail);
	}
	
	
	//发送消息
	public void sendMessage(FundoutOrderDTO fundoutOrderDTO){
		String jsonStr = JSonUtil.toJSonString(fundoutOrderDTO.getOrderId());
		try{
			notifyFacadeService.sendRequest(buildNotify2QueueRequest(jsonStr));
		}catch(Exception e){
			LogUtil.error(BulidEnterpriseWithdrawServiceImpl.class,"发送消息失败!",OPSTATUS.FAIL,
					fundoutOrderDTO.getOrderId().toString(),"[memberCode=" + fundoutOrderDTO.getPayerMemberCode() + "&memberType=" + 
					fundoutOrderDTO.getPayerMemberType() + "&accountName=" + fundoutOrderDTO.getPayeeName() + 
					"&bankCode=" + fundoutOrderDTO.getPayeeBankCode(),e.getMessage(),"",e.getMessage());
		}
	}
	
	//构建对象产生
	private Notify2QueueRequest buildNotify2QueueRequest(String jsonStr) {
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(jsonStr);
		return request;
	}
	
}
