/**
 *Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.base.service.external.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.checkcode.impl.SendMail;
import com.pay.acc.comm.Resource;
import com.pay.base.common.enums.ExternalProcessStatus;
import com.pay.base.common.enums.ExternalTypeEnum;
import com.pay.base.common.enums.ProcessStatus;
import com.pay.base.dao.external.ExternalLogDao;
import com.pay.base.dao.member.MemberDAO;
import com.pay.base.dao.paychain.PayChainDAO;
import com.pay.base.dto.PayChainPayInfo;
import com.pay.base.model.ContextPicture;
import com.pay.base.model.ExternalLog;
import com.pay.base.model.Member;
import com.pay.base.model.PayChain;
import com.pay.base.service.contextPic.ContextPicService;
import com.pay.base.service.external.ExternalNoticeService;
import com.pay.base.service.paychain.PayChainPayInfoService;

/**
 * @author fjl
 * @date 2011-9-20
 */
public class ExternalNoticeServiceImpl implements ExternalNoticeService {

	private SendMail sendMailManager;

	private ExternalLogDao externalLogDao;

	private PayChainDAO payChainDao;
	
	private ContextPicService  contextPicService;

	// 获取企业信息
	private MemberDAO memberDAO;

	private PayChainPayInfoService payChainPayInfoService;

	private long templateId = Resource.TEMPLATEID_EMAIL_PAYCHAIN;
	
	private Log log = LogFactory.getLog(ExternalNoticeServiceImpl.class);

	@Override
	public boolean sendNotice(Long externalId) {
		
		ExternalLog bean = externalLogDao.findById(externalId);
		if(bean != null && bean.getProcessStatus().intValue() == ProcessStatus.PROCESS_SUCCED.getValue()
				&& bean.getExternalProcessStatus() == ExternalProcessStatus.EXTERNAL_PROCESS_SUCCED.getValue()
				&& bean.getExternalType().intValue() == ExternalTypeEnum.PAY_CHAIN.getType()){
			
			log.info("支付链付款成功后发送邮件...externalId=" + externalId);
			PayChain payChain = payChainDao.getPayChin(bean.getCardNo());
			if(payChain != null){
				long memberCode = payChain.getMemberCode() == null ? 0L : payChain.getMemberCode().longValue();
				
				Member member = memberDAO.findMemberByMemCode(memberCode);
				if(member != null && member.getType()== 2){
					
					PayChainPayInfo payInfo = payChainPayInfoService.get(payChain);
					
					payInfo.setOrderNo(bean.getOrderNo());
					payInfo.setAmount(amountFormat(bean.getAmount().divide(BigDecimal.valueOf(1000))));
					payInfo.setPayerName(bean.getPayerName());
					payInfo.setPayDesc(bean.getRemark());
					
					List<ContextPicture>  picList=contextPicService.queryPicListByOwnerId(payChain.getPayChainId());
					payInfo.setPicList(picList);
					
					List<String> email = new ArrayList<String>(1);
					email.add(bean.getPayerContact());
					return sendMailManager.sendMail(email, Resource.MAIL_PAYCHAIN_SUBJECT, templateId, payInfo.toMap());
				}else if(member != null && member.getType()== 1){
					//个人会员不支持，支付链目前
					log.info("支付链面向企业用户，个人用户未开通邮件通知！");
				}
			}	
		}

		return false;
	}

	private String amountFormat(BigDecimal d) {
		d = d == null ? new BigDecimal(0) : d;
		DecimalFormat df = new DecimalFormat("###.00");
		return df.format(d);
	}

	public void setSendMailManager(SendMail sendMailManager) {
		this.sendMailManager = sendMailManager;
	}

	public void setExternalLogDao(ExternalLogDao externalLogDao) {
		this.externalLogDao = externalLogDao;
	}

	public void setPayChainDao(PayChainDAO payChainDao) {
		this.payChainDao = payChainDao;
	}

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setPayChainPayInfoService(
			PayChainPayInfoService payChainPayInfoService) {
		this.payChainPayInfoService = payChainPayInfoService;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public void setContextPicService(ContextPicService contextPicService) {
		this.contextPicService = contextPicService;
	}
	
	

}
