/**
 *  File: BlackListVoter.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.securitycheck.voter.spi.risk.impl;

import com.pay.fundout.securitycheck.common.Constants;
import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.spi.risk.BaseRiskControlVoter;

/**
 * <p>
 * 黑名单投票器
 * </p>
 * 
 * @author darv
 * 
 */
public class BlackListVoter extends BaseRiskControlVoter {

	@Override
	protected int doVoteIfInWhiteList(int busiType, Principal principal, DenyVoteMsg denyVoteMsg) {
		return 0;
	}
	
	@Override
	public boolean supports(int busiType) {
		return false;
	}

	@Override
	protected int doVote(int busiType, Principal principal, DenyVoteMsg denyVoteMsg) {
		try {
			boolean isBlackList = rcNameListService.isBlack(principal.getPayerMemberCode(), Integer.valueOf(principal.getPayerAcctType()), bwList.get(principal.getBusiType()
					+ principal.getPayerMemberType() + principal.getPayeeMemberType()));
			if (isBlackList) {
				principal.setPayerBWFlag(-1);
				denyVoteMsg.setCode(Constants.DENY_CODE_RISK_ISBLACKLIST);
				denyVoteMsg.setTips(null);
				return DENIED;
			} else {
				return GRANTED;
			}
		} catch (Exception e) {
			denyVoteMsg.setCode(Constants.DENY_CODE_SYSTEM_ERROR);
			denyVoteMsg.setTips(new String[] { principal.toString(), this.toString() });
			return DENIED;
		}
	}
}
