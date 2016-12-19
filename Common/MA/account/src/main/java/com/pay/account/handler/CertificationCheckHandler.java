/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.account.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.dto.IdcVerifyDto;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.cidverify.IdCardVerifyService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

/**
 * 实名认证
 * 
 * @author chma
 */
public class CertificationCheckHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(ModifyPasswordHandler.class);
	private MemberService memberService;
	private IdCardVerifyService idCardVerifyService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setIdCardVerifyService(IdCardVerifyService idCardVerifyService) {
		this.idCardVerifyService = idCardVerifyService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();

		String loginName = paraMap.get("loginName");

		MemberDto memberDto = memberService.queryMemberByLoginName(loginName);
		if (null == memberDto) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getErrorCode());
			result.put("responseDesc",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getMessage());
			return JSonUtil.toJSonString(result);
		}

		IdcVerifyDto idcVerifyDto = idCardVerifyService
				.queryVerifyInfo(memberDto.getMemberCode());

		if (null != idcVerifyDto && idcVerifyDto.getStatus() == 1) {
			result.put("certificationState", 1);
		} else {
			result.put("certificationState", 0);
		}

		result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(result);
	}

}
