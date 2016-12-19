package com.pay.fundout.batchinfo.genbatch;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.batchinfo.service.genbatch.BatchFileGenerateService;
import com.pay.poss.base.exception.PossException;

/**		
 *  @author lIWEI
 *  @Date 2011-6-23
 *  @Description 充退出款文件生成测试
 *  @Copyright Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
@Test
@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml",
"classpath*:context/**/*.xml"})
public class RefundBatchFileGenerateTestNG extends AbstractTestNGSpringContextTests {

	@Resource(name = "fo-batchinfo-refundFileGenerateService")
	private BatchFileGenerateService fileGenerateService;

	@Test
	 public void testGenerateFile() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("BATCH_NUM", "M_201106230000000000");
		params.put("filetype", "1");
		params.put("BATCH_FILE_PATH", "/temp_output");
		params.put("BATCH_FILENAME_SUBFIX", ".xls");
		params.put("BATCH_TASK_TYPE", "Busi");
		params.put("USER_KY", "admin");
		try {
			fileGenerateService.generateBatchFile(params);
		} catch (PossException e) {
			e.printStackTrace();
		}
	}

}
