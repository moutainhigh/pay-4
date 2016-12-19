/**
 *  File: ReSendEmailController.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-27   single_zhang     Create
 *
 */
package com.pay.app.controller.base.accountactive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.comm.Resource;
import com.pay.app.common.helper.AppConf;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.service.mail.MailService;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.service.enterprise.EnterpriseBaseService;

/**
 * 重新发送邮箱
 */
public class ReSendEmailController extends MultiActionController {

	private String login;
	/**
	 * 邮件服务
	 */
	private MailService mailService;
	/**
	 * 激活信息服务(对应ACC库)
	 */
	private CheckCodeService checkCodeService;
	/** 企业会员基本信息服务 */
	private EnterpriseBaseService enterpriseBaseService;

	private String toEmail;
	private String mailUrlAction;
	private String emailActionUri;

	private String toFail;

	private String retoEmail;
	/** 激活链接失效页面 */
	private String activeInvalidEmail;
	/** 重发激活邮件成功页面 */
	private String reEmailSuccess;

	private String retosuccEmail;

	private String resendMail = "resendMail";

	public void setMailUrlAction(String mailUrlAction) {
		this.mailUrlAction = mailUrlAction;
	}

	public void setRetoEmail(String retoEmail) {
		this.retoEmail = retoEmail;
	}

	public void setEmailActionUri(String emailActionUri) {
		this.emailActionUri = emailActionUri;
	}

	public void setResendMail(String resendMail) {
		this.resendMail = resendMail;
	}

	public void setRetosuccEmail(String retosuccEmail) {
		this.retosuccEmail = retosuccEmail;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 加入邮件功能
		if (request.getSession().getAttribute("memberCode") == null
				|| request.getSession().getAttribute("loginName") == null
				|| request.getSession().getAttribute("verifyName") == null) {
			return new ModelAndView(login);
		}
		String memberCode = request.getSession().getAttribute("memberCode")
				.toString();
		String email = request.getSession().getAttribute("loginName")
				.toString();
		String verifyName = request.getSession().getAttribute("verifyName")
				.toString();
		String origin = request.getParameter("origin") == null ? CheckCodeOriginEnum.ACTIVE_REGISTER
				.getValue() : request.getParameter("origin");

		// email=DESUtil.decrypt(emailEncryptText);
		List<String> recAddress = new ArrayList<String>(1);
		recAddress.add(email);

		CheckCodeDto ck = new CheckCodeDto();
		ck.setMemberCode(Long.valueOf(memberCode));
		ck.setOrigin(origin);
		ck.setEmail(email);

		if (email != null
				&& mailService.sendMail(verifyName, recAddress, ck,
						mailUrlAction, Resource.TEMPLATEID_ACTIVATION,
						Resource.MAIL_SUBJECT_ACTIVATION)) {

			return new ModelAndView("redirect:/reSendEmail.htm?method=toEmail");
		}

		return new ModelAndView(toFail);
	}

	public ModelAndView retoEmail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (request.getSession().getAttribute("memberCode") == null
				|| request.getSession().getAttribute("loginName") == null
				|| request.getSession().getAttribute("verifyName") == null) {
			return new ModelAndView(login);
		}
		String randCode = request.getSession().getAttribute("rand") == null ? ""
				: request.getSession().getAttribute("rand").toString();
		String email = request.getSession().getAttribute("loginName")
				.toString();
		String code = request.getParameter("randCode") == null ? "" : request
				.getParameter("randCode");
		if ("".equals(randCode) || "".equals(code)
				|| !code.equalsIgnoreCase(randCode)) { // 校验验证码
			return new ModelAndView(toEmail).addObject("errMsg",
					MessageConvertFactory.getMessage("randCode")).addObject(
					"email", email);
		}

		String memberCode = request.getSession().getAttribute("memberCode")
				.toString();

		String verifyName = request.getSession().getAttribute("verifyName")
				.toString();

		// email=DESUtil.decrypt(emailEncryptText);
		List<String> recAddress = new ArrayList<String>(1);
		recAddress.add(email);

		CheckCodeDto ck = new CheckCodeDto();
		ck.setMemberCode(Long.valueOf(memberCode));
		ck.setOrigin(CheckCodeOriginEnum.ACTIVE_REGISTER.getValue());
		ck.setEmail(email);

