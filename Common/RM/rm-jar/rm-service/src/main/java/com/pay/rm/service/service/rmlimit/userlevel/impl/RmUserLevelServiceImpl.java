package com.pay.rm.service.service.rmlimit.userlevel.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.rm.service.common.RmLimitConstants;
import com.pay.rm.service.dao.rmlimit.userlevel.RcUserLevelDAO;
import com.pay.rm.service.dto.rmlimit.userlevel.RcUserLevelDTO;
import com.pay.rm.service.model.rmlimit.userlevel.RcUserLevel;
import com.pay.rm.service.service.RmBaseServiceImpl;
import com.pay.rm.service.service.rmlimit.userlevel.RmUserLevelService;

/**
 * 用户等级
 * 
 * @Description
 * @project rm-service
 * @file RcUserLevelServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-25 Volcano.Wu Create
 */
public class RmUserLevelServiceImpl extends
		RmBaseServiceImpl<RcUserLevel, RcUserLevelDTO> implements
		RmUserLevelService {

	private RcUserLevelDAO rcUserLevelDAO;

	public void setRcUserLevelDAO(RcUserLevelDAO<RcUserLevel> rcUserLevelDAO) {
		this.rcUserLevelDAO = rcUserLevelDAO;
	}

	@Override
	protected BaseDAO<RcUserLevel> getEntityDao() {
		return rcUserLevelDAO;
	}

	@Override
	public Page<RcUserLevelDTO> search(Page<RcUserLevelDTO> page,
			Object... params) {
		return rcUserLevelDAO.findByQuery(
				RmLimitConstants.RCUSERLEVEL_FINDBYSELECTIVE, page, params);
	}

	public List<RcUserLevel> getUserLevelList() {
		Map<String, String> map = new HashMap<String, String>();
		return rcUserLevelDAO.findByQuery(
				RmLimitConstants.RCUSERLEVEL_FINDBYSELECTIVE, map);
	}

}
