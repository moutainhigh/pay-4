package com.pay.base.dao.suggest.impl;

import com.pay.base.dao.suggest.SuggestDAO;
import com.pay.base.model.Suggest;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author Jeffrey_teng
 * @version
 * @data 2010-11-10
 */
public class SuggestDAOImpl extends BaseDAOImpl implements SuggestDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.dao.suggest.SuggestDAO#createSuggest(com.pay.base.model.Suggest
	 * )
	 */
	public Long createSuggest(Suggest suggest) {
		return (Long) this.getSqlMapClientTemplate().insert(
				getNamespace().concat("create"), suggest);
	}

}
