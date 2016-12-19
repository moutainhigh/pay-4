package com.pay.acct.crossBorderPay.dao;

import java.util.List;
import java.util.Map;

import com.pay.acct.crossBorderPay.model.KfFeeConfig;
import com.pay.inf.dao.Page;

public interface KfFeeConfigDao {

	List<KfFeeConfig> findKfFeeConfig(Map<String, String> paraMap,
			Page<KfFeeConfig> page);
	KfFeeConfig findKfFeeConfig(Map<String, String> paraMap);
	void deleteKfFeeConfig(String feeConfigNo);

	void add(KfFeeConfig feeConfig);

	boolean update(KfFeeConfig feeConfig);

}
