/**
 * 
 */
package com.pay.txncore.crosspay.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.dao.SettlementRateAdjustDAO;
import com.pay.txncore.crosspay.service.SettlementBaseRateService;
import com.pay.txncore.crosspay.service.SettlementRateAdjustService;
import com.pay.txncore.crosspay.service.SettlementRateService;
import com.pay.txncore.model.SettlementBaseRate;
import com.pay.txncore.model.SettlementRate;
import com.pay.txncore.model.SettlementRateAdjust;
import com.pay.util.BeanConvertUtil;

/**
 * @author peiyu.yang
 * @since 2015年6月29日
 */
public class SettlementRateAdjustServiceImpl implements
		SettlementRateAdjustService {

	private SettlementRateAdjustDAO settlementRateAdjustDAO;
	private SettlementBaseRateService settlementBaseRateService;
	private SettlementRateService  settlementRateService;

	@Override
	public boolean updateCurrencyRateAdjust(SettlementRateAdjust rateAdjust) {
		return settlementRateAdjustDAO.update(rateAdjust);
	}

	@Override
	public void batchCreate(List<SettlementRateAdjust> rateAdjusts) {
		
		List<SettlementRateAdjust> rateAdjusts_ = new ArrayList<SettlementRateAdjust>();
		
		if(rateAdjusts!=null&&rateAdjusts.size()>0){
			List<SettlementRate> rateList = new ArrayList<SettlementRate>();
			for(SettlementRateAdjust adjust:rateAdjusts){
				SettlementBaseRate baseRate = settlementBaseRateService.findCurrentCurrencyRate
						(adjust.getCurrency(),adjust.getTargetCurrency(),adjust.getStatus(),null);
				
				if(baseRate!=null){
					
					SettlementRate rate = new SettlementRate();
					
					BigDecimal rate_ = new BigDecimal(baseRate.getExchangeRate());
					BigDecimal change = new BigDecimal(adjust.getAdjustRate());
					BigDecimal tmp = new BigDecimal("0");

					tmp = rate_.add(rate_.multiply(change.multiply(new BigDecimal("0.01"))));

					double rateTmp = tmp.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
					
					rate.setCurrencyUnit(baseRate.getCurrencyUnit());
					rate.setCurrency(adjust.getCurrency());
					rate.setEffectDate(adjust.getEffectDate());
					rate.setCreateDate(new Date());
					rate.setTargetCurrency(adjust.getTargetCurrency());
					
					if("0".equals(adjust.getMemberCode()))
					     rate.setRateTag("0");
					else
						rate.setRateTag("1");
					
					rate.setStartPoint(adjust.getStartPoint());
					rate.setEndPoint(adjust.getEndPoint());
					rate.setOperator(adjust.getOperator());
					rate.setMemberCode(adjust.getMemberCode());
					rate.setStatus(adjust.getStatus());
					rate.setExchangeRate(String.valueOf(rateTmp));
					rate.setExpireDate(baseRate.getExpireDate());
					rate.setCardOrg(adjust.getCardOrg());
					rate.setLeastTransAmount(adjust.getLeastTransAmount());
					rate.setLtaCurrencyCode(adjust.getLtaCurrencyCode());
					
					rateList.add(rate);
					
					rateAdjusts_.add(adjust);
				}
				
				settlementRateAdjustDAO.update("batchValidStatus", adjust);
			}
			settlementRateService.batchCreate(rateList);
		}
		settlementRateAdjustDAO.batchCreate(rateAdjusts_);
	}

	public void setSettlementRateAdjustDAO(SettlementRateAdjustDAO settlementRateAdjustDAO) {
		this.settlementRateAdjustDAO = settlementRateAdjustDAO;
	}

	@Override
	public List<SettlementRateAdjust> findByCriteria(SettlementRateAdjust rate,Page page) {
		return (List<SettlementRateAdjust>) BeanConvertUtil.convert(
				SettlementRateAdjust.class, settlementRateAdjustDAO.findByCriteria(
						"findByCriteria",rate, page));
	}
	
	@Override
	public List<SettlementRateAdjust> findByCriteria(SettlementRateAdjust rate) {
		return (List<SettlementRateAdjust>) BeanConvertUtil.convert(
				SettlementRateAdjust.class, settlementRateAdjustDAO.findByCriteria(
						"findByCriteria",rate));
	}

	@Override
	public SettlementRateAdjust findCurrencyRateAdjust(String sourceCurrency,
			String targetCurrency, String status, String memberCode,Date currentDate) {
		return settlementRateAdjustDAO.findCurrencyRateAdjust(sourceCurrency,
				targetCurrency,status,memberCode,currentDate);
	}

	@Override
	public int updateCurrencyRateAdjust(List<SettlementRateAdjust> rateList) {
		return settlementRateAdjustDAO.updateBatch("batchValidStatus", rateList);
	}

	public void setSettlementRateService(SettlementRateService settlementRateService) {
		this.settlementRateService = settlementRateService;
	}

	public void setSettlementBaseRateService(
			SettlementBaseRateService settlementBaseRateService) {
		this.settlementBaseRateService = settlementBaseRateService;
	}
}
