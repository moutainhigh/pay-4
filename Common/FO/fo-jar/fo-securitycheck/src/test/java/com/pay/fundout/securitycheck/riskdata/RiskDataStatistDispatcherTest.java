package com.pay.fundout.securitycheck.riskdata;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.riskdata.RiskDataStatistDispatcher;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:context/spring/**/*.xml", "classpath*:context/*.xml" })
public class RiskDataStatistDispatcherTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-securitycheck-riskDataStatistDispatcher")
	private RiskDataStatistDispatcher dispatcher;

	@Test
	public void testDispatch() {
		dispatcher.dispatch(buildPrincipal(), "BY_DAY");
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
