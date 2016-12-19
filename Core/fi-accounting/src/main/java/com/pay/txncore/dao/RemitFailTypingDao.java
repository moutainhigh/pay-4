package com.pay.txncore.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.KfPayTradeDetail;

public interface RemitFailTypingDao {

	List<KfPayTradeDetail> findKfPayTradeDetail(
			KfPayTradeDetail kfPayTradeDetail, Page page);

	void update(Map<String, Object> paraMap);

	List<KfPayTradeDetail> findKfPayTradeDetail(
			KfPayTradeDetail kfPayTradeDetail);

}
