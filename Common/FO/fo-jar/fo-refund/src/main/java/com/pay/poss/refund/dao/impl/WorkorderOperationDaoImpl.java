/**
 *  <p>File: WorkorderOperationDaoImpl.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.refund.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.refund.dao.WorkorderOperationDao;
import com.pay.poss.refund.model.WordorderOperationLogDto;

public class WorkorderOperationDaoImpl extends
		BaseDAOImpl<WordorderOperationLogDto> implements WorkorderOperationDao {

	@Override
	public void insertBatch(List<WordorderOperationLogDto> paramList) {

		super.batchCreate("create", paramList);
	}
}
