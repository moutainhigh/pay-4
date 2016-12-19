/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.account.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.jms.notification.request.EmailNotifyRequest;
import com.pay.jms.notification.request.SMSNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.IdentityUtil;
import com.pay.util.JSonUtil;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;
import com.pay.util.UUIDUtil;

/**
 * 找回密码
 * 
 * @author chma
 */
public class FindPasswordHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(FindPasswordHandler.class);
	private MemberService memberService;
	private CheckCodeService checkCodeService;
	private MemberQueryService memberQueryService;
	private JmsSender jmsSender;
	final long smsTemplateId = 38;
	final long loginMailTemplateId = 39;

	final long payMailTemplateId = 7;
	private String findPwdUrl;
	private String findPayPwdUrl;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setFindPwdUrl(String findPwdUrl) {
		this.findPwdUrl = findPwdUrl;
	}

	public void setFindPayPwdUrl(String findPayPwdUrl) {
		this.findPayPwdUrl = findPayPwdUrl;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();

		logger.info("收到找回密码请求：" + dataMsg);

		String loginName = paraMap.get("loginName");
		String type = paraMap.get("type");
		String flag = paraMap.get("flag");

		if (StringUtils.isEmpty(loginName) || StringUtil.isEmpty(type)
				|| !NumberUtil.isNumber(type) || StringUtil.isEmpty(flag)
				|| !NumberUtil.isNumber(flag)) {
			result.put("responseCode",
					ResponseCodeEnum.INVALID_PARAMETER.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.INVALID_PARAMETER.getDesc());
			return JSonUtil.toJSonString(result);
		}

		// 获取用户信息
		MemberDto member = memberService.queryMemberByLoginName(StringUtils
				.trim(StringUtils.lowerCase(loginName)));

		if (member == null) {
			return buildFailResponse(result,
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR);
		}

		Integer idType = IdentityUtil.getIDType(loginName);
		// 如果是企业用户，则提示无法通过这个方式找回
		if (member.getType() == MemberTypeEnum.MERCHANT.getCode()) {
			return buildFailResponse(result,
					ErrorExceptionEnum.MEMBER_FIND_PASSWORD_NOT_SUPPORT);
		}
		// 临时会员提示用户不存在
		if (member.getStatus() == MemberStatusEnum.PROVISIONAL.getCode()) {
			// 用户不存在
			return buildFailResponse(result,
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR);
		}
		Long memberCode = member.getMemberCode();

		MemberInfoDto memberInfo = memberQueryService.doQueryMemberInfoNsTx(
				loginName, memberCode, member.getType(), null);

		if (IdentityUtil.MOBILE == Integer.valueOf(type)) {

			String mobile = memberInfo.getMobile();

			if (StringUtil.isEmpty(mobile)) {
				return buildFailResponse(result,
						ErrorExceptionEnum.MEMBER_NOT_BINDING_MOBILE);
			}

			String str = checkCode();
			result.put("checkCode", str);

			String businessType = CheckCodeOriginEnum.SMS_FINDLOGINPWD
					.getValue();

			if ("2".equals(flag)) {
				businessType = CheckCodeOriginEnum.SMS_FINDPAYPWD.getValue();
			}

			checkCodeService.createCheckCode(str, memberCode, businessType,
					null, mobile);
			// send sms

			SMSNotifyRequest request = new SMSNotifyRequest();
			request.addMobile(mobile);
			request.setTemplateId(smsTemplateId);

			paraMap.put("abc", str);
			request.setData(paraMap);
			jmsSender.send(request);

		} else if (IdentityUtil.EMAIL == Integer.valueOf(type)) {

			// 发送密码重置邮件
			String email = memberInfo.getEmail();
			String checkCode = UUIDUtil.uuid();

			String businessType = CheckCodeOriginEnum.EMAIL_FINDLOGINPWD
					.getValue();

			String url = findPwdUrl;

			long mailTemplateId = loginMailTemplateId;

			if ("2".equals(flag)) {
				businessType = CheckCodeOriginEnum.EMAIL_FINDPAYPWD.getValue();
				url = findPayPwdUrl;
				mailTemplateId = payMailTemplateId;
			}

			checkCodeService.createCheckCode(checkCode, memberCode,
					businessType, email, null);
			EmailNotifyRequest request = new EmailNotifyRequest();
			request.addRecAddress(email);
			request.setTemplateId(mailTemplateId);
			request.setSubject("邮箱找回密码");

			paraMap.put("userId", memberInfo.getMemberName());
			paraMap.put("url", url + checkCode);
			request.setData(paraMap);
			jmsSender.send(request);
		} else {
			result.put("securityQuestionId", member.getSecurityQuestion());
			result.put("securityAnswer", member.getSecurityAnswer());
		}

		result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(result);
	}

	private String buildFailResponse(Map<String, Object> result,
			ErrorExceptionEnum errorExceptionEnum) {

		result.put("responseCode", errorExceptionEnum.getErrorCode());
		result.put("responseDesc", errorExceptionEnum.getMessage());
		return JSonUtil.toJSonString(result);
	}

	/**
	 * 获取6位随机数字验证码
	 * 
	 * @return
	 */
	private String checkCode() {
		Random random = new Random();
		String str = "";
		for (int i = 0; i < 6; i++) {
			int tmp = random.nextInt(10);
			str += tmp + "";
		}
		return str;
	}

}
