/**
 *  File: FiBankFacadeServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-29      Sunsea.Li      Changes
 *  
 *
 */
package com.pay.poss.service.fi.input.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.poss.dao.ExternalFacadeDao;
import com.pay.poss.service.fi.input.FiBankFacadeService;

/**
 * @author Sunsea.Li
 * 
 */
public class FiBankFacadeServiceImpl implements FiBankFacadeService {

	private ExternalFacadeDao externalFacadeDao;

	public void setExternalFacadeDao(ExternalFacadeDao externalFacadeDao) {
		this.externalFacadeDao = externalFacadeDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.poss.service.fi.input.FiBankFacadeService#getFiBankList()
	 */
	@Override
	public List<Map<String, String>> getFiBankList() {

		List<Map<String, String>> fiBankList = new ArrayList<Map<String, String>>();
		Map<String, String> fiBankMap = queryFiBankList();// fiBankService.queryAllOrgName();
		for (String key : fiBankMap.keySet()) {
			Map<String, String> bankMap = new HashMap<String, String>();
			bankMap.put("text", fiBankMap.get(key));
			bankMap.put("value", key);
			fiBankList.add(bankMap);
		}
		return fiBankList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.service.fi.input.FiBankFacadeService#getFiBankNameByCode
	 * (java.lang.String)
	 */
	@Override
	public String getFiBankNameByCode(String bankCode) {

		Map<String, String> fiBankMap = queryFiBankList();

		return fiBankMap.get(bankCode);
	}

	private Map<String, String> queryFiBankList() {

		return externalFacadeDao.queryAllOrgName();
	}

}
