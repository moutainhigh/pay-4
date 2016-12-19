package com.pay.poss.accounting.dao.impl;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.accounting.dao.AccountingFeeDao;
import com.pay.poss.accounting.dto.AccountingFeeDto;
import com.pay.poss.accounting.dto.AccountingFeeParamDto;

/**
 * @Description
 * @project ma-manager
 * @file AccountingFeeDaoImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2011-5-2 DDR Create
 */
public class AccountingFeeDaoImpl extends BaseDAOImpl implements
		AccountingFeeDao {

	private BaseDAO pageDaoCommon;

	public void setPageDaoCommon(BaseDAO<AccountingFeeDao> pageDaoCommon) {
		this.pageDaoCommon = pageDaoCommon;
	}

	@Override
	public Page<AccountingFeeDto> findPage(Page<AccountingFeeDto> page,
			AccountingFeeParamDto dto) {
		return (Page<AccountingFeeDto>) pageDaoCommon.findByQuery(
				namespace.concat("search"), page, dto);
	}

	@Override
	public int countSearch(AccountingFeeParamDto dto) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("search_COUNT"), dto);
	}

}
