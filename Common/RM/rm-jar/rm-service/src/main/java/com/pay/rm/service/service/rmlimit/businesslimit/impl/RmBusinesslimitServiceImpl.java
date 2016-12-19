package com.pay.rm.service.service.rmlimit.businesslimit.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.rm.service.common.RmLimitConstants;
import com.pay.rm.service.dao.rmlimit.businesslimit.RcBusinessLimitDAO;
import com.pay.rm.service.dto.rmlimit.businesslimit.RcBusinessLimitDTO;
import com.pay.rm.service.model.rmlimit.businesslimit.RcBusinessLimit;
import com.pay.rm.service.service.RmBaseServiceImpl;
import com.pay.rm.service.service.rmlimit.businesslimit.RmBusinesslimitService;

/**
 * 商户限额
 * 
 * @Description
 * @project rm-service
 * @file RcBusinesslimitServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-25 Volcano.Wu Create
 */
public class RmBusinesslimitServiceImpl extends
		RmBaseServiceImpl<RcBusinessLimit, RcBusinessLimitDTO> implements
		RmBusinesslimitService {

	private RcBusinessLimitDAO rcBusinesslimitDAO;

	public void setRcBusinesslimitDAO(
			RcBusinessLimitDAO<RcBusinessLimit> rcBusinesslimitDAO) {
		this.rcBusinesslimitDAO = rcBusinesslimitDAO;
	}

	@Override
	protected BaseDAO<RcBusinessLimit> getEntityDao() {
		return rcBusinesslimitDAO;
	}

	@Override
	public Page<RcBusinessLimitDTO> search(Page<RcBusinessLimitDTO> page,
			Object... params) {
		return rcBusinesslimitDAO.findByQuery(
				RmLimitConstants.RCBUSINESSLIMIT_FINDBYSELECTIVE, page, params);
	}

	@Override
	public RcBusinessLimitDTO getRcBusinessLimitDTO(Map<String, Object> params) {
		RcBusinessLimit rbL = (RcBusinessLimit) rcBusinesslimitDAO.findById(RmLimitConstants.RCBUSINESSLIMIT_FINDBYSELECTIVE,params);
		
		RcBusinessLimitDTO rblDTO = null;
		if(rbL!=null){
			rblDTO = new RcBusinessLimitDTO();
			rblDTO.setBatchAccounts(rbL.getBatchAccounts());
			rblDTO.setBusinessType(rbL.getBusinessType());
			rblDTO.setDayLimit(rbL.getDayLimit());
			rblDTO.setDayTimes(rbL.getDayTimes());
			rblDTO.setMcc(rbL.getMcc());
			rblDTO.setMonthLimit(rbL.getMonthLimit());
			rblDTO.setMonthTimes(rbL.getMonthTimes());
			rblDTO.setRiskLevel(rbL.getRiskLevel());
			rblDTO.setSequenceId(rbL.getSequenceId());
			rblDTO.setSingleLimit(rbL.getSingleLimit());
			rblDTO.setStatus(rbL.getStatus());
		}
		
		return rblDTO ;
	}
}
