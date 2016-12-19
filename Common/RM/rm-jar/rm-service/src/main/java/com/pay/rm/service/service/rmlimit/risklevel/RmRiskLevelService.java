package com.pay.rm.service.service.rmlimit.risklevel;

import java.util.List;

import com.pay.rm.service.dto.rmlimit.risklevel.RcRiskLevelDTO;
import com.pay.rm.service.model.rmlimit.risklevel.RcRiskLevel;
import com.pay.rm.service.service.RmBaseService;

/**
 * 风险等级
 * @Description 
 * @project 	rm-service
 * @file 		RcRiskLevelService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-25		Volcano.Wu			Create
 */
public interface RmRiskLevelService extends RmBaseService<RcRiskLevel, RcRiskLevelDTO>{
	
	public List<RcRiskLevel> getRiskLevelList();

}
