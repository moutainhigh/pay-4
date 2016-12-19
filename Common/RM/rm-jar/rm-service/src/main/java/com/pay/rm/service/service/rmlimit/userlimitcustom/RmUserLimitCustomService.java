package com.pay.rm.service.service.rmlimit.userlimitcustom;

import com.pay.rm.service.dto.rmlimit.userlimitcustom.RcUserLimitCustomDTO;
import com.pay.rm.service.model.rmlimit.userlimitcustom.RcUserLimitCustom;
import com.pay.rm.service.service.RmBaseService;

/**
 * 个人定制限额
 * @Description 
 * @project 	rm-service
 * @file 		RcBusinessLimitCustomService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-25		Volcano.Wu			Create
 */
public interface RmUserLimitCustomService extends RmBaseService<RcUserLimitCustom, RcUserLimitCustomDTO>{
	/**
	 * 查询是否重复新增
	 * @param params
	 * @return
	 */
	Integer serachById(RcUserLimitCustom rcUserLimitCustomDTO );
}
