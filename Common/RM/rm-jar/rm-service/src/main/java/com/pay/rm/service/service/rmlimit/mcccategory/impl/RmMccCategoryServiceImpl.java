package com.pay.rm.service.service.rmlimit.mcccategory.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.rm.base.exception.PossException;
import com.pay.rm.service.common.RmLimitConstants;
import com.pay.rm.service.dao.rmlimit.mcccategory.RcMccCategoryDAO;
import com.pay.rm.service.dto.rmlimit.mcccategory.RcMccCategoryDTO;
import com.pay.rm.service.model.rmlimit.mcccategory.RcMccCategory;
import com.pay.rm.service.service.RmBaseServiceImpl;
import com.pay.rm.service.service.rmlimit.mcccategory.RmMccCategoryService;
import com.pay.util.BeanConvertUtil;

/**
 * 行业类别
 * 
 * @Description
 * @project rm-service
 * @file RcMccCategoryServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-25 Volcano.Wu Create
 */
public class RmMccCategoryServiceImpl extends
		RmBaseServiceImpl<RcMccCategory, RcMccCategoryDTO> implements
		RmMccCategoryService {

	private RcMccCategoryDAO rcMccCategoryDAO;

	public void setRcMccCategoryDAO(RcMccCategoryDAO rcMccCategoryDAO) {
		this.rcMccCategoryDAO = rcMccCategoryDAO;
	}

	@Override
	protected BaseDAO<RcMccCategory> getEntityDao() {
		return rcMccCategoryDAO;
	}

	public Page<RcMccCategoryDTO> search(Page<RcMccCategoryDTO> page,
			Object... params) {
		List<RcMccCategory> list = (List<RcMccCategory>) rcMccCategoryDAO.findByQuery(
				RmLimitConstants.RCMCCCATEGORY_FINDBYSELECTIVE, page, params);
		return (Page<RcMccCategoryDTO>) BeanConvertUtil.convert(
				RcMccCategoryDTO.class, list);
	}

	@Override
	public RcMccCategory getRcMccCategoryByCategoryId(String categoryId) {
		return (RcMccCategory) rcMccCategoryDAO.findById(categoryId);
	}

	/**
	 * 查询所有行业类别
	 */
	@Override
	public List<RcMccCategoryDTO> queryAllRcMccCategory() {
		Map<String, String> map = new HashMap<String, String>();
		List<RcMccCategory> list = rcMccCategoryDAO.findByQuery(
				RmLimitConstants.RCMCCCATEGORY_FINDBYSELECTIVE, map);
		return (List<RcMccCategoryDTO>) BeanConvertUtil.convert(
				RcMccCategoryDTO.class, list);
	}

	@Override
	public boolean delete(String id) throws PossException {

		return rcMccCategoryDAO.delete(id);
	}
}
