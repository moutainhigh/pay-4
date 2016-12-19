package com.pay.poss.enterprisemanager.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.poss.enterprisemanager.dao.BouncedFineConfigDao;
import com.pay.poss.enterprisemanager.service.BouncedFineConfigService;

/**
 * 配置拒付罚款规则
 * @author delin
 * @date 2016年7月19日20:03:50
 */
public class BouncedFineConfigServiceImpl implements BouncedFineConfigService{

	private BouncedFineConfigDao bouncedFineConfigDao;
	
	public void setBouncedFineConfigDao(BouncedFineConfigDao bouncedFineConfigDao) {
		this.bouncedFineConfigDao = bouncedFineConfigDao;
	}

	@Override
	public Object addBouncedFineConfig(Map parameterMap) {
	return	bouncedFineConfigDao.addBouncedFineConfig(parameterMap);
	}

	@Override
	public List<Map<String,Object>> findBouncedFineConfig(Map map) {
		return	bouncedFineConfigDao.findBouncedFineConfig(map);
	}

	@Override
	public void deleteBouncedConf(String id) {
		bouncedFineConfigDao.deleteBouncedConf(id);
	}

	@Override
	public void updateBouncedFineConfig(Map map) {
		bouncedFineConfigDao.updateBouncedFineConfig(map);
	}

}
