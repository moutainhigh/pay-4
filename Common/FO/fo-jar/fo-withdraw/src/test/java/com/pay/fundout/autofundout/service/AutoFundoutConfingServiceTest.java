package com.pay.fundout.autofundout.service;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.autofundout.service.AutoFundoutConfigService;
import com.pay.poss.base.exception.PossException;

		@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/spring/base/*.xml",
		"classpath*:context/**/*.xml",
		"classpath*:context/spring/autofundout/*.xml"})
public class AutoFundoutConfingServiceTest extends AbstractTestNGSpringContextTests {
	@Resource(name="fundout-autoFundoutConfigService")
	AutoFundoutConfigService autoFundoutConfigService ;

	/**
	 * @param autoFundoutConfigService the autoFundoutConfigService to set
	 */
	public void setAutoFundoutConfigService(
			AutoFundoutConfigService autoFundoutConfigService) {
		this.autoFundoutConfigService = autoFundoutConfigService;
	}

	@Test
	public void testFindByMemberCodeAndBankCard() throws PossException{
		System.err.println(autoFundoutConfigService.findByMemberCodeAndBankCard(10000000001l, "6225882117165271", "120"));
	}
}
