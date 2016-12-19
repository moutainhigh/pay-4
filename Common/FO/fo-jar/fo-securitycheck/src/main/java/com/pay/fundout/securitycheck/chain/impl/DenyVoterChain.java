package com.pay.fundout.securitycheck.chain.impl;

import com.pay.fundout.securitycheck.chain.AbstractVoterChain;
import com.pay.fundout.securitycheck.voter.DENIEDVoter;

public class DenyVoterChain extends AbstractVoterChain {

	public DenyVoterChain() {
		respository.add(new DENIEDVoter());
	}

	@Override
	public boolean supports(String busiType) {
		return true;
	}

	@Override
	public String getDescript() {
		return "DefaultVoterChain ,only contain DENIEDVoter";
	}

}
