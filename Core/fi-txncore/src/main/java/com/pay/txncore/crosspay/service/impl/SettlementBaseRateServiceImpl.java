/**
 * 
 */
package com.pay.txncore.crosspay.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.dao.SettlementBaseRateDAO;
import com.pay.txncore.crosspay.service.SettlementBaseRateService;
import com.pay.txncore.model.SettlementBaseRate;
import com.pay.util.BeanConvertUtil;
import com.pay.util.StringUtil;

/**
 * @author peiyu.yang
 * @since 2015年6月29日
 */
public class SettlementBaseRateServiceImpl implements
		SettlementBaseRateService {

	private SettlementBaseRateDAO settlementBaseRateDAO;

	@Override
	public boolean updateCurrencyRate(SettlementBaseRate rate) {
		return settlementBaseRateDAO.update(rate);
	}

	@Override
	public void batchCreate(List<SettlementBaseRate> rates) {	
		if(rates!=null&&rates.size()>0){
			for(SettlementBaseRate rate:rates){
				settlementBaseRateDAO.update("batchValidStatus",rate);
			}
		}
		
		settlementBaseRateDAO.batchCreate(rates);
	}

	public void setSettlementBaseRateDAO(SettlementBaseRateDAO settlementBaseRateDAO) {
		this.settlementBaseRateDAO = settlementBaseRateDAO;
	}

	@Override
	public List<SettlementBaseRate> findByCriteria(SettlementBaseRate rate,Page page) {
		return (List<SettlementBaseRate>) BeanConvertUtil.convert(
				SettlementBaseRate.class, settlementBaseRateDAO.findByCriteria(
						"findByCriteria",rate, page));
	}

	@Override
	public SettlementBaseRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, String status, Date currentDate) {
		SettlementBaseRate rate1 = settlementBaseRateDAO.findCurrentCurrencyRate(sourceCurrency,
				targetCurrency,status,currentDate);
		if(rate1==null){
			rate1 = settlementBaseRateDAO.findCurrentCurrencyRate(sourceCurrency,
					"CNY",status,currentDate);
			if(rate1==null)
				return null;
			else{
				SettlementBaseRate rate2 = settlementBaseRateDAO
						.findCurrentCurrencyRate(targetCurrency,"CNY",status,currentDate);
				if(rate2!=null&&!StringUtil.isEmpty(rate2.getExchangeRate())){
					BigDecimal rate = new BigDecimal(rate1.getExchangeRate())
					                    .divide(new BigDecimal(rate2.getExchangeRate()),7, BigDecimal.ROUND_HALF_UP);
					SettlementBaseRate rate0 = new SettlementBaseRate();
					rate0.setCurrency(sourceCurrency);
					rate0.setTargetCurrency(targetCurrency);
					rate0.setStatus("1");
					rate0.setExchangeRate(rate.toString());
					return rate0;
				}else
					return null;
			}
		}
		
		return rate1;
	}

	@Override
	public int updateCurrencyRate(List<SettlementBaseRate> rateList) {
		return settlementBaseRateDAO.updateBatch("batchValidStatus", rateList);
	}

}
