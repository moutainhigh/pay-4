package com.pay.poss.safemanager.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.safemanager.dao.OperatorCertManageDao;
import com.pay.poss.safemanager.dto.OperatorCertDto;
import com.pay.poss.safemanager.dto.OperatorCertStatDto;
import com.pay.poss.safemanager.dto.OperatorCertUseDto;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		OperatorCertManageDaoImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2011 pay Corporation. All rights reserved.
 * Date				Author				Changes
 * 2011-12-1			DaiDeRong			Create
 */
public class OperatorCertManageDaoImpl extends BaseDAOImpl implements OperatorCertManageDao {

	/* (non-Javadoc)
	 * @see com.pay.poss.safemanager.dao.CertManageDao#queryOperatorCertInfo(com.pay.poss.safemanager.dto.OperatorCertDto)
	 */
	@Override
	public List<OperatorCertDto> queryOperatorCertInfo(OperatorCertDto certDto) {
		
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("queryOperatorCertInfo"), certDto);
	}

	/* (non-Javadoc)
	 * @see com.pay.poss.safemanager.dao.CertManageDao#queryOperatorCertUseInfo(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<OperatorCertUseDto> queryOperatorCertUseInfo(Long memberCode,
			Long operatorId) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("operatorId", operatorId);
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("queryOperatorCertUseInfo"), map);
	}

	@Override
	public int updateCertStatus(Long id, int status) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return this.getSqlMapClientTemplate().update(namespace.concat("updateCertStatus"), map);
	}

	@Override
	public int updateUsePlaceStatus(Long id, int status) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return this.getSqlMapClientTemplate().update(namespace.concat("updateUseStatus"), map);
	}

	@Override
	public int updateUseStatusByCertId(Long memberCertId, int status) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("memberCertId", memberCertId);
		map.put("status", status);
		return this.getSqlMapClientTemplate().update(namespace.concat("updateUseStatusByCertId"), map);
	}

	@Override
	public OperatorCertStatDto queryCertCountStat(Date begin, Date end) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("begin", begin);
		map.put("end", end);
		return (OperatorCertStatDto) this.getSqlMapClientTemplate().queryForObject(namespace.concat("queryCertCountStat"), map);
	}

	
	
	
	
	
}
