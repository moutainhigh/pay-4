 /** @Description 
 * @project 	poss-base
 * @file 		CodeTableService.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-27		Henry.Zeng			Create 
*/
package com.pay.rm.base.tags.codetable.service;

import java.util.List;

import com.pay.rm.base.exception.PossUntxException;
import com.pay.rm.base.tags.codetable.dto.CodeTableDTO;

/**
 * <p>获取表数据的Service</p>
 * @author Henry.Zeng
 * @since 2010-10-27
 * @see 
 */
public interface CodeTableService {
	public final static  String CODE_TABLE_SERVICE_NAME = "context_rm_base_codeTableService";
	
	
	/**
	 * 获取该表下面的所有数据
	 * @param codeTableId
	 * @return
	 * @throws PossUntxException 
	 */
	public List<CodeTableDTO> getCodeTables(final String codeTableId) throws PossUntxException;
	/**
	 * 获取该表下面的数据并是页面指定的数据
	 * @param codeTableId
	 * @param selectedValue
	 * @return
	 * @throws PossUntxException 
	 */
	public CodeTableDTO getCodeTable(final String codeTableId,final String selectedValue) throws PossUntxException;
	
}
