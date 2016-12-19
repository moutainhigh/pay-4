/**
 *  File: UserSafeManageController.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-23   lihua     Create
 *
 */
package com.pay.app.controller.base.usersafe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.app.base.api.annotation.HasFeature;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.annotation.PutAppLog;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.api.wrapper.SafeControllerWrapper;
import com.pay.app.base.exception.LoginTimeOutException;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.client.TxncoreClientService;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.common.safequestion.Safequestion;
import com.pay.app.facade.dto.MaSumDto;
import com.pay.app.service.mail.MailService;
import com.pay.app.service.sms.SmsService;
import com.pay.app.validator.GreetingValidator;
import com.pay.app.validator.ValidatorDto;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.MemberInfoDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.Acct;
import com.pay.base.model.AcctAttrib;
import com.pay.base.model.Operator;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.acctatrrib.AcctAttribService;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.operator.impl.OperatorServiceFacadeImpl;
import com.pay.base.service.queryhistory.QueryBalanceService;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.CheckUtil;
import com.pay.util.DateUtil;
import com.pay.util.FormatDate;
import com.pay.util.HexUtil;
import com.pay.util.NumberUtil;
import com.pay.util.OutputUtil;

/**
 * 账户安全设置
 */


/**
 * @author xiaodai.Rainy
 *
 * @data 2015年12月16日上午10:15:37
 *  
 * @param 修改512秘钥下载
 */
public class UserSafeManageController extends MultiActionController {

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

	/**
	 * 付款到支付的设置支付密码页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@HasFeature({ CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
	public ModelAndView tosetPaypwdforpay2(HttpServletRequest request,
			HttpServletResponse response) {

		return new ModelAndView(updatePaypwdforpay2);
	}

	/**
	 * 密码安全问题修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//20160423 Mack MPS3期去除注解权限控制，通过菜单控制  @OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATEQUESTION")
	@PutAppLog(logType = CutsConstants.USER_LOG_SAFEQUESTION)
	@HasFeature({ CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
	public ModelAndView updatesafequestion(HttpServletRequest request,

	HttpServletResponse response) throws Exception {
		// 1.先验证支付密码是否正确 2.修改会员的安全问题
		String memberCode = "";
		Map<String, Object> resMsg = new HashMap<String, Object>();
		LoginSession loginSession = (LoginSession) request.getSession()
				.getAttribute("userSession");
		if (null != loginSession) {
			memberCode = null == loginSession.getMemberCode() ? ""
					: loginSession.getMemberCode();
		}
		String oldquestionId = request.getParameter("oldquestionId");
		String question = request.getParameter("question");
		String answer = request.getParameter("answer");
		String oldanswer = request.getParameter("oldanswer");

		/**** 验证问题ID是否是数字 **/
		if (NumberUtil.isNumber(question) && NumberUtil.isNumber(oldquestionId)) {

			if ("0".equals(question)) {
				return toupdatesafequestionpage(request, response).addObject(
						"resultinfo",
						MessageConvertFactory.getMessage("chosequestion"));
			}

			if (null != memberCode && !"".equals(memberCode)) {
				if (null != oldanswer && !"".equals(oldanswer)
						&& !question.equals("0")) {
					// 重构校验安全问题， (切换到acc库)
					ResultDto resultDtos = memberService
							.validateSecurMemberQuestionWidthMemberInfo(
									Long.valueOf(memberCode),
									Integer.parseInt(oldquestionId), oldanswer);
					if (null == resultDtos.getErrorCode()) {// 验证原安全问题
						// 重构修改安全问题， (切换到acc库)
						ResultDto resultDto = memberService
								.resetSecurityQuestionRnTx(
										Long.valueOf(memberCode),
										Integer.parseInt(question), answer);
						if (null == resultDto.getErrorCode()) {// 返回修改成功， 状态为
																// "1"
							if (SessionHelper.isCorpLogin()) {
								resMsg.put("classcss", "feedback succeed");
								resMsg.put("returninfo", MessageConvertFactory
										.getMessage("changeSuccess"));
								return new ModelAndView(toCorpResultview,
										resMsg);
							}
							resMsg.put("classcss", "feedback succeed");
							resMsg.put("returninfo", MessageConvertFactory
									.getMessage("changeSuccess"));
							return new ModelAndView(toresultview, resMsg);
						}
					} else {
						// 原安全问题答案错误
						return toupdatesafequestionpage(request, response)
								.addObject(
										"returninfo",
										MessageConvertFactory
												.getMessage("answerfailure"));
					}
				}

			}

		}

