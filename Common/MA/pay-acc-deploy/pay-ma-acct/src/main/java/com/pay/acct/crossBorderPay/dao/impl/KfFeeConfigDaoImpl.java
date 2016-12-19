package com.pay.acct.crossBorderPay.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.acct.crossBorderPay.dao.KfFeeConfigDao;
import com.pay.acct.crossBorderPay.model.KfFeeConfig;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class KfFeeConfigDaoImpl extends BaseDAOImpl<KfFeeConfig> implements KfFeeConfigDao {

	@Override
	public List<KfFeeConfig> findKfFeeConfig(Map<String, String> paraMap,
			Page<KfFeeConfig> page) {
		return this.findByCriteria("queryConditionsMap",paraMap, page);
	}
	
	public void deleteKfFeeConfig(String feeConfigNo) {
			this.delete(feeConfigNo);
	}

	@Override
	public void add(KfFeeConfig feeConfig) {
		this.create(feeConfig);
	}

	@Override
	public boolean update(KfFeeConfig feeConfig) {
		return super.update(feeConfig);
	}

	@Override
	public KfFeeConfig findKfFeeConfig(Map<String, String> paraMap) {
		return this.findObjectByCriteria("queryConditionsMap",paraMap);
	}

}
