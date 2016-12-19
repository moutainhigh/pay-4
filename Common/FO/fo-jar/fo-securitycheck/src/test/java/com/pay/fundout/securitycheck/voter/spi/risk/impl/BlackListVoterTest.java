package com.pay.fundout.securitycheck.voter.spi.risk.impl;


import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.spi.risk.impl.BlackListVoter;

@ContextConfiguration(locations = { "classpath*:context/spring/base/*.xml",
		"classpath*:config/env/test-dataAccessContext.xml", "classpath*:context/spring/**/*.xml",
		"classpath*:config/spring/**/*.xml", "classpath*:context/*.xml" })
public class BlackListVoterTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-securitycheck-BlackListVoter")
	private BlackListVoter blackListVoter;

	@Test
	public void testDoVoteIfInBlackList() {
		Principal principal = new Principal("22");
		principal.setPayerMemberCode("10000000001");
		principal.setPayAmount(31000);
		principal.setPayerAcctType("10");
		principal.setPayerMemberType(2);
		
		principal.setPayeeMemberType(1);

		System.out.println("isBlackList:" + blackListVoter.doVote(22, principal, new DenyVoteMsg()));
	}

}
