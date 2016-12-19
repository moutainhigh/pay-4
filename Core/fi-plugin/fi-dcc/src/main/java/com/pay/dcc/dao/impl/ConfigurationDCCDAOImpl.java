package com.pay.dcc.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.pay.dcc.dao.ConfigurationDCCDAO;
import com.pay.dcc.model.PartnerDCCConfig;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class ConfigurationDCCDAOImpl  extends BaseDAOImpl<PartnerDCCConfig> implements ConfigurationDCCDAO{

	public List<PartnerDCCConfig> findDCCConfiguration(
			PartnerDCCConfig DCCConfig,Page page) {
//		HashMap map=new HashMap();
//		map.put("partnerId", DCCConfig.getPartnerId());
//		map.put("partnerName", DCCConfig.getPartnerName());
		List<PartnerDCCConfig> list = super.findByCriteria("findByCriteria",DCCConfig,page);
		return list;
	}
		
	public void saveDCCConfiguration(PartnerDCCConfig dccConfig) {
		getSqlMapClientTemplate().insert(namespace.concat("insertPartnerDCCConfig"),
				dccConfig);
	}

	@Override
	public void updateDCCConfiguration(PartnerDCCConfig dccConfig) {
		getSqlMapClientTemplate().update(namespace.concat("updatePartnerDCCConfig"),
				dccConfig);
	}

	@Override
	public void bulkUpdateDCC(PartnerDCCConfig dccConfig) {
		getSqlMapClientTemplate().update(namespace.concat("bulkUpdateDCC"),
				dccConfig);

	}

	@Override
	public List<PartnerDCCConfig> findDCCConfiguration(
			PartnerDCCConfig DCCConfig) {
		List<PartnerDCCConfig> list = super.findByCriteria("findByCriteria",DCCConfig);
		return list;
	}
}
