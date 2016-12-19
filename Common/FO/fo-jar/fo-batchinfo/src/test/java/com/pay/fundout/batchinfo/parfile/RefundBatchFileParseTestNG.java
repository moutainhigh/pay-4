/**
 *  File: RefundFileParserTestNG.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-10      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.batchinfo.parfile;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.batchinfo.service.parfile.FileParseService;
import com.pay.poss.base.exception.PossException;

/**
 * @author Jason_wang
 *
 */
@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/spring/base/*.xml",
		"classpath*:context/spring/bankfile/context-fundout-bankfile-service.xml"})
public class RefundBatchFileParseTestNG extends AbstractTestNGSpringContextTests{

	@Resource(name="fo-bankfile-refundFileParserService")
	private FileParseService fileParseService;
	
	/**
	 * @param fileParseService the fileParseService to set
	 */
	public void setFileParseService(FileParseService fileParseService) {
		this.fileParseService = fileParseService;
	}
	
	//@Test
	public void testParserFile4HuaXia(){
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("BATCH_NUM","M_906");
		params.put("FILE_PATH","D:/");
		params.put("FILE_NAME","HuaXia-tk.txt");
		params.put("BANK_CODE","1201");
		params.put("BUSINESS_TYPE","RF");
		params.put("FILE_NO","203");
		try {
			fileParseService.fileParse(params);
		} catch (PossException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testParserFile4CP(){
		Map<String,String> params = new HashMap<String, String>();
		params.put("BATCH_NUM","M_906");
		params.put("FILE_PATH","D:/");
		params.put("FILE_NAME","充退银行结果文件 M_096.xls");
		params.put("BANK_CODE","1202");
		params.put("BUSINESS_TYPE","RF");
		params.put("FILE_NO","203");
		try {
			fileParseService.fileParse(params);
		} catch (PossException e) {
			e.printStackTrace();
		}
	}
}
