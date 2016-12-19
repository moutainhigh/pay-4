package com.pay.channel.service.impl;

import java.util.List;

import com.pay.channel.dao.SettlementCurrencyConfigDAO;
import com.pay.channel.model.SettlementCurrencyConfig;
import com.pay.channel.service.SettlementCurrencyConfigService;

public class SettlementCurrencyConfigServiceImpl implements
		SettlementCurrencyConfigService {

	private SettlementCurrencyConfigDAO settlementCurrencyConfigDAO;
	
	@Override
	public void addSettlementCurrencyConfig(SettlementCurrencyConfig scc)
			throws Exception {
//		List<SettlementCurrencyConfig> list = settlementCurrencyConfigDAO.queryListScc(scc);
		List<SettlementCurrencyConfig> list = settlementCurrencyConfigDAO.queryIsOnly(scc);
		if(list != null && list.size()>0 ){
			throw new Exception("该结算币种配置已经存在");
		}else{
			settlementCurrencyConfigDAO.addSettlementCurrencyConfig(scc);
		}
	}

	@Override
	public void updateSettlementCurrencyConfig(SettlementCurrencyConfig scc)
			throws Exception {
//		List<SettlementCurrencyConfig> list = settlementCurrencyConfigDAO.queryListScc(scc);
		List<SettlementCurrencyConfig> list = settlementCurrencyConfigDAO.queryIsOnly(scc);
		int size = list.size();
		if(list != null && size>1 ){
			throw new Exception("该结算币种配置已经存在");
		}else if(size ==1){
			SettlementCurrencyConfig old = list.get(0);
			boolean result = false;
			if(old.getConfigId() == (long)scc.getConfigId() && (!scc.getMark().equals(old.getMark()) || scc.getGrade().intValue()!=old.getGrade().intValue())){
				settlementCurrencyConfigDAO.updateSettlementCurrencyConfig(scc);
			}else{
				throw new Exception("该结算币种配置已经存在");
			}
		}else{
			settlementCurrencyConfigDAO.updateSettlementCurrencyConfig(scc);
		}
	}

	@Override
	public void deleteSettlementCurrencyConfig(SettlementCurrencyConfig scc) {
		settlementCurrencyConfigDAO.deleteSettlementCurrencyConfig(scc);
	}

	public void setSettlementCurrencyConfigDAO(
			SettlementCurrencyConfigDAO settlementCurrencyConfigDAO) {
		this.settlementCurrencyConfigDAO = settlementCurrencyConfigDAO;
	}

}
