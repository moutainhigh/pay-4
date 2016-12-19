/**
 *  File: PayPwdManageController.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-9-2   lihua     Create
 *
 */
package com.pay.app.controller.base.usersafe;

import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.comm.Resource;
import com.pay.acc.common.MaConstant;
import com.pay.acc.service.account.AccountUnlockService;
import com.pay.acc.service.account.SafeQuestionVerifyService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.account.dto.VerifyResultDto;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.annotation.PutAppLog;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.api.wrapper.SafeControllerWrapper;
import com.pay.app.base.exception.LoginTimeOutException;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.AppConf;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.common.safequestion.Safequestion;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.MemberDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.CheckUtil;
import com.pay.util.FormatDate;
import com.pay.util.OutputUtil;
import com.pay.util.SignatureHelper;

/**
 * 
 */
public class PayPwdManageController extends UserSafeManageController {

	// 返回状态正常
	private final static int result_success = 1;
	// 返回状态异常
	private final static int result_failure = 0;

	private IMessageDigest iMessageDigest;

	private EnterpriseBaseService enterpriseBaseService;

	private SafeQuestionVerifyService safeQuestionVerifyService;

	private AccountUnlockService accountUnlockService;

	private CheckCodeService checkCodeService;

	private String mailUrlPwd;
	private String mailUrlPwdCorp;

	private static final String[] PWD_UPDATEPAYPWD = new String[] {
			"oldpaypassword", "newpaypassword1", "newpaypassword2" };

	private static final String[] PWD_FINDPAYPWD = new String[] {
			"newpaypassword1", "newpaypassword2" };

	public void setMailUrlPwd(String mailUrlPwd) {
		this.mailUrlPwd = mailUrlPwd;
	}

	public void setMailUrlPwdCorp(String mailUrlPwdCorp) {
		this.mailUrlPwdCorp = mailUrlPwdCorp;
	}

