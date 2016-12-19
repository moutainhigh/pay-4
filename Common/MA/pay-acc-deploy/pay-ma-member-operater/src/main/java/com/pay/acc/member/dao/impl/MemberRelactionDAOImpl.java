package com.pay.acc.member.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.member.dao.MemberRelactionDAO;
import com.pay.acc.member.model.MemberRelaction;
import com.pay.inf.dao.impl.BaseDAOImpl;

@SuppressWarnings("unchecked")
public class MemberRelactionDAOImpl extends BaseDAOImpl<MemberRelaction>
		implements MemberRelactionDAO {

	@Override
	public boolean isBePartOfTheBourse(Map<String, Object> paramMap) {
		Integer obj = (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("countMemberRelaction"), paramMap);
		if (null == obj || obj == 0) {
			return false;
		}
		return true;
	}

	@Override
	public MemberRelaction selectMemberInfoDtoByBargainerName(Long bhource,
			String bargainerName) {
		Map<String,Object> paramMap=new HashMap<String,Object>(2);
		paramMap.put("fatherMemberCode", bhource);
		paramMap.put("sonzhName", bargainerName);
		List<MemberRelaction> list=this.getSqlMapClientTemplate().queryForList(namespace.concat("selectMemberRelationByMember"), paramMap);
		if(null!=list && list.size()==1){//只处理有一条记录的，如果有多条记录，是数据问题，直接无视
			return list.get(0);
		}
		return null;

	}
	
	@Override
	public List<MemberRelaction> selectMemberRelationListByMember(Long member,
			Long type) {
		Map<String,Object> paramMap=new HashMap<String,Object>(1);
		if (type == 1) {
			paramMap.put("fatherMemberCode", member);
		}else{
			paramMap.put("sonMemberCode", member);
		}
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("selectMemberRelationByMember"), paramMap);
	}



}
