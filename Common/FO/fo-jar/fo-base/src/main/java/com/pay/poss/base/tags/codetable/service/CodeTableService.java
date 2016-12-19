 /** @Description 
 * @project 	poss-base
 * @file 		CodeTableService.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-27		Henry.Zeng			Create 
*/
package com.pay.poss.base.tags.codetable.service;

import java.util.List;

import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.tags.codetable.dto.CodeTableDTO;

/**
 * <p>获取表数据的Service</p>
 * @author Henry.Zeng
 * @since 2010-10-27
 * @see 
 */
public interface CodeTableService {
	public final static  String CODE_TABLE_SERVICE_NAME = "context_fundout_base_codeTableService";
	
	public final static  String CODE_TABLE_WITHDRAW_BANK_NAME = "fundOutBankTable";
	
	public final static  String CODE_TABLE_WITHDRAW_BUSI_NAME = "fundOutBusinessTable";
	
	
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
