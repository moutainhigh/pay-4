package com.pay.txncore.service.chargeback.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.BouncedRateListVO;
import com.pay.txncore.service.chargeback.BouncedFineTaskService;

public class BouncedBalanceNotEnoughServiceImpl implements BouncedFineTaskService{
	
	private BaseDAO balanceNotEnoughDAO;
	

	@Override
	public List<Map<String,Object>> queryFineRuleAndRate() {
		return balanceNotEnoughDAO.findAll("queryFineRuleAndRate");
	}

	public void setbalanceNotEnoughDAO(BaseDAO balanceNotEnoughDAO) {
		this.balanceNotEnoughDAO = balanceNotEnoughDAO;
	}

	@Override
	public void insertBouncedFine(Map<String, Object> fineRuleAndRate) {
		balanceNotEnoughDAO.delete(fineRuleAndRate);
		Object create = balanceNotEnoughDAO.create(fineRuleAndRate);
	}

	@Override
	public List<Map> findBouncedFineOrder(Map<String, String> params) {
		return balanceNotEnoughDAO.findByCriteria("findBouncedFineOrder", params);
	}

	@Override
	public void updateBouncedFine(Map map) {
		 balanceNotEnoughDAO.update("updateBouncedFine", map);
	}

	@Override
	public void batchCreate(List<BouncedRateListVO> list) {
		balanceNotEnoughDAO.delete("deleteBouncedRate",list);
		balanceNotEnoughDAO.batchCreate("createRateList", list);
		
	}

	@Override
	public void updateFineRuleAndRate(Map map) {
		balanceNotEnoughDAO.update("updateFineRuleAndRate",map);
	}

	@Override
	public Long queryBouncedFineOrderNo() {
		List<Map> bouncedFine = balanceNotEnoughDAO.findAll("queryBouncedFineNo");
		return Long.valueOf((String)bouncedFine.get(0).get("id"));
	}

	public BaseDAO getBalanceNotEnoughDAO() {
		return balanceNotEnoughDAO;
	}

	public void setBalanceNotEnoughDAO(BaseDAO balanceNotEnoughDAO) {
		this.balanceNotEnoughDAO = balanceNotEnoughDAO;
	}

}
