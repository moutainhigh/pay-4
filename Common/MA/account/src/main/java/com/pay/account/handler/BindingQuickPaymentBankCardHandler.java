/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.account.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.member.MemberQuickBankAcctService;
import com.pay.account.exception.ErrorExceptionEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.inf.service.BankService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 绑定快捷银行卡
 * 
 * @author chma
 */
public class BindingQuickPaymentBankCardHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private BankService bankService;
	private MemberService memberService;
	private MemberQuickBankAcctService memberQuickBankAcctService;
	private CheckCodeService checkCodeService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public void setMemberQuickBankAcctService(
			MemberQuickBankAcctService memberQuickBankAcctService) {
		this.memberQuickBankAcctService = memberQuickBankAcctService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		String loginName = paraMap.get("loginName");
		String bankAcct = paraMap.get("bankAcct");
		String bankCode = paraMap.get("bankCode");
		String idType = paraMap.get("idType");
		String idNo = paraMap.get("idNo");
		String mobile = paraMap.get("mobile");
		String checkCode = paraMap.get("checkCode");
		String rsvdText1 = paraMap.get("rsvdText1");
		String rsvdText2 = paraMap.get("rsvdText2");
		String rsvdText3 = paraMap.get("rsvdText3");

		Map<String, Object> result = new HashMap<String, Object>();

		MemberDto memberDto = memberService.queryMemberByLoginName(loginName);

		// 判断会员是否存在
		if (null == memberDto) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getResponseCode());
			result.put("responseDesc",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getResponseDesc());
			return JSonUtil.toJSonString(result);
		}

		if (StringUtil.isEmpty(bankAcct) || StringUtil.isEmpty(bankCode)
				|| StringUtil.isEmpty(idType) || StringUtil.isEmpty(idNo)
				|| StringUtil.isEmpty(mobile) || StringUtil.isEmpty(rsvdText1)
				|| StringUtil.isEmpty(rsvdText2)
				|| StringUtil.isEmpty(rsvdText3)) {
			result.put("responseCode",
					ErrorExceptionEnum.PARAMETER_ERROR.getResponseCode());
			result.put("responseDesc",
					ErrorExceptionEnum.PARAMETER_ERROR.getResponseDesc());
			return JSonUtil.toJSonString(result);
		}

		// 验证码校验

		return JSonUtil.toJSonString(result);
	}

}
