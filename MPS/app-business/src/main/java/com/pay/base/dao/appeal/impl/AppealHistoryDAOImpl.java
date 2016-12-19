package com.pay.base.dao.appeal.impl;

import com.pay.base.dao.appeal.AppealHistoryDAO;
import com.pay.base.model.AppealHistory;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author single_zhang
 * @version
 * @data 2010-12-31
 */

public class AppealHistoryDAOImpl extends BaseDAOImpl implements
		AppealHistoryDAO {

	@Override
	public Long createAppealHistory(AppealHistory appealHistory) {
		
		return (Long) this.getSqlMapClientTemplate().insert(
				namespace.concat("insertAppealHistory"), appealHistory);
	}

}
