package com.pay.poss.memberrelation.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.memberrelation.dao.MemberRelationDao;
import com.pay.poss.memberrelation.model.MemberRelation;

public class MemberRelationDaoImpl extends BaseDAOImpl<MemberRelation> implements MemberRelationDao {

	@Override
	public List<MemberRelation> queryMemberRelationByCondition(
			MemberRelation mebmerRelation) {		
		return getSqlMapClientTemplate().queryForList(namespace.concat("queryMemberRelationByCondition"),mebmerRelation);
	}

	@Override
	public int countMemberRelationByCondition(MemberRelation mebmerRelation) {
		Integer obj=(Integer) getSqlMapClientTemplate().queryForObject(namespace.concat("countMemberRelationByCondition"),mebmerRelation);
		if(null==obj){
			return 0;
		}
		return obj;
	}

}
