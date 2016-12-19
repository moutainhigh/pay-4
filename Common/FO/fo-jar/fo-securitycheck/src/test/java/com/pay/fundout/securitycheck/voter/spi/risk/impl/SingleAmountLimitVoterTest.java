package com.pay.fundout.securitycheck.voter.spi.risk.impl;

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
public class SingleAmountLimitVoterTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-securitycheck-SingleAmountLimitVoter")
	private Voter singleAmountLimitVoter;

	@Test
	public void test未超限() {
		Principal principal = new Principal("20");
		principal.setPayerMemberCode("10000000638");
		principal.setPayerMemberType(1);
		principal.setPayerAcctType("10");

		principal.setPayeeMemberCode("10000000439");
		principal.setPayeeMemberType(1);
		principal.setPayeeAcctType("10");

		principal.setPayAmount(500L);
		Assert.assertEquals(singleAmountLimitVoter.vote(principal, new DenyVoteMsg()), Voter.GRANTED);
	}
	
	@Test
	public void test超限() {
		Principal principal = new Principal("20");
		principal.setPayerMemberCode("10000000638");
		principal.setPayerMemberType(1);
		principal.setPayerAcctType("10");

		principal.setPayeeMemberCode("10000000439");
		principal.setPayeeMemberType(1);
		principal.setPayeeAcctType("10");

		principal.setPayAmount(5000000000000000000L);
		DenyVoteMsg msg=new DenyVoteMsg();
		Assert.assertEquals(singleAmountLimitVoter.vote(principal, msg), Voter.DENIED);
		Assert.assertEquals(msg.getCode(), Constants.DENY_CODE_RISK_OUTOFAMT_SINGAL);
	}

}
