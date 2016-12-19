package com.pay.rm.service.service.rmlimit.userlimit.impl;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.rm.service.common.RmLimitConstants;
import com.pay.rm.service.dao.rmlimit.userlimit.RcUserLimitDAO;
import com.pay.rm.service.dto.rmlimit.userlimit.RcUserLimitDTO;
import com.pay.rm.service.model.rmlimit.userlimit.RcUserLimit;
import com.pay.rm.service.service.RmBaseServiceImpl;
import com.pay.rm.service.service.rmlimit.userlimit.RmUserLimitService;

/**
 * 用户限额
 * 
 * @Description
 * @project rm-service
 * @file RcpayayLimitServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-25 Volcano.Wu Create
 */
public class RmUserLimitServiceImpl extends
		RmBaseServiceImpl<RcUserLimit, RcUserLimitDTO> implements
		RmUserLimitService {

	private RcUserLimitDAO rcUserLimitDAO;

	public void setRcUserLimitDAO(RcUserLimitDAO<RcUserLimit> rcUserLimitDAO) {
		this.rcUserLimitDAO = rcUserLimitDAO;
	}

	@Override
	protected BaseDAO<RcUserLimit> getEntityDao() {
		return rcUserLimitDAO;
	}

	@Override
	public Page<RcUserLimitDTO> search(Page<RcUserLimitDTO> page,
			Object... params) {
		return rcUserLimitDAO.findByQuery(
				RmLimitConstants.RCUSERLIMIT_RCBUSINESS, page, params);
	}

}
