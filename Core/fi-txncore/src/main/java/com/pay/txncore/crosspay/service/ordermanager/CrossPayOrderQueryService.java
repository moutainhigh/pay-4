package com.pay.txncore.crosspay.service.ordermanager;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.model.OrderQueryResult;

public interface CrossPayOrderQueryService {

	public List<OrderQueryResult> queryOrderDetailList(Map queryDetailPara,
			Integer pageNo, Integer pageSize);

	public Map<String, Object> queryOrderDetailListCount(Map queryDetailPara);

	public OrderQueryResult queryOrderDetailList(Map queryDetailPara);

	public List<OrderQueryResult> queryOrderDetailLists(Map queryDetailPara);

	Page<OrderQueryResult> queryOrderForPage(Map<String, Object> orderCriteria,
			Page<OrderQueryResult> origPage);

}
