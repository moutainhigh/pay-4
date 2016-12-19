/**
 *  File: UserSafeManageController.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-23   lihua     Create
 *
 */
package com.pay.app.controller.base.usersafe;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.client.TxncoreClientService;
import com.pay.app.service.mail.MailService;
import com.pay.app.service.sms.SmsService;
import com.pay.base.dto.MemberDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.Operator;
import com.pay.base.model.PlatformMember;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.acctatrrib.AcctAttribService;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.operator.impl.OperatorServiceFacadeImpl;
import com.pay.base.service.platformmember.PlatformMemberService;
import com.pay.base.service.queryhistory.QueryBalanceService;
import com.pay.base.service.user.UserLoginService;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.JSonUtil;

/**
 * 账户安全设置
 */


/**
 * @author kuang.song
 *
 * @data 2016年08月25日上午10:15:37
 *  
 * @param 修改512秘钥下载
 */
public class PlatformMembersManageController extends MultiActionController {

	// 重构的service
	private MemberService memberService;
	// 重构的acctservice
	private AcctService acctService;

	private IMessageDigest iMessageDigest;

	private AcctAttribService acctAttribService;

	private QueryBalanceService queryCorpBalanceService;
	//
	private OperatorServiceFacadeImpl operatorServiceFacade ;

	/*
	 * 邮件服务
	 */
	private MailService mailService;
	// 结果视图
	private String toresultview;
	// 企业服务的结果视图
	private String toCorpResultview;
	// 找回支付密码输入新的支付密码
	private String nextpwd;
	// 企业服务 找回支付密码输入新的支付密码
	private String corpnextpwd;
	// 找回支付密码输入安全问题答案
	private String nextquestion;
	// 企业服务 找回支付密码输入安全问题答案
	private String corpnextquestion;
	// 跳转至修改支付密码页面
	private String updatepaypwdpage;
	// 企业服务跳转到修改支付密码页面
	private String updateCorpPaypwdpage;
	// 跳转至修改问候语页面
	private String updategreetingpage;
	// 跳转至企业服务修改问候语页面
	private String corpupdategreetingpage;

	// 跳转至修改密码安全问题页面
	private String updatesafequestionpage;
	// 跳转至企业修改密码安全问题页面
	private String updatecorpsafequestionpage;

	// 跳转至通过安全问题找回支付密码页面
	private String foundpaypwdbysafequestionpage;
	// 企业服务 跳转至通过安全问题找回支付密码页面
	private String foundcorppaypwdbysafequestionpage;

	// 跳转至通过邮箱找回支付密码页面 step 1
	private String foundpaypwdbyemailpage;
	// 企业服务 跳转至通过邮箱找回支付密码页面 step 1
	private String foundcorppaypwdbyemailpage;

	// 跳转至通过邮箱找回支付密码结果页面 step 2
	private String foundpaypwdbyemailresultpage;
	// 企业服务 跳转至通过邮箱找回支付密码结果页面 step 2
	private String foundcorppaypwdbyemailresultpage;

	// 点击注册邮箱里找回支付密码链接页面
	private String foundpaypwdbyefromemailpage;
	// 通过注册手机找回支付密码
	private String findsafepwdbymobilepage;
	// 企业服务 通过注册手机找回支付密码
	private String findcorpsafepwdbymobilepage;
	// 发送手机验证码页面
	private String foundsafepwdbymobilepage;
	// 企业服务 发送手机验证码页面
	private String foundcorpsafepwdbymobilepage;

	// 通过手机设置支付密码step3
	private String setpaypwdbymobile;

	// 企业服务 通过手机设置支付密码step3
	private String setcorppaypwdbymobile;

	private String accountIndex;

	private String findmodepage;
	// 企业服务
	private String findCorpmodepage;

	private String setpaypwdpage;

	private String setCorpPaypwdpage;

	private String foundpwdsucc;

	// 企业服务
	private String corpfoundpwdsucc;

	private String foundpwderror;

	// 企业服务
	private String corpfoundpwderror;

	private String setsafequestionpage;

	private String updateloginpwdpage;

	// 企业服务的修改登录密码
	private String corpUpdateloginpwdpage;

	private String updatePaypwdforpay2;

	private SmsService smsService;

	// 企业服务的下载密钥
	private String keydownloadpage;
	private TxncoreClientService txncoreClientService;
	private String newPayGatewayPublicKey;
	
	private String corpPlatformMembersView;
	
	private UserLoginService userLoginService;
	
	private PlatformMemberService platformMemberService;
	
	public ModelAndView init(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(corpPlatformMembersView);
	}
	
