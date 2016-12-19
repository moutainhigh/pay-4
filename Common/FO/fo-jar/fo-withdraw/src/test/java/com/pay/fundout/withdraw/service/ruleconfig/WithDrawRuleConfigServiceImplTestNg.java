/**
 *  File: WithDrawRuleConfigServiceImplTestNg.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.ruleconfig;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.model.ruleconfig.BatchRuleConfig;
import com.pay.fundout.withdraw.service.ruleconfig.BatchRuleConfigService;

/**
 * @author darv
 * 
 */
@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:config/spring/platform/*.xml", "classpath*:context/context-fundout-ruleconfig-*.xml" })
public class WithDrawRuleConfigServiceImplTestNg extends AbstractTestNGSpringContextTests {
	@Autowired
	@Qualifier("fundout-ruleconfig-BatchRuleConfigService")
	BatchRuleConfigService batchRuleConfigService;

	// set注入
	public void setWithDrawRuleConfigService(final BatchRuleConfigService param) {
		this.batchRuleConfigService = param;
	}

	public BatchRuleConfigService getWithDrawRuleConfigService() {
		return batchRuleConfigService;
	}


	@Test
	public void createBatchRuleConfigTest() {
		BatchRuleConfig rc=new BatchRuleConfig();
		rc.setSequenceId(5L);
		rc.setBatchTimeConfId(3L);
		rc.setEffectDate(new Date());
		rc.setLostEffectDate(new Date());
		rc.setCreationDate(new Date());
		rc.setMaxOrderCounts(1000l);
		rc.setStatus(1);
		rc.setBatchRuleDesc("adfsadf");
		rc.setBusiType(2);
		
		System.out.println(this.batchRuleConfigService.createBatchRuleConfig(rc));
		
	}
	
}
