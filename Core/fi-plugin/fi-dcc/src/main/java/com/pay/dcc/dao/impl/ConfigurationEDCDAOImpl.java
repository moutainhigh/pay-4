package com.pay.dcc.dao.impl;

import com.pay.dcc.dao.ConfigurationEDCDAO;
import com.pay.dcc.model.EDCConfig;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class ConfigurationEDCDAOImpl  extends BaseDAOImpl<EDCConfig> implements ConfigurationEDCDAO{

	@Override
	public void saveEDCConfiguration(EDCConfig edc) {
		getSqlMapClientTemplate().insert(namespace.concat("insertedc"),
				edc);
	}

}
