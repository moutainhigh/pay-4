package com.pay.poss.refund.common.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pay.poss.base.common.properties.CommonConfiguration;
import com.pay.poss.base.env.context.ContextService;

@ContextConfiguration(locations = {
		"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:config/spring/platform/*.xml"})
public class PoiExcelUtilTest extends AbstractTestNGSpringContextTests{

	@BeforeClass
	public void init() {
	}
	
	@Test
	public void testProcessExcel(){
		Map<String,String> fileInfo = new HashMap<String, String>();
		fileInfo.put("BATCH_FILE_PATH", CommonConfiguration.getStrProperties("refundBatchFilePath"));
		fileInfo.put("BATCH_FILENAME_SUBFIX", ".xls");
		fileInfo.put("BATCH_NUM", "SYS_0");
		fileInfo.put("BATCH_TASK_TYPE", "200001");
		//PoiExcelUtil.processExcel(fileInfo);
	}
}
