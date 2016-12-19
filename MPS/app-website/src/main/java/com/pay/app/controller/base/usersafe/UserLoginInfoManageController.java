/**
 *  File: UserLoginInfoManageController.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-28   lihua     Create
 *
 */
package com.pay.app.controller.base.usersafe;

import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.comm.Resource;
import com.pay.acc.common.MaConstant;
import com.pay.acc.service.account.SafeQuestionVerifyService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.account.dto.VerifyResultDto;
import com.pay.acc.service.member.MemberUnlockService;
import com.pay.app.base.api.annotation.HasFeature;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.api.wrapper.SafeControllerWrapper;
import com.pay.app.base.exception.LoginTimeOutException;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.RequestLocal;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.service.sms.SmsService;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.dto.IndividualInfoDto;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.MemberDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.Acct;
import com.pay.base.model.Operator;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.acctatrrib.AcctAttribService;
import com.pay.base.service.individual.IndividualInfoService;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.CheckUtil;
import com.pay.util.DateUtil;
import com.pay.util.SignatureHelper;

/**
 * 
 */
public class UserLoginInfoManageController extends MultiActionController {

	// 本地用户验证
	// private UserCheckServiceFacade userCheckServiceFacade;

	// private MemberUpdateService memberUpdateService;

	private SmsService smsService;

	// 重构的service
	private MemberService memberService;

	private AcctAttribService acctAttribService;

	private IMessageDigest iMessageDigest;

	private SafeQuestionVerifyService safeQuestionVerifyService;

	private AcctService acctService;

	private OperatorServiceFacade operatorServiceFacade;
	/** 邮件服务 */
	private CheckCodeService checkCodeService;
	/** 个人信息 */
	private IndividualInfoService individualInfoService;

	private String toresultview;

	private String toresultviewwithoutlogin;
	// 企业
	private String tocorpresultView;

	private String updateloginpwdpage;

	private MemberUnlockService memberUnlockService;

	// 企业
	private String updatecorploginpwdpage;

	private String findLoginPwdFirstpage;
	private String findLoginPwdSecondpage;
	private String findLoginPwdThirdpage;
	private String findloginpwdresultpage;
	/** 选择找回登录密码方式 */
	private String findLoginPwdSelect;
	/** 手机找回登录密码 */
	private String findLoginPwdMobile;
	/** 邮箱找回登录密码 */
	private String findLoginPwdEmail;
	/** 企业会员找回登录密码 */
	private String findLoginPwdCorp;
	// 临时会员
	private static final int STATUS_PROVISIONAL = 5;
	/** 找回登录密码邮件有效时间(3天) */
	private static final int EFFECTIVE_TIME = 3;
	/** 找回登录密码手机短信有效时间(30分钟) */
	private static final int EFFECTIVE_TIME_MOBILE = 30;
	/** 会员号Session key值 */
	private static final String SESSION_KEY_MEMBER_CODE = "FIND_LOGIN_PASSWORD_CODE";
	/** 会员登录名Session key值 */
	private static final String SESSION_KEY_LOGIN_OBJECT = "FIND_LOGIN_PASSWORD_SIGN_OBJECT";
	/** 操作第三步 */
	private static final String OPERATION_STEP = "COMPLETE_THE_THREE";

	private static final String[] PWD_UPDATEPASSWORD = new String[] {
			"oldLoginPwd", "newLoginPwd", "newLoginPwd1" };

	private static final String[] PWD_FINDPASSWORD = new String[] {
			"newLoginPwd", "newLoginPwd1" };

	private String mailUrlFindLoginPwd;

	public void setMailUrlFindLoginPwd(String mailUrlFindLoginPwd) {
		this.mailUrlFindLoginPwd = mailUrlFindLoginPwd;
	}

