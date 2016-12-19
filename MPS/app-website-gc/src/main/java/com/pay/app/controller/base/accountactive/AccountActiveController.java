/**
 *  File: AccountActiveController.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-21   single_zhang     Create
 *
 */
package com.pay.app.controller.base.accountactive;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.member.MemberProductService;
import com.pay.acc.service.member.MemberVerifyService;
import com.pay.acc.service.member.dto.MemberVerifyResult;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.client.TxncoreClientService;
import com.pay.app.common.helper.MemberLevel;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.facade.cidverify.CidVerify2GovServiceFacade;
import com.pay.app.service.mail.MailService;
import com.pay.base.common.enums.ScaleEnum;
import com.pay.base.common.enums.SecurityLvlEnum;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

public class AccountActiveController extends MultiActionController {

	/**
	 * 登陆首页
	 */
	private String toCheckCode;
	private String activeMessage;
	private String accountSafe;
	// 企业服务的账户安全首页
	private String corpAccountSafe;
	/**
	 * 邮件服务
	 */
	private MailService mailService;

	/**
	 * 实名认证
	 */
	private CidVerify2GovServiceFacade cidVerify2GovService;

	/**
	 * 会员认证
	 */
	MemberVerifyService memberVerifyService;

	/**
	 * 口令卡的services
	 */
	private IMatrixCardService matrixCardService;

	// 切换acc库
	private MemberService memberService;

	private AcctService acctService;

	/** 操作员服务 */
	protected OperatorServiceFacade operatorServiceFacade;

	private static final String origin = "app";

	private MemberProductService memberProductService;

	/**
	 * 密钥下载产品编码
	 */
	private static final String PRODUCT_CODE_KEY_DOWNLOAD = "USERSAFE_KEYDOWNLOAD";
	/**
	 * 网关公钥
	 */
	private static final String PARTNER_CONFIG_CODE1 = "code1";

	private TxncoreClientService txncoreClientService;

	/**
	 * 激活信息填写的方法,信息填写完之后跳转到app.html页面
	 * 
	 * @param request
	 * @param response
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	private static final Log log = LogFactory
			.getLog(AccountActiveController.class);

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, MemberCriteria memberCriteria) {
		String randCode = request.getSession().getAttribute("rand") == null ? ""
				: request.getSession().getAttribute("rand").toString();
		String code = request.getParameter("randCode") == null ? "" : request
				.getParameter("randCode");

		if (!code.equalsIgnoreCase(randCode)) {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("criteria", memberCriteria);
			paraMap.put("msgStr", MessageConvertFactory.getMessage("randCode"));
			return new ModelAndView(activeMessage, paraMap);
		}
		if (request.getSession().getAttribute("userid") != null) {
			String userId = request.getSession().getAttribute("userid")
					.toString();
			memberCriteria.setUserId(userId);
		}
		// ResultDto result = null;
		// accountActiveService.doMemberFillRnTx(memberCriteria);
		/*
		 * if (result.getErrorCode() != null) { Map<String, Object> paraMap =
		 * new HashMap<String, Object>(); paraMap.put("criteria",
		 * memberCriteria); paraMap.put("msgStr", result.getErrorMsg()); return
		 * new ModelAndView(activeMessage, paraMap); }
		 * 
		 * List<String> recAddress = new ArrayList<String>();
		 * recAddress.add(memberCriteria.getEmail()); CheckCode ck = new
		 * CheckCode(); ck.setMemberCode(result.getMemberCode());
		 * ck.setOrigin(origin); ck.setEmail(memberCriteria.getEmail()); if
		 * (mailService.sendMail(userId, recAddress, ck,
		 * Resource.MAIL_URL_ACTIVATION, Resource.TEMPLATEID_ACTIVATION,
		 * Resource.MAIL_SUBJECT_ACTIVATION)) { // modify by lei.jiangl 2010-8-4
		 * log.info("mail send success .........."); return new
		 * ModelAndView(toCheckCode).addObject("email",
		 * memberCriteria.getEmail()); }
		 */

