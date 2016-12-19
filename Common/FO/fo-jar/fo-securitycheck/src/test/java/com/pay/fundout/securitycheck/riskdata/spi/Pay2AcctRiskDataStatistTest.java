package com.pay.fundout.securitycheck.riskdata.spi;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.riskdata.RiskDataStatist;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:context/spring/**/*.xml", "classpath*:context/*.xml" })
public class Pay2AcctRiskDataStatistTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-securitycheck-pay2acctRiskDataStatist")
	private RiskDataStatist statist;

	@Test
	public void testGetRiskData() {
		System.out.println(statist.getRiskData(buildPrincipal(), "BY_DAY"));
	}

	private Principal buildPrincipal() {
		Principal result = new Principal("21");

		result.setPayerMemberCode("10000000439");
		result.setPayerAcctCode("20010200100011000000043910");
		result.setPayerAcctType("10");
		result.setPayeeMemberCode("10000000114");
		result.setPayeeAcctCode("20010200100011000000011410");
		result.setPayeeAcctType("10");

		result.setAmount(100L);

		return result;
	}
}
