package com.pay.rm.service.service.rmlimit.userlimitcustom.impl;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.rm.service.dao.rmlimit.userlimitcustom.RcUserimitCustomDAO;
import com.pay.rm.service.dto.rmlimit.userlimitcustom.RcUserLimitCustomDTO;
import com.pay.rm.service.model.rmlimit.userlimitcustom.RcUserLimitCustom;
import com.pay.rm.service.service.RmBaseServiceImpl;
import com.pay.rm.service.service.rmlimit.userlimitcustom.RmUserLimitCustomService;

/**
 * 个人定制限额
 * 
 * @Description
 * @project rm-service
 * @file RcBusinessLimitCustomServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-25 Volcano.Wu Create
 */
public class RmUserLimitCustomServiceImpl extends
		RmBaseServiceImpl<RcUserLimitCustom, RcUserLimitCustomDTO> implements
		RmUserLimitCustomService {

	private RcUserimitCustomDAO rcUserimitCustomDAO;

	/**
	 * @param rcUserimitCustomDAO
	 *            the rcUserimitCustomDAO to set
	 */
	public void setRcUserimitCustomDAO(
			RcUserimitCustomDAO<RcUserLimitCustom> rcUserimitCustomDAO) {
		this.rcUserimitCustomDAO = rcUserimitCustomDAO;
	}

	@Override
	protected BaseDAO<RcUserLimitCustom> getEntityDao() {
		return rcUserimitCustomDAO;
	}

	@Override
	public Page<RcUserLimitCustomDTO> search(Page<RcUserLimitCustomDTO> page,
			Object... params) {
		return rcUserimitCustomDAO.findByQuery("findBySelective", page, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.rm.service.service.rmlimit.businesslimitcustom.
	 * RmBusinessLimitCustomService#serachById(java.util.Map)
	 */
	@Override
	public Integer serachById(RcUserLimitCustom rcBusinessLimitCustom) {
		return (Integer) rcUserimitCustomDAO.findObjectByCriteria("serachById",
				rcBusinessLimitCustom);
	}

}
