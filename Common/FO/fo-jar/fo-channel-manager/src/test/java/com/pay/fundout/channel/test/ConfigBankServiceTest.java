 /** @Description 
 * @project 	fo-channel-manager
 * @file 		ConfigBankServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-2		Henry.Zeng			Create 
*/
package com.pay.fundout.channel.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.channel.dao.configbank.ConfigBankDAO;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-11-2
 * @see 
 */
@ContextConfiguration(locations={"classpath*:context/env/*.xml",
"classpath*:context/spring/configbank/*.xml"})
public class ConfigBankServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private transient ConfigBankDAO configBankDAO;
	
	public void setConfigBankDAO(final ConfigBankDAO configBankDAO) {
		this.configBankDAO = configBankDAO;
	}
	

	@Test
	public void queryFundOutBank2Withdraw() {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("targetBankId","800" );
		params.put("foMode", "1");
		params.put("fobusiness", "0");
		String withdrawBankId = configBankDAO.queryFoBank2Withdraw(params);
		System.out.println(withdrawBankId);
	}

}