	/**
	 * 支付密码修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATEPAYPWD")
	@PutAppLog(logType = CutsConstants.USER_LOG_UPDATEPAYPWD)
	public ModelAndView paypassword(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		SafeControllerWrapper request = new SafeControllerWrapper(req,
				PWD_UPDATEPAYPWD);
		Map<String, Object> resMsg = new HashMap<String, Object>();

		// 如果是GET方式提交
		if (request.getMethod().equalsIgnoreCase("GET")) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("errorRequestMethod"));
			resMsg.put("classcss", "feedback warning");
			return new ModelAndView(getToresultview(), resMsg);
		}

		// 1.验证输入的登录密码是否正确 2.重置新的支付用户密码
		String memberCode = "";
		String mode = null == request.getParameter("paypwdurl") ? "" : request
				.getParameter("paypwdurl");
		String loginPwd = null == request.getParameter("loginPwd") ? ""
				: request.getParameter("loginPwd");
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		if (null != loginSession) {
			memberCode = null == loginSession.getMemberCode() ? ""
					: loginSession.getMemberCode();
		}

		String oldpaypassword = null == request.getParameter("oldpaypassword") ? ""
				: request.getParameter("oldpaypassword");
		String paypassword0 = null == request.getParameter("newpaypassword1") ? ""
				: request.getParameter("newpaypassword1");
		String paypassword = null == request.getParameter("newpaypassword2") ? ""
				: request.getParameter("newpaypassword2");

		int result = result_failure;
		if (null != memberCode && !"".equals(memberCode)) {
			if ("set".equals(mode)) {// 验证登录密码(状态:设置) 切换到acc库
				ResultDto resultdto = getMemberService().verifyLoginPwdNsTx(
						Long.valueOf(loginSession.getMemberCode()), loginPwd);
				if (null == resultdto.getErrorCode()) {
					result = result_success;
				}
			} else {
				if (null != oldpaypassword && !"".equals(oldpaypassword)) {// 验证支付密码是否正确(状态:修改)
					Long operatorId = SessionHelper.getOperatorIdBySession();
					ResultDto resultDto = getAcctService()
							.doVerifyPayPasswordRnTx(Long.valueOf(memberCode),
									 AcctTypeEnum.BASIC_CNY.getCode(), oldpaypassword,
									operatorId);
					if (null == resultDto.getErrorCode()) {
						result = result_success;
					}
				}
			}

			// 两次输入的密码不一致
			if (!paypassword0.equals(paypassword)) {
				resMsg.put("returninfo",
						MessageConvertFactory.getMessage("differentPwd"));
				resMsg.put("classcss", "feedback warning");
				if (SessionHelper.isCorpLogin()) {
					return new ModelAndView(getUpdateCorpPaypwdpage(), resMsg);
				}
				return new ModelAndView(getUpdatepaypwdpage(), resMsg);
			}

			// 验证支付密码是否包含特殊字符
			if (CheckUtil.isPwdContainsSpecialCharacter(paypassword0)) {
				resMsg.put("returninfo",
						MessageConvertFactory.getMessage("specialCharInPayPwd"));
				resMsg.put("classcss", "feedback warning");
				if (SessionHelper.isCorpLogin()) {
					return new ModelAndView(getUpdateCorpPaypwdpage(), resMsg);
				}
				return new ModelAndView(getUpdatepaypwdpage(), resMsg);
			}

			// 支付密码格式不正确
			if (!CheckUtil.checkPayPwd(paypassword0)) {
				resMsg.put("returninfo",
						MessageConvertFactory.getMessage("invalidePayPwd"));
				resMsg.put("classcss", "feedback warning");
				if (SessionHelper.isCorpLogin()) {
					return new ModelAndView(getUpdateCorpPaypwdpage(), resMsg);
				}
				return new ModelAndView(getUpdatepaypwdpage(), resMsg);
			}

			// 验证支付密码和登录密码是否一致 start
			MemberDto member = getMemberService().findByMemberCode(
					Long.parseLong(memberCode));
			if (member != null) {
				if (member.getLoginPwd() != null
						&& member.getLoginPwd().equals(
								iMessageDigest.genMessageDigest(paypassword
										.getBytes()))) {
					resMsg.put("returninfo", MessageConvertFactory
							.getMessage("errorForSamePassword"));
					resMsg.put("classcss", "feedback warning");
					if (SessionHelper.isCorpLogin()) {
						return new ModelAndView(getUpdateCorpPaypwdpage(),
								resMsg);
					}
					return new ModelAndView(getUpdatepaypwdpage(), resMsg);
				}
			}
			// 验证支付密码和登录密码是否一致 end

			if (result_success == result) { // 验证密码正确
				if (null != paypassword && !"".equals(paypassword)) {// 用户密码重置
					ResultDto results = getAcctService().doResetPayPwdRnTx(
							Long.valueOf(memberCode),
							 AcctTypeEnum.BASIC_CNY.getCode(), paypassword, null);
					if (null == results.getErrorCode()) {// 状态1 为正常
						if (SessionHelper.isCorpLogin()) {
							resMsg.put("returninfo", MessageConvertFactory
									.getMessage("changeSuccess"));
							resMsg.put("classcss", "feedback succeed");
							return new ModelAndView(getToCorpResultview(),
									resMsg);
						}
						resMsg.put("returninfo", MessageConvertFactory
								.getMessage("changeSuccess"));
						resMsg.put("classcss", "feedback succeed");
						return new ModelAndView(getToresultview(), resMsg);
					}
				}
			} else {// 密码输入错误
				if ("set".equals(mode)) {
					if (SessionHelper.isCorpLogin()) {
						return new ModelAndView(getSetCorpPaypwdpage())
								.addObject("returninfo", MessageConvertFactory
										.getMessage("loginPwdError"));
					}
					return new ModelAndView(getSetpaypwdpage()).addObject(
							"returninfo",
							MessageConvertFactory.getMessage("loginPwdError"));
				} else {
					if (SessionHelper.isCorpLogin()) {
						return new ModelAndView(getUpdateCorpPaypwdpage())
								.addObject("returninfo", MessageConvertFactory
										.getMessage("loginPwdError"));
					}
					return new ModelAndView(getUpdatepaypwdpage()).addObject(
							"returninfo",
							MessageConvertFactory.getMessage("loginPwdError"));
				}
			}
		}
		if (SessionHelper.isCorpLogin()) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("changefailure"));
			resMsg.put("classcss", "feedback warning");
			return new ModelAndView(getToCorpResultview(), resMsg);
		}
		resMsg.put("returninfo",
				MessageConvertFactory.getMessage("changefailure"));
		resMsg.put("classcss", "feedback warning");
		return new ModelAndView(getToresultview(), resMsg);
	}

	// /**
	// * 付款到支付的设置支付密码
	// *
	// * @param request
	// * @param response
	// * @throws Exception
	// */
	// @OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATEPAYPWD")
	// public void setPaypwdForpay2(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	//
	// LoginSession loginSession = SessionHelper.getLoginSession(request);
	// String memberCode = "";
	// String username = "";
	// String resultinfo = "N";
	// response.setContentType("text/plain;charset=UTF-8");
	// PrintWriter out = null;
	// out = response.getWriter();
	// response.setHeader("Cache-Control", "no-cache");
	// String loginPwd = null == request.getParameter("loginPwd")
	// ? ""
	// : request.getParameter("loginPwd");
	// String paypwd = null == request.getParameter("payPassWord")
	// ? ""
	// : request.getParameter("payPassWord");
	// if (null != loginSession) {
	// memberCode = null == loginSession.getMemberCode()
	// ? ""
	// : loginSession.getMemberCode();
	// username = null == loginSession.getLoginName() ? "" : loginSession
	// .getLoginName();
	// }
	//
	//
	// MemberDto member =
	// getMemberService().findByMemberCode(Long.parseLong(memberCode));
	// if(member!=null){
	// // 验证支付密码和登录密码是否一致 start
	// if(member.getLoginPwd()!=null &&
	// member.getLoginPwd().equals(iMessageDigest.genMessageDigest(paypwd.getBytes()))){
	// resultinfo = "N2";//如果支付密码和登录密码一致
	// }
	// //验证支付密码和登录密码是否一致 end
	// }else{
	//
	//
	// // if (!"".equals(username)) {
	// // Long m = getUserCheckServiceFacade().checkUser(username, loginPwd);
	// // if (null != m) {
	// if (null != paypwd && !"".equals(paypwd)) {// 用户密码重置
	// ResultDto resultDto =
	// getAcctService().doResetPayPwdRnTx(Long.valueOf(memberCode),
	//  AcctTypeEnum.BASIC_CNY.getCode(), paypwd);
	// if (null == resultDto.getErrorCode()) {// 状态1 为正常
	// resultinfo = "Y";
	// }
	// }
	// // } else {
	// // resultinfo = "N1";// 登录密码错误
	// // }
	// // }
	// }
	//
	// out.print(resultinfo);
	// out.flush();
	// out.close();
	// }

