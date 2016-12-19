/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.account.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.member.MemberBankService;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.MemberVerifyService;
import com.pay.acc.service.member.dto.MemberBankDto;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.DESUtil;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 绑定提现银行卡
 * 
 * @author chma
 */
public class BindingWithdrawBankCardHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(BindingWithdrawBankCardHandler.class);
	private MemberBankService memberBankService;
	private MemberService memberService;
	private MemberQueryService memberQueryService;
	private MemberVerifyService memberVerifyService;

	public void setMemberBankService(MemberBankService memberBankService) {
		this.memberBankService = memberBankService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setMemberVerifyService(MemberVerifyService memberVerifyService) {
		this.memberVerifyService = memberVerifyService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();
		String loginName = paraMap.get("loginName");
		String bankAcct = paraMap.get("bankAcct");
		String bankCode = paraMap.get("bankCode");
		String bankBranchId = paraMap.get("bankBranchId");
		String bankBranch = paraMap.get("bankBranch");
		String isDefault = paraMap.get("isDefault");
		String province = paraMap.get("province");
		String city = paraMap.get("city");

		if (StringUtil.isEmpty(isDefault)) {
			isDefault = "0";
		}

		MemberDto memberDto = memberService.queryMemberByLoginName(loginName);

		if (memberDto == null) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getErrorCode());
			result.put("responseDesc",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getMessage());
			return JSonUtil.toJSonString(result);
		}

		// 判断是否实名认证
		boolean verifyFlag = memberVerifyService
				.doQueryRealNameVerifyNsTx(memberDto.getMemberCode());
		if (!verifyFlag) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_NOT_VERIFY.getErrorCode());
			result.put("responseDesc",
					ErrorExceptionEnum.MEMBER_NOT_VERIFY.getMessage());
			return JSonUtil.toJSonString(result);
		}

		MemberInfoDto memberInfoDto = memberQueryService
				.doQueryMemberInfoNsTx(loginName, memberDto.getMemberCode(),
						memberDto.getType(), null);

		MemberBankDto bankDto = memberBankService.doQueryMemberBankNsTx(
				memberDto.getMemberCode(), DESUtil.encrypt(bankAcct));

		try {
			// 更新银行卡
			if (null != bankDto) {
				bankDto.setBankId(bankCode);
				bankDto.setBranchBankName(bankBranch);
				bankDto.setCity(Long.valueOf(city));
				bankDto.setProvince(Long.valueOf(province));
				bankDto.setIsPrimaryBankacct(Integer.valueOf(isDefault));
				bankDto.setBranchBankId(Long.valueOf(bankBranchId));
				bankDto.setName(memberInfoDto.getMemberName());
				bankDto.setStatus(1);
				memberBankService.doModifyBankCard(bankDto);

				result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
				return JSonUtil.toJSonString(result);
			}

			// 添加银行卡

			bankDto = new MemberBankDto();
			bankDto.setBankAcctId(DESUtil.encrypt(bankAcct));
			bankDto.setBankId(bankCode);
			bankDto.setMemberCode(memberDto.getMemberCode());
			bankDto.setBranchBankName(bankBranch);
			bankDto.setCity(Long.valueOf(city));
			bankDto.setProvince(Long.valueOf(province));
			bankDto.setIsPrimaryBankacct(Integer.valueOf(isDefault));
			bankDto.setStatus(1);
			bankDto.setBranchBankId(Long.valueOf(bankBranchId));
			bankDto.setCreationDate(new Date());
			bankDto.setName(memberInfoDto.getMemberName());
			memberBankService.doBindingBankCard(bankDto);
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
