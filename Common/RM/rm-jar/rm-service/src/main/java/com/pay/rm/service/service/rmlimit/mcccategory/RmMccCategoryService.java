package com.pay.rm.service.service.rmlimit.mcccategory;

import java.util.List;

import com.pay.rm.base.exception.PossException;
import com.pay.rm.service.dto.rmlimit.mcccategory.RcMccCategoryDTO;
import com.pay.rm.service.model.rmlimit.mcccategory.RcMccCategory;
import com.pay.rm.service.service.RmBaseService;

/**
 * 行业类别
 * 
 * @Description
 * @project rm-service
 * @file RcMccCategoryService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved.
 *          Date Author Changes 2010-10-25 Volcano.Wu Create
 */
public interface RmMccCategoryService extends
		RmBaseService<RcMccCategory, RcMccCategoryDTO> {

	public RcMccCategory getRcMccCategoryByCategoryId(String rcMccCategoryId);

	public List<RcMccCategoryDTO> queryAllRcMccCategory();

	public boolean delete(String id) throws PossException;
}
