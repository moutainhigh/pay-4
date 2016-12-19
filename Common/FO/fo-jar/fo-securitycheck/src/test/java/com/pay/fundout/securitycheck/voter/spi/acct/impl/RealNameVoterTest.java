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
public class RealNameVoterTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-securitycheck-RealNameVoter")
	private Voter realNameVoter;

	@Test
	public void testDoVoteOK() {
		Principal principal = new Principal("10");
		principal.setPayerMemberCode("10000000104");
		principal.setPayerMemberType(1);
		principal.setPayerAcctType("10");

		Assert.assertEquals(realNameVoter.vote(principal, new DenyVoteMsg()), Voter.GRANTED);
	}

	@Test
	public void testDoVoteNOTOK() {
		Principal principal = new Principal("10");
		principal.setPayerMemberCode("10000000638");
		principal.setPayerMemberType(1);
		principal.setPayerAcctType("10");

		DenyVoteMsg msg = new DenyVoteMsg();
		Assert.assertEquals(realNameVoter.vote(principal, msg), Voter.DENIED);
		Assert.assertEquals(msg.getCode(), Constants.DENY_CODE_ACCT_REALNAME);
	}
}
