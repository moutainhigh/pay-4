/**
 * 
 */
package com.pay.base.fi.service.impl;

import java.util.Map;

import com.pay.base.fi.dao.PartnerConfigDao;
import com.pay.base.fi.model.PartnerConfig;
import com.pay.base.fi.service.PartnerConfigService;

/**
 * @author PengJiangbo
 *
 */
public class PartnerConfigServiceImpl implements PartnerConfigService {

	private PartnerConfigDao partnerConfigDao ;
	
	/* (non-Javadoc)
	 * @see com.pay.base.fi.service.PartnerConfigService#findPartnerConfig(java.util.Map)
	 */
	@Override
	public PartnerConfig findPartnerConfig(Map<String, Object> hMap) {
		
		return (PartnerConfig) this.partnerConfigDao.findObjectByCriteria("findObjectByCriteria", hMap) ;
	}

	public void setPartnerConfigDao(PartnerConfigDao partnerConfigDao) {
		this.partnerConfigDao = partnerConfigDao;
	}

	
}
