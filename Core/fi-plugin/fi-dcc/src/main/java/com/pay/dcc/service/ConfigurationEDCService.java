package com.pay.dcc.service;

import com.pay.dcc.model.EDCConfig;

public interface ConfigurationEDCService {

	void saveEDCConfiguration(EDCConfig edc,String[] currencyCodes);

}
