package com.pay.poss.enterprisemanager.dao;

import java.util.List;
import java.util.Map;
/**
 * 配置拒付罚款规则
 * @author delin
 * @date 2016年7月19日20:03:50
 */
public interface BouncedFineConfigDao {

	Object addBouncedFineConfig(Map parameterMap);

	List<Map<String,Object>> findBouncedFineConfig(Map map);

	void deleteBouncedConf(String id);

	void updateBouncedFineConfig(Map map);

}
