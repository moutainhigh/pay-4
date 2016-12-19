/**
 *  File: MonthAmountLimitVoter.java
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
import com.pay.fundout.securitycheck.model.RiskData;
import com.pay.fundout.securitycheck.voter.spi.risk.BaseRiskControlVoter;
import com.pay.rm.facade.dto.RCLimitResultDTO;

/**
 * <p>
 * 当月付款次数和限额投票器
 * </p>
 * 
 * @author darv
 * 
 */
public class MonthLimitVoter extends BaseRiskControlVoter {

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
			RiskData riskData = riskDataStatService.dispatch(principal, "BY_MONTH");
			if (riskData == null) { // 风控数据不存在
				denyVoteMsg.setCode(Constants.DENY_CODE_RISK_NODATA);
				denyVoteMsg.setTips(new String[] { principal.toString(), this.toString() });
				return DENIED;
			}

			if (baseRule.getMonthLimit() < riskData.getMonthLimit() + principal.getPayAmount()) {
				denyVoteMsg.setCode(Constants.DENY_CODE_RISK_OUTOFAMT_MONTH);
				// 每月最多{0}{1}元，您今天最多还可以{0}{2}元
				denyVoteMsg.setTips(new String[] { "$REPLACE", decimalFormat.format(baseRule.getMonthLimit() / 1000.0), decimalFormat.format((baseRule.getMonthLimit() - riskData.getMonthLimit()) / 1000.0) });
				return DENIED;
			}

			if (baseRule.getMonthTimes() < riskData.getMonthTimes()) {
				denyVoteMsg.setCode(Constants.DENY_CODE_RISK_OUTOFCNT_MONTH);
				// 您本月还可以{0}{1}次
				denyVoteMsg.setTips(new String[] { "$REPLACE", String.valueOf(baseRule.getMonthTimes() - riskData.getMonthTimes()) });
				return DENIED;
			}
			return GRANTED;
		} catch (Exception e) {
			denyVoteMsg.setCode(Constants.DENY_CODE_SYSTEM_ERROR);
			denyVoteMsg.setTips(new String[] { principal.toString(), this.toString() });
			return DENIED;
		}
	}
}
