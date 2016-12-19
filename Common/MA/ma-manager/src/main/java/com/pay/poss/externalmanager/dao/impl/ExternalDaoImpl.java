package com.pay.poss.externalmanager.dao.impl;



import java.util.Hashtable;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.externalmanager.dao.IExternalDao;
import com.pay.poss.externalmanager.model.Member;



public class ExternalDaoImpl extends BaseDAOImpl<Member> implements IExternalDao{

	@Override
	public Long queryMemberByEmailAndOrgCode(String email,	String orgCode) {
		Map<String,String> paraMap = new Hashtable<String,String>();
		paraMap.put("email", email);
		paraMap.put("orgCode", orgCode);
		Long memberCode =(Long)this.getSqlMapClientTemplate().queryForObject("external.queryMemberByEmailAndOrgCode", paraMap);
		return memberCode;
	}

	@Override
	public Integer queryAccountByMemberCode(Long memberCode) {
		
		return (Integer)this.getSqlMapClientTemplate().queryForObject("external.queryAccountByMemberCode",memberCode);
	}

	@Override
	public Boolean isMemberAccountActive(Long memberCode, Integer accountType) {
		Map<String,Object> dataMap = new Hashtable<String,Object>();
		dataMap.put("memberCode", memberCode);		
		dataMap.put("accountType", accountType);
		Integer myReturn =(Integer) this.getSqlMapClientTemplate().queryForObject("external.isMemberAccountActive",dataMap);
		if(myReturn.equals(1)){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public Long isMemberActive(String memberName, String loginName) {
		Map<String,String> dataMap = new Hashtable<String,String>();
		dataMap.put("memberName", memberName);
		dataMap.put("loginName", loginName);
		Long memberCode =(Long) this.getSqlMapClientTemplate().queryForObject("external.isMemberActive",dataMap);
		return memberCode;
	}





	
	
}
