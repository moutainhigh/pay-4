/**
 *  File: SingleAmountLimitVoter.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-15     darv      Changes
 *  
 *
 */
package com.pay.fundout.securitycheck.voter.spi.risk.impl;

import com.pay.fundout.securitycheck.common.Constants;
import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.spi.risk.BaseRiskControlVoter;
import com.pay.rm.facade.dto.RCLimitResultDTO;

/**
 * <p>
 * 单笔限额投票器
 * </p>
 * 
 * @author darv
 * 
 */
public class SingleAmountLimitVoter extends BaseRiskControlVoter {

	@Override
	protected int doVote(int busiType, Principal principal, DenyVoteMsg denyVoteMsg) {
		return super.doVote(busiType, principal, denyVoteMsg);
	}

	@Override
	protected int doVoteIfInWhiteList(int busiType, Principal principal, DenyVoteMsg denyVoteMsg) {
		try {
			RCLimitResultDTO baseRule = cacheRiskRuleIfNecessary(principal);
			if (baseRule == null) { // 风控规则不存在　
				denyVoteMsg.setCode(Constants.DENY_CODE_RISK_NORULE);
				denyVoteMsg.setTips(new String[] { principal.toString(), this.toString() });
				return DENIED;
			}

			if (baseRule.getSingleLimit() < principal.getPayAmount()) {
				denyVoteMsg.setCode(Constants.DENY_CODE_RISK_OUTOFAMT_SINGAL);
				// 单笔最多{0}{1}元
				denyVoteMsg.setTips(new String[] { "$REPLACE", decimalFormat.format(baseRule.getSingleLimit() / 1000.0) });
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
