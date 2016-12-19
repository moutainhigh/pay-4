package com.pay.fundout.securitycheck.chainfactory.impl;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.securitycheck.chain.VoterChain;
import com.pay.fundout.securitycheck.chainfactory.VoterChainFactory;
import com.pay.fundout.securitycheck.voter.Voter;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:context/spring/**/*.xml", "classpath*:context/*.xml" })
public class DefaultVoterChainFactoryTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-securitycheck-chainfactory")
	private VoterChainFactory factory;

	@Test
	public void testFetchVoterChain() {
		VoterChain chain = factory.fetchVoterChain("24");
		if (chain != null) {
			for (Voter voter : chain) {
				System.out.println(voter.getDescript());
			}
		}
	}

}
