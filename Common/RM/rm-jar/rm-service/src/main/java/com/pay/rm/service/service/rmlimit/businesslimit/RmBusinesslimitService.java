package com.pay.rm.service.service.rmlimit.businesslimit;

import java.util.Map;

import com.pay.rm.service.dto.rmlimit.businesslimit.RcBusinessLimitDTO;
import com.pay.rm.service.model.rmlimit.businesslimit.RcBusinessLimit;
import com.pay.rm.service.service.RmBaseService;

/**
 * 商户限额
 * @Description 
 * @project 	rm-service
 * @file 		RcBusinesslimitService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-25		Volcano.Wu			Create
 */
public interface RmBusinesslimitService extends RmBaseService<RcBusinessLimit, RcBusinessLimitDTO>{
	
	/**
	 * 根据查询条件
	 * @param params
	 * @return
	 */
	RcBusinessLimitDTO getRcBusinessLimitDTO(Map<String,Object> params);

}
