package com.pay.fundout.securitycheck.voter.spi.acct.impl;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.fundout.securitycheck.common.Constants;
import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.Voter;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:context/spring/**/*.xml", "classpath*:config/spring/**/*.xml", "classpath*:context/*.xml" })
public class AcctPayerFrozenVoterTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-securitycheck-AcctPayerFrozenVoter")
	private Voter acctFrozenVoter;

	@Test
	public void testDoVoteOK() {

		Principal principal = new Principal("10");
		principal.setPayerMemberCode("10000000638");
		principal.setPayerMemberType(1);
		principal.setPayerAcctType("10");

		// 10000000638各属性全为1，故需投票通过
		Assert.assertEquals(acctFrozenVoter.vote(principal, new DenyVoteMsg()), Voter.GRANTED);
	}

	@Test
	public void testDoVoteNotOK() {

		Principal principal = new Principal("10");
		principal.setPayerMemberCode("10000000637");
		principal.setPayerMemberType(1);
		principal.setPayerAcctType("10");

		DenyVoteMsg msg = new DenyVoteMsg();
		int result = acctFrozenVoter.vote(principal, msg);

		// 10000000637各属性全为0，故需投票不通过
		Assert.assertEquals(Voter.DENIED, result);
		Assert.assertEquals(Constants.DENY_CODE_ACCT_PAYER_FREEZE, msg.getCode());
	}
}
