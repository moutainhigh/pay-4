/**
 *  File: WithdrawAmountRule
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-12      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.validate.rule;

import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;
import com.pay.inf.rule.MessageRule;
import com.pay.poss.service.ma.member.WithdrawMemberFacadeService;

/**
 * 提现金额验证规则 -是否超出提现可提现金额
 * @author zliner
 */
public class WithdrawAmountRule extends MessageRule {
	
	private WithdrawMemberFacadeService withdrawMemberFacadeService;
	/**
	 * 提现金额验证规则判断--是否超出提现可提现金额
	 * @param validateBean        待验证规则对象
	 * @return boolean            true表示验证规则通过，false表示该验证规则未通过
	 * @throws Exception          验证中抛出运行时异常
	 */
	protected boolean makeDecision(Object validateBean) throws Exception {
		WithdrawRequestDTO withDrawRequest = (WithdrawRequestDTO)validateBean;
		Long canWithdrawAmount =  withdrawMemberFacadeService.getWithdrawAmount(
				Long.valueOf(withDrawRequest.getMemberCode()), withDrawRequest.getAcctType());
		Long getBalance =  withdrawMemberFacadeService.getBalance(
				Long.valueOf(withDrawRequest.getMemberCode()), withDrawRequest.getAcctType());
		Long totol = withDrawRequest.getApplyWithdrawAmount().longValue()+withDrawRequest.getFee().longValue();
		if (withDrawRequest.getApplyWithdrawAmount().longValue() <= canWithdrawAmount && totol<=getBalance) {
			return true;
		}else {
			withDrawRequest.setMessageId(getMessageId());
			return false;
		}
	}
	
	public void setWithdrawMemberFacadeService(
			WithdrawMemberFacadeService withdrawMemberFacadeService) {
		this.withdrawMemberFacadeService = withdrawMemberFacadeService;
	}
}
