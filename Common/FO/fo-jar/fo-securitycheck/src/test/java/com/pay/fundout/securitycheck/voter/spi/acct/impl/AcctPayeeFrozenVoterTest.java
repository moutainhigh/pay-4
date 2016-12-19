package com.pay.fundout.securitycheck.voter.spi.acct.impl;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.Voter;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:context/spring/**/*.xml", "classpath*:config/spring/**/*.xml", "classpath*:context/*.xml" })
public class AcctPayeeFrozenVoterTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-securitycheck-AcctPayeeFrozenVoter")
	private Voter acctFrozenVoter;

	@Test
	public void testDoVoteOK() {
		// 10000000638各属性全为1
		Principal principal = new Principal("10");
		principal.setPayerMemberCode("10000000638");
		principal.setPayerMemberType(1);
		principal.setPayerAcctType("10");

		// 收款方为中间科目，无需投票
		Assert.assertEquals(acctFrozenVoter.vote(principal, new DenyVoteMsg()), Voter.ABSTAIN);
	}

}