		if (SessionHelper.isCorpLogin()) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("changefailure"));
			resMsg.put("classcss", "feedback warning");
			return new ModelAndView(toCorpResultview, resMsg);
		}
		resMsg.put("returninfo",
				MessageConvertFactory.getMessage("changefailure"));
		resMsg.put("classcss", "feedback warning");
		return new ModelAndView(toresultview, resMsg);
	}

	/**
	 * 跳转至修改问候语页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//20160423 Mack MPS3期去除注解权限控制，通过菜单控制  @OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATEGREETING")
	@HasFeature({ CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
	public ModelAndView toupdategreetingpage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String returninfo = null == request.getParameter("returninfo") ? ""
				: request.getParameter("returninfo");
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		Map<String, Object> resMsg = new HashMap<String, Object>();
		String greeting = "";
		MemberCriteria memberCriteria = null;
		if (null != loginSession) {
			String memberCode = null == loginSession.getMemberCode() ? ""
					: loginSession.getMemberCode();
			if (!"".equals(memberCode)) {
				// 切换到acc库
				memberCriteria = memberService
						.queryMemberCriteriaByMemberCodeNsTx(Long
								.valueOf(memberCode));
			} else {
				resMsg.put("returninfo",
						MessageConvertFactory.getMessage("unlogin"));
				resMsg.put("classcss", "feedback warning");
				return new ModelAndView(toresultview, resMsg);
			}

			if (null != memberCriteria) {// 获取用户原来的问候语
				greeting = memberCriteria.getWelcomeMsg();
			}
			// 判断是否是企业会员
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(corpupdategreetingpage).addObject(
						"greeting", greeting).addObject("returninfo",
						returninfo);
			}
		}
		return new ModelAndView(updategreetingpage).addObject("greeting",
				greeting).addObject("returninfo", returninfo);
	}

	/**
	 * 修改问候语
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws LoginTimeOutException
	 */
