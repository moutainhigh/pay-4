package com.pay.rm.service.dao.rmlimit.mcccategory.impl;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.rm.base.exception.PlatformDaoException;
import com.pay.rm.service.dao.rmlimit.mcccategory.RcMccCategoryDAO;
import com.pay.rm.service.model.rmlimit.mcccategory.RcMccCategory;

/**
 * 行业类别
 * 
 * @Description
 * @project rm-service
 * @file RcMccCategoryDAOImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-22 Volcano.Wu Change
 */
public class RcMccCategoryDAOImpl extends BaseDAOImpl implements
		RcMccCategoryDAO {

	@Override
	public boolean delete(String categoryId) throws PlatformDaoException {
		return getSqlMapClientTemplate().delete(namespace.concat("delete"),
				categoryId) == 1;
	}

	@Override
	public RcMccCategory findById(String categoryId) {
		return (RcMccCategory) getSqlMapClientTemplate().queryForObject(
				namespace.concat("findById"), categoryId);
	}
}