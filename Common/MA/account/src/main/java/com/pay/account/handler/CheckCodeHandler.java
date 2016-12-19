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
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.jms.notification.request.SMSNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.IdentityUtil;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 获取验证码
 * 
 * @author chma
 */
public class CheckCodeHandler implements EventHandler {

	private static final String DEFAULT_TIMEOUT_MINUTE = "30";
	public final Log logger = LogFactory.getLog(CheckCodeHandler.class);
	private MemberService memberService;
	private CheckCodeService checkCodeService;
	private JmsSender jmsSender;
	private String checkCodeTimeoutMinute;
	static Integer defaultSmsTemplateId = 10600;
	static Map<String, Integer> smsTempalteIds = new HashMap<String, Integer>();
	static {
		smsTempalteIds.put("active_register", 10601);
		smsTempalteIds.put("sms_findLoginPwd", 10602);
		smsTempalteIds.put("sms_findPayPwd", 10603);
		smsTempalteIds.put("bind_mobile_sms", 10604);
		smsTempalteIds.put("quick_pay_mobile", 10605);
		smsTempalteIds.put("reset_login_pwd", 10606);
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	public void setCheckCodeTimeoutMinute(String checkCodeTimeoutMinute) {
		this.checkCodeTimeoutMinute = checkCodeTimeoutMinute;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();

		String loginName = paraMap.get("loginName");
		String mobile = paraMap.get("mobile");
		String businessType = paraMap.get("businessType");

		if (!IdentityUtil.validateMobile(mobile)) {
			result.put("responseCode",
					ResponseCodeEnum.INVALID_PARAMETER.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.INVALID_PARAMETER.getDesc());
			return JSonUtil.toJSonString(result);
		}

		CheckCodeOriginEnum checkEnum = CheckCodeOriginEnum
				.getCheckCodeOriginEnum(businessType);

		if (checkEnum == null) {
			return buildFailResponse(result,
					ErrorExceptionEnum.INVAILD_PARAMETER);
		}

		Integer smsTemplateId = smsTempalteIds.get(businessType);
		if (null == smsTemplateId) {
			smsTemplateId = defaultSmsTemplateId;
		}

		// 获取用户信息
		Long memberCode = null;

		if (!StringUtil.isEmpty(loginName)) {
			MemberDto member = memberService.queryMemberByLoginName(StringUtils
					.trim(StringUtils.lowerCase(loginName)));
			if (null != member) {
				memberCode = member.getMemberCode();
			}
		}

		String str = checkCode();
		result.put("checkCode", str);

		checkCodeService.createCheckCode(str, memberCode, businessType, null,
				mobile);
		// send sms
		SMSNotifyRequest request = new SMSNotifyRequest();
		request.addMobile(mobile);
		request.setTemplateId(smsTemplateId);

		if (StringUtil.isEmpty(checkCodeTimeoutMinute)) {
			checkCodeTimeoutMinute = DEFAULT_TIMEOUT_MINUTE;
		}

		paraMap.put("checkCode", str);
		paraMap.put("maxMinute", checkCodeTimeoutMinute);
		request.setData(paraMap);
		jmsSender.send(request);

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
