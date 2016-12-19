package com.pay.acct.crossBorderPay.service;

import java.util.List;
import java.util.Map;

import com.pay.acct.crossBorderPay.model.KfFeeConfig;
import com.pay.inf.dao.Page;

public interface KfFeeConfigService {

	List<KfFeeConfig> findKfFeeConfig(Map<String, String> paraMap,
			Page<KfFeeConfig> page);
	KfFeeConfig findKfFeeConfig(Map<String, String> paraMap);
	void deleteKfFeeConfig(String feeConfigNo);

	void add(KfFeeConfig feeConfig);

	void update(KfFeeConfig feeConfig);

}