//20160423 Mack MPS3期去除注解权限控制，通过菜单控制 	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATEGREETING")
	@PutAppLog(logType = CutsConstants.USER_LOG_UPDATEGREETING)
	@HasFeature({ CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
	public ModelAndView updategreeting(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		SafeControllerWrapper request = new SafeControllerWrapper(req,
				new String[] { "loginpwd" });
		// 1.先查出会员信息 2.修改问候语
		String msg = "";
		ResultDto resultDto = null;
		String loginpwd = null == request.getParameter("loginpwd") ? ""
				: request.getParameter("loginpwd");
		String memberCode = "";
		MemberCriteria memberCriteria = null;
		Map<String, Object> resMsg = new HashMap<String, Object>();
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		if (null != loginSession) {
			memberCode = null == loginSession.getMemberCode() ? ""
					: loginSession.getMemberCode();
			if (!"".equals(memberCode)) {
				// 切换到acc库
				memberCriteria = memberService
						.queryMemberCriteriaByMemberCodeNsTx(Long
								.valueOf(memberCode));
			}
		}
		if (null != memberCode && !"".equals(memberCode)) {// 验证登录密码是否正确
			if (!"".equals(loginpwd)) {
				// 切换到acc库
				resultDto = memberService.verifyLoginPwdNsTx(
						Long.valueOf(memberCode), loginpwd);
			}
		}

		if (resultDto != null && null == resultDto.getErrorCode()) {// 登录密码输入正确
			if (null != memberCriteria) {// 调用修改会员信息方法
				msg = null == request.getParameter("greeting") ? "" : request
						.getParameter("greeting");// 从页面获取新的问候语
				ValidatorDto vd = GreetingValidator.validate(msg);// 校验表单提交的内容
				if (vd.hasErrors()) {
					resMsg.put("returninfo", vd.getError());
					resMsg.put("classcss", "feedback warning");
					return new ModelAndView(toresultview, resMsg);
				}
				// 如果问候语为空
				if (StringUtils.isEmpty(msg.trim())) {
					resMsg.put("returninfo",
							MessageConvertFactory.getMessage("greetingempty"));
					resMsg.put("classcss", "feedback warning");
					return new ModelAndView(toresultview, resMsg);
				}

				MemberInfoDto emberInfoDto = new MemberInfoDto(); // 切换到acc库
				emberInfoDto.setGreeting(msg.trim());
				emberInfoDto.setMemberCode(Long.valueOf(memberCode));
				ResultDto result = null;
				if (SessionHelper.isCorpLogin()) {
					result = memberService
							.doUpdateCorpMemberInfoRnTx(emberInfoDto);
				} else {
					result = memberService.doUpdateMemberInfoRnTx(emberInfoDto);
				}
				if (null == result.getErrorCode()) {// 修改会员信息成功
					// 判断是否是企业会员
					if (SessionHelper.isCorpLogin()) {
						resMsg.put("returninfo", MessageConvertFactory
								.getMessage("changeSuccess"));
						resMsg.put("classcss", "feedback succeed");
						return new ModelAndView(toCorpResultview, resMsg);
					}
					resMsg.put("returninfo",
							MessageConvertFactory.getMessage("changeSuccess"));
					resMsg.put("classcss", "feedback succeed");
					return new ModelAndView(toresultview, resMsg);
				}
			}
		} else {// 登录密码输入错误
			return toupdategreetingpage(request, response).addObject(
					"returninfo",
					MessageConvertFactory.getMessage("loginPwdError"));
		}
		// 判断是否是企业会员
		if (SessionHelper.isCorpLogin()) {
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("changefailure"));
			resMsg.put("classcss", "feedback warning");
			return new ModelAndView(toCorpResultview, resMsg);
		}
		resMsg.put("returninfo",
				MessageConvertFactory.getMessage("changefailure"));
		resMsg.put("classcss", "feedback warning");
		return new ModelAndView(toresultview, resMsg);

	}

	/**
	 * 修改问候语(账户首页)
	 * 
	 * @param request
	 * @param response
	 * @throws LoginTimeOutException
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATEGREETING")
	@HasFeature({ CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
	public ModelAndView updategreetingforaccount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String greeting = null == request.getParameter("greetinginfo") ? ""
				: request.getParameter("greetinginfo");
		String loginmembercode = "";
		MemberCriteria memberCriteria = null;
		if (null != loginSession) {
			loginmembercode = null == loginSession.getMemberCode() ? ""
					: loginSession.getMemberCode();
		}

		if (!"".equals(loginmembercode)) {
			// 切换到acc库
			memberCriteria = memberService
					.queryMemberCriteriaByMemberCodeNsTx(Long
							.valueOf(loginmembercode));
			if (null != memberCriteria) {
				MemberInfoDto memberInfo = new MemberInfoDto();
				memberInfo.setGreeting(greeting);
				memberService.doUpdateMemberInfoRnTx(memberInfo); // 切换到acc库
			}
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String userName = loginSession.getLoginName();
		String createDate = "";
		// 切换到acc库
		MemberCriteria mem = memberService
				.queryMemberCriteriaByMemberCodeNsTx(Long
						.valueOf(loginmembercode));
		if (null != mem) {// modify by lei.jiangl
			// memberCriteria.getCreateDate=null
			mem.setUserName(userName);
			if (null == mem.getSalutatory()) {
				paraMap.put("Salutatory",
						MessageConvertFactory.getMessage("set"));
			} else {
				paraMap.put("Salutatory",
						MessageConvertFactory.getMessage("change"));
			}
			if (mem.getCreateDate() != null) {
				createDate = FormatDate.formatDate(mem.getCreateDate());
			}
		}
		boolean bool = acctService.isHavePayPwd(Long.valueOf(loginmembercode),
				AcctTypeEnum.BASIC_CNY.getCode());
		if (bool) {
			paraMap.put("paypwdState",
					MessageConvertFactory.getMessage("seted"));// 设置
			paraMap.put("paypwdState1",
					MessageConvertFactory.getMessage("change"));// 操作类型为修改
			// paraMap.put("paypwdurl", "update");
			paraMap.put("found", MessageConvertFactory.getMessage("found"));
		} else {
			paraMap.put("paypwdState",
					MessageConvertFactory.getMessage("unset"));// 未设置
			paraMap.put("paypwdState1", MessageConvertFactory.getMessage("set"));// 操作类型为设置
			paraMap.put("paypwdurl", "set");

		}
		Acct acct = acctService.getByMemberCode(Long.valueOf(loginmembercode),
				AcctTypeEnum.BASIC_CNY.getCode());
		MaSumDto sum = queryCorpBalanceService.queryHistoryBusinessSum(null,
				null, acct, null, null,null,null);

		boolean boo = false;

		double balance = sum == null ? 0L : sum.getBalanceSumNo().doubleValue();

		paraMap.put("memberCriteria", mem);
		paraMap.put("createDate", createDate);// 注册时间
		paraMap.put("balance", balance);
		paraMap.put("boo", boo);// 关联
		return new ModelAndView(accountIndex, paraMap);
	}

	/**
	 * 跳转至修改支付密码页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws LoginTimeOutException
	 */
	//20160423 Mack MPS3期去除注解权限控制，通过菜单控制  @OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATEPAYPWD")
	@HasFeature({ CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
	public ModelAndView toupdatepaypwdpage(HttpServletRequest request,
			HttpServletResponse response) throws LoginTimeOutException {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String paypwdStatus = null == request.getParameter("paypwdurl") ? ""
				: request.getParameter("paypwdurl");
		String myAccount = null == request.getParameter("myAccount") ? ""
				: request.getParameter("myAccount");
		if ("set".equals(paypwdStatus)) {
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(setCorpPaypwdpage).addObject(
						"myAccount", myAccount);
			}
			return new ModelAndView(setpaypwdpage).addObject("myAccount",
					myAccount);
		} else {
			if (SessionHelper.isCorpLogin()) {
				return new ModelAndView(updateCorpPaypwdpage).addObject(
						"myAccount", myAccount);
			}
			return new ModelAndView(updatepaypwdpage).addObject("myAccount",
					myAccount);
		}
	}

	/**
	 * 跳转至修改密码安全问题页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//20160423 Mack MPS3期去除注解权限控制，通过菜单控制  @OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATEQUESTION")
	@HasFeature({ CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
	public ModelAndView toupdatesafequestionpage(HttpServletRequest request,

	HttpServletResponse response) throws Exception {

		Map<String, Object> resMsg = new HashMap<String, Object>();
		String myAccount = null == request.getParameter("myAccount") ? ""
				: request.getParameter("myAccount");// 判断是不是账户桌面那边的请求
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String questionId = "";
		String memberCode = "";
		MemberCriteria memberCriteria = null;
		if (null != loginSession) {
			memberCode = loginSession.getMemberCode() == null ? ""
					: loginSession.getMemberCode();
		}
		if (!"".equals(memberCode)) {
			// 切换到acc库
			memberCriteria = memberService
					.queryMemberCriteriaByMemberCodeNsTx(Long
							.valueOf(memberCode));
		} else {
			if (SessionHelper.isCorpLogin()) {
				resMsg.put("returninfo",
						MessageConvertFactory.getMessage("unlogin"));
				resMsg.put("classcss", "feedback warning");
				return new ModelAndView(toCorpResultview, resMsg);
			}
			resMsg.put("returninfo",
					MessageConvertFactory.getMessage("unlogin"));
			resMsg.put("classcss", "feedback warning");
			return new ModelAndView(toresultview, resMsg);
		}

		if (null != memberCriteria) {// 获取用户原来的安全问题
			questionId = memberCriteria.getQuestionId();
		}
		String question = Safequestion.getsafequestion(questionId);
		if (SessionHelper.isCorpLogin()) {
			return new ModelAndView(updatecorpsafequestionpage)
					.addObject("oldquestion", question)
					.addObject("oldquestionId", questionId)
					.addObject("myAccount", myAccount);
		}
		return new ModelAndView(updatesafequestionpage)
				.addObject("oldquestion", question)
				.addObject("oldquestionId", questionId)
				.addObject("myAccount", myAccount);
	}

	/**
	 * 跳转至通过安全问题找回支付密码页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws LoginTimeOutException
	 */
//20160423 Mack MPS3期去除注解权限控制，通过菜单控制 	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_FOUNDPAYPWD")
	@HasFeature({ CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
	public ModelAndView tofoundpaypwdbysafequestionpage(
			HttpServletRequest request, HttpServletResponse response)
			throws LoginTimeOutException {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		if (SessionHelper.isCorpLogin()) {
			return new ModelAndView(foundcorppaypwdbysafequestionpage);
		}
		return new ModelAndView(foundpaypwdbysafequestionpage);
	}

	/**
	 * 跳转至通过邮箱找回支付密码 step 1
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws LoginTimeOutException
	 */
	//20160423 Mack MPS3期去除注解权限控制，通过菜单控制  @OperatorPermission(operatPermission = "OPERATOR_USERSAFE_FOUNDPAYPWD")
	@HasFeature({ CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
	public ModelAndView tofoundpaypwdbyemailpage(HttpServletRequest request,
			HttpServletResponse response) throws LoginTimeOutException {

		// 输入注册邮箱。验证？
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		if (SessionHelper.isCorpLogin()) {
			return new ModelAndView(foundcorppaypwdbyemailpage);
		}
		return new ModelAndView(foundpaypwdbyemailpage);
	}

	/**
	 * 跳转至通过注册手机找回支付密码页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws LoginTimeOutException
	 */
	@OperatorPermission(operatPermission = "OPERATOR_USERSAFE_FOUNDPAYPWD")
	@PutAppLog(logType = CutsConstants.USER_LOG_FINDPAYPWD)
	@HasFeature({ CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
	public ModelAndView tofindsafepwdbymobile(HttpServletRequest request,
			HttpServletResponse response) throws LoginTimeOutException {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		if (SessionHelper.isCorpLogin()) {
			return new ModelAndView(findcorpsafepwdbymobilepage);
		}
		return new ModelAndView(findsafepwdbymobilepage);
	}

	/**
	 * 选择找回支付密码方式
	 * 
	 * @return
	 * @throws Exception
	 */
	public ModelAndView selectfindmode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String loginName = "";
		boolean ismobile = false;
		boolean isemail = false;
		Map<Object, Object> msg = new HashMap<Object, Object>();
		// 判断注册方式
		if (null != loginSession) {
			loginName = null == loginSession.getLoginName() ? "" : loginSession
					.getLoginName();
		}
		if (!"".equals(loginName)) {
			if (CheckUtil.checkEmail(loginName)) {
				isemail = true;
			}
			if (CheckUtil.checkPhone(loginName)) {
				ismobile = true;
			}
		}
		msg.put("ismobile", ismobile);
		msg.put("isemail", isemail);
		if (SessionHelper.isCorpLogin()) {
			return new ModelAndView(findCorpmodepage).addObject("ismobile",
					ismobile).addObject("isemail", isemail);
		}
		return new ModelAndView(findmodepage).addObject("ismobile", ismobile)
				.addObject("isemail", isemail);
	}

	/**
	 * 跳转至修改登录密码页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws LoginTimeOutException
	 */
	//20160423 Mack MPS3期去除注解权限控制，通过菜单控制  @OperatorPermission(operatPermission = "OPERATOR_USERSAFE_UPDATELOGINPWD")
	@HasFeature({ CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
	public ModelAndView toupdateLoginPwd(HttpServletRequest request,
			HttpServletResponse response) throws LoginTimeOutException {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String myAccount = null == request.getParameter("myAccount") ? ""
				: request.getParameter("myAccount");
		if (SessionHelper.isCorpLogin()) {
			return new ModelAndView(corpUpdateloginpwdpage).addObject(
					"myAccount", myAccount);
		}
		return new ModelAndView(updateloginpwdpage).addObject("myAccount",
				myAccount);
	}

	/**
	 * 验证登陆密码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void validateLoginPassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String memberCode = null;
		PrintWriter out = null;
		out = response.getWriter();
		boolean flag = false;
		try {
			LoginSession loginSession = SessionHelper.getLoginSession(request);
			memberCode = loginSession.getMemberCode();
		} catch (Exception e) {
			// 用户未登录
			memberCode = String.valueOf(request.getSession().getAttribute(
					"FIND_LOGIN_PASSWORD_CODE") == null ? "" : request
					.getSession().getAttribute("FIND_LOGIN_PASSWORD_CODE"));
		}

		String loginpwdStr = null;
		String loginpwdPN = request.getParameter("loginpwd") == null ? ""
				: request.getParameter("loginpwd");
		if (loginpwdPN != null && StringUtils.isNotBlank(memberCode)) {
			loginpwdStr = request.getParameter(loginpwdPN) == null ? ""
					: request.getParameter(loginpwdPN);
			String loginpwd = iMessageDigest.genMessageDigest(loginpwdStr
					.getBytes());
			Acct acct = acctService.getByMemberCode(Long.parseLong(memberCode),
					AcctTypeEnum.BASIC_CNY.getCode());
			if (acct != null) {
				AcctAttribDto acctAttribDto = acctAttribService
						.queryAcctAttribByAcctCode(acct.getAcctCode());
				if (acctAttribDto != null
						&& !loginpwd.equals(acctAttribDto.getPayPwd())) {
					flag = true;
				}
			}
		}

		OutputUtil.write(out, flag);
	}

	/**
	 * 密钥下载
	 * 
	 * @return
	 * @throws Exception
	 */
	public ModelAndView keydownload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String loginName = "";
		Map<String, String> msg = new HashMap<String, String>();
		// 判断注册方式
		if (null != loginSession) {
			loginName = null == loginSession.getLoginName() ? "" : loginSession
					.getLoginName();
		} else {
			return null;
		}
		if (!"".equals(loginName)) {
		}
		// 企业会员
		if (SessionHelper.isCorpLogin()) {
			// 开始下载获取请求数据
			String mode = request.getParameter("mode");
			String finished = request.getParameter("finished");
			if (null != finished && finished.equals("true")) {
				msg.put("success", "Y");
				return new ModelAndView(keydownloadpage, msg);
			}
			logger.info("申请下载的密钥类型为：" + mode);
			String temFolder = getServletContext().getRealPath("/tempFiles");
			//判断文件是否存在
			File tmp = new File(temFolder);
			if (!tmp.exists()) {
				tmp.mkdirs();
			}
			String publicKey = null;
			String downloadFilePath = null;
			if (StringUtils.isNotEmpty(mode)) {
				// 生成jks文件
				String keypass = generateMD5(6);
				String storepass = generateMD5(6);
				String jksName = loginSession.getMemberCode() + "_"
						+ DateUtil.getNowDate("yyyyMMddHHmmss") + "_rsa.jks";
				StringBuffer cmd = new StringBuffer();
				cmd.append("keytool -genkey -alias HNAKEY -keyalg RSA -sigalg SHA1withRSA -validity 3650 -keypass ");
				cmd.append(keypass);
				cmd.append(" -storepass ");
				cmd.append(storepass);
				cmd.append(" -dname \"CN=shcssh, OU=cssh, O=shcssh, L=sh, ST=sh, C=cn\" ");
				cmd.append("-keystore ");
				cmd.append(temFolder + "/" + jksName);

				String cmdStr = cmd.toString();
				
				Process ps = Runtime.getRuntime().exec(cmd.toString());
				
				String[] sh = new String[] { "/bin/sh", "-c", cmdStr };
				ProcessBuilder pb = new ProcessBuilder(sh);
				Process p = pb.start();

				logger.info("生成jks文件成功：" + temFolder + "/" + jksName);
				Thread.sleep(5000);

				// 读取jks中的公钥
				PublicKey publicKeyClass = getPublicKey(temFolder + "/"
						+ jksName, storepass, "HNAKEY");
				if (null != publicKeyClass) {
					byte[] pubkeyByteArray = publicKeyClass.getEncoded();
					publicKey = HexUtil.toHexString(pubkeyByteArray);
				} else {
					msg.put("success", "N");
					return new ModelAndView(keydownloadpage, msg);
				}
				if (mode.toLowerCase().equals("rsa")) {
					// 生成jks说明文件
					String txtName = "readme_" + loginSession.getMemberCode()
							+ "_" + DateUtil.getNowDate("yyyyMMddHHmmss")
							+ ".txt";
					File newfile = new File(temFolder + "/" + txtName);
					newfile.createNewFile();
					BufferedWriter output = new BufferedWriter(new FileWriter(
							newfile));
					StringBuffer content = new StringBuffer();
					content.append("#商户jks文件名");
					content.append(System.getProperty("line.separator"));
					content.append("rsa.kayPath=" + jksName);
					content.append(System.getProperty("line.separator"));
					content.append("#商户jks的storepass");
					content.append(System.getProperty("line.separator"));
					content.append("rsa.storepass=" + storepass);
					content.append(System.getProperty("line.separator"));
					content.append("rsa.alias=HNAKEY");
					content.append(System.getProperty("line.separator"));
					content.append("rsa.pwd=" + keypass);
					content.append(System.getProperty("line.separator"));
					content.append(" ");
					content.append(System.getProperty("line.separator"));
					content.append("#网关生产公钥");
					content.append(System.getProperty("line.separator"));
					content.append("gateway.pubkey="
							+ newPayGatewayPublicKey);
					content.append(System.getProperty("line.separator"));
					content.append(" ");
					content.append(System.getProperty("line.separator"));
					content.append("#如果采用md5方式加密方式连接，商户的加密密钥为商户生产公钥");
					content.append(System.getProperty("line.separator"));
					content.append("#商户生产公钥");
					content.append(System.getProperty("line.separator"));
					content.append("rsa.pubkey=" + publicKey);
					output.write(content.toString());
					output.close();
					logger.info("生成jks说明文件成功：" + temFolder + "/" + txtName);
					Thread.sleep(1000);
					// 生成下载文件
					logger.info("生成zip压缩文件");
					File[] file1 = { new File(temFolder + "/" + jksName),
							new File(temFolder + "/" + txtName) };
					downloadFilePath = ZipFile(temFolder, file1);
					Thread.sleep(5000);
				} else if (mode.toLowerCase().equals("md5")) {
					String md5FileName = loginSession.getMemberCode() + "_"
							+ DateUtil.getNowDate("yyyyMMddHHmmss")
							+ "_md5.txt";
					File md5file = new File(temFolder + "/" + md5FileName);
					md5file.createNewFile();
					BufferedWriter output = new BufferedWriter(new FileWriter(
							md5file));
					StringBuffer content = new StringBuffer();
					content.append("#MD5方式连接，请采用下面的商户公钥");
					content.append(System.getProperty("line.separator"));
					content.append("md5.publicKey=" + publicKey);

					output.write(content.toString());
					output.close();
					Thread.sleep(5000);
					downloadFilePath = temFolder + "/" + md5FileName;
					
					//从这边开始SHA512生成下载
				}else if(mode.toLowerCase().equals("sha512")){
					String sha512FileName = loginSession.getMemberCode() + "_"
							+ DateUtil.getNowDate("yyyyMMddHHmmss")
							+ "_sha512.txt";
					File md5file = new File(temFolder + "/" + sha512FileName);
					md5file.createNewFile();
					BufferedWriter output = new BufferedWriter(new FileWriter(
							md5file));
					StringBuffer content = new StringBuffer();
					content.append("#SHA512方式连接，请采用下面的商户公钥");
					content.append(System.getProperty("line.separator"));
					content.append("sha512.publicKey=" + publicKey);

					output.write(content.toString());
					output.close();
					Thread.sleep(5000);
					downloadFilePath = temFolder + "/" + sha512FileName;
				}
				// 将公钥保存到partnerConfig中
				logger.info("开始更新公钥");
				if (txncoreClientService.partnerConfigCreate(
						loginSession.getMemberCode(), publicKey)) {
					msg.put("success", "Y");
					try {
						File downloadFile = new File(downloadFilePath);
						String downloadfileName = downloadFile.getName();
						FileInputStream fi = new FileInputStream(downloadFile);
						byte[] bt = new byte[fi.available()];
						fi.read(bt);
						response.setContentType("application/msdownload;charset=UTF-8");

						// System.out.print(response.getContentType());
						response.setCharacterEncoding("UTF-8");
						String fileName = java.net.URLEncoder.encode(
								downloadfileName, "UTF-8");
						response.setHeader(
								"Content-Disposition",
								"attachment; filename="
										+ new String(
												fileName.getBytes("UTF-8"),
												"GBK"));
						response.setContentLength(bt.length);
						ServletOutputStream sos = response.getOutputStream();
						sos.write(bt);
						fi.close();
						// response.getWriter().write("审核完成");
						return null;
					} catch (Exception e) {
						logger.error("异常 ：下载出现异常" + e);
						msg.put("success", "N");
					}
					msg.put("success", "Y");
				} else {
					msg.put("success", "N");
				}
			} else {
				msg.put("success", "N");
			}
			return new ModelAndView(keydownloadpage, msg);
		}
		// 非企业会员
		return new ModelAndView(keydownloadpage, msg);
	}

	// 方法
	private String generateMD5(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			if ("char".equalsIgnoreCase(charOrNum)) {
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	private PublicKey getPublicKey(String keyStoreFile, String storeFilePass,
			String keyAlias) {
		// 读取密钥是所要用到的工具类
		KeyStore ks;
		// 公钥类所对应的类
		PublicKey pubkey = null;
		try {

			// 得到实例对象
			ks = KeyStore.getInstance("JKS");
			FileInputStream fin;
			try {

				// 读取JKS文件
				fin = new FileInputStream(keyStoreFile);
				try {
					// 读取公钥
					ks.load(fin, storeFilePass.toCharArray());
					java.security.cert.Certificate cert = ks
							.getCertificate(keyAlias);
					pubkey = cert.getPublicKey();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (CertificateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return pubkey;
	}

	/**
	 *  下载密钥 验证支付密码 
	 * @return
	 */
	public ModelAndView checkPaymentPwd(HttpServletRequest request, HttpServletResponse response){
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String paymentPwd=request.getParameter("paymentPwd");//输入的支付密码
		Map<String,String> map=new HashMap<String, String>();//封装参数到MAP
		//String operatorIdentity = SessionHelper.getLoginSession().getOperatorIdentity() ;
		String name = SessionHelper.getLoginSession().getVerifyName() ;
		try {
			map.put("paymentPwd", iMessageDigest.genMessageDigest(paymentPwd.getBytes()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		map.put("memberCode", loginSession.getMemberCode());
		if("admin".equals(name)){
			AcctAttrib acctAttrib=acctAttribService.checkPaymentPwd(map);		
			if(acctAttrib==null){
				try {
					response.getWriter().print("N");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				try {
					response.getWriter().print("Y");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}else{
			map.put("name", name) ;
			Operator operator = this.operatorServiceFacade.checkOperatorPaymentPwd(map) ;
			if(operator==null){
				try {
					response.getWriter().print("N");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				try {
					response.getWriter().print("Y");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
	}
	
	
	
	
	private String ZipFile(String saveFolder, File[] file) throws IOException {

		byte[] buffer = new byte[1024];

		// 生成的ZIP文件名为Demo.zip

		String strZipName = saveFolder + "/HnaKey"
				+ DateUtil.getNowDate("yyyyMMddHHmmss") + ".zip";

		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				strZipName));

		for (int i = 0; i < file.length; i++) {

			FileInputStream fis = new FileInputStream(file[i]);

			out.putNextEntry(new ZipEntry(file[i].getName()));

			int len;

			// 读入需要下载的文件的内容，打包到zip文件

			while ((len = fis.read(buffer)) > 0) {

				out.write(buffer, 0, len);

			}

			out.closeEntry();

			fis.close();

		}

		out.close();

		return strZipName;
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
	
}
