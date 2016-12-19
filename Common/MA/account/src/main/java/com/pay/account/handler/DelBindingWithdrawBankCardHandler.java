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
import com.pay.acc.service.member.MemberBankService;
import com.pay.acc.service.member.dto.MemberBankDto;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.DESUtil;
import com.pay.util.JSonUtil;

/**
 * 逻辑删除提现银行卡
 * 
 * @author chma
 */
public class DelBindingWithdrawBankCardHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(DelBindingWithdrawBankCardHandler.class);
	private MemberBankService memberBankService;
	private MemberService memberService;

	public void setMemberBankService(MemberBankService memberBankService) {
		this.memberBankService = memberBankService;
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
		String bankAcct = paraMap.get("bankAcct");

		MemberDto memberDto = memberService.queryMemberByLoginName(loginName);

		if (memberDto == null) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getErrorCode());
			result.put("responseDesc",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getMessage());
			return JSonUtil.toJSonString(result);
		}

		MemberBankDto bankDto = memberBankService.doQueryMemberBankNsTx(
				memberDto.getMemberCode(), DESUtil.encrypt(bankAcct));
		if (null == bankDto) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_BANK_ACCT_ERROR.getErrorCode());
			result.put("responseDesc",
					ErrorExceptionEnum.MEMBER_BANK_ACCT_ERROR.getMessage());
			return JSonUtil.toJSonString(result);
		}

		try {
			bankDto.setStatus(9);
			memberBankService.doModifyBankCard(bankDto);
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
