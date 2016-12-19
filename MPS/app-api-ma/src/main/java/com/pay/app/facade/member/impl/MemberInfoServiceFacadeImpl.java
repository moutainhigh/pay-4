package com.pay.app.facade.member.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.util.StringUtil;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.app.facade.dto.MemberCriteria;
import com.pay.app.facade.dto.MemberRegisterBO;
import com.pay.app.facade.dto.PowersModel;
import com.pay.app.facade.dto.ResultDto;
import com.pay.app.facade.member.MemberInfoServiceFacade;
import com.pay.app.facade.powers.PowersListService;

/**
 * File: MemberInfoServiceFacadeImpl.java Description: Copyright 2006-2010 pay
 * Corporation. All rights reserved. Date 2010-8-18 Author zengjin Changes
 * Comment 会员信息相关
 */
public class MemberInfoServiceFacadeImpl implements MemberInfoServiceFacade {
	private static final Log log = LogFactory
			.getLog(MemberInfoServiceFacadeImpl.class);

	

	private final static String REGTYPE_MOBILE = "1";

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.app.facade.member.impl.MemberInfoServiceFacade#
	 * queryMemberInfoByUserId(java.lang.String)
	 */
	public MemberCriteria queryMemberInfoByUserId(final String userId) {
		
		MemberCriteria memberInfo = null;
//		try {
//			memberBaseInfoBO = memberAppInfoService.queryMemberBaseInfo(userId);
//			memberInfo = new MemberCriteria();
//			memberInfo.setMemberCode(memberBaseInfoBO.getMemberCode() == null
//					? null
//					: memberBaseInfoBO.getMemberCode().toString());
//			memberInfo.setStatus(memberBaseInfoBO.getStatus() == null
//					? null
//					: memberBaseInfoBO.getStatus().toString());
//			memberInfo.setVerifyName(memberBaseInfoBO.getName());
//			memberInfo.setSalutatory(transferWelcomeMsg(memberBaseInfoBO
//					.getGreeting()));
//			memberInfo.setWelcomeMsg(memberBaseInfoBO.getGreeting());
//			memberInfo.setAnswer(memberBaseInfoBO.getSecurityAnswer());
//			memberInfo.setQuestionId(memberBaseInfoBO.getSecurityQuestion());
//			memberInfo.setUpdateDate(memberBaseInfoBO.getUpdateDate());
//		} catch (MaMemberQueryException e) {
//			log.error("memberAppInfoService.queryMemberBaseInfo throws error",
//					e);
//		}

		return memberInfo;
	}

	public MemberCriteria queryMemberInfoByMemberCode(final Long memberCode) {
		//MemberBaseInfoBO memberBaseInfoBO = null;
		MemberCriteria memberInfo = null;
//		try {
//			memberBaseInfoBO = memberQueryService
//					.queryMemberBaseInfoByMemberCode(memberCode);
//			// memberAppInfoService.queryMemberBaseInfoByMemberCode(memberCode);
//			memberInfo = new MemberCriteria();
//			memberInfo.setMemberCode(memberBaseInfoBO.getMemberCode() == null
//					? null
//					: memberBaseInfoBO.getMemberCode().toString());
//			memberInfo.setStatus(memberBaseInfoBO.getStatus() == null
//					? null
//					: memberBaseInfoBO.getStatus().toString());
//			memberInfo.setVerifyName(memberBaseInfoBO.getName());
//			memberInfo.setSalutatory(transferWelcomeMsg(memberBaseInfoBO
//					.getGreeting()));
//			memberInfo.setWelcomeMsg(memberBaseInfoBO.getGreeting());
//			memberInfo.setAnswer(memberBaseInfoBO.getSecurityAnswer());
//			memberInfo.setQuestionId(memberBaseInfoBO.getSecurityQuestion());
//			memberInfo.setUpdateDate(memberBaseInfoBO.getUpdateDate());
//			memberInfo.setRegType(memberBaseInfoBO.getRegType());
//			memberInfo.setLoginName(memberBaseInfoBO.getLoginName());
//			memberInfo.setCreateDate(memberBaseInfoBO.getCreationDate());
//		} catch (MaMemberQueryException e) {
//			log
//					.error(
//							"memberAppInfoService.queryMemberBaseInfoByMemberCode throws error",
//							e);
//		}

		return memberInfo;
	}

