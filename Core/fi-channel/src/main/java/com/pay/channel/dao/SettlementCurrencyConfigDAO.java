package com.pay.channel.dao;

import java.util.List;

import com.pay.channel.model.SettlementCurrencyConfig;
import com.pay.inf.dao.Page;

/**
 * 
 * @author zhaoyang
 *
 */
public interface SettlementCurrencyConfigDAO {

	/**分页查询结算币种信息
	 * @param scc 查询条件组装的结算币种对象
	 * @param page 分页信息对象
	 * @return 符合查询条件的结算币种集合
	 */
	List<SettlementCurrencyConfig> queryListScc(SettlementCurrencyConfig scc,Page page);
	
	/**查询结算币种信息
	 * @param scc 查询条件组装的结算币种对象
	 * @return 符合查询条件的结算币种集合
	 */
	List<SettlementCurrencyConfig> queryListScc(SettlementCurrencyConfig scc);
	
	List<SettlementCurrencyConfig> queryIsOnly(SettlementCurrencyConfig scc);
	
	/**添加结算币种信息
	 * @param scc 结算币种对象
	 */
	void addSettlementCurrencyConfig(SettlementCurrencyConfig scc);
	
	/**更新结算币种信息
	 * @param scc 结算币种对象
	 */
	void updateSettlementCurrencyConfig(SettlementCurrencyConfig scc);
	
	/**删除结算币种信息
	 * @param scc 结算币种对象
	 */
	void deleteSettlementCurrencyConfig(SettlementCurrencyConfig scc);
}
