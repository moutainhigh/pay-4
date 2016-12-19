package com.pay.base.dao.appeal.impl;

import com.pay.base.dao.appeal.AppealDAO;
import com.pay.base.model.Appeal;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author single_zhang
 * @version
 * @data 2010-12-31
 */

public class AppealDAOImpl extends BaseDAOImpl implements AppealDAO {

	@Override
	public Long createAppeal(Appeal appeal) {
		// TODO Auto-generated method stub
		return (Long) this.getSqlMapClientTemplate().insert(
				getNamespace().concat("insertAppeal"), appeal);
	}

	@Override
	public String getAppealCode() {
		return (String) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getAppealCode"));
	}

}
