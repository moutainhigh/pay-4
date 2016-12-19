package com.pay.rm.service.service.rmlimit.businesslimitcustom;

import java.util.Map;

import com.pay.rm.service.dto.rmlimit.businesslimitcustom.RcBusinessLimitCustomDTO;
import com.pay.rm.service.model.rmlimit.businesslimitcustom.RcBusinessLimitCustom;
import com.pay.rm.service.service.RmBaseService;

/**
 * 商户定制限额
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
public interface RmBusinessLimitCustomService extends RmBaseService<RcBusinessLimitCustom, RcBusinessLimitCustomDTO>{
	/**
	 * 查询是否重复新增
	 * @param params
	 * @return
	 */
	Integer serachById(RcBusinessLimitCustom rcBusinessLimitCustom );
	
	/**
	 * 根据业务类型及商户会员号查询商户定制限额限次
	 * @param param
	 * @return
	 */
	RcBusinessLimitCustomDTO getRcBusinessLimitCustomDTO(Map<String,Object> param);  
}
