/*
 * pay.com Inc.
 * Copyright (c) 2006-2011 All Rights Reserved.
 */
package com.pay.base.dao.enterprise.impl;

import java.util.List;

import com.pay.base.dao.enterprise.EnterpriseContractDAO;
import com.pay.base.model.EnterpriseContract;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 * @author zhi.wang
 * @version $Id: EnterpriseContractDAOImpl.java, v 0.1 2011-2-21 下午03:34:12
 *          zhi.wang Exp $
 */
public class EnterpriseContractDAOImpl extends BaseDAOImpl implements
		EnterpriseContractDAO {

	/**
	 * @param enterpriseContract
	 * @return
	 * @see com.pay.base.dao.enterprise.EnterpriseContractDAO#createContract(com.pay.base.model.EnterpriseContract)
	 */
	@Override
	public Long createContract(EnterpriseContract enterpriseContract) {
		return (Long) super.create(enterpriseContract);
	}

	public List<EnterpriseContract> getContractByMemberCode(Long memberCode) {
		List<EnterpriseContract> contractEnterpriseList = super
				.getSqlMapClientTemplate().queryForList(
						getNamespace().concat("queryContractByMemberCode"),
						memberCode);
		;

		return contractEnterpriseList;
	}

}