	public MemberCriteria queryMallMemberInfoByMemberCode(final Long memberCode) {
		//MemberRegisterBO memberRegInfoBO = null;
		MemberCriteria memberInfo = null;
//		try {
//			memberRegInfoBO = memberQueryService
//					.queryMemberInfoByMemberCode(memberCode);
//			memberInfo = new MemberCriteria();
//			memberInfo.setMemberCode(memberRegInfoBO.getMemberCode() == null
//					? null
//					: memberRegInfoBO.getMemberCode().toString());
//			memberInfo.setVerifyName(memberRegInfoBO.getRealName());
//			memberInfo.setUserId(memberRegInfoBO.getUserId());
//			memberInfo.setSalutatory(transferWelcomeMsg(memberRegInfoBO
//					.getGreeting()));
//			memberInfo.setWelcomeMsg(memberRegInfoBO.getGreeting());
//			memberInfo.setAnswer(memberRegInfoBO.getSecurityAnswer());
//			memberInfo.setQuestionId(memberRegInfoBO.getSecurityQuestion());
//			memberInfo.setRegType(memberRegInfoBO.getRegType());
//			memberInfo.setLoginName(memberRegInfoBO.getLoginName());
//			memberInfo.setContact(memberRegInfoBO.getContact());
//			memberInfo.setAddress(memberRegInfoBO.getAddress());
//			memberInfo.setAptoticPhone(memberRegInfoBO.getTel());
//			memberInfo.setCardNo(memberRegInfoBO.getCertificateNo());
//			memberInfo.setCardType(new Integer(memberRegInfoBO
//					.getCertificateType() == null ? 1 : new Integer(
//					memberRegInfoBO.getCertificateType())));
//			memberInfo.setFaxes(memberRegInfoBO.getFax());
//			memberInfo.setMsn(memberRegInfoBO.getMsn());
//			memberInfo.setProvince(memberRegInfoBO.getProvince());
//			memberInfo.setCity(memberRegInfoBO.getCity());
//			memberInfo.setQuestion(memberRegInfoBO.getSecurityQuestion());
//			memberInfo.setQq(memberRegInfoBO.getQq());
//			memberInfo.setPostCode(memberRegInfoBO.getZip());
//			memberInfo.setCreateDate(memberRegInfoBO.getCreationDate());
//		} catch (MaMemberQueryException e) {
//			log
//					.error(
//							"memberAppInfoService.queryMemberBaseInfoByMemberCode throws error",
//							e);
//		}

		return memberInfo;
	}

	public ResultDto memberLogin(String loginName, String passWord) {
		MemberCriteria memberInfo = null;
		ResultDto resultDto = new ResultDto();
		// MemberBaseInfoBO memberBaseInfoBO = null;
		Long memberCode = null;
//		try {
//			memberCode = memberValidateService.doValidateLoginNsTx(loginName,
//					passWord);
//			// memberBaseInfoBO=this.doLoginMemberTest(loginName, passWord);
//			if (memberCode != null) {
//				memberInfo = this.queryMemberInfoByMemberCode(memberCode);
//				if (memberInfo != null) {
//					resultDto.setMemerCode(memberCode.toString());
//					resultDto.setObject(memberInfo);
//				} else {
//					resultDto.setErrorCode("0003");
//					resultDto.setErrorMsg(MessageConvertFactory
//							.getMessage("loginError"));
//				}
//			} else {
//				resultDto.setErrorCode("0003");
//				resultDto.setErrorMsg(MessageConvertFactory
//						.getMessage("loginError"));
//			}
//		} catch (MaRegisterException e) {
//			log.error("memberValidateService.doLoginMemberNsTxthrows error", e);
//			resultDto.setErrorCode("0001");
//			resultDto.setErrorMsg(e.getErrorEnum().getMessage());
//		}

		return resultDto;
	}

	public boolean checkLoginName(String regType, String loginName) {
		boolean flag = true;
//		try {
//			if (REGTYPE_MOBILE.equals(regType)) {
//				flag = memberValidateService.doCheckMemberMobileNsTx(loginName);
//			} else {
//				flag = memberValidateService.doCheckMemberEmailNsTx(loginName);
//			}
//		} catch (MaRegisterException e) {
//			log.error("memberValidateService.doCheckMember throws error", e);
//		}

		return flag;

	}

	public ResultDto doMemberRegister(MemberRegisterBO memberRegisterBO) {
		Long memberCode = 1000000009L;
		ResultDto resultDto = new ResultDto();
//		try {
//			memberCode = memberRegisterService
//					.doMemberRegisterRnTx(memberRegisterBO);
//			if (memberCode != null) {
//				resultDto.setMemerCode(String.valueOf(memberCode));
//			} else {
//				resultDto.setErrorCode("0001");
//				resultDto.setErrorMsg("memberCode is null");
//			}
//		} catch (MaRegisterException e) {
//			log.error(
//					"memberRegisterService.doMemberRegisterRnTx throws error",
//					e);
//			resultDto.setErrorCode("0002");
//			resultDto.setErrorMsg(e.getErrorEnum().getMessage());
//
//		}
		return resultDto;
	}

	public boolean doCheckMemberEmail(String email) {
//		try {
//			return memberValidateService.doCheckMemberEmailNsTx(email);
//		} catch (MaRegisterException e) {
//			log.error("memberValidateService.doCheckMemberEmailNsTx error", e);
//		}
		return false;
	}

	public boolean doCheckMemberMobileNsTx(String mobile) {
//		try {
//			return memberValidateService.doCheckMemberMobileNsTx(mobile);
//		} catch (MaRegisterException e) {
//			log.error("memberValidateService.doCheckMemberMobileNsTx error", e);
//		}
		return false;
	}

	
	




	@Override
	public Long queryMemberCodeByUserName(String username) {
		// TODO Auto-generated method stub
//		try {
//			//return memberQueryService.doQueryMemberCodeByLoginName(username);
//		} catch (MaMemberQueryException e) {
//			// TODO Auto-generated catch block
//			log.error("[queryMemberCodeByUserName]" + e);
//			return null;
//		}
			return null;
	}

	

	private String transferWelcomeMsg(String greeting) {
		String welcomeMsg = greeting;
		if (!StringUtil.isEmpty(welcomeMsg) && welcomeMsg.length() > 10) {
			welcomeMsg = welcomeMsg.substring(0, 10) + " ...";
		}
		return welcomeMsg;
	}

	@Override
	public List<PowersModel> getFeatureMenu(String memberCode) {
		// TODO Auto-generated method stub
		return null;
	}
}
