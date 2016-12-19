/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.base.dao.member;

import com.pay.base.dto.MemberRelationDto;
import com.pay.inf.dao.BaseDAO;



/**
 * 会员父子关系DAO
 * 
 * @author 戴德荣
 * @version 1.0
 * @data 2011-06-24 
 * 
 */
public interface MemberRelationDAO extends BaseDAO<MemberRelationDto> {
	public MemberRelationDto getMemberRelationBySonMemberCode(Long sonMemberCode);
	
	public boolean isFatherAndSon(Long sonMemberCode,Long fatherMemberCode);
}
