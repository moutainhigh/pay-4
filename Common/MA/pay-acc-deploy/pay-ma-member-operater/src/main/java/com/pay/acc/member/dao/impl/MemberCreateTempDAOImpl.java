package com.pay.acc.member.dao.impl;

import com.pay.acc.member.dao.MemberCreateTempDAO;
import com.pay.acc.member.model.Acct;
import com.pay.acc.member.model.AcctAttrib;
import com.pay.acc.member.model.IndividualInfo;
import com.pay.acc.member.model.Member;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-11-15 下午02:21:04
 */
@SuppressWarnings("unchecked")
public class MemberCreateTempDAOImpl extends BaseDAOImpl<Object> implements MemberCreateTempDAO {
	
	@Override
	public Member createMember(Member member) {
		Long num= (Long)getSqlMapClientTemplate().insert(getNamespace().concat("createMember"), member);
		member.setMemberCode(num);
		return member;
	}
	
	@Override
	public void createAcct(Acct acct) {
		getSqlMapClientTemplate().insert(getNamespace().concat("createAcct"), acct);
	}

	@Override
	public Long createAcctAttrib(AcctAttrib acctAttrib) {
		return (Long) getSqlMapClientTemplate().insert(getNamespace().concat("createAcctAttrib"), acctAttrib);
	}

	@Override
	public IndividualInfo createIndividualInfo(IndividualInfo individualInfo) {
		getSqlMapClientTemplate().insert(getNamespace().concat("createIndividualInfo"), individualInfo);
		return null;
	}

	@Override
	public String getAccCodeById(Long acctAttrId) {
		return (String)getSqlMapClientTemplate().queryForObject(getNamespace().concat("getAttCodeById"), acctAttrId);
	}

	@Override
	public void createMemberProduct(Long memberCode) {
		getSqlMapClientTemplate().insert(getNamespace().concat("createMemberProduct"), memberCode);
	}


}
