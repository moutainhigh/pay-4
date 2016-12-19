package com.pay.fundout.securitycheck.voter.spi.acct.impl;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.fundout.securitycheck.common.Constants;
import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.spi.acct.BaseAcctAttributeVoter;
import com.pay.poss.base.exception.PossUntxException;

/**
 * <p>
 * 账户属性冻结投票器
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-12-14
 */
public class AcctPayerFrozenVoter extends BaseAcctAttributeVoter {

	@Override
	protected int doVote(int busiType, Principal principal, DenyVoteMsg denyVoteMsg) {
		if (payerUndo.contains(busiType)) {
			return ABSTAIN;
		}

		try {
			AcctAttribDto acctAttrInfo = cachePayerAcctIfNecessary(principal);
			if (acctAttrInfo.getFrozen().intValue() == 0) {
				// 付款账户已冻结
				denyVoteMsg.setCode(Constants.DENY_CODE_ACCT_PAYER_FREEZE);
				denyVoteMsg.setTips(null);
				return DENIED;
			} else {
				return GRANTED;
			}
		} catch (PossUntxException e) {
			denyVoteMsg.setCode(Constants.DENY_CODE_ACCT_PAYER_FREEZE);// 因为ma对应冻结是通过异常方式返回，故不要当成是系统错误码返回
			denyVoteMsg.setTips(null);
			return DENIED;
		}
	}

}
