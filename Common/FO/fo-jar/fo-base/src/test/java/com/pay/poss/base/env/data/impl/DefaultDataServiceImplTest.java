 /** @Description 
 * @project 	poss-base
 * @file 		DefaultDataServiceImplTest.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-9		Rick_lv			Create 
*/
package com.pay.poss.base.env.data.impl;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.poss.base.env.data.IDataService;


@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:context/spring/base/*.xml" })
public class DefaultDataServiceImplTest extends AbstractTestNGSpringContextTests{
	@Resource(name = "POSS.DATASERVICE")
	private IDataService dataService;
	
	@Test
	public void testDD(){
		dataService.getCodeMapping("", "");
	}
}