	public ModelAndView platformmembers(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String paypwdStatus = null == request.getParameter("paypwdurl") ? ""
				: request.getParameter("paypwdurl");
		String myAccount = null == request.getParameter("myAccount") ? ""
				: request.getParameter("myAccount");
		if ("set".equals(paypwdStatus)) {
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(corpPlatformMembersView).addObject(
						"myAccount", myAccount);
			}
			return new ModelAndView(corpPlatformMembersView).addObject("myAccount",
					myAccount);
		} else {
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(corpPlatformMembersView).addObject(
						"myAccount", myAccount);
			}
			return new ModelAndView(corpPlatformMembersView).addObject("myAccount",
					myAccount);
		}
	}
	
	public ModelAndView addPlatformMembers(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		ResultDto resultDto;
		String memberCode = request.getParameter("memberCode");
		String identity = request.getParameter("identity");
		String password = request.getParameter("password");
		
		//check if memberCode exist or active
		MemberDto normalMember = this.memberService.findByMemberCode(Long.valueOf(memberCode));
		
		Operator platformOperator = this.operatorServiceFacade.getByIdentityMemCode(loginSession.getOperatorIdentity(), Long.valueOf(loginSession.getFathermemberCode()));
		
		ModelAndView modelAndView = new ModelAndView(corpPlatformMembersView);
		modelAndView.addObject("memberCode", memberCode);
		modelAndView.addObject("identity", identity);
		
		if(normalMember == null) {
			resultDto = new ResultDto();
			resultDto.setErrorCode("MEMBER_ERROR");
			resultDto.setErrorMsg("会员号不存在");
			writeResultJson(response, resultDto);
			return null;
		}
		
		if(normalMember.getStatus() != 1) {
			resultDto = new ResultDto();
			resultDto.setErrorCode("MEMBER_ERROR");
			resultDto.setErrorMsg("会员号状态异常");
			writeResultJson(response, resultDto);
			return null;
		}
		
		String loginName = normalMember.getLoginName();
		
		Operator operator = this.operatorServiceFacade.getByIdentityMemCode(identity, Long.valueOf(memberCode));
		if(operator == null) {
			resultDto = new ResultDto();
			resultDto.setErrorCode("OPERATOR_ERROR");
			resultDto.setErrorMsg("登录名必须为关联会员号的管理员名称！");
			writeResultJson(response, resultDto);
			return null;
		}
		
		if(!operator.getName().equals("admin")) {
			resultDto = new ResultDto();
			resultDto.setErrorCode("OPERATOR_ERROR");
			resultDto.setErrorMsg("当前操作员不是管理员");
			writeResultJson(response, resultDto);
			return null;
		}
		
		PlatformMember pltMemberCriteria = new PlatformMember();
		pltMemberCriteria.setFather_member_code(Long.valueOf(loginSession.getFathermemberCode()));
		System.out.println(loginSession.getFathermemberCode());
		pltMemberCriteria.setSon_member_code(Long.valueOf(memberCode));
		List<PlatformMember> existingPlatformMembers = this.platformMemberService.getSonMemberByFatherCode(pltMemberCriteria);
		if(existingPlatformMembers!= null && !existingPlatformMembers.isEmpty()) {
			for(PlatformMember platformMember : existingPlatformMembers) {
				if(platformMember.getStatus() == 1) {
					resultDto = new ResultDto();
					resultDto.setErrorCode("MEMBER_ERROR");
					resultDto.setErrorMsg("会员正在关联中");
					writeResultJson(response, resultDto);
					return null;
				} else if(platformMember.getStatus() == 2) {
					resultDto = new ResultDto();
					resultDto.setErrorCode("MEMBER_ERROR");
					resultDto.setErrorMsg("会员已关联");
					writeResultJson(response, resultDto);
					return null;
				} 
			}
		}
		resultDto = userLoginService.enterpriceMemberLogin(loginName, identity, password);
		
		if(resultDto != null && resultDto.getErrorCode() == null && resultDto.getErrorMsg() == null) {
			PlatformMember pltMember = new PlatformMember();
			pltMember.setCreate_date(new Date());
			pltMember.setFather_member_code(Long.valueOf(loginSession.getFathermemberCode()));
			pltMember.setFather_operator_id(platformOperator.getOperatorId());
			pltMember.setSon_member_code(Long.valueOf(memberCode));
			pltMember.setSon_operator_id(Long.valueOf(operator.getOperatorId().toString()));
			pltMember.setStatus(1); // 待审核
			pltMember.setUpdate_date(new Date());
			this.platformMemberService.createPlatformMembers(pltMember);
		}
		
		writeResultJson(response, resultDto);
		return null;
	}
	
	private void writeResultJson(HttpServletResponse response, ResultDto resultDto) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.getOutputStream().write(JSonUtil.bean2json(resultDto).getBytes("UTF-8"));
	}
	  
	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setToresultview(String toresultview) {
		this.toresultview = toresultview;
	}

	public void setNextpwd(String nextpwd) {
		this.nextpwd = nextpwd;
	}

	public void setNextquestion(String nextquestion) {
		this.nextquestion = nextquestion;
	}

	public void setUpdatepaypwdpage(String updatepaypwdpage) {
		this.updatepaypwdpage = updatepaypwdpage;
	}

	public void setUpdategreetingpage(String updategreetingpage) {
		this.updategreetingpage = updategreetingpage;
	}

	public void setUpdatesafequestionpage(String updatesafequestionpage) {
		this.updatesafequestionpage = updatesafequestionpage;
	}

	public void setFoundpaypwdbysafequestionpage(
			String foundpaypwdbysafequestionpage) {
		this.foundpaypwdbysafequestionpage = foundpaypwdbysafequestionpage;
	}

	public void setFoundpaypwdbyemailpage(String foundpaypwdbyemailpage) {
		this.foundpaypwdbyemailpage = foundpaypwdbyemailpage;
	}

	public void setFoundpaypwdbyemailresultpage(
			String foundpaypwdbyemailresultpage) {
		this.foundpaypwdbyemailresultpage = foundpaypwdbyemailresultpage;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void setFoundpaypwdbyefromemailpage(
			String foundpaypwdbyefromemailpage) {
		this.foundpaypwdbyefromemailpage = foundpaypwdbyefromemailpage;
	}

	public void setFindsafepwdbymobilepage(String findsafepwdbymobilepage) {
		this.findsafepwdbymobilepage = findsafepwdbymobilepage;
	}

	public void setFoundsafepwdbymobilepage(String foundsafepwdbymobilepage) {
		this.foundsafepwdbymobilepage = foundsafepwdbymobilepage;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public void setAccountIndex(String accountIndex) {
		this.accountIndex = accountIndex;
	}

	public void setFindmodepage(String findmodepage) {
		this.findmodepage = findmodepage;
	}

	public void setSetpaypwdpage(String setpaypwdpage) {
		this.setpaypwdpage = setpaypwdpage;
	}

	public void setFoundpwdsucc(String foundpwdsucc) {
		this.foundpwdsucc = foundpwdsucc;
	}

	public void setFoundpwderror(String foundpwderror) {
		this.foundpwderror = foundpwderror;
	}

	public void setSetsafequestionpage(String setsafequestionpage) {
		this.setsafequestionpage = setsafequestionpage;
	}

	public void setUpdateloginpwdpage(String updateloginpwdpage) {
		this.updateloginpwdpage = updateloginpwdpage;
	}

	public void setSetpaypwdbymobile(String setpaypwdbymobile) {
		this.setpaypwdbymobile = setpaypwdbymobile;
	}

	public void setUpdatePaypwdforpay2(String updatePaypwdforpay2) {
		this.updatePaypwdforpay2 = updatePaypwdforpay2;
	}

	public String getToresultview() {
		return toresultview;
	}

	public String getSetpaypwdpage() {
		return setpaypwdpage;
	}

	public String getUpdatepaypwdpage() {
		return updatepaypwdpage;
	}

	public MailService getMailService() {
		return mailService;
	}

	public String getNextpwd() {
		return nextpwd;
	}

	public String getNextquestion() {
		return nextquestion;
	}

	public String getUpdategreetingpage() {
		return updategreetingpage;
	}

	public String getUpdatesafequestionpage() {
		return updatesafequestionpage;
	}

	public String getFoundpaypwdbysafequestionpage() {
		return foundpaypwdbysafequestionpage;
	}

	public String getFoundpaypwdbyemailpage() {
		return foundpaypwdbyemailpage;
	}

	public String getFoundpaypwdbyemailresultpage() {
		return foundpaypwdbyemailresultpage;
	}

	public String getFoundpaypwdbyefromemailpage() {
		return foundpaypwdbyefromemailpage;
	}

	public String getFindsafepwdbymobilepage() {
		return findsafepwdbymobilepage;
	}

	public String getFoundsafepwdbymobilepage() {
		return foundsafepwdbymobilepage;
	}

	public String getSetpaypwdbymobile() {
		return setpaypwdbymobile;
	}

	public String getAccountIndex() {
		return accountIndex;
	}

	public String getFindmodepage() {
		return findmodepage;
	}

	public String getFoundpwdsucc() {
		return foundpwdsucc;
	}

	public String getFoundpwderror() {
		return foundpwderror;
	}

	public String getSetsafequestionpage() {
		return setsafequestionpage;
	}

	public String getUpdateloginpwdpage() {
		return updateloginpwdpage;
	}

	public String getUpdatePaypwdforpay2() {
		return updatePaypwdforpay2;
	}

	public SmsService getSmsService() {
		return smsService;
	}

	public void setCorpupdategreetingpage(String corpupdategreetingpage) {
		this.corpupdategreetingpage = corpupdategreetingpage;
	}

	public void setToCorpResultview(String toCorpResultview) {
		this.toCorpResultview = toCorpResultview;
	}

	public String getToCorpResultview() {
		return toCorpResultview;
	}

	public String getUpdateCorpPaypwdpage() {
		return updateCorpPaypwdpage;
	}

	public void setUpdateCorpPaypwdpage(String updateCorpPaypwdpage) {
		this.updateCorpPaypwdpage = updateCorpPaypwdpage;
	}

	public void setSetCorpPaypwdpage(String setCorpPaypwdpage) {
		this.setCorpPaypwdpage = setCorpPaypwdpage;
	}

	public String getSetCorpPaypwdpage() {
		return setCorpPaypwdpage;
	}

	public void setCorpUpdateloginpwdpage(String corpUpdateloginpwdpage) {
		this.corpUpdateloginpwdpage = corpUpdateloginpwdpage;
	}

	public void setUpdatecorpsafequestionpage(String updatecorpsafequestionpage) {
		this.updatecorpsafequestionpage = updatecorpsafequestionpage;
	}

	public void setFindCorpmodepage(String findCorpmodepage) {
		this.findCorpmodepage = findCorpmodepage;
	}

	public void setFoundcorppaypwdbysafequestionpage(
			String foundcorppaypwdbysafequestionpage) {
		this.foundcorppaypwdbysafequestionpage = foundcorppaypwdbysafequestionpage;
	}

	public void setFoundcorppaypwdbyemailpage(String foundcorppaypwdbyemailpage) {
		this.foundcorppaypwdbyemailpage = foundcorppaypwdbyemailpage;
	}

	public void setFindcorpsafepwdbymobilepage(
			String findcorpsafepwdbymobilepage) {
		this.findcorpsafepwdbymobilepage = findcorpsafepwdbymobilepage;
	}

	public String getFoundcorppaypwdbyemailresultpage() {
		return foundcorppaypwdbyemailresultpage;
	}

	public void setFoundcorppaypwdbyemailresultpage(
			String foundcorppaypwdbyemailresultpage) {
		this.foundcorppaypwdbyemailresultpage = foundcorppaypwdbyemailresultpage;
	}

	public String getCorpnextquestion() {
		return corpnextquestion;
	}

	public void setCorpnextquestion(String corpnextquestion) {
		this.corpnextquestion = corpnextquestion;
	}

	public String getCorpnextpwd() {
		return corpnextpwd;
	}

	public void setCorpnextpwd(String corpnextpwd) {
		this.corpnextpwd = corpnextpwd;
	}

	public String getCorpfoundpwdsucc() {
		return corpfoundpwdsucc;
	}

	public void setCorpfoundpwdsucc(String corpfoundpwdsucc) {
		this.corpfoundpwdsucc = corpfoundpwdsucc;
	}

	public String getCorpfoundpwderror() {
		return corpfoundpwderror;
	}

	public void setCorpfoundpwderror(String corpfoundpwderror) {
		this.corpfoundpwderror = corpfoundpwderror;
	}

	public String getFoundcorpsafepwdbymobilepage() {
		return foundcorpsafepwdbymobilepage;
	}

	public void setFoundcorpsafepwdbymobilepage(
			String foundcorpsafepwdbymobilepage) {
		this.foundcorpsafepwdbymobilepage = foundcorpsafepwdbymobilepage;
	}

	public String getSetcorppaypwdbymobile() {
		return setcorppaypwdbymobile;
	}

	public void setSetcorppaypwdbymobile(String setcorppaypwdbymobile) {
		this.setcorppaypwdbymobile = setcorppaypwdbymobile;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public AcctService getAcctService() {
		return acctService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public void setQueryCorpBalanceService(
			QueryBalanceService queryCorpBalanceService) {
		this.queryCorpBalanceService = queryCorpBalanceService;
	}

	public void setKeydownloadpage(String keydownloadpage) {
		this.keydownloadpage = keydownloadpage;
	}

	public void setTxncoreClientService(
			TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setNewPayGatewayPublicKey(String newPayGatewayPublicKey) {
		this.newPayGatewayPublicKey = newPayGatewayPublicKey;
	}

	public void setOperatorServiceFacade(
			OperatorServiceFacadeImpl operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}

	public String getCorpPlatformMembersView() {
		return corpPlatformMembersView;
	}

	public void setCorpPlatformMembersView(String corpPlatformMembersView) {
		this.corpPlatformMembersView = corpPlatformMembersView;
	}

	public void setUserLoginService(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}

	public void setPlatformMemberService(PlatformMemberService platformMemberService) {
		this.platformMemberService = platformMemberService;
	}
}
