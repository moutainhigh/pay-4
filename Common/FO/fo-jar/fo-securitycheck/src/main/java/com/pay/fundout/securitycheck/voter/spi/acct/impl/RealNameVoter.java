package com.pay.fundout.securitycheck.voter.spi.acct.impl;

import org.apache.commons.lang.StringUtils;

import com.pay.fundout.securitycheck.common.Constants;
import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.spi.acct.BaseAcctAttributeVoter;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

/**
 * <p>
 * 是否实名认证投票器
 * </p>
 * 
 * @author darv
 * 
 */
public class RealNameVoter extends BaseAcctAttributeVoter {

	@Override
	protected int doVote(int busiType, Principal principal, DenyVoteMsg denyVoteMsg) {
		if (payerUndo.contains(busiType)) {
			return ABSTAIN;
		}

		try {
			boolean isRealName = verifyRealName(principal.getPayerMemberCode());
			if (isRealName) {
				return GRANTED;
			} else {
				denyVoteMsg.setCode(Constants.DENY_CODE_ACCT_REALNAME);
				denyVoteMsg.setTips(null);
				return DENIED;
			}
		} catch (Exception e) {
			denyVoteMsg.setCode(Constants.DENY_CODE_SYSTEM_ERROR);
			denyVoteMsg.setTips(new String[] { principal.toString(), this.toString() });
			return DENIED;
		}
	}

	private boolean verifyRealName(String memberCode) throws PossUntxException {
		if (StringUtils.isEmpty(memberCode) || memberCode.length() == 13) {
			return true;
		}
		try {
			return massPayService.verifyRealName(Long.valueOf(memberCode));
		} catch (Exception e) {
			logger.error("查询会员信息失败 [memberCode=" + memberCode + "]", e);
			throw new PossUntxException("查询账户信息失败 [memberCode=" + memberCode + "]", ExceptionCodeEnum.UN_KNOWN_EXCEPTION, e);
		}

	}
}