	/**
	 * 修改登录密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATELOGINPWD")
	@HasFeature({ CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
	public ModelAndView updateLoginPwd(HttpServletRequest req,
			HttpServletResponse response) throws Exception {

		SafeControllerWrapper request = new SafeControllerWrapper(req,
				PWD_UPDATEPASSWORD);

		LoginSession loginSession = SessionHelper.getLoginSession(request);
		Map<String, Object> resMsg = new HashMap<String, Object>();

		// 如果是GET方式提交
		if (request.getMethod().equalsIgnoreCase("GET")) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("errorRequestMethod"));
			resMsg.put("classcss", "feedback warning");
			return new ModelAndView(toresultview, resMsg);
		}

		String loginName = "";
		Long memberCode = Long.valueOf(loginSession.getMemberCode());

		String oldLoginPwd = null == request.getParameter("oldLoginPwd") ? ""
				: request.getParameter("oldLoginPwd");
		String newLoginPwd = null == request.getParameter("newLoginPwd") ? ""
				: request.getParameter("newLoginPwd");
		String newLoginPwd1 = null == request.getParameter("newLoginPwd1") ? ""
				: request.getParameter("newLoginPwd1");

		// 验证2次输入密码是否一致
		if (!newLoginPwd.equals(newLoginPwd1)) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("differentPwd"));
			resMsg.put("classcss", "feedback warning");
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(updatecorploginpwdpage, resMsg);
			}
			return new ModelAndView(updateloginpwdpage, resMsg);
		}

		// 验证登录密码是否包含特殊字符
		if (CheckUtil.isPwdContainsSpecialCharacter(newLoginPwd1)) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("specialCharInLoginPwd"));
			resMsg.put("classcss", "feedback warning");
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(updatecorploginpwdpage, resMsg);
			}
			return new ModelAndView(updateloginpwdpage, resMsg);
		}

		// 登录密码格式不正确
		if (!CheckUtil.checkLoginPwd(newLoginPwd1)) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("invalideLoginPwd"));
			resMsg.put("classcss", "feedback warning");
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(updatecorploginpwdpage, resMsg);
			}
			return new ModelAndView(updateloginpwdpage, resMsg);
		}

		// 验证支付密码和登录密码是否一致 start
		Acct acct = acctService.getByMemberCode(memberCode,
				 AcctTypeEnum.BASIC_CNY.getCode());
		if (acct != null) {
			AcctAttribDto acctAttribDto = acctAttribService
					.queryAcctAttribByAcctCode(acct.getAcctCode());
			if (acctAttribDto != null) {
				if (acctAttribDto.getPayPwd() != null
						&& acctAttribDto.getPayPwd().equals(
								iMessageDigest.genMessageDigest(newLoginPwd
										.getBytes()))) {
					resMsg.put("returninfo", MessageConvertFactory
							.getMessage("errorForSamePassword"));
					resMsg.put("classcss", "feedback warning");
					if (SessionHelper.isCorpLogin()) {
						return new ModelAndView(updatecorploginpwdpage, resMsg);
					}
					return new ModelAndView(updateloginpwdpage, resMsg);
				}
			}
		}
		// 验证支付密码和登录密码是否一致 end

		if (null != loginSession) {
			loginName = loginSession.getLoginName();
		}
		if (!"".equals(loginName) && !"".equals(oldLoginPwd)) {

			Long operatorId = loginSession.getOperatorId();
			// 重构修改登录密码 (切换到acc库)
			if (SessionHelper.isCorpLogin()) {
				if ((operatorId == null || operatorId < 1)
						&& StringUtils.isNotBlank(loginSession
								.getOperatorIdentity())) {
					Operator operator = operatorServiceFacade
							.getByIdentityMemCode(
									loginSession.getOperatorIdentity(),
									memberCode);
					if (operator != null) {
						operatorId = operator.getOperatorId();
					}
				}
			}
			// 重构验证旧登录密码 (切换到acc库)
			ResultDto resultDto = memberService.verifyLoginPwd(memberCode,
					oldLoginPwd, operatorId);
			if (null == resultDto.getErrorCode()) {
				ResultDto resultDto2 = memberService.resetLoginPwdRnTx(
						memberCode, newLoginPwd, operatorId);
				if (null == resultDto2.getErrorCode()) {
					memberUnlockService.unLock(memberCode);// 会员登录解锁
					if (SessionHelper.isCorpLogin()) {
						resMsg.put("returninfo", MessageConvertFactory
								.getMessage("changeSuccess"));
						resMsg.put("classcss", "feedback succeed");
						return new ModelAndView(tocorpresultView, resMsg);
					}
					resMsg.put("returninfo",
							MessageConvertFactory.getMessage("changeSuccess"));
					resMsg.put("classcss", "feedback succeed");
					return new ModelAndView(toresultview, resMsg);
				} else if (resultDto2.getErrorCode().equals(
						ErrorCodeEnum.MEMBER_VERIFY_PASSWORD_ERROR
								.getErrorCode())) {
					// 如果密码验证失败
					resMsg.put("returninfo", MessageConvertFactory
							.getMessage("invalideLoginPwd"));
					resMsg.put("classcss", "feedback warning");
					if (SessionHelper.isCorpLogin()) {
						return new ModelAndView(updatecorploginpwdpage, resMsg);
					}
					return new ModelAndView(updateloginpwdpage, resMsg);
				}
			}
		}
		if (SessionHelper.isCorpLogin()) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("loginPwdError"));
			resMsg.put("classcss", "feedback warning");
			return new ModelAndView(updatecorploginpwdpage, resMsg);
		}

		resMsg.put("returninfo",
				MessageConvertFactory.getMessage("loginPwdError"));
		resMsg.put("classcss", "feedback warning");
		return new ModelAndView(updateloginpwdpage, resMsg);
	}

	/**
	 * 找回登录密码 first step
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView loginpwd(HttpServletRequest request,
			HttpServletResponse response) {

		return new ModelAndView(findLoginPwdFirstpage);
	}

	/**
	 * 找回登录密码 second step
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_FINDLOGINPWD")
	public ModelAndView findLoginPwdFirst(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String randCode = (String) request.getSession().getAttribute("rand");
		String code = request.getParameter("randCode");
		String username = request.getParameter("username") == null ? ""
				: request.getParameter("username");

		Map<String, String> msg = new HashMap<String, String>();
		if (StringUtils.isNotBlank(randCode)
				&& StringUtils.equalsIgnoreCase(code, randCode)) {
			// 获取用户信息
			MemberDto member = memberService.findMemberByLoginName(StringUtils
					.trim(StringUtils.lowerCase(username)));
			if (member == null) {
				// 用户不存在
				msg.put("returninfo",
						MessageConvertFactory.getMessage("noaccount"));
				return new ModelAndView(findLoginPwdFirstpage, msg);
			}
			// 如果是企业用户，则提示无法通过这个方式找回 add by jerry_jin
			if (member.getType() == MemberTypeEnum.MERCHANT.getCode()) {
				msg.put("returninfo", MessageConvertFactory
						.getMessage("findLoginPwdWithCompMember"));
				msg.put("classcss", "feedback warning");
				return new ModelAndView(toresultviewwithoutlogin, msg);
			}
			// 临时会员提示用户不存在
			if (member.getStatus() == STATUS_PROVISIONAL) {
				// 用户不存在
				msg.put("returninfo",
						MessageConvertFactory.getMessage("noaccount"));
				return new ModelAndView(findLoginPwdFirstpage, msg);
			}
			String memberCode = String.valueOf(member.getMemberCode());
			request.getSession().setAttribute(SESSION_KEY_MEMBER_CODE,
					memberCode);
			username = member.getLoginName();
			SessionFindOjbect sObject = new SessionFindOjbect();
			sObject.setLoginName(username);
			sObject.setMemberCode(memberCode);
			if (CheckUtil.checkPhone(username)) {
				sObject.setMobile(username);
			}
			request.getSession()
					.setAttribute(SESSION_KEY_LOGIN_OBJECT, sObject);
			String signData = SignatureHelper
					.generateAppSignature(getSignData());
			sObject.setSignData(signData);
			msg.put("userLoginType", String.valueOf(member.getLoginType()));
			msg.put("isMobile", String.valueOf(CheckUtil.checkPhone(username)));
			msg.put("signData", signData);
			msg.put("userName", username);
			if (CheckUtil.checkPhone(username)) {
				sObject.setMobile(username);
				msg.put("uName",
						username.substring(0, 3) + "****"
								+ username.substring(7, 11));
			} else {
				msg.put("uName",
						username.substring(0, username.indexOf("@") - 2)
								+ "**"
								+ username.substring(username.indexOf("@"),
										username.length()));
			}
			return new ModelAndView(findLoginPwdSelect, msg);

		} else {
			// 验证码错误
			msg.put("returninfo", MessageConvertFactory.getMessage("randCode"));
			return new ModelAndView(findLoginPwdFirstpage, msg);
		}

	}

	/**
	 * 找回登录密码 third step
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws LoginTimeOutException
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_FINDLOGINPWD")
	public ModelAndView findLoginPwdSecond(HttpServletRequest request,
			HttpServletResponse response) throws LoginTimeOutException {
		String question = request.getParameter("question");
		String answer = request.getParameter("safeanswer");
		String str = request.getParameter("signData");
		Map<String, String> msg = new HashMap<String, String>();
		if (hasSession(request) && isSignature(str)) {
			SessionFindOjbect sOject = (SessionFindOjbect) RequestLocal
					.getSession().getAttribute(SESSION_KEY_LOGIN_OBJECT);
			String loginName = sOject.getLoginName();
			msg.put("findType", "3");
			msg.put("loginName", loginName);
			msg.put("signData", str);
			String memberCode = sOject.getMemberCode();

			MaResultDto maResultDto = safeQuestionVerifyService.doVerify(
					Long.valueOf(memberCode), Integer.parseInt(question),
					answer);
			// 验证成功
			if (maResultDto.getResultStatus() == MaConstant.SECCESS) {
				sOject.setStep(OPERATION_STEP);
				return new ModelAndView(findLoginPwdThirdpage, msg);
			} else if (maResultDto.getResultStatus() == MaConstant.ERROR_NOT_LOCK) {
				// 验证失败，但是没有锁定
				VerifyResultDto vrd = (VerifyResultDto) maResultDto.getObject();
				msg.put("returninfo", MessageFormat.format(
						MessageConvertFactory
								.getMessage("answerfailureandnotice"),
						new Object[] { vrd.getTotalTime(),
								vrd.getTotalTime() - vrd.getLeavingTime() }));
				return new ModelAndView(findLoginPwdSecondpage, msg);
			} else if (maResultDto.getResultStatus() == MaConstant.ERROR_AND_LOCK) {
				// 验证失败，并且锁定
				VerifyResultDto vrd = (VerifyResultDto) maResultDto.getObject();
				msg.put("returninfo", MessageFormat.format(
						MessageConvertFactory
								.getMessage("answerfailureandlocknotice"),
						new Object[] { vrd.getTotalTime(),
								vrd.getLeavingMinute() }));
				return new ModelAndView(findLoginPwdSecondpage, msg);
			}

			msg.put("errorMsg", MessageConvertFactory.getMessage("errorHandle"));
			return new ModelAndView(findloginpwdresultpage, msg);

			// ResultDto resultDto =
			// memberService.validateSecurMemberQuestionWidthMemberInfo(Long.valueOf(memberCode),
			// Integer.parseInt(question), answer);
			// if (null == resultDto.getErrorCode()) {
			// return new ModelAndView(findLoginPwdThirdpage,msg);
			// } else {
			// msg.put("returninfo", MessageConvertFactory
			// .getMessage("answerfailure"));
			// return new ModelAndView(findLoginPwdSecondpage, msg);
			// }
		} else {// 非法操作
			msg.put("errorMsg", MessageConvertFactory.getMessage("errorHandle"));
			return new ModelAndView(findloginpwdresultpage, msg);
		}
	}

	/**
	 * 找回登录密码 third step
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws LoginTimeOutException
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_FINDLOGINPWD")
	public ModelAndView findLoginPwdSelect(HttpServletRequest request,
			HttpServletResponse response) throws LoginTimeOutException {
		String radioFindType = request.getParameter("radioFindType");
		String str = request.getParameter("signData");
		Map<String, String> msg = new HashMap<String, String>();
		if (hasSession(request) && isSignature(str)) {
			SessionFindOjbect sOject = (SessionFindOjbect) request.getSession()
					.getAttribute(SESSION_KEY_LOGIN_OBJECT);
			String memberCode = sOject.getMemberCode();
			msg.put("signData", str);
			if (StringUtils.equals(radioFindType, "1")) {
				// 发送短信到用户
				List<String> mobilelist = new ArrayList<String>();
				mobilelist.add(sOject.getLoginName());
				smsService.sendSms(memberCode,
						CheckCodeOriginEnum.SMS_FINDLOGINPWD.getValue(),
						sOject.getLoginName(), mobilelist,
						Resource.TEMPLATEID_SMS_LOGINPWD);
				msg.put("mobile", sOject.getLoginName().substring(0, 3)
						+ "****" + sOject.getLoginName().substring(7, 11));
				return new ModelAndView(findLoginPwdMobile, msg);
			} else if (StringUtils.equals(radioFindType, "2")) {
				CheckCodeDto ck = new CheckCodeDto();
				ck.setMemberCode(Long.valueOf(memberCode));
				ck.setOrigin(CheckCodeOriginEnum.EMAIL_FINDLOGINPWD.getValue());
				ck.setEmail(sOject.getLoginName());
				List<String> recAddress = new ArrayList<String>(1);
				recAddress.add(sOject.getLoginName());
				IndividualInfoDto individualInfoDto = individualInfoService
						.queryIndividualInfoByMemberCode(Long
								.valueOf(memberCode));
				if (individualInfoDto != null) {
					checkCodeService.sendMail(individualInfoDto.getName(),
							recAddress, ck, mailUrlFindLoginPwd,
							Resource.TEMPLATEID_EMAIL_LOGINPWD,
							Resource.MAIL_LOGIN_PWD_FIND);
					msg.put("uEmail",
							sOject.getLoginName().substring(0,
									sOject.getLoginName().indexOf("@") - 2)
									+ "**"
									+ sOject.getLoginName().substring(
											sOject.getLoginName().indexOf("@"),
											sOject.getLoginName().length()));
					return new ModelAndView(findLoginPwdEmail, msg);
				} else {
					logger.warn("findLoginPwdSelect error,individualInfoDto is null!loginName="
							+ sOject.getLoginName()
							+ ",memberCode="
							+ memberCode);
					// 非法操作
					msg.put("isError", "Y");
					msg.put("errorMsg",
							MessageConvertFactory.getMessage("errorHandle"));
					return new ModelAndView(findLoginPwdEmail, msg);
				}
			} else {
				msg.put("loginName", sOject.getLoginName());
				return new ModelAndView(findLoginPwdSecondpage, msg);
			}
		}
		// 非法操作
		msg.put("errorMsg", MessageConvertFactory.getMessage("errorHandle"));
		return new ModelAndView(findloginpwdresultpage, msg);
	}

	/**
	 * 找回登录密码 third step
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws LoginTimeOutException
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_FINDLOGINPWD")
	public ModelAndView findLoginPwdByMobile(HttpServletRequest request,
			HttpServletResponse response) throws LoginTimeOutException {

		String checkCode = request.getParameter("checkCode");
		String str = request.getParameter("signData");
		Map<String, String> msg = new HashMap<String, String>();

		String view = findLoginPwdMobile;
		if (hasSession(request) && isSignature(str)
				&& StringUtils.isNotBlank(checkCode)) {
			msg.put("signData", str);
			SessionFindOjbect sOject = (SessionFindOjbect) request.getSession()
					.getAttribute(SESSION_KEY_LOGIN_OBJECT);
			String memberCode = sOject.getMemberCode();
			CheckCodeDto ck = smsService.getByMemerCode(memberCode,
					CheckCodeOriginEnum.SMS_FINDLOGINPWD);
			String obj = sOject.getUserType();
			if (StringUtils.equals(obj, "2")) {
				view = findLoginPwdCorp;
			}
			msg.put("userType", obj);
			msg.put("loginName", sOject.getLoginName());
			// 如果验证码符合
			if (null != ck && ck.getStatus() == 1
					&& StringUtils.equals(ck.getCheckCode(), checkCode)) {
				msg.put("findType", "1");
				// 校验checkCode是否有效
				if (!this.checkEffectiveMobile(ck.getCreateTime())) {
					msg.put("isEffective", "N");
					return new ModelAndView(view, msg).addObject("mobile",
							sOject.getMobile().substring(0, 3) + "****"
									+ sOject.getMobile().substring(7, 11));
				}
				sOject.setCheckCode(checkCode);
				sOject.setStep(OPERATION_STEP);
				return new ModelAndView(findLoginPwdThirdpage, msg);
			} else {
				msg.put("returninfo",
						MessageConvertFactory.getMessage("randCode"));
				return new ModelAndView(view, msg).addObject("mobile", sOject
						.getMobile().substring(0, 3)
						+ "****"
						+ sOject.getMobile().substring(7, 11));
			}
		} else {// 非法操作
			msg.put("errorMsg", MessageConvertFactory.getMessage("errorHandle"));
			return new ModelAndView(findloginpwdresultpage, msg);
		}
	}

	/**
	 * 邮箱找回登录密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws LoginTimeOutException
	 */
	public ModelAndView emailValidate(HttpServletRequest request,
			HttpServletResponse response) throws LoginTimeOutException {

		String checkCode = request.getParameter("code");

		Map<String, String> msg = new HashMap<String, String>();
		if (StringUtils.isBlank(checkCode)) {
			return new ModelAndView("redirect:/outapp.htm");
		}

		CheckCodeDto ck = checkCodeService.getByCheckCodeAndOrigin(checkCode,
				CheckCodeOriginEnum.EMAIL_FINDLOGINPWD);
		if (ck != null) {
			// 验证是否使用过
			if (ck.getStatus() == 2) {
				msg.put("isError", "Y");
				return new ModelAndView(findLoginPwdEmail, msg);
			}
			// 检查checkCode是否有效
			if (!this.checkEffectiveTime(ck.getCreateTime())) {
				msg.put("isError", "Y");
				return new ModelAndView(findLoginPwdEmail, msg);
			}
			MemberDto memberDto = memberService.findByMemberCode(ck
					.getMemberCode());
			// 校验会员是否存在
			if (memberDto == null) {
				return new ModelAndView("redirect:/outapp.htm");
			}
			// 是否为个人会员
			if (memberDto.getType() != MemberTypeEnum.INDIVIDUL.getCode()) {
				msg.put("isError", "Y");
				msg.put("errorMsg",
						MessageConvertFactory.getMessage("errorHandle"));
				return new ModelAndView(findLoginPwdEmail, msg);
			}
			request.getSession().setAttribute(SESSION_KEY_MEMBER_CODE,
					String.valueOf(memberDto.getMemberCode()));
			SessionFindOjbect sObject = new SessionFindOjbect();
			sObject.setCheckCode(checkCode);
			sObject.setLoginName(memberDto.getLoginName());
			sObject.setMemberCode(String.valueOf(memberDto.getMemberCode()));
			if (CheckUtil.checkPhone(memberDto.getLoginName())) {
				sObject.setMobile(memberDto.getLoginName());
			}
			request.getSession()
					.setAttribute(SESSION_KEY_LOGIN_OBJECT, sObject);
			String signData = SignatureHelper
					.generateAppSignature(getSignData());
			sObject.setSignData(signData);
			msg.put("signData", signData);
			msg.put("findType", "2");
			msg.put("loginName", memberDto.getLoginName());
			sObject.setStep(OPERATION_STEP);
			return new ModelAndView(findLoginPwdThirdpage, msg);
		} else {// 非法操作
			msg.put("isError", "Y");
			msg.put("errorMsg", MessageConvertFactory.getMessage("errorHandle"));
			return new ModelAndView(findLoginPwdEmail, msg);
		}
	}

