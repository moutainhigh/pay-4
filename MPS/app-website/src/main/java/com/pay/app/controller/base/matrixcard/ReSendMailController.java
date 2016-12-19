/**
 *  File: AccountActiveController.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-9-25   single_zhang     Create
 *
 */
package com.pay.app.controller.base.matrixcard;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.comm.Resource;
import com.pay.app.base.exception.LoginTimeOutException;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.service.mail.MailService;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.service.member.MemberService;

/**
 * 重新发送解绑的邮件 
 * @author Administrator
 *
 */
public class ReSendMailController extends MultiActionController {
	private String login;
	/**
	 * 邮件服务
	 */
	private MailService mailService;
	
	/**
	 * 重新发送邮件的服务(Acc数据库)
	 */
	private CheckCodeService checkCodeService;

	private String toEmail;

	private String toFail;
    
	private String retoEmail;

	private String retosuccEmail;
	
    private String resendMail="resendMail";
    
    //发送邮件的限制
    private static final int LIMITED_TIME = 5;
    
	private MemberService memberService;
	
	public String LoginName(String type,HttpServletRequest request){
		LoginSession loginSession=null;
		try {
			loginSession = SessionHelper.getLoginSession(request);
		} catch (LoginTimeOutException e) {
			e.printStackTrace();
		}
		String email="";
		String mobileNo="";
		String name = "";
		Long memberCode=Long.parseLong(loginSession.getMemberCode());
		MemberCriteria memberCriteria = memberService.queryMemberCriteriaByMemberCodeNsTx(memberCode);
		String loginName=loginSession.getLoginName();
		if(null!=memberCriteria){
			mobileNo=memberCriteria.getMobileNo();
			email=memberCriteria.getEmail();
		}
		if(StringUtils.isNotEmpty(loginName) && loginName.contains("@")){
			email=loginName;
		}else{
			mobileNo=loginName;
		}
		
		if(StringUtils.isNotEmpty(type) && type.equals("E")){
		    name=email;	
		}else{
			name=mobileNo;
		}
		return name;
   }
    
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 加入邮件功能
		if (request.getSession().getAttribute("memberCode") == null || request.getSession().getAttribute("loginName") == null) {
			return new ModelAndView(login);
		}
		String memberCode=request.getSession().getAttribute("memberCode").toString();
		String email=LoginName("E",request);
		String verifyName=request.getSession().getAttribute("verifyName").toString();
		String origin=request.getParameter("origin")==null?CheckCodeOriginEnum.MATRIX_UNBIND.getValue():request.getParameter("origin");

		List<String> recAddress = new ArrayList<String>(1);
		recAddress.add(email);
		
		CheckCodeDto ck = new CheckCodeDto();
		ck.setMemberCode(Long.valueOf(memberCode));
		ck.setOrigin(origin);
		ck.setEmail(email);
	
	   if (email!=null && mailService.sendMail(verifyName, recAddress, ck,
				Resource.MAIL_URL_MATRIXCARDUNBIND,
				Resource.EMAILUNBIND_TEMPLATEID,
				Resource.MAIL_MATRIXCARD_UNBIND)) {
			return new ModelAndView("redirect:/app/reSendUnBindMail.htm?method=toEmail").addObject("pi",email);
		}
		
		return new ModelAndView(toFail);
	}	
	
	public ModelAndView retoEmail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String randCode = request.getSession().getAttribute("rand") == null? "": request.getSession().getAttribute("rand").toString();
		String code = request.getParameter("randCode") == null ? "" : request.getParameter("randCode");
		String email=LoginName("E",request);
		if ("".equals(randCode) || "".equals(code) || !code.equalsIgnoreCase(randCode)) { //校验验证码
			return new ModelAndView(toEmail)
			.addObject("errMsg", MessageConvertFactory.getMessage("randCode")).addObject("email",email);
		}
		request.getSession().removeAttribute("rand");
		// 重发邮件功能
		if (request.getSession().getAttribute("memberCode") == null || request.getSession().getAttribute("loginName") == null) {
			return new ModelAndView(login);
		}
		String memberCode=request.getSession().getAttribute("memberCode").toString();
		String verifyName=request.getSession().getAttribute("verifyName").toString();

		List<String> recAddress = new ArrayList<String>(1);
		recAddress.add(email);
		
		CheckCodeDto tck = new CheckCodeDto();
		tck.setMemberCode(Long.valueOf(memberCode));
		tck.setEmail(email);
		tck.setOrigin(CheckCodeOriginEnum.MATRIX_UNBIND.getValue());
	  if(null!=email&&checkCodeService.resendMail(verifyName, recAddress, tck,
			  Resource.MAIL_URL_MATRIXCARDUNBIND,
			  Resource.EMAILUNBIND_TEMPLATEID, 
			  Resource.MAIL_MATRIXCARD_UNBIND, Integer.valueOf(LIMITED_TIME))){
		 return new ModelAndView("redirect:/app/reSendUnBindMail.htm?method=toEmail&resendMail="+resendMail).addObject("pi",email);  
	  }
		return new ModelAndView(toFail);
	}	
	
	public ModelAndView toEmail(HttpServletRequest request,
			HttpServletResponse response) {
		if (request.getSession().getAttribute("memberCode") == null
				|| request.getSession().getAttribute("loginName") == null) {
			return new ModelAndView(login);
		}
		String email = request.getParameter("pi");
		if(null!=request.getParameter("resendMail")&&resendMail.equals(request.getParameter("resendMail")))
		{
			return new ModelAndView(retosuccEmail).addObject("email", email);	
		}
		return new ModelAndView(toEmail).addObject("email", email);
	}

	/************* set方法 ***************/
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public void setToFail(String toFail) {
		this.toFail = toFail;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setRetoEmail(String retoEmail) {
		this.retoEmail = retoEmail;
	}
	
	public void setRetosuccEmail(String retosuccEmail) {
		this.retosuccEmail = retosuccEmail;
	}
	
	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
}
