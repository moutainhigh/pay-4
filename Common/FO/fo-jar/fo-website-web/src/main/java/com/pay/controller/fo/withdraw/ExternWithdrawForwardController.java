/**
 *  File: ExternWithdrawForwardController.java
 *  Description:
 *  Copyright 2014-2014 Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2014年12月1日   alex     Create
 *
 */
package com.pay.controller.fo.withdraw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.RequestLocal;
import com.pay.app.base.session.SessionHelper;
import com.pay.util.MD5Util;
import com.pay.util.SignatureHelper;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;

/**
 * 
 */
public class ExternWithdrawForwardController extends MultiActionController {

	private final Log logger = LogFactory.getLog(getClass());

	private String indexView;
	private MemberService memberService;
	MemberQueryService memberQueryService;
	private static String verifyKey = "AFDCF7B4-D6AC-4290-BEA5-563A2422CFA7";

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, WithdrawCommand command) {

		String loginName = request.getParameter("loginName");
		String verifySign = request.getParameter("verifySign");
		if (!StringUtil.isEmpty(loginName)) {

			// 验证签名
			String verifyMAC = MD5Util.md5Hex(loginName + verifyKey);
			if (StringUtil.isEmpty(verifySign)
					|| !verifySign.equalsIgnoreCase(verifyMAC)) {
				logger.info("loginName:" + loginName);
				logger.error("签名为空或者签名校验不通过！");
			}

			MemberDto memberDto = memberService
					.queryMemberByLoginName(loginName);
			if (null != memberDto) {

				MemberInfoDto memberInfo = memberQueryService
						.doQueryMemberInfoNsTx(loginName,
								memberDto.getMemberCode(), memberDto.getType(),
								null);
				LoginSession loginSession = new LoginSession();
				loginSession.setVerifyName(memberInfo.getMemberName());
				loginSession.setMemberCode(memberInfo.getMemberCode() + "");
				loginSession.setActiveStatus("1");
				loginSession.setScaleType(3);
				loginSession.setLoginName(memberInfo.getLoginName());
				loginSession.setMemberType(memberInfo.getMemberType());
				loginSession.setSecurityLvl(2);
				SessionHelper.setLoginSession(loginSession);
				request.getSession().setAttribute("memberCode",
						memberDto.getMemberCode() + "");

				String signData = RequestLocal.getSession().getId() + "|"
						+ WebUtil.getAgent(RequestLocal.getRequest());
				request.getSession().setAttribute("memberSignatureData",
						SignatureHelper.generateAppSignature(signData));

				return new ModelAndView(indexView);
			}
		}

		return null;
	}
	
}
