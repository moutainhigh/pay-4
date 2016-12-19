 /** @Description 
 * @project 	poss-withdraw
 * @file 		FoAbstractParaser.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-21		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.service.result;

import java.util.Map;

import com.pay.fundout.withdraw.dto.result.WithdrawImportFileDTO;
import com.pay.poss.base.exception.PossException;

/**
 * <p>解析文件包括复核和结果导入</p>
 * @author Henry.Zeng
 * @since 2010-9-21
 * @see 
 */
public interface ParserFileHandlerService {
	/**
	 * 解析文件包括复核和结果导入
	 * @param importFileDto
	 * @return
	 */
	public Map<String,Object> parserFileRdTx(WithdrawImportFileDTO importFileDto) throws PossException ;
	
}
