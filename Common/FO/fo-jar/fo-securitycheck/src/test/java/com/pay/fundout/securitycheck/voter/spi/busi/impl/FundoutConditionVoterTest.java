package com.pay.fundout.securitycheck.voter.spi.busi.impl;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.Voter;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:context/spring/**/*.xml", "classpath*:context/*.xml" })
public class FundoutConditionVoterTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-securitycheck-repayCheckVoter")
	private Voter voter;

	@Test
	public void testDoVote() {
		// 出款成功
		Principal principal = new Principal("25");
		principal.setOrderSeqSrc("2001012171514015405");//该笔交易成功
		principal.setPayerMemberCode("10000000114");
		int result = voter.vote(principal, new DenyVoteMsg());
		Assert.assertEquals(result, 1);

		principal = new Principal("13");
		principal.setOrderSeqSrc("2001012171514015405");
		principal.setPayerMemberCode("10000000114");
		result = voter.vote(principal, new DenyVoteMsg());
		Assert.assertEquals(result, -1);
		
		principal = new Principal("111");
		principal.setOrderSeqSrc("2001012171514015405");
		principal.setPayerMemberCode("10000000114");
		result = voter.vote(principal, new DenyVoteMsg());
		Assert.assertEquals(result, 0);
	}

}
