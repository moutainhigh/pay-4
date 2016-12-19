package com.pay.channel.service;

import com.pay.channel.model.SettlementCurrencyConfig;
/**
 * 
 * @author zhaoyang
 *
 */
public interface SettlementCurrencyConfigService {

	void addSettlementCurrencyConfig(SettlementCurrencyConfig scc) throws Exception;
	
	void updateSettlementCurrencyConfig(SettlementCurrencyConfig scc) throws Exception;
	
	void deleteSettlementCurrencyConfig(SettlementCurrencyConfig scc);
}
