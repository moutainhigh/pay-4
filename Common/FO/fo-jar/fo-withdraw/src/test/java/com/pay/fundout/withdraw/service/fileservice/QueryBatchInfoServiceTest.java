 /** @Description 
 * @project 	poss-withdraw
 * @file 		QueryBatchInfoServiceTest.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-14		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.service.fileservice;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-9-14
 * @see 
 */
@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/platform/*.xml","classpath*:config/spring/**/context-fundout-withdraw-rc-bean.xml" })
public class QueryBatchInfoServiceTest extends AbstractTestNGSpringContextTests {

	@Resource(name="fundout-withdraw-querybatchfileservice")
	private QueryBatchFileService batchFileService;
	public void setBatchFileService(QueryBatchFileService batchFileService) {
		this.batchFileService = batchFileService;
	}
	@Test
	public void testBatchFileService() {
//		Page
//		batchFileService.queryBatchFile(page, null);
	}
	
}
