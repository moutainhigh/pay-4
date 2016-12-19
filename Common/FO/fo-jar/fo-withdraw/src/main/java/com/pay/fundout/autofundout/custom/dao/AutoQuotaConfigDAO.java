package com.pay.fundout.autofundout.custom.dao;

/**
 * 2010/12/10
 * jack.liu_liu
 */
import com.pay.fundout.autofundout.custom.model.AutoFundoutConfig;
import com.pay.fundout.autofundout.custom.model.AutoQuotaConfig;
import com.pay.inf.dao.BaseDAO;

public interface AutoQuotaConfigDAO extends BaseDAO<AutoQuotaConfig> {
	/**
	 * 增加配置
	 * 
	 * @param config
	 * @return
	 */
	public boolean update(AutoFundoutConfig config);

	public long findId(long configId);

	public AutoQuotaConfig findById(long configId);

	public int delete(Long configId);
}