	/***
	 * 企业会员找回登录密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws LoginTimeOutException
	 */
	public ModelAndView corpEmailValidate(HttpServletRequest request,
			HttpServletResponse response) throws LoginTimeOutException {

		String checkCode = request.getParameter("code");

		Map<String, String> msg = new HashMap<String, String>();
		if (StringUtils.isBlank(checkCode)) {
			return new ModelAndView("redirect:/outapp.htm");
		}

		CheckCodeDto ck = checkCodeService.getByCheckCodeAndOrigin(checkCode,
				CheckCodeOriginEnum.POSS_CORP_EMAIL_FINDLOGINPWD);
		if (ck != null) {
			// 验证是否使用过
			if (ck.getStatus() == 2) {
				msg.put("isError", "Y");
				return new ModelAndView(findLoginPwdCorp, msg);
			}
		/*	// 校验手机号是否规范
			if (StringUtils.isBlank(ck.getPhone())
					|| !CheckUtil.checkPhone(ck.getPhone())) {
				msg.put("isError", "Y");
				return new ModelAndView(findLoginPwdCorp, msg);
			}*/
			// 检查checkCode是否有效
			if (!this.checkEffectiveTime(ck.getCreateTime())) {
				msg.put("isError", "Y");
				return new ModelAndView(findLoginPwdCorp, msg);
			}
			MemberDto memberDto = memberService.findByMemberCode(ck
					.getMemberCode());
			// 校验会员是否存在
			if (memberDto == null) {
				return new ModelAndView("redirect:/outapp.htm");
			}
			// 是否为企业会员
			if (memberDto.getType() != MemberTypeEnum.MERCHANT.getCode()) {
				msg.put("isError", "Y");
				msg.put("errorMsg",
						MessageConvertFactory.getMessage("errorHandle"));
				return new ModelAndView(findLoginPwdCorp, msg);
			}
			// 发送短信到用户
			List<String> mobilelist = new ArrayList<String>();
			mobilelist.add(ck.getPhone());
			//smsService.sendSms(String.valueOf(memberDto.getMemberCode()),CheckCodeOriginEnum.SMS_FINDLOGINPWD.getValue(),ck.getPhone(), mobilelist,Resource.TEMPLATEID_SMS_LOGINPWD);
		/*	msg.put("mobile", ck.getPhone().substring(0, 3) + "****"
					+ ck.getPhone().substring(7, 11));
*/
			request.getSession().setAttribute(SESSION_KEY_MEMBER_CODE,
					String.valueOf(memberDto.getMemberCode()));
			SessionFindOjbect sObject = new SessionFindOjbect();
			sObject.setLoginName(memberDto.getLoginName());
			sObject.setMemberCode(String.valueOf(memberDto.getMemberCode()));
		/*	sObject.setMobile(ck.getPhone());*/
			sObject.setStep(OPERATION_STEP);
			request.getSession()
					.setAttribute(SESSION_KEY_LOGIN_OBJECT, sObject);
			String signData = SignatureHelper
					.generateAppSignature(getSignData());
			sObject.setSignData(signData);
			msg.put("signData", signData);
			sObject.setCheckCode2(checkCode);
			sObject.setUserType("2");
			msg.put("findType", "1");
			msg.put("loginName", memberDto.getLoginName());
			//return new ModelAndView(findLoginPwdCorp, msg);
			return new ModelAndView(findLoginPwdThirdpage, msg);
		} else {// 非法操作
			msg.put("isError", "Y");
			msg.put("errorMsg", MessageConvertFactory.getMessage("errorHandle"));
			return new ModelAndView(findLoginPwdCorp, msg);
		}
	}

