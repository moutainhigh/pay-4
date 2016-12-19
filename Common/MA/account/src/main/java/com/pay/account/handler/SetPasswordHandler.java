/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.account.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.JSonUtil;

/**
 * 
 * @author chma
 */
public class SetPasswordHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(SetPasswordHandler.class);
	private IMessageDigest iMessageDigest;
	private AcctAttribService acctAttribService;
	private MemberService memberService;

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();

		String loginName = paraMap.get("loginName");
		String payPwd = paraMap.get("payPwd");

		MemberDto memberDto = memberService.queryMemberByLoginName(loginName);

		if (null == memberDto) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getErrorCode());
			result.put("responseDesc",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getMessage());
			return JSonUtil.toJSonString(result);
		}

		try {
			payPwd = iMessageDigest.genMessageDigest(payPwd.getBytes());

			String memberAcctCode = memberDto.getMemberCode().toString()
					+ AcctTypeEnum.BASIC_CNY.getCode();
			AcctAttribDto acctAttribDto = acctAttribService
					.queryAcctAttribWithMemberAcctCode(Long
							.valueOf(memberAcctCode));
			if (null == acctAttribDto) {
				result.put("responseCode",
						ErrorExceptionEnum.ACCT_ATTRIBUTE.getErrorCode());
				result.put("responseDesc",
						ErrorExceptionEnum.ACCT_ATTRIBUTE.getMessage());
				return JSonUtil.toJSonString(result);
			}
			acctAttribService.updatePayPwd(acctAttribDto.getAcctCode(), payPwd);
		} catch (Exception e) {
			logger.error("digest password error:", e);
			result.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(result);
		}

		result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(result);
	}

}
