package com.pay.acct.buySettleParities.service.impl;

import java.util.List;
import java.util.Map;


import com.pay.acct.buySettleParities.dao.BuySettleForeignCurrencyServiceDao;
import com.pay.acct.buySettleParities.model.BuysettlePoundageConfig;
import com.pay.acct.buySettleParities.service.BuySettleForeignCurrencyService;
import com.pay.inf.dao.Page;

/**
 * 购结汇手续费配置查询
 * @author delin
 * @date 2016年8月4日15:34:31
 */
public class BuySettleForeignCurrencyServiceImpl implements BuySettleForeignCurrencyService{
	
	private BuySettleForeignCurrencyServiceDao buySettleForeignCurrecnyServiceDao;

	public void setBuySettleForeignCurrecnyServiceDao(
			BuySettleForeignCurrencyServiceDao buySettleForeignCurrecnyServiceDao) {
		this.buySettleForeignCurrecnyServiceDao = buySettleForeignCurrecnyServiceDao;
	}

	@Override
	public List<BuysettlePoundageConfig> queryBuySettleForeignCurrency(Map<String, Object> param,
			Page<Object> page) {
		return buySettleForeignCurrecnyServiceDao.queryBuySettleForeignCurrency(param,page);
	}

	@Override
	public void addBuySettleForeignCurrency(Map<String, Object> param) {
		buySettleForeignCurrecnyServiceDao.addBuySettleForeignCurrency(param);
	}

	@Override
	public void deleteBuySettleForeignCurrency(String id) {
		buySettleForeignCurrecnyServiceDao.deleteBuySettleForeignCurrency( id);
	}

	@Override
	public void updateBuySettleForeignCurrency(Map<String, Object> param) {
		buySettleForeignCurrecnyServiceDao.	updateBuySettleForeignCurrency(param);
	}

	@Override
	public BuysettlePoundageConfig queryBuySettleForeignCurrency(Map<String, Object> param) {
		return buySettleForeignCurrecnyServiceDao.queryBuySettleForeignCurrency(param)	;
	}

}
