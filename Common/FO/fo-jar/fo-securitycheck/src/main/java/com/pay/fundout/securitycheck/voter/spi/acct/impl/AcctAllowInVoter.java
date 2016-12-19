/**
 *  File: AcctAllowInVoter.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-15     darv      Changes
 *  
 *
 */
package com.pay.fundout.securitycheck.voter.spi.acct.impl;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.fundout.securitycheck.common.Constants;
import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.spi.acct.BaseAcctAttributeVoter;
import com.pay.poss.base.exception.PossUntxException;

/**
 * <p>
 * 账户止入投票器
 * </p>
 * 
 * @author darv
 * 
 */
public class AcctAllowInVoter extends BaseAcctAttributeVoter {
	@Override
	protected int doVote(int busiType, Principal principal, DenyVoteMsg denyVoteMsg) {
		if (payeeUndo.contains(busiType)) {
			return ABSTAIN;
		}

		try {
			AcctAttribDto acctAttrInfo = cachePayeeAcctIfNecessary(principal);

			if (acctAttrInfo == null) {// 找不到账户，说明不需要校验该账户属性
				return ABSTAIN;
			}
			if (acctAttrInfo.getAllowIn().intValue() == 0) {// 收款账户止入
				denyVoteMsg.setCode(Constants.DENY_CODE_ACCT_ALLOWIN);
				// {收款方}的账户状态为{止入}，不可以进行{收款}操作，请联系收款方
				denyVoteMsg.setTips(new String[] { "收款方", "止入" });
				return DENIED;
			} else {
				return GRANTED;
			}
		} catch (PossUntxException e) {
			denyVoteMsg.setCode(Constants.DENY_CODE_SYSTEM_ERROR);
			denyVoteMsg.setTips(new String[] { principal.toString(), this.toString() });
			return DENIED;
		}
	}
}
