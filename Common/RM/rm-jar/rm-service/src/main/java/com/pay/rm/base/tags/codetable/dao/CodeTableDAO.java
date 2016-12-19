/** @Description 
 * @project 	poss-base
 * @file 		CodeTableDAO.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-27		Henry.Zeng			Create 
 */
package com.pay.rm.base.tags.codetable.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.rm.base.exception.PossUntxException;
import com.pay.rm.base.tags.codetable.dto.CodeTableDTO;
import com.pay.rm.base.tags.codetable.dto.CodeTableDefinitionDTO;

/**
 * <p>
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-10-27
 * @see
 */
public interface CodeTableDAO extends BaseDAO<CodeTableDTO> {
	/**
	 * 取得表中的所有代码
	 * 
	 * @param tableDef
	 * @return List
	 * @throws PafaDAOException
	 */
	public List<CodeTableDTO> getCodeTables(CodeTableDefinitionDTO tableDef)
			throws PossUntxException;
}
