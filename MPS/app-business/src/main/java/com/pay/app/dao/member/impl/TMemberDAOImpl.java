/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.dao.member.impl;

import com.pay.app.dao.member.TMemberDAO;
import com.pay.app.model.TMember;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 会员基本信息DAO
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-19 上午11:27:25
 * 
 */
public class TMemberDAOImpl extends BaseDAOImpl implements TMemberDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.dao.member.TMemberDAO#findMemberByMemCode(long)
	 */
	@Override
	public TMember findMemberByMemCode(long memberCode) {
		return (TMember) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getMemberByMemCode"), memberCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.dao.member.TMemberDAO#updateMember(com.pay.app.model.Member)
	 */
	@Override
	public int updateMember(TMember member) {
		if (member != null) {
			return this.getSqlMapClientTemplate().update(
					getNamespace().concat("updateMember"), member);
		}
		return 0;
	}

	@Override
	public Long findMemberByLoginName(String loginName) {
		return (Long) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getMemberByLoginName"), loginName);
	}

}
