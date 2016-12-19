package com.pay.fundout.autofundout.custom.dao;

/**
 * 2010/12/10
 * jack.liu_liu
 */
import java.util.List;

import com.pay.fundout.autofundout.custom.model.AutoFundoutConfig;
import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.inf.dao.BaseDAO;

public interface AutoFundoutConfigDAO extends BaseDAO<AutoFundoutConfig> {

	/**
	 * 更新配置
	 * 
	 * @param config
	 * @return
	 */
	public boolean update(AutoFundoutConfig config);

	/**
	 * 删除配置
	 * 
	 * @param config
	 * @return
	 */
	public boolean disable(Long config);

	/**
	 * 根据会员号找到对应的有效的配置信息
	 * 
	 * @param config
	 * @return
	 */
	public AutoFundoutConfig findById(Long memberCode);

	public Integer findType(long memberCode);

	/**
	 * 查询前台定制所有定期企业自动提现列表
	 * 
	 * @return
	 */
	List<AutoFundoutResult> queryAutoTimeFundoutResult();

	/**
	 * 查询前台定制所有定额企业自动提现列表
	 * 
	 * @return
	 */
	List<AutoFundoutResult> queryAutoQuotaFundoutResult();

	/**
	 * 查询前台定制的所有定期按时间点企业自动提现列表
	 * 
	 * @return
	 */
	List<AutoFundoutResult> queryAutoMoreTimeFundoutResult();

	/**
	 * 查询会员是否配置了自动提现
	 * 
	 * @param memberCode
	 * @param bankCard
	 * @param bankCode
	 * @return
	 */
	Integer findByMemberCodeAndBankCard(AutoFundoutConfig autoFundoutConfig);

}
