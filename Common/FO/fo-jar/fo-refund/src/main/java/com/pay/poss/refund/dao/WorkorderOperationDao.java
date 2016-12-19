/** @Description 
 * @project 	poss-reconcile
 * @file 		WorkorderOperationDao.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-09-01		Sunsea_Li		Create 
 */
package com.pay.poss.refund.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.refund.model.WordorderOperationLogDto;

public interface WorkorderOperationDao extends
		BaseDAO<WordorderOperationLogDto> {

	public void insertBatch(List<WordorderOperationLogDto> paramList);
}
