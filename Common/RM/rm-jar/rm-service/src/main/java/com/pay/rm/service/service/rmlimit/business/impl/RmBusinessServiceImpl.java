package com.pay.rm.service.service.rmlimit.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.rm.service.common.RmLimitConstants;
import com.pay.rm.service.dao.rmlimit.business.RcBusinessDAO;
import com.pay.rm.service.dto.rmlimit.business.RcBusinessDTO;
import com.pay.rm.service.model.rmlimit.business.RcBusiness;
import com.pay.rm.service.service.RmBaseServiceImpl;
import com.pay.rm.service.service.rmlimit.business.RmBusinessService;

/**
 * 业务类型
 * 
 * @Description
 * @project rm-service
 * @file RcBusinessServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-25 Volcano.Wu Create
 */
public class RmBusinessServiceImpl extends
		RmBaseServiceImpl<RcBusiness, RcBusinessDTO> implements
		RmBusinessService {

	private RcBusinessDAO rcBusinessDAO;

	public void setRcBusinessDAO(RcBusinessDAO<RcBusiness> rcBusinessDAO) {
		this.rcBusinessDAO = rcBusinessDAO;
	}

	@Override
	protected BaseDAO<RcBusiness> getEntityDao() {
		return rcBusinessDAO;
	}

	@Override
	public Page<RcBusinessDTO> search(Page<RcBusinessDTO> page,
			Object... params) {
		return rcBusinessDAO.findByQuery(
				RmLimitConstants.RCBUSINESS_FINDBYSELECTIVE, page, params);
	}

	public List<RcBusinessDTO> getBusinessList() {
		Map<String, String> map = new HashMap<String, String>();
		return rcBusinessDAO.findByQuery(
				RmLimitConstants.RCBUSINESS_FINDBYSELECTIVE, map);
	}

	/**
	 * 查询所有业务类型
	 * 
	 * @return List<Map<String,String>>
	 */
	public List<Map<String, String>> queryRcBusiness() {
		List<Map<String, String>> rcBusinessMapList = new ArrayList<Map<String, String>>();
		List<RcBusiness> rcBusinessList = rcBusinessDAO.findByQuery(
				RmLimitConstants.QUERY_RCBUSINESS, null);
		Map<String, String> map;
		if (rcBusinessList != null) {
			for (RcBusiness rcBusiness : rcBusinessList) {
				map = new HashMap<String, String>();
				String businessId = String.valueOf(rcBusiness.getBusinessId());
				String businessName = rcBusiness.getBusinessName();
				map.put("value", businessId);
				map.put("text", businessName);
				rcBusinessMapList.add(map);
			}
		}
		return rcBusinessMapList;
	}

}
