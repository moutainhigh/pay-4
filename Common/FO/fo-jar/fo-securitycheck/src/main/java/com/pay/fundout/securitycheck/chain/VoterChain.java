package com.pay.fundout.securitycheck.chain;

import com.pay.fundout.securitycheck.voter.Voter;

public interface VoterChain extends Iterable<Voter> {

	public boolean supports(String busiType);

	public String getDescript();

	public void addNextVoter(Voter voter);
}
