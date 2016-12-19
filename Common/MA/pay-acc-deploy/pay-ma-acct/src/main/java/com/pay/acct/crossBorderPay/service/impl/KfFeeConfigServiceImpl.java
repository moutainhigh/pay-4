package com.pay.acct.crossBorderPay.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.acct.crossBorderPay.dao.KfFeeConfigDao;
import com.pay.acct.crossBorderPay.model.KfFeeConfig;
import com.pay.acct.crossBorderPay.service.KfFeeConfigService;
import com.pay.inf.dao.Page;

public class KfFeeConfigServiceImpl  implements KfFeeConfigService{
	
	private KfFeeConfigDao kfFeeConfigDao;
	
	public List<KfFeeConfig> findKfFeeConfig(Map<String, String> paraMap,
			Page<KfFeeConfig> page) {
		return kfFeeConfigDao.findKfFeeConfig(paraMap,page);
	}

	public void deleteKfFeeConfig(String feeConfigNo) {
		kfFeeConfigDao.deleteKfFeeConfig(feeConfigNo);
	}
	@Override
	public void add(KfFeeConfig feeConfig) {
		kfFeeConfigDao.add(feeConfig);
	}
	
	@Override
	public void update(KfFeeConfig feeConfig) {
		kfFeeConfigDao.update(feeConfig);
	}
	
	public void setKfFeeConfigDao(KfFeeConfigDao kfFeeConfigDao) {
		this.kfFeeConfigDao = kfFeeConfigDao;
	}

	@Override
	public KfFeeConfig findKfFeeConfig(Map<String, String> paraMap) {
		return kfFeeConfigDao.findKfFeeConfig(paraMap);
	}

}
