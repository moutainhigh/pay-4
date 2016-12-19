/**
 *  File: LinkerGroupServiceImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.service.linker.impl;

import com.pay.base.dto.LinkerGroupDTO;
import com.pay.base.model.LinkerGroup;
import com.pay.base.service.linker.LinkerGroupService;
import com.pay.inf.service.impl.BaseServiceImpl;

/**
 * 
 */
public class LinkerGroupServiceImpl extends BaseServiceImpl implements
		LinkerGroupService {

	@SuppressWarnings("unchecked")
	public Class getDtoClass() {
		return LinkerGroupDTO.class;
	}

	@Override
	protected Class getModelClass() {
		return LinkerGroup.class;
	}

}
