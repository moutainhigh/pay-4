/**
 *  File: BaseFileParser.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-10      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.bankfile.parser;

import java.util.Map;

import com.pay.poss.base.exception.PossException;

/**
 * @author Jason_wang
 *
 */
public interface BaseFileParser {

	/**
	 * 解析文件
	 * @param fileInfo
	 * @return
	 * @throws PossException
	 */
	Map<String,Object> parserFileInfo(Map<String,String> fileInfo)throws PossException;
}
