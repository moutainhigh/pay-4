package com.pay.dcc.dao;

import java.util.List;

import com.pay.dcc.model.PartnerDCCConfig;
import com.pay.inf.dao.Page;

public interface ConfigurationDCCDAO {

	public List<PartnerDCCConfig> findDCCConfiguration(PartnerDCCConfig DCCConfig,Page page);
	public List<PartnerDCCConfig> findDCCConfiguration(PartnerDCCConfig DCCConfig);

	public void saveDCCConfiguration(PartnerDCCConfig dccConfig);

	public void updateDCCConfiguration(PartnerDCCConfig dccConfig);

	public void bulkUpdateDCC(PartnerDCCConfig dccConfig);

}
