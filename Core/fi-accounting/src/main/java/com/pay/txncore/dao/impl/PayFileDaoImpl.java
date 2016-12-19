package com.pay.txncore.dao.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.PayFileDao;
import com.pay.txncore.model.KfPayTrade;
import com.pay.txncore.model.KfPayTradeDetail;

public class PayFileDaoImpl extends BaseDAOImpl<KfPayTrade> implements PayFileDao {

	@Override
	public List<KfPayTrade> findKfPayTrade(KfPayTrade kfPayTrade,Page page) {
		return this.findByCriteria("queryConditions",kfPayTrade, page);
	}

	@Override
	public List<KfPayTrade> findKfPayTrade(KfPayTrade kfPayTrade) {
		return this.findByCriteria("queryConditions",kfPayTrade);
	}

	@Override
	public boolean updatePayFileDao(KfPayTrade kfPayTrade) {
		return this.update(kfPayTrade);
	}
}
