/** @Description 
 * @project 	fo-securitycheck
 * @file 		UnanimousCommittee.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-28		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.committee.impl;

import com.pay.fundout.securitycheck.chain.VoterChain;
import com.pay.fundout.securitycheck.committee.AbstractCommittee;
import com.pay.fundout.securitycheck.exception.DeniedException;
import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.Voter;

/**
 * <p>
 * 一致同意委员会，即一票否决
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-28
 * @see
 */
public class UnanimousCommittee extends AbstractCommittee {

	@Override
	protected void doDecide(Principal principal, VoterChain chain) throws DeniedException {
		int grant = 0;
		int abstain = 0;
		DenyVoteMsg msg = new DenyVoteMsg();

		for (Voter voter : chain) {
			int result = voter.vote(principal, msg);
			if (logger.isDebugEnabled()) {
				logger.debug("Voter: " + voter.getDescript() + ", returned: " + result);
			}
			switch (result) {
			case Voter.GRANTED:
				grant++;
				break;
			case Voter.DENIED:
				throw new DeniedException(principal.getBusiType(), msg.getCode(), msg.getTips());
			default:
				abstain++;
				break;
			}
		}

		// 到此说明没有投反对票
		if (grant > 0) {
			return;
		}
	}
}
