package com.pay.fundout.autofundout.custom.dao;

/**
 * 2010/12/10
 * jack.liu_liu
 */
import java.util.List;

import com.pay.fundout.autofundout.custom.model.AutoTimeConfig;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.exception.AppException;

public interface AutoTimeConfigDAO extends BaseDAO<AutoTimeConfig> {

	/**
	 * 批量增加配置
	 * 
	 * @param configList
	 * @throws AppException
	 */
	public void create(List<AutoTimeConfig> configList);

	public boolean update(AutoTimeConfig config);

	public long findId(long configId);

	List<AutoTimeConfig> findByConfigId(long configId);

	public int delete(Long configId);
}
