package com.pay.poss.memberrelation.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.memberrelation.model.MemberRelation;

public interface MemberRelationDao extends BaseDAO<MemberRelation> {

	List<MemberRelation> queryMemberRelationByCondition(
			MemberRelation mebmerRelation);

	int countMemberRelationByCondition(MemberRelation mebmerRelation);
}
