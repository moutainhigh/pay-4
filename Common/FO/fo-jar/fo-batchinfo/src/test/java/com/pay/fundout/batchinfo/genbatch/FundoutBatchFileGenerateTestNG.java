/**
 *  File: WithdrawFileGeneratorTestNG.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-9      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.batchinfo.genbatch;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.batchinfo.service.genbatch.BatchFileGenerateService;
import com.pay.poss.base.exception.PossException;


@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/**/*.xml"})
public class FundoutBatchFileGenerateTestNG extends AbstractTestNGSpringContextTests{

	@Resource(name="fo-batchinfo-withdrawFileGenerateService")
	private BatchFileGenerateService fileGenerateService;
	
	/**
	 * @param fileGenerateService the fileGenerateService to set
	 */
	public void setFileGenerateService(BatchFileGenerateService fileGenerateService) {
		this.fileGenerateService = fileGenerateService;
	}
	
	@Test
	public void testGenerateFile(){
		Map<String,String> params = new HashMap<String, String>();
		params.put("BATCH_NUM","M_201105271557081015");
		params.put("filetype","0");
		params.put("BATCH_FILE_PATH","/temp_output");
		params.put("BATCH_FILENAME_SUBFIX",".xls");
		params.put("BATCH_TASK_TYPE","Busi");
		params.put("USER_KY","admin");
		try {
			fileGenerateService.generateBatchFile(params);
		} catch (PossException e) {
			e.printStackTrace();
		}
	}
}
