package com.pay.rm.service.service.rmlimit.mcclimit.impl;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.rm.service.common.RmLimitConstants;
import com.pay.rm.service.dao.rmlimit.mcclimit.RcMccLimitDAO;
import com.pay.rm.service.dto.rmlimit.mcclimit.RcMccLimitDTO;
import com.pay.rm.service.model.rmlimit.mcclimit.RcMccLimit;
import com.pay.rm.service.service.RmBaseServiceImpl;
import com.pay.rm.service.service.rmlimit.mcclimit.RmMccLimitService;

/**
 * 行业限额
 * 
 * @Description
 * @project rm-service
 * @file RmMccLimitServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-25 Volcano.Wu Create
 */
public class RmMccLimitServiceImpl extends
		RmBaseServiceImpl<RcMccLimit, RcMccLimitDTO> implements
		RmMccLimitService {

	private RcMccLimitDAO rcMccLimitDAO;

	public void setRcMccLimitDAO(RcMccLimitDAO<RcMccLimit> rcMccLimitDAO) {
		this.rcMccLimitDAO = rcMccLimitDAO;
	}

	@Override
	protected BaseDAO<RcMccLimit> getEntityDao() {
		return rcMccLimitDAO;
	}

	@Override
	public Page<RcMccLimitDTO> search(Page<RcMccLimitDTO> page,
			Object... params) {
		return rcMccLimitDAO.findByQuery(
				RmLimitConstants.RCMCCLIMIT_FINDBYSELECTIVE, page, params);
	}

}
