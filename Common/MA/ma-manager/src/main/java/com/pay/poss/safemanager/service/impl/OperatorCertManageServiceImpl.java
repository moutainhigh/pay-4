/**
 * 
 */
package com.pay.poss.safemanager.service.impl;

import java.util.Date;
import java.util.List;

import com.pay.poss.safemanager.dao.OperatorCertManageDao;
import com.pay.poss.safemanager.dto.OperatorCertDto;
import com.pay.poss.safemanager.dto.OperatorCertStatDto;
import com.pay.poss.safemanager.service.OperatorCertManageService;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		OperatorCertManageServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2011 pay Corporation. All rights reserved.
 * Date				Author				Changes
 * 2011-11-30		DaiDeRong			Create
 */
public class OperatorCertManageServiceImpl implements OperatorCertManageService {

	private OperatorCertManageDao operatorCertManageDao;
	
	

	

	/**
	 * @param operatorCertManageDao the operatorCertManageDao to set
	 */
	public void setOperatorCertManageDao(OperatorCertManageDao operatorCertManageDao) {
		this.operatorCertManageDao = operatorCertManageDao;
	}

	/* (non-Javadoc)
	 * @see com.pay.poss.safemanager.service.OperatorCertManageService#queryOperatorCertInfo(com.pay.poss.safemanager.dto.OperatorCertDto)
	 */
	@Override
	public List<OperatorCertDto> queryOperatorCertInfo(OperatorCertDto paramDto) {
		return operatorCertManageDao.queryOperatorCertInfo(paramDto);
	}
	
	/* (non-Javadoc)
	 * @see com.pay.poss.safemanager.service.OperatorCertManageService#queryOperatorCertUseInfo(java.lang.Long, java.lang.Long)
	 */
	@Override
	public OperatorCertDto queryOperatorCertUseInfo(Long memberCode,
			Long operatorId) {
		OperatorCertDto paramDto = new OperatorCertDto();
		paramDto.setMemberCode(memberCode);
		paramDto.setOperatorId(operatorId);
		List<OperatorCertDto> list =  queryOperatorCertInfo(paramDto);
		OperatorCertDto certDtoDest = null;
		if(list.size() == 1){
			certDtoDest = list.get(0);
			certDtoDest.setOperatorCertUseDtos(operatorCertManageDao.queryOperatorCertUseInfo(memberCode, operatorId));
		}
		
		return certDtoDest;
	}

	@Override
	public boolean removeCert(Long certMemberId) {
		//0申请  1制证 2正常 3注销
		boolean isRemv =  operatorCertManageDao.updateCertStatus(certMemberId, 3) == 1;
		boolean isUpdateAllUsPalce = false;
		if(isRemv){
			isUpdateAllUsPalce =  operatorCertManageDao.updateUseStatusByCertId(certMemberId, 0) >= 1;
		}
		return isRemv || isUpdateAllUsPalce;
	}

	@Override
	public boolean removeUesPalceCert(Long certManageId) {
		return operatorCertManageDao.updateUsePlaceStatus(certManageId, 0) == 1;
	}

	@Override
	public OperatorCertStatDto queryCertStat(Date begin, Date end) {
		return operatorCertManageDao.queryCertCountStat(begin, end);
	}

}
