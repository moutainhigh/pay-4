/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.base.dao.member.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.base.dao.member.MemberRelationDAO;
import com.pay.base.dto.MemberRelationDto;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 会员父子关系DAO
 * 
 * @author 戴德荣
 * @version 1.0
 * @data 2011-06-24 
 * 
 */
public class MemberRelationDAOImpl extends BaseDAOImpl<MemberRelationDto> implements MemberRelationDAO {

	public MemberRelationDto getMemberRelationBySonMemberCode(Long sonMemberCode){
		return (MemberRelationDto) this.getSqlMapClientTemplate().queryForObject(getNamespace().concat("selectRelationBySonMemberCode"),sonMemberCode);
	}


	@Override
	public boolean isFatherAndSon(Long sonMemberCode, Long fatherMemberCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("fatherMemberCode", fatherMemberCode);
		map.put("sonMemberCode", sonMemberCode);
		Integer count = (Integer) this.getSqlMapClientTemplate().queryForObject(getNamespace().concat("selectCountByFatherAndSonMemberCode"), map);
	
		return count.intValue() > 0 ? true: false;
	}
	
	
}



















