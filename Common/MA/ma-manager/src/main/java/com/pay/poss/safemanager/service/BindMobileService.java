/**
 * 
 */
package com.pay.poss.safemanager.service;

import java.util.List;

import com.pay.inf.exception.AppException;
import com.pay.poss.safemanager.dto.OperatorBindDto;
import com.pay.poss.safemanager.dto.OperatorBindParamDto;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		BindMobileService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2011-11-22		DaiDeRong			Create
 */
public interface BindMobileService {

	/**
	 * 查询 操作绑定信息
	 * @param operatorBindParamDto
	 * @return
	 */
	List<OperatorBindDto>  queryOperatorBindInfos(OperatorBindParamDto operatorBindParamDto);
	
	/**
	 * 更改手机 号
	 * @param operatorId
	 * @param mobile
	 * @return
	 */
	boolean modifyBindMobile(Long operatorId,String mobile);
	
	/**
	 * 取消绑定
	 * @param operatorId
	 * @return
	 */
	boolean unBindMobile(Long operatorId) throws AppException;


}
