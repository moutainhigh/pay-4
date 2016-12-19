/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.account.handler;

import java.util.HashMap;
import java.util.List;
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
import com.pay.inf.service.BankService;
import com.pay.util.DESUtil;
import com.pay.util.JSonUtil;

/**
 * 查询提现银行卡
 * 
 * @author chma
 */
public class QueryWithdrawBankCardHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private MemberBankService memberBankService;
	private MemberService memberService;
	private BankService bankService;

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

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

		MemberDto memberDto = memberService.queryMemberByLoginName(loginName);

		if (null == memberDto) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getErrorCode());
			result.put("responseDesc",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getMessage());
			return JSonUtil.toJSonString(result);
		}

		List<MemberBankDto> bankList = memberBankService
				.doQueryMemberBankNsTx(memberDto.getMemberCode());

		if (null != bankList && !bankList.isEmpty()) {
			for (MemberBankDto bank : bankList) {
				String bankName = bankService.getBankById(bank.getBankId());
				bank.setBankName(bankName);
				bank.setBankAcctId(DESUtil.decrypt(bank.getBankAcctId()));
			}
		}

		result.put("bankCardList", bankList);

		result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(result);
	}

}
