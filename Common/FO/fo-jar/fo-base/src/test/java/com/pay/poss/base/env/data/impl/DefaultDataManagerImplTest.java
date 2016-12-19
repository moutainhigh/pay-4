/** @Description 
 * @project 	poss-base
 * @file 		DefaultDataManagerImplTest.java 
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

import com.pay.poss.base.env.data.IDataManager;
import com.pay.poss.base.exception.PossException;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:context/spring/base/*.xml" })
public class DefaultDataManagerImplTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "POSS.DATAMANAGER")
	private IDataManager dataMgr;

	@Test
	public void testLoad() {
		try {
			dataMgr.load();
		} catch (PossException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}