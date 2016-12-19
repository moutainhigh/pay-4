package com.pay.txncore.crosspay.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.model.OrderQueryResult;

public interface OrderQueryDAO extends BaseDAO<OrderQueryResult> {

	public List<OrderQueryResult> queryOrderDetailList(Map queryDetailPara,
			Integer pageNo, Integer pageSize);

	Map<String, Object> countRefundOrderList(Map queryDetailPara);

	List<OrderQueryResult> queryOrderDetailList(Map queryDetailPara);
	
	List<Map>queryOrderInBackGroundForPage(String sql,Map<String,Object>paramMap,Page page);
	
	Map<String,Object>viewOrderDetails(String sql,Map queryParam);
}
