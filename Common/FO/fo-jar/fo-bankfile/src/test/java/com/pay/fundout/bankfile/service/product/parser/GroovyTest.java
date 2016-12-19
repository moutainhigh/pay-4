package com.pay.fundout.bankfile.service.product.parser;

import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.pay.fundout.bankfile.parser.BaseFileParser;

/**		
 *  @author lIWEI
 *  @Date 2011-5-31
 *  @Description
 *  @Copyright Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
public class GroovyTest {
	public static void main(String[] args) throws Exception, IOException{
		Map<String,String> params = new HashMap<String, String>();
	
		params.put("BATCH_NUM","M_2014");
		params.put("FILE_PATH","C:/Users/liwei/Desktop/银行文件/银联/");
		params.put("FILE_NAME","结果回导.txt");
		params.put("BANK_CODE","11001001");
		params.put("BUSINESS_TYPE","FR_");
		params.put("FILE_NO","213");
		params.put("TRADE_TYPE","2");
		params.put("G_FILE_KY","203");
		
		GroovyTest t = new GroovyTest();
		ClassLoader parent = t.getClass().getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);
		Class<?> groovyClass =loader.parseClass(new File("/opt/groovy/cnpy_R.groovy"));//groovy文件路径，可放在工程里面或者外面任何目录下，计划放在opt目录下方便动态部署
		BaseFileParser groovyObject = (BaseFileParser) groovyClass.newInstance();
		groovyObject.parserFileInfo(params);
		
	}
}
