package com.pay.txncore.crosspay.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.model.FrozenOrder;
import com.pay.txncore.crosspay.model.FrozenOrderCriteria;

public interface FrozenOrderService {

	FrozenOrder findById(Long id);

	long createFrozenOrder(FrozenOrder frozenOrder);

	boolean updateFrozenOrder(FrozenOrder frozenOrder);

	boolean deleteFrozenOrder(Long id);

	List<FrozenOrder> findByCriteria(FrozenOrderCriteria criteria);

	public Page<FrozenOrder> queryFrozenOrderForPage(
			Map<String, Object> frozenOrderCriteria, Page<FrozenOrder> origPage);

	public Map<String, Object> getTradeOrder(String tradeOrderNo);

	public void frozenOrder(Map<String, Object> conMap) throws Exception;

	public void unFrozenOrder(Map<String, Object> conMap) throws Exception;
}