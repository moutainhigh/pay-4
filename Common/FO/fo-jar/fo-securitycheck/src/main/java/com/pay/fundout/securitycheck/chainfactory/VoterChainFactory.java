package com.pay.fundout.securitycheck.chainfactory;

import com.pay.fundout.securitycheck.chain.VoterChain;

public interface VoterChainFactory {
	public VoterChain fetchVoterChain(String busiType);
}
