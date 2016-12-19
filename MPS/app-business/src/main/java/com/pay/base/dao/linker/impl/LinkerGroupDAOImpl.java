/**
 *  File: LinkerGroupDAOImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.dao.linker.impl;

import com.pay.base.dao.linker.LinkerGroupDAO;
import com.pay.base.model.LinkerGroup;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 */
public class LinkerGroupDAOImpl extends BaseDAOImpl implements
		LinkerGroupDAO {

	public Class getModelClass() {
		
		return LinkerGroup.class;
	}

}