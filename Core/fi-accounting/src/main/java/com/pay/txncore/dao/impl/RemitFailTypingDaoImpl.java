package com.pay.txncore.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.RemitFailTypingDao;
import com.pay.txncore.model.KfPayTradeDetail;

public class RemitFailTypingDaoImpl  extends BaseDAOImpl implements RemitFailTypingDao {

	@Override
	public List<KfPayTradeDetail> findKfPayTradeDetail(
			KfPayTradeDetail kfPayTradeDetail, Page page) {
		return this.findByCriteria("queryConditions", kfPayTradeDetail, page);
	}

	@Override
	public void update(Map<String, Object> paraMap) {
		super.update(paraMap);
	}

	@Override
	public List<KfPayTradeDetail> findKfPayTradeDetail(
			KfPayTradeDetail kfPayTradeDetail) {
		return this.findByCriteria("queryConditions", kfPayTradeDetail);
	}

}
