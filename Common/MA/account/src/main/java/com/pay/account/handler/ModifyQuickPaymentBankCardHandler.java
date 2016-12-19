/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.account.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.member.MemberBankService;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

/**
 * 绑定提现银行卡
 * 
 * @author chma
 */
public class ModifyQuickPaymentBankCardHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private MemberService memberService;
	private MemberBankService memberBankService;

	public void setMemberBankService(MemberBankService memberBankService) {
		this.memberBankService = memberBankService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		return JSonUtil.toJSonString("");
	}

}
