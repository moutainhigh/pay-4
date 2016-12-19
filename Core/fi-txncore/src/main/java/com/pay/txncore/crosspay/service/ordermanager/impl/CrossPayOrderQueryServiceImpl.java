package com.pay.txncore.crosspay.service.ordermanager.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.dao.OrderQueryDAO;
import com.pay.txncore.crosspay.model.OrderQueryResult;
import com.pay.txncore.crosspay.service.ordermanager.CrossPayOrderQueryService;

public class CrossPayOrderQueryServiceImpl implements CrossPayOrderQueryService {

	private OrderQueryDAO orderQueryDao;

	@Override
	public List<OrderQueryResult> queryOrderDetailList(Map queryDetailPara,
			Integer pageNo, Integer pageSize) {

		List<OrderQueryResult> resultList = orderQueryDao.queryOrderDetailList(
				queryDetailPara, pageNo, pageSize);

		return resultList;
	}

	@Override
	public Map<String, Object> queryOrderDetailListCount(Map queryDetailPara) {
		return orderQueryDao.countRefundOrderList(queryDetailPara);
	}

	@Override
	public OrderQueryResult queryOrderDetailList(Map queryDetailPara) {

		List<OrderQueryResult> resultList = orderQueryDao
				.queryOrderDetailList(queryDetailPara);

		return resultList.get(0);
	}

	@Override
	public List<OrderQueryResult> queryOrderDetailLists(Map queryDetailPara) {
		List<OrderQueryResult> resultList = orderQueryDao
				.queryOrderDetailList(queryDetailPara);
		return resultList;
	}

	@Override
	public Page<OrderQueryResult> queryOrderForPage(
			Map<String, Object> orderCriteria, Page<OrderQueryResult> page) {

		List<OrderQueryResult> resultList = orderQueryDao.findByCriteria(
				"selectOrders", orderCriteria, page);
		page.setResult(resultList);
		return page;
	}

	public void setOrderQueryDao(OrderQueryDAO orderQueryDao) {
		this.orderQueryDao = orderQueryDao;
	}

}
