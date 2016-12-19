package com.pay.poss.safemanager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.safemanager.dao.BindMobileDao;
import com.pay.poss.safemanager.dto.OperatorBindDto;
import com.pay.poss.safemanager.dto.OperatorBindParamDto;
/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		BindMobileDaoImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2011 pay Corporation. All rights reserved.
 * Date				Author				Changes
 * 2011-12-1			DaiDeRong			Create
 */
public class BindMobileDaoImpl  extends BaseDAOImpl implements BindMobileDao{

	@Override
	public List<OperatorBindDto> queryOperatorBindInfo(
			OperatorBindParamDto bindParamDto) {
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("queryOperatorBindInfo"),bindParamDto);
	}

	@Override
	public int updateBindMobile(Long operatorId, String mobile) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorId",operatorId);
		map.put("mobile",mobile);
		return getSqlMapClientTemplate().update(this.getNamespace().concat("updateBindMobile"), map);
	}

	@Override
	public int queryCertStatus(Long operatorId,boolean isAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorId",operatorId);
		map.put("isAdmin", isAdmin ? 1 : 0);
		Integer i = (Integer) getSqlMapClientTemplate().queryForObject(getNamespace().concat("queryCertStatus"), map);
		return i==null ? -1 :i.intValue();
	}

	@Override
	public OperatorBindDto queryOperatorBindInfoByOperatorId(Long operatorId) {
		OperatorBindParamDto dto = new OperatorBindParamDto();
		dto.setOperatorId(operatorId+"");
		List<OperatorBindDto> list = this.queryOperatorBindInfo(dto);
		return  list.size()==1 ? list.get(0):null;
	}

	
	
}
