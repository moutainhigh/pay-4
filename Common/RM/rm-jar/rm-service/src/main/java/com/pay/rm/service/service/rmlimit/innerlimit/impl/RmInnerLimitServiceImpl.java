package com.pay.rm.service.service.rmlimit.innerlimit.impl;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.rm.service.common.RmLimitConstants;
import com.pay.rm.service.dao.rmlimit.innerlimit.RcInnerLimitDAO;
import com.pay.rm.service.dto.rmlimit.innerlimit.RcInnerLimitDTO;
import com.pay.rm.service.model.rmlimit.innerlimit.RcInnerLimit;
import com.pay.rm.service.service.RmBaseServiceImpl;
import com.pay.rm.service.service.rmlimit.innerlimit.RmInnerLimitService;

/**
 * 直营限额
 * 
 * @Description
 * @project rm-service
 * @file RmInnerLimitServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-25 Volcano.Wu Create
 */
public class RmInnerLimitServiceImpl extends
		RmBaseServiceImpl<RcInnerLimit, RcInnerLimitDTO> implements
		RmInnerLimitService {

	private RcInnerLimitDAO rcInnerLimitDAO;

	public void setRcInnerLimitDAO(RcInnerLimitDAO<RcInnerLimit> rcInnerLimitDAO) {
		this.rcInnerLimitDAO = rcInnerLimitDAO;
	}

	@Override
	protected BaseDAO<RcInnerLimit> getEntityDao() {
		return rcInnerLimitDAO;
	}

	@Override
	public Page<RcInnerLimitDTO> search(Page<RcInnerLimitDTO> page,
			Object... params) {
		return rcInnerLimitDAO.findByQuery(
				RmLimitConstants.RC_INNER_LIMIT_FINDBYSELECTIVE, page, params);
	}

}
