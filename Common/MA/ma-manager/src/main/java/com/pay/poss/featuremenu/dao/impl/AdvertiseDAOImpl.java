package com.pay.poss.featuremenu.dao.impl;

import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.featuremenu.dao.AdvertiseDAO;
import com.pay.poss.featuremenu.model.Advertisement;



/**
 * @Description 
 * @project 	ma-manager
 * @file 		AdvertiseServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-31		geng			Create
 */
public class AdvertiseDAOImpl extends BaseDAOImpl<Advertisement> implements AdvertiseDAO {
	
	public boolean updateSortById(Map<String ,Object> paraMap) {
		return getSqlMapClientTemplate().update(namespace.concat("updateSortById"), paraMap) == 1;
	}
	
}
