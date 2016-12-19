/**
 * 
 */
package com.pay.poss.safemanager.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.safemanager.dto.OperatorBindDto;
import com.pay.poss.safemanager.dto.OperatorBindParamDto;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		BindMobileDao.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 woyo Corporation. All rights reserved.
 * Date					Author				Changes
 * 2011-11-22			DaiDeRong			Create
 */
public interface BindMobileDao extends BaseDAO{
	
	
	

	/**
	 * 通过操作员id号查询绑定信息
	 * @param operatorId
	 * @return 
	 */
	OperatorBindDto queryOperatorBindInfoByOperatorId(Long operatorId);
	
	/**
	 * 查询操作员绑定信息
	 * @param bindParamDto
	 * @return List<OperatorBindDto>
	 */
	List<OperatorBindDto>  queryOperatorBindInfo(OperatorBindParamDto bindParamDto);
	
	/**
	 * 更新绑定的手机 号
	 * @param operatorId
	 * @param mobile
	 * @return 1表示OK
	 */
	int updateBindMobile(Long operatorId,String mobile);
	
	
	/**
	 * 查询用户证书状态 
	 * @param operatorId
	 * @param isAdmin
	 * @return 状态值
	 */
	int queryCertStatus(Long operatorId,boolean isAdmin);
}
