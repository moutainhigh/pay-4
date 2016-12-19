package com.pay.app.dao.subscribe.impl;

import java.util.List;

import com.pay.app.dao.subscribe.SubscribeTypeDAO;
import com.pay.app.dto.SubscribeTypeDto;
import com.pay.app.model.SubscribeType;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author jim_chen
 * @version 2010-10-12
 */
@SuppressWarnings("unchecked")
public class SubscribeTypeDAOImpl extends BaseDAOImpl implements
		SubscribeTypeDAO {

	@Override
	public List<SubscribeType> querySubscribeTypeList(
			SubscribeTypeDto subscribeTypeDto) {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("selectSubscribeTypeByMap"), subscribeTypeDto);
	}

	@Override
	public int countSubscribeType(SubscribeTypeDto subscribeTypeDto) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("count"), subscribeTypeDto);
	}

}