	/**
	 * 找回登录密码 forth step(提交)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_FINDLOGINPWD")
	public ModelAndView findLoginPwdThird(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		// LoginSession loginSession = SessionHelper.getLoginSession(request);
		SafeControllerWrapper request = new SafeControllerWrapper(req,
				PWD_FINDPASSWORD);
		Map<String, String> msg = new HashMap<String, String>();

		// 如果是GET方式提交
		if (request.getMethod().equalsIgnoreCase("GET")) {
			if (req.getSession().getAttribute("userSession") == null) {
				return new ModelAndView("redirect:/outapp.htm");
			}
			msg.put("returninfo",
					MessageConvertFactory.getMessage("errorRequestMethod"));
			msg.put("classcss", "feedback warning");
			return new ModelAndView(toresultview, msg);
		}
		String str = request.getParameter("signData");
		if (hasSession(request) && isSignature(str)) {
			String newLoginPwd = request.getParameter("newLoginPwd");

			String newLoginPwd1 = request.getParameter("newLoginPwd1");
			String findType = request.getParameter("findType");
			msg.put("findType", findType);
			SessionFindOjbect sOject = (SessionFindOjbect) request.getSession()
					.getAttribute(SESSION_KEY_LOGIN_OBJECT);
			if (!StringUtils.equals(sOject.getStep(), OPERATION_STEP)) {
				msg.put("errorMsg",
						MessageConvertFactory.getMessage("errorHandle"));
				msg.put("classcss", "feedback warning");
				return new ModelAndView(findloginpwdresultpage, msg);
			}
			String obj1 = sOject.getUserType();
			msg.put("loginName", sOject.getLoginName());
			msg.put("signData", str);
			msg.put("userType", obj1);
			// 验证2次密码是否一致
			if (StringUtils.isBlank(newLoginPwd)
					|| !StringUtils.equals(newLoginPwd, newLoginPwd1)) {
				msg.put("returninfo",
						MessageConvertFactory.getMessage("differentPwd"));
				msg.put("classcss", "feedback warning");
				return new ModelAndView(findLoginPwdThirdpage, msg);
			}

			// 验证登录密码是否包含特殊字符
			if (CheckUtil.isPwdContainsSpecialCharacter(newLoginPwd1)) {
				msg.put("returninfo", MessageConvertFactory
						.getMessage("specialCharInLoginPwd"));
				msg.put("classcss", "feedback warning");
				return new ModelAndView(findLoginPwdThirdpage, msg);
			}

			// 验证密码格式
			if (!CheckUtil.checkLoginPwd(newLoginPwd)) {
				msg.put("returninfo",
						MessageConvertFactory.getMessage("invalideLoginPwd"));
				msg.put("classcss", "feedback warning");
				return new ModelAndView(findLoginPwdThirdpage, msg);
			}

			String memberCode = sOject.getMemberCode();
			// 验证和支付密码是否 一致
			Acct acct = acctService.getByMemberCode(Long.parseLong(memberCode),
					 AcctTypeEnum.BASIC_CNY.getCode());
			if (acct != null) {
				AcctAttribDto acctAttribDto = acctAttribService
						.queryAcctAttribByAcctCode(acct.getAcctCode());
				if (acctAttribDto != null) {
					if (acctAttribDto.getPayPwd() != null
							&& acctAttribDto.getPayPwd().equals(
									iMessageDigest.genMessageDigest(newLoginPwd
											.getBytes()))) {
						msg.put("returninfo", MessageConvertFactory
								.getMessage("errorForSamePassword"));
						msg.put("classcss", "feedback warning");
						return new ModelAndView(findLoginPwdThirdpage, msg);
					}
				}
			}

			ResultDto resultDto = memberService.resetLoginPwdRnTx(
					Long.valueOf(memberCode), newLoginPwd, null);
			if (null == resultDto.getErrorCode()) {

				memberUnlockService.unLock(Long.valueOf(memberCode));// 会员登录解锁

				//
				String obj = sOject.getCheckCode();
				if (StringUtils.isNotBlank(obj)) {
					// 将校验码状态更新为已校验
					checkCodeService.updateCheckStateByMemCode(
							Long.valueOf(memberCode), String.valueOf(obj));
				}

				boolean result = true;
				if (StringUtils.equals(obj1, "2")) {
					Operator operator = operatorServiceFacade
							.getAdminOperator(Long.valueOf(memberCode));
					if (operator != null) {
						result = operatorServiceFacade.updateOperatorPWD(
								newLoginPwd, operator.getOperatorId(),
								Long.valueOf(memberCode));
					}
					String objCode = sOject.getCheckCode2();
					if (StringUtils.isNotBlank(objCode)) {
						// 将校验码状态更新为已校验
						checkCodeService.updateCheckStateByMemCode(
								Long.valueOf(memberCode),
								String.valueOf(objCode));
					}
				}
				if (result) {
					msg.put("returninfo",
							MessageConvertFactory.getMessage("changeSuccess"));
					msg.put("classcss", "feedback succeed");
				} else {
					msg.put("returninfo", MessageConvertFactory
							.getMessage("invalideLoginPwd"));
					msg.put("classcss", "feedback warning");
				}
				return new ModelAndView(findloginpwdresultpage, msg);
			} else {
				logger.warn("findLoginPwdThird failed!memberCode=" + memberCode
						+ ",rsetLoginPwd errorCode=" + resultDto.getErrorCode());
				msg.put("returninfo",
						MessageConvertFactory.getMessage("invalideLoginPwd"));
				msg.put("classcss", "feedback warning");
				return new ModelAndView(findLoginPwdThirdpage, msg);
			}
		}

		msg.put("errorMsg", MessageConvertFactory.getMessage("errorHandle"));
		msg.put("classcss", "feedback warning");
		return new ModelAndView(findloginpwdresultpage, msg);
	}

	/**
	 * 重新发送验证码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void resendCheckCodeformobile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/plain;charset=UTF-8");
		String result = "N";
		boolean istrue = false;
		PrintWriter out = null;
		out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		String str = request.getParameter("signData");
		if (request.getMethod().equalsIgnoreCase("POST") && hasSession(request)
				&& isSignature(str)) {
			SessionFindOjbect sOject = (SessionFindOjbect) request.getSession()
					.getAttribute(SESSION_KEY_LOGIN_OBJECT);
			String memberCode = sOject.getMemberCode();
			String mobile = sOject.getMobile();
			List<String> mobilelist = new ArrayList<String>();
			mobilelist.add(mobile);
			MemberCriteria memberCriteria = null;
			if (StringUtils.isNotBlank(memberCode)) {
				memberCriteria = memberService
						.queryMemberCriteriaByMemberCodeNsTx(Long
								.valueOf(memberCode));
				if (null != memberCriteria) {
					if (null != mobilelist && mobilelist.size() > 0) {
						istrue = smsService
								.sendSms(memberCode,
										CheckCodeOriginEnum.SMS_FINDLOGINPWD
												.getValue(), memberCriteria
												.getVerifyName(), mobilelist,
										Resource.TEMPLATEID_SMS_LOGINPWD);
						if (istrue)
							result = "Y";
					}
				}
			}
		}
		out.print(result);
		out.flush();
		out.close();
	}

	/**
	 * 重发找回登录密码邮件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void resendCheckCodeForEmail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
		String result = "N";
		PrintWriter out = null;
		out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		String str = request.getParameter("signData");
		if (request.getMethod().equalsIgnoreCase("POST") && hasSession(request)
				&& isSignature(str)) {
			SessionFindOjbect sOject = (SessionFindOjbect) request.getSession()
					.getAttribute(SESSION_KEY_LOGIN_OBJECT);
			String memberCode = sOject.getMemberCode();
			String email = sOject.getLoginName();
			if (StringUtils.isNotBlank(memberCode)
					&& StringUtils.isNotBlank(email)) {
				List<String> recAddress = new ArrayList<String>(1);
				recAddress.add(email);
				CheckCodeDto ck = new CheckCodeDto();
				ck.setMemberCode(Long.valueOf(memberCode));
				ck.setOrigin(CheckCodeOriginEnum.EMAIL_FINDLOGINPWD.getValue());
				ck.setEmail(email);
				MemberCriteria memberCriteria = memberService
						.queryMemberCriteriaByMemberCodeNsTx(Long
								.valueOf(memberCode));
				if (memberCriteria != null) {
					// 重发找回登录密码邮件
					if (checkCodeService.resendMail(
							memberCriteria.getVerifyName(), recAddress, ck,
							mailUrlFindLoginPwd,
							Resource.TEMPLATEID_EMAIL_LOGINPWD,
							Resource.MAIL_LOGIN_PWD_FIND, EFFECTIVE_TIME)) {
						result = "Y";
					}
				}
			}
		}
		out.print(result);
		out.flush();
		out.close();
	}

	/**
	 * 检查Session是否过期
	 * 
	 * @param request
	 * @return
	 */
	private static boolean hasSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute(SESSION_KEY_MEMBER_CODE) == null
				|| session.getAttribute(SESSION_KEY_LOGIN_OBJECT) == null) {
			return false;
		}
		return true;
	}

	private static String getSignData() {
		SessionFindOjbect sOject = (SessionFindOjbect) RequestLocal
				.getSession().getAttribute(SESSION_KEY_LOGIN_OBJECT);
		if (sOject == null) {
			return "";
		}
		String args = RequestLocal.getSession().getAttribute(
				SESSION_KEY_MEMBER_CODE) == null ? "" : RequestLocal
				.getSession().getAttribute(SESSION_KEY_MEMBER_CODE).toString()
				+ sOject.getLoginName().toString();
		return args;
	}

	/**
	 * 检查Session是否合法
	 * 
	 * @param signStr
	 * @return
	 */
	private static boolean isSignature(String signStr) {
		String newSignatureData = SignatureHelper
				.generateAppSignature(getSignData());
		return StringUtils.equals(signStr, newSignatureData);
	}

	/**
	 * 检查是否有效<br>
	 * 有效则返回true,否则返回false;
	 * 
	 * @return
	 */
	private boolean checkEffectiveTime(Date createtime) {
		Date date = DateUtil.skipDateTime(createtime, EFFECTIVE_TIME);
		if (date != null) {
			Date sysDate = new Date();
			if (sysDate.after(date)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean checkEffectiveMobile(Date createTime) {
		if (createTime != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(createTime);
			cal.add(Calendar.MINUTE, EFFECTIVE_TIME_MOBILE);
			Date date = cal.getTime();
			Date sysDate = new Date();
			if (sysDate.after(date)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	protected class SessionFindOjbect {
		String memberCode;
		String loginName;
		String mobile;
		String signData;
		String checkCode;
		String checkCode2;
		String userType;
		String step;

		public String getMemberCode() {
			return memberCode;
		}

		public void setMemberCode(String memberCode) {
			this.memberCode = memberCode;
		}

		public String getLoginName() {
			return loginName;
		}

		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getSignData() {
			return signData;
		}

		public void setSignData(String signData) {
			this.signData = signData;
		}

		public String getCheckCode() {
			return checkCode;
		}

		public void setCheckCode(String checkCode) {
			this.checkCode = checkCode;
		}

		public String getCheckCode2() {
			return checkCode2;
		}

		public void setCheckCode2(String checkCode2) {
			this.checkCode2 = checkCode2;
		}

		public String getUserType() {
			return userType;
		}

		public void setUserType(String userType) {
			this.userType = userType;
		}

		public String getStep() {
			return step;
		}

		public void setStep(String step) {
			this.step = step;
		}
	}

	public void setToresultview(String toresultview) {
		this.toresultview = toresultview;
	}

	public void setUpdateloginpwdpage(String updateloginpwdpage) {
		this.updateloginpwdpage = updateloginpwdpage;
	}

	public void setFindLoginPwdFirstpage(String findLoginPwdFirstpage) {
		this.findLoginPwdFirstpage = findLoginPwdFirstpage;
	}

	public void setFindLoginPwdSecondpage(String findLoginPwdSecondpage) {
		this.findLoginPwdSecondpage = findLoginPwdSecondpage;
	}

	public void setFindLoginPwdThirdpage(String findLoginPwdThirdpage) {
		this.findLoginPwdThirdpage = findLoginPwdThirdpage;
	}

	public void setFindloginpwdresultpage(String findloginpwdresultpage) {
		this.findloginpwdresultpage = findloginpwdresultpage;
	}

	public void setTocorpresultView(String tocorpresultView) {
		this.tocorpresultView = tocorpresultView;
	}

	public void setUpdatecorploginpwdpage(String updatecorploginpwdpage) {
		this.updatecorploginpwdpage = updatecorploginpwdpage;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setOperatorServiceFacade(
			OperatorServiceFacade operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public void setToresultviewwithoutlogin(String toresultviewwithoutlogin) {
		this.toresultviewwithoutlogin = toresultviewwithoutlogin;
	}

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setIndividualInfoService(
			IndividualInfoService individualInfoService) {
		this.individualInfoService = individualInfoService;
	}

	public void setFindLoginPwdSelect(String findLoginPwdSelect) {
		this.findLoginPwdSelect = findLoginPwdSelect;
	}

	public void setFindLoginPwdMobile(String findLoginPwdMobile) {
		this.findLoginPwdMobile = findLoginPwdMobile;
	}

	public void setFindLoginPwdEmail(String findLoginPwdEmail) {
		this.findLoginPwdEmail = findLoginPwdEmail;
	}

	public void setFindLoginPwdCorp(String findLoginPwdCorp) {
		this.findLoginPwdCorp = findLoginPwdCorp;
	}

	public void setSafeQuestionVerifyService(
			SafeQuestionVerifyService safeQuestionVerifyService) {
		this.safeQuestionVerifyService = safeQuestionVerifyService;
	}

	public void setMemberUnlockService(MemberUnlockService memberUnlockService) {
		this.memberUnlockService = memberUnlockService;
	}

}
