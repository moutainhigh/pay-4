package com.pay.fundout.securitycheck.committee.impl;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.securitycheck.committee.impl.UnanimousCommittee;
import com.pay.fundout.securitycheck.exception.DeniedException;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.tool.ErrorTipUtil;

@ContextConfiguration(locations = { "classpath*:context/spring/base/*.xml",
		"classpath*:config/env/test-dataAccessContext.xml", "classpath*:context/spring/**/*.xml",
		"classpath*:config/spring/**/*.xml", "classpath*:context/*.xml" })
public class UnanimousCommitteeTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-securitycheck-unanimousCommittee")
	private UnanimousCommittee committee;

	@Test
	public void testDoDecide() {
		try {
			committee.decide(buildPrincipal());
		} catch (DeniedException e) {
			System.out.println(ErrorTipUtil.getErrorTip(e));
		}
	}

	private Principal buildPrincipal() {
		Principal principal = new Principal("22");
//		principal.setPayerMemberCode("1002200000001");
//		principal.setPayerAcctType("10");
//		principal.setPayerMemberType(1);
//		
//		principal.setPayeeMemberCode("10000000623");
//		principal.setPayeeAcctType("10");
//		principal.setPayeeMemberType(1);
//		
//		principal.setPayAmount(1000);
//		principal.setAmount(1000);
		
		
		principal.setPayerMemberCode("10000000001");
		principal.setPayAmount(31000);
		principal.setPayerAcctType("10");
		principal.setPayerMemberType(2);
		
		principal.setPayeeMemberType(1);
		
		
		return principal;
	}
}
