/**
 * 
 */
package com.pay.txncore.crosspay.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.dao.TransactionRateAdjustDAO;
import com.pay.txncore.crosspay.service.TransactionBaseRateService;
import com.pay.txncore.crosspay.service.TransactionRateAdjustService;
import com.pay.txncore.crosspay.service.TransactionRateService;
import com.pay.txncore.model.TransactionBaseRate;
import com.pay.txncore.model.TransactionRate;
import com.pay.txncore.model.TransactionRateAdjust;
import com.pay.util.BeanConvertUtil;

/**
 * @author peiyu.yang
 * @since 2015年6月29日
 */
public class TransactionRateAdjustServiceImpl implements
   TransactionRateAdjustService {

	private TransactionRateAdjustDAO transactionRateAdjustDAO;
	
	private TransactionRateService transactionRateService;
	
	private TransactionBaseRateService transactionBaseRateService;

	@Override
	public boolean updateCurrencyRateAdjust(TransactionRateAdjust rateAdjust) {
		return transactionRateAdjustDAO.update(rateAdjust);
	}

	@Override
	public void batchCreate(List<TransactionRateAdjust> rateAdjusts) {
		
		List<TransactionRateAdjust> rateAdjusts_=new ArrayList<TransactionRateAdjust>();
		if(rateAdjusts!=null&&rateAdjusts.size()>0){
			List<TransactionRate> rateList = new ArrayList<TransactionRate>();
			for(TransactionRateAdjust adjust:rateAdjusts){
				TransactionBaseRate baseRate = transactionBaseRateService.findCurrentCurrencyRate
						(adjust.getCurrency(),adjust.getTargetCurrency(),adjust.getStatus(),null);
				if(baseRate!=null){
					TransactionRate rate = new TransactionRate();
					BigDecimal rate_ = new BigDecimal(baseRate.getExchangeRate());
					BigDecimal change = new BigDecimal(adjust.getAdjustRate());
					BigDecimal tmp = new BigDecimal("0");
					
				    tmp = rate_.add(rate_.multiply(change.multiply(new BigDecimal("0.01"))));

					double rateTmp = tmp.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
					
					rate.setCurrencyUnit(baseRate.getCurrencyUnit());
					rate.setCurrency(adjust.getCurrency());
					rate.setEffectDate(adjust.getEffectDate());
					rate.setTargetCurrency(adjust.getTargetCurrency());
					rate.setCardOrg(adjust.getCardOrg());
					rate.setLeastTransAmount(adjust.getLeastTransAmount());
					rate.setCreateDate(new Date());
					rate.setLtaCurrencyCode(adjust.getLtaCurrencyCode());
					
					if("0".equals(adjust.getMemberCode()))
					     rate.setRateTag("0");
					else
						 rate.setRateTag("1");
					
					rate.setEndPoint(adjust.getEndPoint());
					rate.setStartPoint(adjust.getStartPoint());
					rate.setOperator(adjust.getOperator());
					rate.setMemberCode(adjust.getMemberCode());
					rate.setStatus(adjust.getStatus());
					rate.setExchangeRate(String.valueOf(rateTmp));
					rate.setExpireDate(baseRate.getExpireDate());
					rateList.add(rate);
					
					rateAdjusts_.add(adjust);
				}
				
				transactionRateAdjustDAO.update("batchValidStatus",adjust);
			}
			transactionRateService.batchCreate(rateList);
		}
		
		transactionRateAdjustDAO.batchCreate(rateAdjusts_);
	}

	public void setTransactionRateAdjustDAO(TransactionRateAdjustDAO transactionRateAdjustDAO) {
		this.transactionRateAdjustDAO = transactionRateAdjustDAO;
	}

	@Override
	public List<TransactionRateAdjust> findByCriteria(TransactionRateAdjust rateAdjust,Page page) {
		return (List<TransactionRateAdjust>) BeanConvertUtil.convert(
				TransactionRateAdjust.class, transactionRateAdjustDAO.findByCriteria(
						"findByCriteria",rateAdjust, page));
	}

	@Override
	public TransactionRateAdjust findCurrencyRateAdjust(String sourceCurrency,
			String targetCurrency, String status, String memberCode,Date currentDate) {
		return transactionRateAdjustDAO.findCurrencyRateAdjust(sourceCurrency,
				targetCurrency,status,memberCode,currentDate);
	}

	@Override
	public int updateCurrencyRateAdjust(List<TransactionRateAdjust> rateList) {
		return transactionRateAdjustDAO.updateBatch("batchValidStatus", rateList);
	}

	public void setTransactionRateService(
			TransactionRateService transactionRateService) {
		this.transactionRateService = transactionRateService;
	}

	public void setTransactionBaseRateService(
			TransactionBaseRateService transactionBaseRateService) {
		this.transactionBaseRateService = transactionBaseRateService;
	}

	@Override
	public List<TransactionRateAdjust> findByCriteria(
			TransactionRateAdjust rateAdjust) {
		return (List<TransactionRateAdjust>) BeanConvertUtil.convert(
				TransactionRateAdjust.class, transactionRateAdjustDAO.findByCriteria(
						"findByCriteria",rateAdjust));
	}
	
	
}
