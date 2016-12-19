package com.pay.rm.service.service.rmlimit.risklevel.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.rm.service.common.RmLimitConstants;
import com.pay.rm.service.dao.rmlimit.risklevel.RcRiskLevelDAO;
import com.pay.rm.service.dto.rmlimit.risklevel.RcRiskLevelDTO;
import com.pay.rm.service.model.rmlimit.risklevel.RcRiskLevel;
import com.pay.rm.service.service.RmBaseServiceImpl;
import com.pay.rm.service.service.rmlimit.risklevel.RmRiskLevelService;

/**
 * 风险等级
 * 
 * @Description
 * @project rm-service
 * @file RcRiskLevelServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-25 Volcano.Wu Create
 */
public class RmRiskLevelServiceImpl extends
		RmBaseServiceImpl<RcRiskLevel, RcRiskLevelDTO> implements
		RmRiskLevelService {

	private RcRiskLevelDAO rcRiskLevelDAO;

	public void setRcRiskLevelDAO(RcRiskLevelDAO<RcRiskLevel> rcRiskLevelDAO) {
		this.rcRiskLevelDAO = rcRiskLevelDAO;
	}

	@Override
	protected BaseDAO<RcRiskLevel> getEntityDao() {
		return rcRiskLevelDAO;
	}

	@Override
	public Page<RcRiskLevelDTO> search(Page<RcRiskLevelDTO> page,
			Object... params) {
		return rcRiskLevelDAO.findByQuery(
				RmLimitConstants.RCRISKLEVEL_FINDBYSELECTIVE, page, params);
	}

	public List<RcRiskLevel> getRiskLevelList() {
		Map<String, String> map = new HashMap<String, String>();
		return rcRiskLevelDAO.findByQuery(
				RmLimitConstants.RCRISKLEVEL_FINDBYSELECTIVE, map);
	}

}
