package com.pay.dcc.service;

import java.util.List;

import com.pay.dcc.model.PartnerDCCConfig;
import com.pay.inf.dao.Page;

public interface ConfigurationDCCService {

	List<PartnerDCCConfig> findDCCConfiguration(PartnerDCCConfig dccConfig,Page page);
	List<PartnerDCCConfig> findDCCConfiguration(PartnerDCCConfig dccConfig);

	void saveDCCConfiguration(PartnerDCCConfig dccConfig ,String[] currencyCodeMarkup);

	void updateDCCConfiguration(PartnerDCCConfig dccConfig);

	void bulkUpdateDCC(PartnerDCCConfig dccConfig, String[] split);

}
