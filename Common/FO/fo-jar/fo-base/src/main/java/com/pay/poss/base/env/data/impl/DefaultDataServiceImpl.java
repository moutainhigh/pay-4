/** @Description 
 * @project 	poss-base
 * @file 		DefaultDataServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-9		Rick_lv			Create 
 */
package com.pay.poss.base.env.data.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.poss.base.env.data.AbstractDataService;
import com.pay.poss.base.model.CodeMapping;

/**
 * <p>
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-11-9
 * @see
 */
public class DefaultDataServiceImpl extends AbstractDataService {

	@Override
	public List<CodeMapping> getCodeMapping(String family, String category) {
		// 临时方案
		Map<String, String> params = new HashMap<String, String>();
		params.put("FAMILY", family);
		params.put("CATEGORY", category);
		params.put("STATUS", "0");
		return daoService.findByQuery("base.dataservice.queryCodeMappings", params);
	}

	@Override
	public CodeMapping getCodeMapping(String family, String category, String code) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("FAMILY", family);
		params.put("CATEGORY", category);
		params.put("STATUS", "0");
		params.put("CODE", code);
		@SuppressWarnings("unchecked")
		List<CodeMapping> codeMappings = daoService.findByQuery("base.dataservice.queryCodeMappings", params);
		if(codeMappings!=null && codeMappings.size()>0){
			return codeMappings.get(0);
		}
		return null;
	}

}