		if (null != email
				&& checkCodeService.resendMail(verifyName, recAddress, ck,
						mailUrlAction, Resource.TEMPLATEID_ACTIVATION,
						Resource.MAIL_SUBJECT_ACTIVATION,
						new Integer(AppConf.get(AppConf.mail_interval)))) {
			return new ModelAndView(
					"redirect:/reSendEmail.htm?method=toEmail&resendMail="
							+ resendMail);
		}
		return new ModelAndView(toFail).addObject("email", email);

	}

	/**
	 * 过期激活链接的重新发送邮件
	 * 
	 * @author wangzhi
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView reEmailForInvalid(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String randCode = request.getSession().getAttribute("rand") == null ? ""
				: request.getSession().getAttribute("rand").toString();
		String code = request.getParameter("randCode") == null ? "" : request
				.getParameter("randCode");
		String memberCode = request.getParameter("memberCode");
		String loginName = request.getParameter("loginName");
		String limitedDay = request.getParameter("limitedDay");
		String verifyName = request.getParameter("verifyName");
		String isSuPage = request.getParameter("isSuPage");
		int memberType = ServletRequestUtils.getIntParameter(request,
				"memberType", 1);// 1为个人，2为企业
		String pageView = activeInvalidEmail;
		if (StringUtils.isNotBlank(isSuPage)) {
			pageView = reEmailSuccess;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("email", loginName);
		map.put("loginName", loginName);
		map.put("verifyName", verifyName);
		map.put("limitedDay", limitedDay);
		map.put("memberType", memberType);

		if ("".equals(randCode) || "".equals(code)
				|| !code.equalsIgnoreCase(randCode)) { // 校验验证码
			map.put("errMsg", MessageConvertFactory.getMessage("randCode"));
			return new ModelAndView(pageView, map);
		}
		// 重发邮件功能
		if (StringUtils.isBlank(memberCode) || StringUtils.isBlank(loginName)) {
			return new ModelAndView(login);
		}
		String email = loginName;

		List<String> recAddress = new ArrayList<String>(1);
		recAddress.add(email);

		CheckCodeDto ck = new CheckCodeDto();
		ck.setMemberCode(Long.valueOf(memberCode));
		ck.setOrigin(CheckCodeOriginEnum.POSS_CORP_ACTIVE_REGISTER.getValue());
		ck.setEmail(email);

		// 失效
		EnterpriseBase enterpriseBase = enterpriseBaseService
				.findByMemberCode(Long.valueOf(memberCode));
		if (enterpriseBase != null) {
			verifyName = enterpriseBase.getZhName();
		}
		if (null != email
				&& checkCodeService.resendMail(verifyName, recAddress, ck,
						emailActionUri, Resource.TEMPLATEID_ACTIVATION,
						Resource.MAIL_SUBJECT_ACTIVATION,
						Integer.valueOf(limitedDay))) {
			return new ModelAndView(reEmailSuccess, map);
		}
		return new ModelAndView(pageView, map);
	}

	public ModelAndView toEmail(HttpServletRequest request,
			HttpServletResponse response) {
		if (request.getSession().getAttribute("memberCode") == null
				|| request.getSession().getAttribute("loginName") == null) {
			return new ModelAndView(login);
		}
		Object errMsg = request.getAttribute("errMsg");
		if (null != errMsg) {
			return new ModelAndView(toFail).addObject("errMsg", errMsg)
					.addObject("email", request.getAttribute("email"));
		}
		String email = request.getSession().getAttribute("loginName")
				.toString();
		if (null != request.getParameter("resendMail")
				&& resendMail.equals(request.getParameter("resendMail"))) {
			return new ModelAndView(retosuccEmail).addObject("email", email);
		}
		return new ModelAndView(toEmail).addObject("email", email);
	}

	public ModelAndView toFail(HttpServletRequest request,
			HttpServletResponse response) {
		if (request.getSession().getAttribute("memberCode") == null
				|| request.getSession().getAttribute("loginName") == null) {
			return new ModelAndView(login);
		}
		String errMsg = MessageConvertFactory.getMessage("sendMailError");
		return new ModelAndView(toFail).addObject("errMsg", errMsg).addObject(
				"email", request.getSession().getAttribute("loginName"));
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

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setActiveInvalidEmail(String activeInvalidEmail) {
		this.activeInvalidEmail = activeInvalidEmail;
	}

	public void setReEmailSuccess(String reEmailSuccess) {
		this.reEmailSuccess = reEmailSuccess;
	}

	public void setEnterpriseBaseService(
			EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

}
