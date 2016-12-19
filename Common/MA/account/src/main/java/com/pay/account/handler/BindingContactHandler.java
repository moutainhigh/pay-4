/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.account.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.member.dto.IndividualInfoDto;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.IndividualInfoService;
import com.pay.acc.member.service.MemberService;
import com.pay.account.exception.ErrorExceptionEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.jms.notification.request.EmailNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.IdentityUtil;
import com.pay.util.JSonUtil;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;

/**
 * 绑定联系方式
 * 
 * @author chma
 */
public class BindingContactHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(BindingContactHandler.class);
	private MemberService memberService;
	private IndividualInfoService individualInfoService;
	private CheckCodeService checkCodeService;
	private String activeEmailUrl;
	private JmsSender jmsSender;
	private final static int active_email_template_id = 10607;

	public void setIndividualInfoService(
			IndividualInfoService individualInfoService) {
		this.individualInfoService = individualInfoService;
	}

	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	public void setActiveEmailUrl(String activeEmailUrl) {
		this.activeEmailUrl = activeEmailUrl;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> paraMap = null;
		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
		} catch (Exception e) {
			logger.error("parse request error:", e);
			result.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(result);
		}

		// 判断会员号参数存在
		String loginName = paraMap.get("loginName");
		String idType = paraMap.get("idType");
		String identity = paraMap.get("identity");

		if (StringUtil.isEmpty(loginName) || StringUtil.isEmpty(idType)
				|| !NumberUtil.isNumber(idType)) {
			result.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(result);
		}

		// MemberDto memberDto2 =
		// memberService.queryMemberByLoginName(identity);
		// if(memberDto2 != null){
		// result.put("responseCode",
		// ResponseCodeEnum.UNDEFINED_ERROR.getCode());
		// result.put("responseDesc",
		// identity+"已被注册！请更换其他手机号码后重试！");
		// return JSonUtil.toJSonString(result);
		// }

		MemberDto memberDto = memberService.queryMemberByLoginName(loginName);

		// 判断会员是否存在
		if (null == memberDto) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getResponseCode());
			result.put("responseDesc",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getResponseDesc());
			return JSonUtil.toJSonString(result);
		}

		IndividualInfoDto individualInfoDto = individualInfoService
				.queryIndividualInfoByMemberCode(memberDto.getMemberCode());

		Integer type = IdentityUtil.getIDType(identity);
		boolean f = false;
		
		if (Integer.parseInt(idType) == type && type == IdentityUtil.EMAIL) {
			individualInfoDto.setEmail(identity);
			
			String checkCode = UUID.randomUUID().toString();
			checkCodeService
					.createCheckCode(checkCode, memberDto.getMemberCode(),
							CheckCodeOriginEnum.ACTIVE_EMAIL.getValue(),
							identity, null);
			String url = activeEmailUrl + checkCode;
			
			EmailNotifyRequest request = new EmailNotifyRequest();
			request.addRecAddress(identity);
			request.setTemplateId(active_email_template_id);
			request.setSubject("绑定验证邮箱");

			paraMap.put("url", url);
			request.setData(paraMap);
			jmsSender.send(request);
		} else if (Integer.parseInt(idType) == type
				&& type == IdentityUtil.MOBILE) {
			individualInfoDto.setMobile(identity);
			f = individualInfoService.updateIndividualInfoByMemberCode(individualInfoDto);
			
			if (!f) {
				result.put("responseCode",
						ResponseCodeEnum.UNDEFINED_ERROR.getCode());
				result.put("responseDesc",
						ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
				return JSonUtil.toJSonString(result);
			}
		} else {
			result.put("responseCode",
					ResponseCodeEnum.INVALID_PARAMETER.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.INVALID_PARAMETER.getDesc());
			return JSonUtil.toJSonString(result);
		}

		if (f && !memberDto.getLoginName().equals(identity)
				&& "1".equals(idType)) {
			String oldLoginName = memberDto.getLoginName();
			memberDto.setLoginName(identity);
			logger.info("即将修改新登录名称：" + identity);

			try {
				memberService.updateLoginNameByMemberCode(memberDto);
				// MemberIdentityDto idDto =
				// memberIdentityService.getMemberIdentity(oldLoginName);
				// idDto.setIdContent(identity);
				// memberIdentityService.updateMemberIdentity(idDto);
			} catch (Exception e) {
				logger.error("update member login name error:", e);

				result.put("responseCode",
						ResponseCodeEnum.UNDEFINED_ERROR.getCode());
				result.put("responseDesc", "修改登录名失败！");
				return JSonUtil.toJSonString(result);
			}
		}

		result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(result);
	}
}
