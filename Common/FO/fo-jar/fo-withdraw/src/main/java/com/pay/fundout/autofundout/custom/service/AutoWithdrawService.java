package com.pay.fundout.autofundout.custom.service;

import java.util.List;
import java.util.Map;

import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.poss.base.exception.PossException;

public interface AutoWithdrawService {
	/**
	 * 根据企业的会员号得到企业的定期或者定额的配置信息
	 * @param memberCode
	 * @return   返回 map(AutoFundoutConfig  AutoQuotaConfig/AutoTimeConfig)
	 */
	public Map<String, Object> existsConfig(Long memberCode);
	/**
	 * 添加自动提现的配置信息
	 * @param map(autoFunoutConfig  autoTimeConfig/AutoQuotaConfig)
	 * @return   返回 AutoFundoutConfig  AutoQuotaConfig/AutoTimeConfig
	 */
	public boolean createRdTx(Map<String, Object> config) throws PossException;
	/**
	 * 更改企业的自动提现的配置信息
	 * @param map(autoFunoutConfig  autoTimeConfig/AutoQuotaConfig)
	 * @return   返回 AutoFundoutConfig  AutoQuotaConfig/AutoTimeConfig
	 */
	public boolean updateRdTx(Map<String, Object> config) throws PossException;
	
	/**
	 * 删除配置
	 * @param config
	 * @return
	 */
	public boolean disable(Long configId) throws Exception;  
	
	/**
	 * 查询前台定制所有定期企业自动提现列表
	 * @return
	 */
	List<AutoFundoutResult> queryAutoTimeFundoutResult() ;
	/**
	 * 查询前台定制所有定期按时间点企业自动提现列表
	 * @return
	 */
	List<AutoFundoutResult> queryAutoMoreTimeFundoutResult() ;
	
	/**
	 * 查询前台定制所有定额企业自动提现列表
	 * @return
	 */
	List<AutoFundoutResult> queryAutoQuotaFundoutResult() ;
}
