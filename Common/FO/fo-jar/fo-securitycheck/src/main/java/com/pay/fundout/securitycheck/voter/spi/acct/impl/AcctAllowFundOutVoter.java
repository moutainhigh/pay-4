/**
 *  File: AcctAllowFundOutVoter.java
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
 * 账户是否可转出投票器
 * </p>
 * 
 * @author darv
 * 
 */
public class AcctAllowFundOutVoter extends BaseAcctAttributeVoter {
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

			if (acctAttrInfo.getAllowTransferOut().intValue() == 0) {
				denyVoteMsg.setCode(Constants.DENY_CODE_ACCT_FUNDOUT);
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
