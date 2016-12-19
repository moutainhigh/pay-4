package com.pay.rm.service.service.rmlimit.businesslimitcustom.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.rm.service.common.RmLimitConstants;
import com.pay.rm.service.dao.rmlimit.businesslimitcustom.RcBusinessLimitCustomDAO;
import com.pay.rm.service.dto.rmlimit.businesslimitcustom.RcBusinessLimitCustomDTO;
import com.pay.rm.service.model.rmlimit.businesslimitcustom.RcBusinessLimitCustom;
import com.pay.rm.service.service.RmBaseServiceImpl;
import com.pay.rm.service.service.rmlimit.businesslimitcustom.RmBusinessLimitCustomService;

/**
 * 商户定制限额
 * 
 * @Description
 * @project rm-service
 * @file RcBusinessLimitCustomServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-25 Volcano.Wu Create
 */
public class RmBusinessLimitCustomServiceImpl extends
		RmBaseServiceImpl<RcBusinessLimitCustom, RcBusinessLimitCustomDTO>
		implements RmBusinessLimitCustomService {

	private RcBusinessLimitCustomDAO rcBusinessLimitCustomDAO;

	public void setRcBusinessLimitCustomDAO(
			RcBusinessLimitCustomDAO<RcBusinessLimitCustom> rcBusinessLimitCustomDAO) {
		this.rcBusinessLimitCustomDAO = rcBusinessLimitCustomDAO;
	}

	@Override
	protected BaseDAO<RcBusinessLimitCustom> getEntityDao() {
		return rcBusinessLimitCustomDAO;
	}

	@Override
	public Page<RcBusinessLimitCustomDTO> search(
			Page<RcBusinessLimitCustomDTO> page, Object... params) {
		return rcBusinessLimitCustomDAO.findByQuery(
				RmLimitConstants.RCBUSINESSLIMITCUSTOM_FINDBYSELECTIVE, page,
				params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.rm.service.service.rmlimit.businesslimitcustom.
	 * RmBusinessLimitCustomService#serachById(java.util.Map)
	 */
	@Override
	public Integer serachById(RcBusinessLimitCustom rcBusinessLimitCustom) {
		return (Integer) rcBusinessLimitCustomDAO.findObjectByCriteria(
				"serachById", rcBusinessLimitCustom);
	}

	@Override
	public RcBusinessLimitCustomDTO getRcBusinessLimitCustomDTO(
			Map<String, Object> param) {
		
		List<RcBusinessLimitCustom> list = rcBusinessLimitCustomDAO.findByQuery("findBySelective",param);
		
		RcBusinessLimitCustomDTO cusDTO =null;
		
		System.out.println("list-size: "+list.size());
		
		if(list!=null&&list.size()>0){
			RcBusinessLimitCustom rblc = list.get(0);
			cusDTO = new RcBusinessLimitCustomDTO();
			
			cusDTO.setBusinessId(rblc.getBusinessId());
			cusDTO.setBatchAccounts(rblc.getBatchAccounts());
			cusDTO.setBusinessType(rblc.getBusinessType());
			cusDTO.setDayLimit(rblc.getDayLimit());
			cusDTO.setDayTimes(rblc.getDayTimes());
			cusDTO.setMcc(rblc.getMcc());
			cusDTO.setMonthLimit(rblc.getMonthLimit());
			cusDTO.setMonthTimes(rblc.getMonthTimes());
			cusDTO.setDayLimit(rblc.getDayLimit());
			cusDTO.setDayTimes(rblc.getDayTimes());
			cusDTO.setStatus(rblc.getStatus());
			cusDTO.setSingleLimit(rblc.getSingleLimit());
		}
		
		return cusDTO;
	}

}
