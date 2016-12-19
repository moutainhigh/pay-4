/**
 *  File: SubscribeServiceImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.service.messagebox.impl;

import com.pay.app.dto.SubscribeDTO;
import com.pay.app.model.Subscribe;
import com.pay.app.service.messagebox.SubscribeService;
import com.pay.inf.service.impl.BaseServiceImpl;

/**
 * 
 */
public class SubscribeServiceImpl extends BaseServiceImpl implements
		SubscribeService {

	@SuppressWarnings("unchecked")
	public Class getDtoClass() {
		return SubscribeDTO.class;
	}

	@Override
	protected Class getModelClass() {
		// TODO Auto-generated method stub
		return Subscribe.class;
	}

}
