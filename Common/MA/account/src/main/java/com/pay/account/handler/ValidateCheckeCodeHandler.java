/**
 * 
 */
package com.pay.account.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * @author alex
 *
 */
public class ValidateCheckeCodeHandler implements EventHandler {

	private static final String DEFAULT_TIMEOUT_MINUTE = "30";
	public final Log logger = LogFactory
			.getLog(ValidateCheckeCodeHandler.class);
	private CheckCodeService checkCodeService;
	private MemberService memberService;
	private String checkCodeTimeoutMinute;

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setCheckCodeTimeoutMinute(String checkCodeTimeoutMinute) {
		this.checkCodeTimeoutMinute = checkCodeTimeoutMinute;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		String loginName = paraMap.get("loginName");
		String mobile = paraMap.get("mobile");
		String businessType = paraMap.get("businessType");
		String checkCode = paraMap.get("checkCode");

		Map<String, Object> result = new HashMap<String, Object>();

		// 获取用户信息
		MemberDto member = memberService.queryMemberByLoginName(StringUtils
				.trim(StringUtils.lowerCase(loginName)));

		Long memberCode = null != member ? member.getMemberCode() : null;

		CheckCodeOriginEnum checkEnum = CheckCodeOriginEnum
				.getCheckCodeOriginEnum(businessType);

		if (checkEnum == null) {
			return buildFailResponse(result,
					ErrorExceptionEnum.INVAILD_PARAMETER);
		}

		CheckCodeDto checkCodeObject = checkCodeService.getByLastCheckCode(
				memberCode, checkEnum, mobile);

		if (null == checkCodeObject
				|| !checkCodeObject.getCheckCode().equalsIgnoreCase(checkCode)
				|| checkCodeObject.getStatus() == 2) {
			return buildFailResponse(result,
					ErrorExceptionEnum.CHECK_CODE_ERROR);
		}

		if (StringUtil.isEmpty(checkCodeTimeoutMinute)) {
			checkCodeTimeoutMinute = DEFAULT_TIMEOUT_MINUTE;
		}

		Date createDate = checkCodeObject.getCreateTime();
		Date currentDate = new Date();
		long skipTime = (currentDate.getTime() - createDate.getTime()) / 1000 / 60;
		if (skipTime > Integer.parseInt(checkCodeTimeoutMinute)) {
			return buildFailResponse(result,
					ErrorExceptionEnum.CHECK_CODE_TIMEOUT);
		}

		checkCodeObject.setStatus(2);
		checkCodeService.updateCheckCode(checkCodeObject);

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
}
