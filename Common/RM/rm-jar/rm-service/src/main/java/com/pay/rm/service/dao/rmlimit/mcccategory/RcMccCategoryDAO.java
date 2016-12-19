package com.pay.rm.service.dao.rmlimit.mcccategory;

import com.pay.inf.dao.BaseDAO;
import com.pay.rm.base.exception.PlatformDaoException;

/**
 * 行业类别
 * 
 * @Description
 * @project rm-service
 * @file RcMccCategoryDAO.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-22 Volcano.Wu Change
 */
public interface RcMccCategoryDAO<RcMccCategory> extends BaseDAO<RcMccCategory> {

	public boolean delete(String categoryId) throws PlatformDaoException;

	public RcMccCategory findById(String categoryId);
}