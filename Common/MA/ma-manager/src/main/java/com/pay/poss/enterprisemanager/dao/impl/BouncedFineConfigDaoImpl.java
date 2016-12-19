package com.pay.poss.enterprisemanager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.enterprisemanager.dao.BouncedFineConfigDao;
/**
 * 配置拒付罚款规则
 * @author delin
 * @date 2016年7月19日20:03:50
 */
public class BouncedFineConfigDaoImpl extends BaseDAOImpl implements BouncedFineConfigDao{

	@Override
	public Object addBouncedFineConfig(Map parameterMap) {
		Object create = this.create(parameterMap);
		return create;
	}

	@Override
	public List<Map<String,Object>> findBouncedFineConfig(Map map) {
		return this.findByCriteria(map);
	}

	@Override
	public void deleteBouncedConf(String id) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("id", id);
		this.delete(map);
	}

	@Override
	public void updateBouncedFineConfig(Map map) {
		boolean update = this.update(map);
	}

}
