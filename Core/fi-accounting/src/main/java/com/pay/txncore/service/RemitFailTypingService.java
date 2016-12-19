package com.pay.txncore.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.KfPayTradeDetail;

public interface RemitFailTypingService {

	List<KfPayTradeDetail> findKfPayTradeDetail(
			KfPayTradeDetail kfPayTradeDetail, Page page);

	void batchReviewed(Map<String, Object> paraMap);
	void batchUpdate(List<KfPayTradeDetail> list);
	List<KfPayTradeDetail> findKfPayTradeDetail(
			KfPayTradeDetail kfPayTradeDetail);
}
