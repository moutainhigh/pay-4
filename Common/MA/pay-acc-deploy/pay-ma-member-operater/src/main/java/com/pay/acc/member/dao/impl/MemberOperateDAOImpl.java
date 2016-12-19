package com.pay.acc.member.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.member.dao.MemberOperateDAO;
import com.pay.acc.member.model.MemberOperate;
import com.pay.acc.member.model.Operator;
import com.pay.inf.dao.impl.BaseDAOImpl;

@SuppressWarnings("unchecked")
public class MemberOperateDAOImpl extends BaseDAOImpl<MemberOperate> implements MemberOperateDAO {

	@Override
	public List<MemberOperate> queryMemberOperateByMap(Map<String, Object> paramMap) {
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("selectMemberOperateByMap"), paramMap);
	}

	@Override
	public List<MemberOperate> queryAccountMemberOperateByMap(
			Map<String, Object> paramMap) {
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("selectAccountMemberOperateByMap"), paramMap);
	}

	
	public Operator queryOperatorByOperatorId(Long operatorId){
		return (Operator)this.getSqlMapClientTemplate().queryForObject(namespace.concat("findByOperatorId"), operatorId);
	}
	
	public Operator queryOperatorByMemberCode(Long memberCode,String identity){
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("memberCode", memberCode);
		data.put("identity", identity);
		return (Operator)this.getSqlMapClientTemplate().queryForObject(namespace.concat("findByMemberCodeAndIdentity"), data);
	}
	
	
	public Integer queryCountByErrLogin(Long memberCode){
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("memberCode", memberCode);
		return (Integer)this.getSqlMapClientTemplate().queryForObject(namespace.concat("findErrLoginLogsByMember"),data);
		
	}
	
}
