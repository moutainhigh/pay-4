/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.account.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.JSonUtil;

/**
 * 实名认证
 * 
 * @author chma
 */
public class SecurityQuestionSetupHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(SecurityQuestionSetupHandler.class);
	private MemberService memberService;
	private IMessageDigest iMessageDigest;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, String> result = new HashMap<String, String>();

		String loginName = paraMap.get("loginName");
		String questionId = paraMap.get("questionId");
		String questionAnswer = paraMap.get("questionAnswer");

		MemberDto memberDto = memberService.queryMemberByLoginName(loginName);
		if (null == memberDto) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getErrorCode());
			result.put("responseDesc",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getMessage());
			return JSonUtil.toJSonString(result);
		}

		try {
			memberDto.setSecurityQuestion(Integer.valueOf(questionId));
			memberDto.setSecurityAnswer(iMessageDigest
					.genMessageDigest(questionAnswer.getBytes()));
			memberService.updateMember(memberDto);
			result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("setup question error:", e);
			result.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}

		return JSonUtil.toJSonString(result);
	}

}
