package com.pay.rm.service.service.rmlimit.userlevel;

import java.util.List;

import com.pay.rm.service.dto.rmlimit.userlevel.RcUserLevelDTO;
import com.pay.rm.service.model.rmlimit.userlevel.RcUserLevel;
import com.pay.rm.service.service.RmBaseService;

/**
 * 用户等级
 * @Description 
 * @project 	rm-service
 * @file 		RcUserLevelService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-25		Volcano.Wu			Create
 */
public interface RmUserLevelService extends RmBaseService<RcUserLevel, RcUserLevelDTO>{
	
	public List<RcUserLevel> getUserLevelList();

}