	/**
	 * 通过安全问题找回支付密码-输入支付账号 step 1
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_FOUNDPAYPWD")
	public ModelAndView gotogetsafequestion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> resMsg = new HashMap<String, Object>();

		LoginSession loginSession = SessionHelper.getLoginSession(request);
		MemberCriteria memberCriteria = null;
		String memberCode = null == loginSession.getMemberCode() ? ""
				: loginSession.getMemberCode();
		String randCode = request.getSession().getAttribute("rand") == null ? ""
				: request.getSession().getAttribute("rand").toString();
		String code = request.getParameter("randCode") == null ? "" : request
				.getParameter("randCode");
		String username = request.getParameter("username");
		if (code.toLowerCase().equals(randCode.toLowerCase())) {
			request.getSession().removeAttribute("rand");
			if (null != username && !"".equals(username)
					&& username.equals(loginSession.getLoginName())) {// 根据用户名查询会员信息
				if (null != memberCode && !"".equals(memberCode)) {
					// 切换到acc库
					memberCriteria = getMemberService()
							.queryMemberCriteriaByMemberCodeNsTx(
									Long.valueOf(memberCode));
					if (null != memberCriteria) {
						if (SessionHelper.isCorpLogin()) {
							resMsg.put("safequestion", Safequestion
									.getsafequestion(memberCriteria
											.getQuestionId()));
							resMsg.put("questionId",
									memberCriteria.getQuestionId());
							return new ModelAndView(getCorpnextquestion(),
									resMsg);
						}
						resMsg.put("safequestion",
								Safequestion.getsafequestion(memberCriteria
										.getQuestionId()));
						resMsg.put("questionId", memberCriteria.getQuestionId());
						return new ModelAndView(getNextquestion(), resMsg);
					}

				}
			} else {
				return tofoundpaypwdbysafequestionpage(request, response)
						.addObject(
								"returninfo",
								MessageConvertFactory
										.getMessage("notMyAccount"));
			}
		} else {
			request.getSession().removeAttribute("rand");
			return tofoundpaypwdbysafequestionpage(request, response)
					.addObject("returninfo",
							MessageConvertFactory.getMessage("randCode"));
		}
		request.getSession().removeAttribute("rand");
		if (SessionHelper.isCorpLogin()) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("changefailure"));
			resMsg.put("classcss", "feedback warning");
			return new ModelAndView(getToCorpResultview(), resMsg);
		}
		resMsg.put("returninfo",
				MessageConvertFactory.getMessage("changefailure"));
		resMsg.put("classcss", "feedback warning");
		return new ModelAndView(getToresultview(), resMsg);
	}

	/**
	 * 通过安全问题找回支付密码-验证安全问题 step 2
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_FOUNDPAYPWD")
	public ModelAndView gotogetnewpwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/*
		 * 1.根据memberCode查询会员信息 2.将会员信息里的安全问题和答案与用户输入 的比对 3.步骤2验证正确则重置用户支付密码
		 */

		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String memberCode = "";
		String safequestion = null == request.getParameter("questionId") ? ""
				: request.getParameter("questionId");
		String answer = null == request.getParameter("safeanswer") ? ""
				: request.getParameter("safeanswer");
		String safequestioninfo = null == request.getParameter("safequestion") ? ""
				: request.getParameter("safequestion");
		if (null != loginSession) {
			memberCode = null == loginSession.getMemberCode() ? ""
					: loginSession.getMemberCode();
		}
		Map<String, Object> resMsg = new HashMap<String, Object>();
		if (null != memberCode && !"".equals(memberCode)) {

			MaResultDto maResultDto = safeQuestionVerifyService.doVerify(
					Long.valueOf(memberCode), Integer.parseInt(safequestion),
					answer);

			// 验证成功
			if (maResultDto.getResultStatus() == MaConstant.SECCESS) {
				String token = SignatureHelper.generateAppSignature(memberCode
						+ loginSession.getLoginName());
				request.getSession().setAttribute("UPDATE_PWD",
						String.valueOf(System.currentTimeMillis()));
				if (SessionHelper.isCorpLogin()) {
					return new ModelAndView(getCorpnextpwd()).addObject(
							"token", token);
				}
				return new ModelAndView(getNextpwd()).addObject("token", token);
			} else if (maResultDto.getResultStatus() == MaConstant.ERROR_NOT_LOCK) {
				// 验证失败，但是没有锁定
				VerifyResultDto vrd = (VerifyResultDto) maResultDto.getObject();
				resMsg.put("returninfo", MessageFormat.format(
						MessageConvertFactory
								.getMessage("answerfailureandnotice"),
						new Object[] { vrd.getTotalTime(),
								vrd.getTotalTime() - vrd.getLeavingTime() }));
				resMsg.put("safequestion", safequestioninfo);
				resMsg.put("questionId", safequestion);
				if (SessionHelper.isCorpLogin()) {
					return new ModelAndView(getCorpnextquestion(), resMsg);
				}
				return new ModelAndView(getNextquestion(), resMsg);
			} else if (maResultDto.getResultStatus() == MaConstant.ERROR_AND_LOCK) {
				// 验证失败，并且锁定
				VerifyResultDto vrd = (VerifyResultDto) maResultDto.getObject();
				resMsg.put("returninfo", MessageFormat.format(
						MessageConvertFactory
								.getMessage("answerfailureandlocknotice"),
						new Object[] { vrd.getTotalTime(),
								vrd.getLeavingMinute() }));
				resMsg.put("safequestion", safequestioninfo);
				resMsg.put("questionId", safequestion);
				if (SessionHelper.isCorpLogin()) {
					return new ModelAndView(getCorpnextquestion(), resMsg);
				}
				return new ModelAndView(getNextquestion(), resMsg);
			}

			// ResultDto resultDto =
			// getMemberService().validateSecurMemberQuestionWidthMemberInfo(Long.valueOf(memberCode),Integer.parseInt(safequestion),
			// answer);
			// if (null == resultDto.getErrorCode()) {// 验证成功
			//
			// String token =
			// SignatureHelper.generateAppSignature(memberCode+loginSession.getLoginName());
			//
			// if(SessionHelper.isCorpLogin()){
			// return new ModelAndView(getCorpnextpwd()).addObject("token",
			// token);
			// }
			// return new ModelAndView(getNextpwd()).addObject("token", token);
			// }
		}
		resMsg.put("returninfo",
				MessageConvertFactory.getMessage("answerfailure"));
		resMsg.put("safequestion", safequestioninfo);
		resMsg.put("questionId", safequestion);
		if (SessionHelper.isCorpLogin()) {
			return new ModelAndView(getCorpnextquestion(), resMsg);
		}
		return new ModelAndView(getNextquestion(), resMsg);
	}

	/**
	 * 通过安全问题找回支付密码-通过安全问题重置密码 step 3
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATEPAYPWD")
	@PutAppLog(logType = CutsConstants.USER_LOG_FINDPAYPWD)
	public ModelAndView updatepaypwdbysafequestion(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		SafeControllerWrapper request = new SafeControllerWrapper(req,
				PWD_FINDPAYPWD);
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String memberCode = "";
		if (null != loginSession) {
			memberCode = null == loginSession.getMemberCode() ? ""
					: loginSession.getMemberCode();
		}

		Map<String, Object> resMsg = new HashMap<String, Object>();

		// 如果是GET方式提交
		if (request.getMethod().equalsIgnoreCase("GET")) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("errorRequestMethod"));
			resMsg.put("classcss", "feedback warning");
			return new ModelAndView(getToresultview(), resMsg);
		}

		String paypassword0 = request.getParameter("newpaypassword1") == null ? ""
				: request.getParameter("newpaypassword1").toString();

		String paypassword = request.getParameter("newpaypassword2") == null ? ""
				: request.getParameter("newpaypassword2").toString();

		String token = request.getParameter("token") == null ? "" : request
				.getParameter("token");

		String mytoken = SignatureHelper.generateAppSignature(memberCode
				+ loginSession.getLoginName());

		if (!token.equals(mytoken)
				|| request.getSession().getAttribute("UPDATE_PWD") == null) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("differentMember"));
			resMsg.put("classcss", "feedback warning");
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(getToCorpResultview(), resMsg);
			}
			return new ModelAndView(getToresultview(), resMsg);
		}

		// 两次输入的密码不一致
		if (!paypassword0.equals(paypassword)) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("differentPwd"));
			resMsg.put("classcss", "feedback warning");
			resMsg.put("token", token);
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(getCorpnextpwd(), resMsg);
			}
			return new ModelAndView(getNextpwd(), resMsg);
		}

		// 验证支付密码是否包含特殊字符
		if (CheckUtil.isPwdContainsSpecialCharacter(paypassword0)) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("specialCharInPayPwd"));
			resMsg.put("classcss", "feedback warning");
			resMsg.put("token", token);
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(getCorpnextpwd(), resMsg);
			}
			return new ModelAndView(getNextpwd(), resMsg);
		}

		// 支付密码格式不正确
		if (!CheckUtil.checkPayPwd(paypassword0)) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("invalidePayPwd"));
			resMsg.put("classcss", "feedback warning");
			resMsg.put("token", token);
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(getCorpnextpwd(), resMsg);
			}
			return new ModelAndView(getNextpwd(), resMsg);
		}

		// 验证支付密码和登录密码是否一致 start
		MemberDto member = getMemberService().findByMemberCode(
				Long.parseLong(memberCode));
		if (member != null) {
			if (member.getLoginPwd() != null
					&& member.getLoginPwd().equals(
							iMessageDigest.genMessageDigest(paypassword
									.getBytes()))) {
				resMsg.put("returninfo", MessageConvertFactory
						.getMessage("errorForSamePassword"));
				resMsg.put("classcss", "feedback warning");
				resMsg.put("token", token);
				if (SessionHelper.isCorpLogin()) {
					return new ModelAndView(getCorpnextpwd(), resMsg);
				}
				return new ModelAndView(getNextpwd(), resMsg);
			}
		}
		// 验证支付密码和登录密码是否一致 end

		if (StringUtils.isNotBlank(paypassword)) {
			if (null != memberCode && !"".equals(memberCode)) {
				ResultDto resultdto = getAcctService().doResetPayPwdRnTx(
						Long.valueOf(memberCode),  AcctTypeEnum.BASIC_CNY.getCode(),
						paypassword, null);
				if (null == resultdto.getErrorCode()) {
					accountUnlockService.unLock(Long.valueOf(memberCode),
							 AcctTypeEnum.BASIC_CNY.getCode());// 账户解锁
					if (SessionHelper.isCorpLogin()) {
						return new ModelAndView(getCorpfoundpwdsucc());
					}
					return new ModelAndView(getFoundpwdsucc());
				}
			}
		} else {
			// 支付密码为空
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("invalidePayPwd"));
			resMsg.put("classcss", "feedback warning");
			resMsg.put("token", token);
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(getCorpnextpwd(), resMsg);
			}
			return new ModelAndView(getNextpwd(), resMsg);
		}

		if (SessionHelper.isCorpLogin()) {
			// resMsg.put("returninfo", MessageConvertFactory
			// .getMessage("oldanswerfailure"));
			return new ModelAndView(getCorpfoundpwdsucc(), resMsg);
		}
		return new ModelAndView(getFoundpwdsucc(), resMsg);
	}

	/**
	 * 跳转至通过邮箱找回支付密码 step 2
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATEPAYPWD")
	@PutAppLog(logType = CutsConstants.USER_LOG_FINDPAYPWD)
	public ModelAndView sendlinktoemail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String randCode = request.getSession().getAttribute("rand") == null ? ""
				: request.getSession().getAttribute("rand").toString();
		String code = request.getParameter("randCode") == null ? "" : request
				.getParameter("randCode");
		String email = null == request.getParameter("email") ? "" : request
				.getParameter("email");
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String memberCode = null == loginSession.getMemberCode() ? ""
				: loginSession.getMemberCode();
		String verifyName = null == loginSession.getVerifyName() ? ""
				: loginSession.getVerifyName();
		List<String> maillist = new ArrayList<String>();
		Map<String, Object> resMsg = new HashMap<String, Object>();

		// 验证是否是注册邮箱1.判断是不是注册邮箱
		CheckCodeDto checkCode = new CheckCodeDto();
		checkCode.setEmail(email);
		checkCode.setOrigin(CheckCodeOriginEnum.EMAIL_FINDPAYPWD.getValue());
		checkCode.setMemberCode(Long.valueOf(memberCode));
		maillist.add(email);
		if (code.toLowerCase().equals(randCode.toLowerCase())
				&& !"".equals(code) && !"".equals(randCode)) {// 判断验证码
			request.getSession().removeAttribute("rand");
			if (!"".equals(memberCode)) {
				MemberCriteria memberCriteria = getMemberService()
						.queryMemberCriteriaByMemberCodeNsTx(
								Long.valueOf(memberCode));
				if (null != memberCriteria) {
					if (null != email && !"".equals(email)) {
						if (memberCriteria.getLoginName().equals(email)) {// 验证邮箱
							if (SessionHelper.isCorpLogin()) {
								EnterpriseBase enterpriseBase = enterpriseBaseService
										.findByMemberCode(Long
												.valueOf(memberCode));
								if (null != enterpriseBase) {
									getMailService().sendMail(
											enterpriseBase.getZhName(),
											maillist, checkCode,
											mailUrlPwdCorp,
											Resource.TEMPLATEID_PAYPWD,
											Resource.MAIL_SUBJECT_PWD);
								}
								resMsg.put("email", email);
								return new ModelAndView(
										getFoundcorppaypwdbyemailresultpage(),
										resMsg);
							} else {
								getMailService().sendMail(verifyName, maillist,
										checkCode, mailUrlPwd,
										Resource.TEMPLATEID_PAYPWD,
										Resource.MAIL_SUBJECT_PWD);
								resMsg.put("email", email);
								return new ModelAndView(
										getFoundpaypwdbyemailresultpage(),
										resMsg);

							}
						} else {// 不是注册邮箱
							return tofoundpaypwdbyemailpage(request, response)
									.addObject(
											"resultinfo",
											MessageConvertFactory
													.getMessage("loginNameError"));
						}
					}
				}
			}
		} else {
			// 验证码错误
			request.getSession().removeAttribute("rand");
			return tofoundpaypwdbyemailpage(request, response).addObject(
					"resultinfo", MessageConvertFactory.getMessage("randCode"));
		}
		request.getSession().removeAttribute("rand");
		return new ModelAndView(getFoundpaypwdbyemailresultpage()).addObject(
				"email", email);

	}

	/**
	 * 找回支付密码重新发送邮件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void findpaypwdbyemailresend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String memberCode = "";
		String email = "";
		String resultinfo = "N";
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = null;
		out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		boolean issend = false;
		if (request.getMethod().equalsIgnoreCase("POST")) {
			List<String> maillist = new ArrayList<String>();
			if (null != loginSession) {
				memberCode = loginSession.getMemberCode();
				email = loginSession.getLoginName();
			}

			if (StringUtils.isNotBlank(memberCode)
					&& StringUtils.isNotBlank(email)) {
				CheckCodeDto checkCode = new CheckCodeDto();
				checkCode.setEmail(email);
				checkCode.setOrigin(CheckCodeOriginEnum.EMAIL_FINDPAYPWD
						.getValue());
				checkCode.setMemberCode(Long.valueOf(memberCode));
				maillist.add(email);
				String verifyName = String
						.valueOf(loginSession.getVerifyName());
				if (SessionHelper.isCorpLogin()) {
					EnterpriseBase enterpriseBase = enterpriseBaseService
							.findByMemberCode(Long.valueOf(memberCode));
					if (null != enterpriseBase) {
						issend = getMailService().sendMail(
								enterpriseBase.getZhName(), maillist,
								checkCode, mailUrlPwdCorp,
								Resource.TEMPLATEID_PAYPWD,
								Resource.MAIL_SUBJECT_PWD);
					}
				} else {
					issend = getMailService().sendMail(verifyName, maillist,
							checkCode, mailUrlPwd, Resource.TEMPLATEID_PAYPWD,
							Resource.MAIL_SUBJECT_PWD);

				}
			}
			if (issend)
				resultinfo = "Y";
		}
		out.print(resultinfo);
		out.flush();
		out.close();
	}

	/**
	 * 验证校验码跳转至修改支付密码页面(邮箱找回)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	// comment by Mack 2016年5月27日
	//@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATEPAYPWD")
	public ModelAndView tofoundpaypasswordbyemailpage(
			HttpServletRequest request, HttpServletResponse response) {

		String checkcode = request.getParameter("code");
		String flag = getMailService().findStatesByCheckCode(checkcode);
		Map<String, Object> resMsg = new HashMap<String, Object>();

		if (Integer.parseInt(flag) == result_success) {
			// 获取CheckCode
			boolean result = false;
			CheckCodeDto checkCode = checkCodeService
					.getByCheckCodeAndOrigin(checkcode,
							CheckCodeOriginEnum.EMAIL_FINDPAYPWD);
			;
			if (checkCode == null) {
				CheckCodeDto chCode = checkCodeService
						.getByCheckCodeAndOrigin(checkcode,
								CheckCodeOriginEnum.POSS_CORP_EMAIL_FINDPAYPWD);
				if (chCode == null || chCode.getStatus() == 2) {
					result = true;
				} else {
					checkCode = chCode;
				}
			}
			if (result) {
				resMsg.put("returninfo",
						MessageConvertFactory.getMessage("nolink"));
				resMsg.put("classcss", "feedback warning");
				return new ModelAndView(getToresultview(), resMsg);
			}
			String memberCode = String.valueOf(checkCode.getMemberCode());
			LoginSession loginSession = null;
			try {
				loginSession = SessionHelper.getLoginSession(request);
				Integer days = new Integer(
						AppConf.get(AppConf.mail_paypwd_interval));
				long time = FormatDate.sceondOfTwoDate(
						checkCode.getCreateTime(), days * 24 * 60);
				if (time == 0) {
					resMsg.put("classcss", "feedback warning");
					resMsg.put("returninfo", MessageConvertFactory
							.getMessage("foundPaypwdEmailValid"));
					if (SessionHelper.isCorpLogin()) {
						return new ModelAndView(getToCorpResultview(), resMsg);
					}
					
					String view = getToresultview();
					
					return new ModelAndView(view, resMsg);
				}
				if (!loginSession.getMemberCode().equals(memberCode)) {
					resMsg.put("returninfo",
							MessageConvertFactory.getMessage("nolink"));
					resMsg.put("classcss", "feedback warning");
					return new ModelAndView(getToresultview(), resMsg);
				}
			} catch(LoginTimeOutException e){
				logger.error("请先登录！",e);
				resMsg.put("returninfo",
						"请先登录！");
				resMsg.put("classcss", "feedback warning");
				return new ModelAndView(getToresultview(), resMsg);
			}catch (Exception e) {
				logger.error("no session:",e);
				resMsg.put("returninfo",
						MessageConvertFactory.getMessage("nolink"));
				resMsg.put("classcss", "feedback warning");
				return new ModelAndView(getToresultview(), resMsg);
			}
			getMailService().updateCheckCodeStates(checkcode);
			request.getSession().setAttribute("UPDATE_PWD",
					String.valueOf(System.currentTimeMillis()));
			return new ModelAndView(getFoundpaypwdbyefromemailpage())
					.addObject("memberCode", memberCode).addObject(
							"token",
							SignatureHelper.generateAppSignature(memberCode
									+ loginSession.getLoginName()));
		} else {
			resMsg.put("returninfo", MessageConvertFactory.getMessage("nolink"));
			resMsg.put("classcss", "feedback warning");
			return new ModelAndView(getToresultview(), resMsg);
		}

	}

	/**
	 * 通过邮件修改支付密码-提交
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws LoginTimeOutException
	 */
	// comment by Mack 2016年5月27日
	//@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATEPAYPWD")
	public ModelAndView updatepaypasswordbyemail(HttpServletRequest req,
			HttpServletResponse response) throws LoginTimeOutException {
		SafeControllerWrapper request = new SafeControllerWrapper(req,
				PWD_FINDPAYPWD);

		String paypassword0 = request.getParameter("newpaypassword1") == null ? ""
				: request.getParameter("newpaypassword1").toString();

		String paypassword = request.getParameter("newpaypassword2") == null ? ""
				: request.getParameter("newpaypassword2").toString();

		Map<String, Object> resMsg = new HashMap<String, Object>();
		// 如果登录用户和请求用户不一致，则认为是非法操作
		LoginSession loginSession = SessionHelper.getLoginSession(request);

		String token = request.getParameter("token") == null ? "" : request
				.getParameter("token");

		String mytoken = SignatureHelper.generateAppSignature(loginSession
				.getMemberCode() + loginSession.getLoginName());

		if (!token.equals(mytoken)
				|| request.getSession().getAttribute("UPDATE_PWD") == null) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("differentMember"));
			resMsg.put("classcss", "feedback warning");
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(getToCorpResultview(), resMsg);
			}
			return new ModelAndView(getToresultview(), resMsg);
		}

		// 如果是用get方式提交更新密码，则认为非法操作
		if (request.getMethod().equals("GET")) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("errorRequestMethod"));
			resMsg.put("classcss", "feedback warning");
			return new ModelAndView(getToresultview());
		}

		if (loginSession == null
				|| StringUtils.isBlank(loginSession.getMemberCode())) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("differentMember"));
			resMsg.put("classcss", "feedback warning");
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(getToCorpResultview(), resMsg);
			}
			return new ModelAndView(getToresultview(), resMsg);
		}

		// 验证支付密码是否包含特殊字符
		if (CheckUtil.isPwdContainsSpecialCharacter(paypassword0)) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("specialCharInPayPwd"));
			resMsg.put("classcss", "feedback warning");
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(getToCorpResultview(), resMsg);
			}
			return new ModelAndView(getToresultview(), resMsg);
		}

		// 两次输入的密码不一致
		if (!paypassword0.equals(paypassword)) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("differentPwd"));
			resMsg.put("classcss", "feedback warning");
			resMsg.put("memberCode", loginSession.getMemberCode());
			resMsg.put("token", token);
			return new ModelAndView(getFoundpaypwdbyefromemailpage(), resMsg);
		}

		// 支付密码格式不正确
		if (!CheckUtil.checkPayPwd(paypassword0)) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("invalidePayPwd"));
			resMsg.put("classcss", "feedback warning");
			resMsg.put("memberCode", loginSession.getMemberCode());
			resMsg.put("token", token);
			return new ModelAndView(getFoundpaypwdbyefromemailpage(), resMsg);
		}

		try {
			// 验证支付密码和登录密码是否一致 start
			MemberDto member = getMemberService().findByMemberCode(
					Long.parseLong(loginSession.getMemberCode()));
			if (member != null) {
				if (member.getLoginPwd() != null
						&& member.getLoginPwd().equals(
								iMessageDigest.genMessageDigest(paypassword
										.getBytes()))) {
					resMsg.put("returninfo", MessageConvertFactory
							.getMessage("errorForSamePassword"));
					resMsg.put("classcss", "feedback warning");
					resMsg.put("memberCode", loginSession.getMemberCode());
					resMsg.put("token", token);
					return new ModelAndView(getFoundpaypwdbyefromemailpage(),
							resMsg);
				}
			}
			// 验证支付密码和登录密码是否一致 end

		} catch (Exception e) {
			// 用户session异常
			resMsg.put("returninfo", MessageConvertFactory.getMessage("nolink"));
			resMsg.put("classcss", "feedback warning");
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(getToCorpResultview(), resMsg);
			}
			return new ModelAndView(getToresultview(), resMsg);
		}

		if (null != paypassword && !"".equals(paypassword)) {
			ResultDto resultdto = getAcctService().doResetPayPwdRnTx(
					Long.valueOf(loginSession.getMemberCode()),
					 AcctTypeEnum.BASIC_CNY.getCode(), paypassword, null);
			if (null == resultdto.getErrorCode()) {
				accountUnlockService.unLock(
						Long.valueOf(loginSession.getMemberCode()),
						 AcctTypeEnum.BASIC_CNY.getCode());// 账户解锁
				resMsg.put("returninfo",
						MessageConvertFactory.getMessage("changeSuccess"));
				resMsg.put("classcss", "feedback succeed");
				if (SessionHelper.isCorpLogin()) {
					return new ModelAndView(getToCorpResultview(), resMsg);
				}
				return new ModelAndView(getToresultview(), resMsg);
			}
		}
		resMsg.put("returninfo",
				MessageConvertFactory.getMessage("changefailure"));
		resMsg.put("classcss", "feedback warning");
		if (SessionHelper.isCorpLogin()) {
			return new ModelAndView(getToCorpResultview(), resMsg);
		}
		return new ModelAndView(getToresultview(), resMsg);
	}

	/**
	 * 向手机发送重置支付密码短信
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATEPAYPWD")
	public ModelAndView foundpaypwdbymobile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String mobile = null == request.getParameter("mobile") ? "" : request
				.getParameter("mobile");
		List<String> mobilelist = new ArrayList<String>();
		mobilelist.add(mobile);
		MemberCriteria memberCriteria = null;
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String memberCode = null == loginSession.getMemberCode() ? ""
				: loginSession.getMemberCode();
		String randCode = request.getSession().getAttribute("rand") == null ? ""
				: request.getSession().getAttribute("rand").toString();
		String code = request.getParameter("randCode") == null ? "" : request
				.getParameter("randCode");

		if (code.toLowerCase().equals(randCode.toLowerCase())) {
			request.getSession().removeAttribute("rand");
			if (!"".equals(memberCode)) {
				memberCriteria = getMemberService()
						.queryMemberCriteriaByMemberCodeNsTx(
								Long.valueOf(memberCode));
				if (null != memberCriteria) {
					if (null != mobile && !"".equals(mobile)) {//
						if (memberCriteria.getLoginName().equals(mobile)) {
							getSmsService().sendSms(
									memberCode,
									CheckCodeOriginEnum.SMS_FINDPAYPWD
											.getValue(),
									memberCriteria.getVerifyName(), mobilelist,
									Resource.TEMPLATEID_SMS_PAYPWD);
						} else {
							return tofindsafepwdbymobile(request, response)
									.addObject(
											"returninfo",
											MessageConvertFactory
													.getMessage("loginNameError1"));
						}

					}
				}
			}
		} else {// 验证码错误
			request.getSession().removeAttribute("rand");
			return tofindsafepwdbymobile(request, response).addObject(
					"returninfo", MessageConvertFactory.getMessage("randCode"));
		}
		request.getSession().removeAttribute("rand");
		if (SessionHelper.isCorpLogin()) {
			return new ModelAndView(getFoundcorpsafepwdbymobilepage())
					.addObject("mobile", mobile.substring(7, 11)).addObject(
							"currentMobile", mobile);
		}
		return new ModelAndView(getFoundsafepwdbymobilepage()).addObject(
				"mobile", mobile.substring(7, 11)).addObject("currentMobile",
				mobile);

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
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		response.setContentType("text/plain;charset=UTF-8");
		String result = "N";
		boolean istrue = false;
		PrintWriter out = null;
		out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		if (request.getMethod().equalsIgnoreCase("POST")) {
			String mobile = "";
			String memberCode = "";
			if (null != loginSession) {
				memberCode = loginSession.getMemberCode();
				mobile = loginSession.getLoginName();
			}
			MemberCriteria memberCriteria = null;
			if (StringUtils.isNotBlank(memberCode)
					&& StringUtils.isNotBlank(mobile)) {
				List<String> mobilelist = new ArrayList<String>();
				mobilelist.add(mobile);
				memberCriteria = getMemberService()
						.queryMemberCriteriaByMemberCodeNsTx(
								Long.valueOf(memberCode));
				if (null != memberCriteria) {
					if (null != mobilelist && mobilelist.size() > 0) {
						istrue = getSmsService().sendSms(memberCode,
								CheckCodeOriginEnum.SMS_FINDPAYPWD.getValue(),
								memberCriteria.getVerifyName(), mobilelist,
								Resource.TEMPLATEID_SMS_PAYPWD);
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
	 * 验证验证码找回支付密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws LoginTimeOutException
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_FOUNDPAYPWD")
	public ModelAndView foundpaypwdbymobileverify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		Map<String, Object> resMsg = new HashMap<String, Object>();
		String memberCode = null == loginSession.getMemberCode() ? ""
				: loginSession.getMemberCode();
		String checkCode = request.getParameter("checkCode");
		if (!"".equals(memberCode)) {
			CheckCodeDto ck = getMailService().getByMemerCode(memberCode,
					CheckCodeOriginEnum.SMS_FINDPAYPWD.getValue());
			if (null != checkCode && ck.getCheckCode().equals(checkCode)) {

				// 验证手机短信是否过期
				Integer minutes = new Integer(AppConf.get(AppConf.sms_interval));
				long time = FormatDate.sceondOfTwoDate(ck.getCreateTime(),
						minutes);
				if (time == 0) {
					resMsg.put("returninfo", MessageConvertFactory
							.getMessage("foundPaypwdMobileValid"));
					resMsg.put("classcss", "feedback warning");
					if (SessionHelper.isCorpLogin()) {
						return new ModelAndView(getToCorpResultview(), resMsg);
					}
					return new ModelAndView(getToresultview(), resMsg);
				}
				request.getSession().setAttribute("UPDATE_PWD",
						String.valueOf(System.currentTimeMillis()));
				if (SessionHelper.isCorpLogin()) {
					return new ModelAndView(getSetcorppaypwdbymobile())
							.addObject("token", SignatureHelper
									.generateAppSignature(memberCode
											+ loginSession.getLoginName()));
				}
				return new ModelAndView(getSetpaypwdbymobile()).addObject(
						"token",
						SignatureHelper.generateAppSignature(memberCode
								+ loginSession.getLoginName()));
			} else {// 手机验证码不正确
				resMsg.put("returninfo",
						MessageConvertFactory.getMessage("validatorError"));
				resMsg.put("classcss", "feedback warning");
				if (SessionHelper.isCorpLogin()) {
					return new ModelAndView(getToCorpResultview(), resMsg);
				}
				return new ModelAndView(getToresultview(), resMsg);
			}
		}
		resMsg.put("returninfo",
				MessageConvertFactory.getMessage("mobileValid"));
		resMsg.put("classcss", "feedback warning");
		if (SessionHelper.isCorpLogin()) {
			return new ModelAndView(getToCorpResultview(), resMsg);
		}
		return new ModelAndView(getToresultview(), resMsg);
	}

	/**
	 * 通过手机找回支付密码(提交)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATEPAYPWD")
	public ModelAndView updatepaypwdbymobile(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		SafeControllerWrapper request = new SafeControllerWrapper(req,
				PWD_FINDPAYPWD);
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		Map<String, Object> resMsg = new HashMap<String, Object>();

		// 如果是GET方式提交
		if (request.getMethod().equalsIgnoreCase("GET")) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("errorRequestMethod"));
			resMsg.put("classcss", "feedback warning");
			return new ModelAndView(getToresultview(), resMsg);
		}

		String paypassword0 = request.getParameter("newpaypassword1") == null ? ""
				: request.getParameter("newpaypassword1").toString();

		String paypassword = request.getParameter("newpaypassword2") == null ? ""
				: request.getParameter("newpaypassword2").toString();

		String memberCode = null == loginSession.getMemberCode() ? ""
				: loginSession.getMemberCode();

		String token = request.getParameter("token") == null ? "" : request
				.getParameter("token");

		String mytoken = SignatureHelper.generateAppSignature(loginSession
				.getMemberCode() + loginSession.getLoginName());

		if (!token.equals(mytoken)
				|| request.getSession().getAttribute("UPDATE_PWD") == null) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("differentMember"));
			resMsg.put("classcss", "feedback warning");
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(getToCorpResultview(), resMsg);
			}
			return new ModelAndView(getToresultview(), resMsg);
		}

		// 两次输入的密码不一致
		if (!paypassword0.equals(paypassword)) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("differentPwd"));
			resMsg.put("classcss", "feedback warning");
			resMsg.put("token", token);
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(getSetcorppaypwdbymobile(), resMsg);
			}
			return new ModelAndView(getSetpaypwdbymobile(), resMsg);
		}

		// 验证支付密码是否包含特殊字符
		if (CheckUtil.isPwdContainsSpecialCharacter(paypassword0)) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("specialCharInPayPwd"));
			resMsg.put("classcss", "feedback warning");
			resMsg.put("token", token);
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(getSetcorppaypwdbymobile(), resMsg);
			}
			return new ModelAndView(getSetpaypwdbymobile(), resMsg);
		}

		// 支付密码格式不正确
		if (!CheckUtil.checkPayPwd(paypassword0)) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("invalidePayPwd"));
			resMsg.put("classcss", "feedback warning");
			resMsg.put("token", token);
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(getSetcorppaypwdbymobile(), resMsg);
			}
			return new ModelAndView(getSetpaypwdbymobile(), resMsg);
		}

		// 验证支付密码和登录密码是否一致 start
		MemberDto member = getMemberService().findByMemberCode(
				Long.parseLong(memberCode));
		if (member != null) {
			if (member.getLoginPwd() != null
					&& member.getLoginPwd().equals(
							iMessageDigest.genMessageDigest(paypassword
									.getBytes()))) {
				resMsg.put("returninfo", MessageConvertFactory
						.getMessage("errorForSamePassword"));
				resMsg.put("classcss", "feedback warning");
				resMsg.put("token", token);
				if (SessionHelper.isCorpLogin()) {
					return new ModelAndView(getSetcorppaypwdbymobile(), resMsg);
				}
				return new ModelAndView(getSetpaypwdbymobile(), resMsg);
			}
		}
		// 验证支付密码和登录密码是否一致 end

		if (!"".equals(memberCode) && !"".equals(paypassword)) {
			ResultDto resultdto = getAcctService().doResetPayPwdRnTx(
					Long.valueOf(memberCode),  AcctTypeEnum.BASIC_CNY.getCode(),
					paypassword, null);
			if (null == resultdto.getErrorCode()) {
				accountUnlockService.unLock(Long.valueOf(memberCode),
						 AcctTypeEnum.BASIC_CNY.getCode());// 解锁账户
				resMsg.put("returninfo",
						MessageConvertFactory.getMessage("changeSuccess"));
				resMsg.put("classcss", "feedback succeed");
				if (SessionHelper.isCorpLogin()) {
					return new ModelAndView(getToCorpResultview(), resMsg);
				}
				return new ModelAndView(getToresultview(), resMsg);
			}
		}
		if (SessionHelper.isCorpLogin()) {
			return new ModelAndView(getCorpfoundpwderror());
		}
		return new ModelAndView(getFoundpwderror());
	}

	public void validatePayPassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String memberCode = null;
		try {
			LoginSession loginSession = SessionHelper.getLoginSession(request);
			memberCode = loginSession.getMemberCode();
		} catch (Exception e) {
			// 用户未激活时，不会初始化loginSession
			memberCode = String.valueOf(request.getSession().getAttribute(
					"memberCode"));
		}
		String paypwdStr = "";

		String paypwdPN = request.getParameter("paypwd") == null ? "" : request
				.getParameter("paypwd");
		PrintWriter out = null;
		out = response.getWriter();
		boolean flag = false;

		if (paypwdPN != null) {
			paypwdStr = request.getParameter(paypwdPN) == null ? "" : request
					.getParameter(paypwdPN);
			String paypwd = iMessageDigest.genMessageDigest(paypwdStr
					.getBytes());
			MemberDto memberDto = getMemberService().findByMemberCode(
					Long.parseLong(memberCode));
			if (!paypwd.equals(memberDto.getLoginPwd())) {
				flag = true;
			}
		}

		OutputUtil.write(out, flag);
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setEnterpriseBaseService(
			EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setSafeQuestionVerifyService(
			SafeQuestionVerifyService safeQuestionVerifyService) {
		this.safeQuestionVerifyService = safeQuestionVerifyService;
	}

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setAccountUnlockService(
			AccountUnlockService accountUnlockService) {
		this.accountUnlockService = accountUnlockService;
	}

}
