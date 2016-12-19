/**
 *  File: MemberAuthenticationRule.java
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
 * 会员实名验证方法
 * @author zliner
 */
public class MemberAuthenticationRule extends MessageRule {
	
	private WithdrawMemberFacadeService withdrawMemberFacadeService;
	
	/**
	 * 会员实名验证规则执行会员实名验证方法
	 * @param validateBean        待验证规则对象
	 * @return boolean            true表示验证规则通过，false表示该验证规则未通过
	 * @throws Exception          验证中抛出运行时异常
	 */
	protected boolean makeDecision(Object validateBean) throws Exception {
		WithdrawRequestDTO withdrawRequestDTO =(WithdrawRequestDTO) validateBean;
		if (withdrawRequestDTO.isBusiness()) {
			return true;
		}
		boolean flag = withdrawMemberFacadeService.doQueryRealNameVerify(Long.valueOf(withdrawRequestDTO.getMemberCode()));
		if(flag) {
			return true;
		}else {
			withdrawRequestDTO.setMessageId(getMessageId());
			return false;
		}
	}
	
	public void setWithdrawMemberFacadeService(
			WithdrawMemberFacadeService withdrawMemberFacadeService) {
		this.withdrawMemberFacadeService = withdrawMemberFacadeService;
	}
}
