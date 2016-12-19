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
 *  @author lIWEI
 *  @Date 2011-4-19
 *  @Description 银行反馈复核文件，结果文件解析测试
 *  @Copyright Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml",
"classpath*:context/**/*.xml"})
public class FundoutBatchFileParseTestNG extends AbstractTestNGSpringContextTests{

	@Resource(name="fo-batchinfo-withdrawFileParserService")
	private FileParseService fileParseService;
	
	/**
	 * @param fileParseService the fileParseService to set
	 */
	public void setFileParseService(FileParseService fileParseService) {
		this.fileParseService = fileParseService;
	}
	
	@Test
	public void testParserFile4CP(){
		
		Map<String,String> params = new HashMap<String, String>();
		
		params.put("BATCH_NUM","M_1000");
		params.put("FILE_PATH","C:/Users/liwei/Desktop/银行文件/中信/");
		params.put("FILE_NAME","中信跨行结果文件.xlsx");
		params.put("BANK_CODE","10013001");
		params.put("BUSINESS_TYPE","FR_");
		params.put("TRADE_TYPE","4");
		params.put("FILE_NO","212");
		params.put("G_FILE_KY","202");
		
		try {
			fileParseService.fileParse(params);
		} catch (PossException e) {
			e.printStackTrace();
		}
	}
}
