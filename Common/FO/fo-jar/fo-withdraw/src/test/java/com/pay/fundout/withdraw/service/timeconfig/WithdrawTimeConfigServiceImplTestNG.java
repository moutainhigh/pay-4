/**
 *  File: WithdrawTimeConfigServiceImplTestNG.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.timeconfig;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.model.timeconfig.BatchTimeConfig;
import com.pay.fundout.withdraw.service.timeconfig.BatchTimeConfigService;

/**
 * @author darv
 * 
 */
@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:config/spring/platform/*.xml", "classpath*:context/context-fundout-timeconfig-*.xml" })
public class WithdrawTimeConfigServiceImplTestNG extends AbstractTestNGSpringContextTests {

	@Autowired
	@Qualifier("fundout-timeconfig-BatchTimeConfigService")
	BatchTimeConfigService batchTimeConfigService;

	public BatchTimeConfigService getBatchTimeConfigService() {
		return batchTimeConfigService;
	}

	public void setBatchTimeConfigService(BatchTimeConfigService batchTimeConfigService) {
		this.batchTimeConfigService = batchTimeConfigService;
	}

	@Test
	public void createTimeConfigTest(){
		BatchTimeConfig tc=new BatchTimeConfig();
		tc.setSequenceId(1L);
		tc.setTimeType(1);
		tc.setWeekDayList("1010101");
		tc.setSpecialPoint("1");
		tc.setCreationDate(new Date());
		tc.setUpdateDate(new Date());
		tc.setStatus(1);
		
		System.out.println(this.batchTimeConfigService.createBatchTimeConfig(tc));
		
	}
	
	//@Test
	public void getSequenceIdByWeekDayListTest(){
//		System.out.println(this.batchTimeConfigService.getSequenceIdByWeekDayListAndType("1010100",1));
	}
}
