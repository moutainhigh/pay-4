/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.dao.enterprise.impl;

import com.pay.base.dao.enterprise.EnterpriseContactDAO;
import com.pay.base.model.EnterpriseContact;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 企业会员联系信息DAO
 * 
 * @author wangzhi
 * @version $Id: EnterpriseContactDAOImpl.java, v 0.1 2010-9-30 下午03:51:01
 *          wangzhi Exp $
 */
public class EnterpriseContactDAOImpl extends BaseDAOImpl implements
		EnterpriseContactDAO {

	/**
	 * @param contactId
	 * @return
	 * @see com.pay.base.dao.enterprise.EnterpriseContactDAO#findByContactId(long)
	 */
	@Override
	public EnterpriseContact findByContactId(long contactId) {
		return (EnterpriseContact) this.getSqlMapClientTemplate()
				.queryForObject(getNamespace().concat("findByContactId"),
						contactId);
	}

	/**
	 * @param memberCode
	 * @return
	 * @see com.pay.base.dao.enterprise.EnterpriseContactDAO#findByMemberCode(long)
	 */
	@Override
	public EnterpriseContact findByMemberCode(long memberCode) {
		return (EnterpriseContact) this.getSqlMapClientTemplate()
				.queryForObject(
						getNamespace().concat("findContactByMemberCode"),
						memberCode);
	}

	/**
	 * @param enterpriseContact
	 * @return
	 * @see com.pay.base.dao.enterprise.EnterpriseContactDAO#updateContact(com.pay.base.model.enterpriseContact)
	 */
	@Override
	public int updateContact(EnterpriseContact enterpriseContact) {
		return this.getSqlMapClientTemplate().update(
				getNamespace().concat("updateEnterpriseContact"),
				enterpriseContact);
	}

	@Override
	public Long createContact(EnterpriseContact enterpriseContact) {
		return (Long) super.create(enterpriseContact);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.dao.enterprise.EnterpriseContactDAO#updateEnterpriseLinker
	 * (com.pay.base.model.EnterpriseContact)
	 */
	@Override
	public int updateEnterpriseLinker(EnterpriseContact enterpriseContact) {
		return this.getSqlMapClientTemplate().update(
				getNamespace().concat("updateEnterpriseLinker"),
				enterpriseContact);
	}

}
