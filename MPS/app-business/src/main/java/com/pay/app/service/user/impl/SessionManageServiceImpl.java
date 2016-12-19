/**
 *  File: SessionManageServiceImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.service.user.impl;

import com.pay.app.dto.SessionManageDTO;
import com.pay.app.model.SessionManage;
import com.pay.app.service.user.SessionManageService;
import com.pay.inf.service.impl.BaseServiceImpl;

/**
 * 
 */
public class SessionManageServiceImpl extends BaseServiceImpl implements
		SessionManageService {

	public Class getDtoClass() {
		return SessionManageDTO.class;
	}

	@Override
	protected Class getModelClass() {
		// TODO Auto-generated method stub
		return SessionManage.class;
	}

}
