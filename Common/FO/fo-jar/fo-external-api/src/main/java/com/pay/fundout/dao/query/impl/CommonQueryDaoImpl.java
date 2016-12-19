package com.pay.fundout.dao.query.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.dao.query.CommonQueryDao;
import com.pay.fundout.dto.OrderCondition;
import com.pay.fundout.dto.OrderInfo;
import com.pay.fundout.dto.OrderInfoDetail;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class CommonQueryDaoImpl extends BaseDAOImpl implements CommonQueryDao {

	@Override
	public OrderInfoDetail queryOrderDetailByOrderId(final String sqlId,
			final Map<String, Object> map) {
		return (OrderInfoDetail) getSqlMapClientTemplate().queryForObject(
				namespace.concat(sqlId), map);
	}

	@Override
	public Page<OrderInfo> queryOrdersByCondition(final String sqlId,
			Page<OrderInfo> page, final OrderCondition condition) {

		return findByQuery(sqlId, page, condition);
	}

	@Override
	public List<OrderInfo> queryOrdersByCondition(String sqlId,
			OrderCondition condition) {

		return findByQuery(sqlId, condition);
	}

}
