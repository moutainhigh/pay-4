/**
 *  <p>File: WorkorderOperationServiceImpl.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.refund.service.impl;

import java.util.List;

import com.pay.poss.refund.dao.WorkorderOperationDao;
import com.pay.poss.refund.model.WordorderOperationLogDto;
import com.pay.poss.refund.service.WorkorderOperationService;

public class WorkorderOperationServiceImpl implements WorkorderOperationService {

	private WorkorderOperationDao workorderOperationDao;

	public void setWorkorderOperationDao(
			WorkorderOperationDao workorderOperationDao) {
		this.workorderOperationDao = workorderOperationDao;
	}

	@Override
	public long create(WordorderOperationLogDto pojo) {

		return (Long) workorderOperationDao.create(pojo);
	}

	@Override
	public List<WordorderOperationLogDto> queryByWorkorderKy(String workorderKy) {

		return workorderOperationDao.findByTemplate("queryByWorkorderKy",
				workorderKy);
	}

	@Override
	public void insertBatch(List<WordorderOperationLogDto> paramList) {

		workorderOperationDao.batchCreate("create", paramList);
	}
}
