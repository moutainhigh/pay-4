package com.pay.fundout.securitycheck.voter;

import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.AbstractVoter;

public class DENIEDVoter extends AbstractVoter {
	@Override
	protected int doVote(int busiType, Principal principal, DenyVoteMsg denyVoteMsg) {
		return DENIED;
	}
}
