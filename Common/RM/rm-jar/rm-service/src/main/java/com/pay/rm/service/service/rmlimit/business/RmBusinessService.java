package com.pay.rm.service.service.rmlimit.business;

import java.util.List;
import java.util.Map;

import com.pay.rm.service.dto.rmlimit.business.RcBusinessDTO;
import com.pay.rm.service.model.rmlimit.business.RcBusiness;
import com.pay.rm.service.service.RmBaseService;

/**
 * 业务类型
 * @Description 
 * @project 	rm-service
 * @file 		RcBusinessService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-22		Volcano.Wu			Create
 */
public interface RmBusinessService extends RmBaseService<RcBusiness, RcBusinessDTO>{

	public List<RcBusinessDTO> getBusinessList();
	
	public List<Map<String,String>> queryRcBusiness();
}
