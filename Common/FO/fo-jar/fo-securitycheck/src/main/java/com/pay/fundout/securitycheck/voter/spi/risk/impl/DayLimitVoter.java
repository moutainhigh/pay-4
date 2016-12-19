package com.pay.fundout.securitycheck.voter.spi.risk.impl;

import com.pay.fundout.securitycheck.common.Constants;
import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.model.RiskData;
import com.pay.fundout.securitycheck.voter.spi.risk.BaseRiskControlVoter;
import com.pay.rm.facade.dto.RCLimitResultDTO;

/**
 * <p>
 * 当日付款次数和金额投票器
 * </p>
 * 
 * @author darv
 * 
 */
public class DayLimitVoter extends BaseRiskControlVoter {

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
			RiskData riskData = riskDataStatService.dispatch(principal, "BY_DAY");
			if (riskData == null) { // 风控数据不存在
				denyVoteMsg.setCode(Constants.DENY_CODE_RISK_NODATA);
				denyVoteMsg.setTips(new String[] { principal.toString(), this.toString() });
				return DENIED;
			}

			if (baseRule.getDayLimit() < riskData.getDayLimit() + principal.getPayAmount()) {
				denyVoteMsg.setCode(Constants.DENY_CODE_RISK_OUTOFAMT_DAY);
				// 每日最多{0}{1}元，您今天最多还可以{0}{2}元
				denyVoteMsg.setTips(new String[] { "$REPLACE", decimalFormat.format(baseRule.getDayLimit() / 1000.0), decimalFormat.format((baseRule.getDayLimit() - riskData.getDayLimit()) / 1000.0) });
				return DENIED;
			}

			if (baseRule.getDayTimes() < riskData.getDayTimes()) {
				denyVoteMsg.setCode(Constants.DENY_CODE_RISK_OUTOFCNT_DAY);
				// 您今日还可以{0}{1}次
				denyVoteMsg.setTips(new String[] { "$REPLACE", String.valueOf(baseRule.getDayTimes() - riskData.getDayTimes()) });
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
