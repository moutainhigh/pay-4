package com.pay.poss.service.gateway;
/* *//** @Description 
 * @project 	poss-external-api-impl
 * @file 		MaServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-27		Henry.Zeng			Create 
*//*
package com.pay.poss.service.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

*//**
 * <p>网关接口Service</p>
 * @author Henry.Zeng
 * @since 2010-8-27
 * @see 
 *//*

public class GateWayServiceImplTest extends AbstractTestNGSpringContextTests{
	
	@Autowired
	@Qualifier("rc4GateWayService")
	private transient RC4GateWayServiceApi gateWayService;
	
	
	@Test
	public void testGetBankInfoList() {
		gateWayService.getBankInfoList();
	}

	@Test
	public void testGetProviderCodeList() {
		gateWayService.getProviderCodeList();
	}

	@Test
	public void testGetProviderCodeListByBankCode() {
		gateWayService.getProviderCodeListByBankCode(2010081117151210001L);
	}



}
*/