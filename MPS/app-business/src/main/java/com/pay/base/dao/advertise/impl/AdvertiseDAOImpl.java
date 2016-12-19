package com.pay.base.dao.advertise.impl;

import java.util.Map;

import com.pay.base.dao.advertise.AdvertiseDAO;
import com.pay.inf.dao.impl.BaseDAOImpl;



/**
 * @Description 
 * @project 	ma-manager
 * @file 		AdvertiseServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 * Date				Author			Changes
 * 2010-12-31		geng			Create
 */
public class AdvertiseDAOImpl extends BaseDAOImpl implements AdvertiseDAO {
	
	public boolean updateSortById(Map<String ,Object> paraMap) {
		return getSqlMapClientTemplate().update(namespace.concat("updateSortById"), paraMap) == 1;
	}
	
}