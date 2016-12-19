/**
 * 
 */
package com.pay.poss.safemanager.service.impl;

import java.util.List;

import com.pay.inf.exception.AppException;
import com.pay.poss.safemanager.dao.BindMobileDao;
import com.pay.poss.safemanager.dto.OperatorBindDto;
import com.pay.poss.safemanager.dto.OperatorBindParamDto;
import com.pay.poss.safemanager.service.BindMobileService;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		BindMobileServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author				Changes
 * 2011-11-22			DaiDeRong			Create
 */
public class BindMobileServiceImpl implements BindMobileService {

	
	
	private BindMobileDao bindMobileDao;
	
	
	
	/**
	 * @param bindMobileDao the bindMobileDao to set
	 */
	public void setBindMobileDao(BindMobileDao bindMobileDao) {
		this.bindMobileDao = bindMobileDao;
	}

	/* (non-Javadoc)
	 * @see com.pay.poss.safemanager.service.BindMobileService#queryOperatorBindInfos(com.pay.poss.safemanager.dto.OperatorBindParamDto)
	 */
	@Override
	public List<OperatorBindDto> queryOperatorBindInfos(
			OperatorBindParamDto operatorBindParamDto) {
		return bindMobileDao.queryOperatorBindInfo(operatorBindParamDto);
	}

	@Override
	public boolean modifyBindMobile(Long operatorId, String mobile) {
		return bindMobileDao.updateBindMobile(operatorId, mobile)  ==1;
	}

	@Override
	public boolean unBindMobile(Long operatorId) throws AppException {
		
		//判断是否是管理员
		OperatorBindDto dto = bindMobileDao.queryOperatorBindInfoByOperatorId(operatorId);
		
		if(dto == null ){
			return false;
		}
		int status = bindMobileDao.queryCertStatus(operatorId,
				dto.getIsAdmin() == 1 ? true : false);
		if(status == 2){
			throw new AppException("不能解除手机绑定，解除手机绑定之前先取消证书！");
		}
		return modifyBindMobile(operatorId, " ");
	}

}
