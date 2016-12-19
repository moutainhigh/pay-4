/**
 *  File: AcctPayAbleVoter.java
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
 * 账户是否可以付款
 * </p>
 * 
 * @author darv
 * 
 */
public class AcctPayAbleVoter extends BaseAcctAttributeVoter {
	@Override
	protected int doVote(int busiType, Principal principal, DenyVoteMsg denyVoteMsg) {
		if (payerUndo.contains(busiType)) {
			return ABSTAIN;
		}

		try {
			AcctAttribDto acctAttrInfo = cachePayerAcctIfNecessary(principal);
			if (acctAttrInfo == null) {// 找不到账户，说明不需要校验该账户属性
				return ABSTAIN;
			}
			if (acctAttrInfo.getPayAble().intValue() == 0) {// 付款账户不可进行付款
				denyVoteMsg.setCode(Constants.DENY_CODE_ACCT_PAYOUT);
				denyVoteMsg.setTips(null);
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