		return new ModelAndView(toCheckCode).addObject("email",
				memberCriteria.getEmail());
	}

	/**
	 * 跳转至安全首页
	 * 
	 * @param request
	 * @param response
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "ACCOUNT_SAFE_QUERY")
	public ModelAndView accountSafe(HttpServletRequest request,
			HttpServletResponse response, MemberCriteria criteria)
			throws Exception {
		// 查询服务类型状态
		LoginSession loginSession = SessionHelper.getLoginSession();
		MemberCriteria memberCriteria = null;
		Map<String, Object> resMsg = new HashMap<String, Object>();
		int Securitylevel = loginSession.getSecurityLvl();

		String memberCode = loginSession.getMemberCode() == null ? ""
				: loginSession.getMemberCode();
		if (!"".equals(memberCode)) {
			memberCriteria = memberService
					.queryMemberCriteriaByMemberCodeNsTx(Long
							.valueOf(memberCode));
		}

		if (loginSession.getOperatorId() <= 0) {
			resMsg.put("isAdmin", "Y");
		}

		Object str = request.getSession().getAttribute("epLastLoginTime");
		if (str != null) {
			resMsg.put("dateLine", str);// 上次登录时间
		} else {
			Date nowDate = new Date();
			String lastLoginTime = DateUtil.formatDateTime(
					"yyyy-MM-dd HH:mm:ss", nowDate);
			request.getSession().setAttribute("epLastLoginTime", lastLoginTime);
			resMsg.put("dateLine", lastLoginTime);// 上次登录时间
		}
		if (null != memberCriteria) {
			// 判断用户问候语是设置
			String greeting = memberCriteria.getSalutatory() == null ? ""
					: memberCriteria.getSalutatory();
			if (!"".equals(greeting)) {
				resMsg.put("greetingState",
						MessageConvertFactory.getMessage("seted"));// 已设置
				resMsg.put("greetingState1",
						MessageConvertFactory.getMessage("change"));// 操作类型为修改
				resMsg.put("greeting", greeting);
			} else {
				resMsg.put("greetingState",
						MessageConvertFactory.getMessage("unset"));// 未设置
				resMsg.put("greetingState1",
						MessageConvertFactory.getMessage("set"));// 操作类型为设置
			}

			// 判断用户安全问题是否设置
			String questionid = memberCriteria.getQuestionId() == null ? ""
					: memberCriteria.getQuestionId();
			String answer = memberCriteria.getAnswer() == null ? ""
					: memberCriteria.getAnswer();
			if (!"".equals(questionid) && !"".equals(answer)) {
				resMsg.put("questionState",
						MessageConvertFactory.getMessage("seted"));// 已设置
				resMsg.put("questionState1",
						MessageConvertFactory.getMessage("change"));// 操作类型为修改
				resMsg.put("safequestion", "update");
			} else {
				resMsg.put("questionState",
						MessageConvertFactory.getMessage("unset"));// 未设置
				resMsg.put("questionState1",
						MessageConvertFactory.getMessage("set"));// 操作类型为设置
				resMsg.put("safequestion", "set");
			}
		}
		// 判断用户支付密码是否设置
		boolean isSet = acctService.isHavePayPwd(Long.valueOf(memberCode),
				AcctTypeEnum.BASIC_CNY.getCode());
		if (isSet) {
			resMsg.put("paypwdState", MessageConvertFactory.getMessage("seted"));// 设置
			resMsg.put("paypwdState1",
					MessageConvertFactory.getMessage("change"));// 操作类型为修改
			resMsg.put("paypwdurl", "update");
			resMsg.put("found", MessageConvertFactory.getMessage("found"));
		} else {
			resMsg.put("paypwdState", MessageConvertFactory.getMessage("unset"));// 未设置
			resMsg.put("paypwdState1", MessageConvertFactory.getMessage("set"));// 操作类型为设置
			resMsg.put("paypwdurl", "set");
		}

		// TODO 判断是否已经实名认证
		boolean isCid = cidVerify2GovService.checkQueryCidVerify(memberCode);

		resMsg.put("memberLevel", "");
		MemberVerifyResult memberVerify = memberVerifyService
				.QueryMemberVerifyByMemberCode(Long.parseLong(memberCode));
		int ml = memberVerify.getMemberLevel();
		if (memberVerify.isVerify()) {
			if (ml > MemberLevel.values().length) {
				resMsg.put("memberLevel",
						MessageConvertFactory.getMessage("errormemberlevel"));
			}

			MemberLevel memberLevel = MemberLevel.values()[ml];
			// 如果已认证
			if (memberLevel.isVerified()) {
				resMsg.put("memberLevel", memberLevel.getDisplayName());
				isCid = true;
			}
		}
		resMsg.put("isCid", isCid);

		// 获取用户的安全级别
		if (loginSession.getSecurityLvl() == SecurityLvlEnum.MAXTRIX.getValue()) {
			Securitylevel = SecurityLvlEnum.MAXTRIX.getValue();
		}

		resMsg.put("Securitylevel", Securitylevel);

		if (loginSession.getScaleType() == ScaleEnum.INDIVIDUAL.getValue()) {
			return new ModelAndView(accountSafe, resMsg);
		}

		// 查询当前绑定的手机
		String bindMobile = operatorServiceFacade
				.getBindMobileByMeberCodeOperatorId(Long.valueOf(memberCode),
						loginSession.getOperatorId());
		if (StringUtils.isNotBlank(bindMobile)) {
			resMsg.put("isBindMobile", "Y");
		} else {
			resMsg.put("isBindMobile", "N");
		}

		// 查询商户是否开通密钥下载功能
		boolean resultFlg = memberProductService.isHaveProduct(
				Long.valueOf(memberCode), PRODUCT_CODE_KEY_DOWNLOAD);
		if (resultFlg) {
			resMsg.put("isHasProduct", "Y");
		} else {
			resMsg.put("isHasProduct", "N");
		}
		// 查询商户是否下载过密钥
		Map partnerConfigMap = txncoreClientService.partnerConfigQuery(
				memberCode, PARTNER_CONFIG_CODE1);
		if (null != partnerConfigMap
				&& !StringUtil.isEmpty(String.valueOf(partnerConfigMap.get("value")))) {
			resMsg.put("isDownload", "Y");
		} else {
			resMsg.put("isDownload", "N");
		}

		return new ModelAndView(corpAccountSafe, resMsg);
	}

	public void setAccountSafe(String accountSafe) {
		this.accountSafe = accountSafe;
	}

	/******** set方法 **********/

	public void setToCheckCode(String toCheckCode) {
		this.toCheckCode = toCheckCode;
	}

	public void setActiveMessage(String activeMessage) {
		this.activeMessage = activeMessage;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void setMatrixCardService(IMatrixCardService matrixCardService) {
		this.matrixCardService = matrixCardService;
	}

	public void setCorpAccountSafe(String corpAccountSafe) {
		this.corpAccountSafe = corpAccountSafe;
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

	public void setCidVerify2GovService(
			CidVerify2GovServiceFacade cidVerify2GovService) {
		this.cidVerify2GovService = cidVerify2GovService;
	}

	public void setMemberVerifyService(MemberVerifyService memberVerifyService) {
		this.memberVerifyService = memberVerifyService;
	}

	public void setOperatorServiceFacade(
			OperatorServiceFacade operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}

	public void setMemberProductService(
			MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}

	public void setTxncoreClientService(
			TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

}
