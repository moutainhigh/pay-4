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
public class AcctAllowFundInVoterTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-securitycheck-AcctAllowFundInVoter")
	private Voter acctAllowFundInVoter;

	@Test
	public void test中间科目() {

		// 10000000638各属性全为1
		Principal principal = new Principal("10");
		principal.setPayerMemberCode("10000000638");
		principal.setPayerMemberType(1);
		principal.setPayerAcctType("10");

		// 收款方为中间科目，无需投票
		Assert.assertEquals(acctAllowFundInVoter.vote(principal, new DenyVoteMsg()), Voter.ABSTAIN);
	}

	@Test
	public void test正常会员() {

		// 10000000638各属性全为1
		Principal principal = new Principal("10");
		principal.setPayerMemberCode("10000000638");
		principal.setPayerMemberType(1);
		principal.setPayerAcctType("10");
		
		// 10000000638各属性全为1
		principal.setPayeeMemberCode("10000000638");
		principal.setPayeeMemberType(1);
		principal.setPayeeAcctType("10");

		// 收款方为正常会员，应投票通过
		Assert.assertEquals(acctAllowFundInVoter.vote(principal, new DenyVoteMsg()), Voter.GRANTED);
	}

	@Test
	public void test止入会员() {

		// 10000000638各属性全为1
		Principal principal = new Principal("10");
		principal.setPayerMemberCode("10000000638");
		principal.setPayerMemberType(1);
		principal.setPayerAcctType("10");
		
		// 10000000637各属性全为0
		principal.setPayeeMemberCode("10000000637");
		principal.setPayeeMemberType(1);
		principal.setPayeeAcctType("10");

		// 收款方为止入，应投票不通过
		Assert.assertEquals(acctAllowFundInVoter.vote(principal, new DenyVoteMsg()), Voter.DENIED);
	}

}
