/**
 * 
 */
package com.pay.txncore.crosspay.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.dao.SettlementRateDAO;
import com.pay.txncore.crosspay.service.SettlementRateService;
import com.pay.txncore.model.SettlementRate;
import com.pay.util.BeanConvertUtil;

/**
 * @author peiyu.yang
 * @since 2015年6月29日
 */
public class SettlementRateServiceImpl implements
		SettlementRateService {

	private SettlementRateDAO settlementRateDAO;

	@Override
	public boolean updateCurrencyRate(SettlementRate rate) {
		return settlementRateDAO.update(rate);
	}

	@Override
	public void batchCreate(List<SettlementRate> rates) {	
		if(rates!=null&&rates.size()>0){
			for(SettlementRate rate:rates){
					settlementRateDAO.update("batchValidStatus",rate);
					settlementRateDAO.create(rate);
			}
		}
	}

	public void setSettlementRateDAO(SettlementRateDAO settlementRateDAO) {
		this.settlementRateDAO = settlementRateDAO;
	}
	
	public SettlementRate  getSettlementRate(SettlementRate rate){
		SettlementRate rate_ = settlementRateDAO.findObjectByCriteria("findByCriteria",rate);
		return rate_;
	}

	@Override
	public List<SettlementRate> findByCriteria(SettlementRate rate,Page page) {
		return (List<SettlementRate>) BeanConvertUtil.convert(
				SettlementRate.class, settlementRateDAO.findByCriteria(
						"findByCriteria",rate, page));
	}
	
	@Override
	public List<SettlementRate> findByCriteria(SettlementRate rate) {
		return (List<SettlementRate>) BeanConvertUtil.convert(
				SettlementRate.class, settlementRateDAO.findByCriteria(
						"findByCriteria",rate));
	}

	@Override
	public SettlementRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, String status, String memberCode,Date currentDate) {
		return settlementRateDAO.findCurrentCurrencyRate(sourceCurrency,
				targetCurrency,status,memberCode,currentDate);
	}
	
	@Override
	public SettlementRate findCurrencyRate(Map<String,Object> paramMap) {
		return settlementRateDAO.newFindCurrentCurrencyRate(paramMap);
	}

	@Override
	public int updateCurrencyRateStatus(List<SettlementRate> rateList) {
		return settlementRateDAO.updateBatch("batchValidStatus", rateList);
	}

	@Override
	public int updateCurrencyRate(List<SettlementRate> rateList) {
		return settlementRateDAO.updateBatch("updateRate", rateList);
	}

}
