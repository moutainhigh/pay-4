/**
 *  File: WithdrawTimeConfigServiceImplTestNG.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.ruleoperator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.model.ruleoperator.BatchRuleOperator;
import com.pay.fundout.withdraw.service.ruleoperator.BatchRuleOperatorService;

/**
 * @author darv
 * 
 */
@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:config/spring/platform/*.xml", "classpath*:context/context-fundout-ruleoperator-*.xml" })
public class WithdrawRuleOperatorServiceImplTestNG extends AbstractTestNGSpringContextTests {

	@Autowired
	@Qualifier("fundout-ruleoperator-BatchRuleOperatorService")
	BatchRuleOperatorService batchRuleOperatorService;

	public BatchRuleOperatorService getBatchRuleOperatorService() {
		return batchRuleOperatorService;
	}

	public void setBatchRuleOperatorService(BatchRuleOperatorService batchRuleOperatorService) {
		this.batchRuleOperatorService = batchRuleOperatorService;
	}

	@Test
	public void createRuleOperatorTest() {
		BatchRuleOperator bo=new BatchRuleOperator();
		bo.setSequenceId(1L);
		bo.setBatchRuleId(2L);
		bo.setIdentity("23");
		bo.setCreationDate(new Date());
		bo.setUpdateDate(new Date());
		bo.setStatus(1);
		
		System.out.println(this.batchRuleOperatorService.createBatchRuleOperator(bo));
	}
}
