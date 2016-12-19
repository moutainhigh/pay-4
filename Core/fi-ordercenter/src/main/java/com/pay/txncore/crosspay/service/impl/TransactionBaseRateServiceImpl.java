/**
 * 
 */
package com.pay.txncore.crosspay.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.dao.TransactionBaseRateDAO;
import com.pay.txncore.crosspay.service.TransactionBaseRateService;
import com.pay.txncore.model.TransactionBaseRate;
import com.pay.util.BeanConvertUtil;
import com.pay.util.StringUtil;

/**
 * @author peiyu.yang
 * @since 2015年6月29日
 */
public class TransactionBaseRateServiceImpl implements
		TransactionBaseRateService {

	private TransactionBaseRateDAO transactionBaseRateDAO;
	
	private final Log logger = LogFactory.getLog(getClass());

	@Override
	public boolean updateCurrencyRate(TransactionBaseRate rate) {
		return transactionBaseRateDAO.update(rate);
	}

	@Override
	public void batchCreate(List<TransactionBaseRate> rates) {	
		if(rates!=null&&rates.size()>0){
			for(TransactionBaseRate rate:rates){
				transactionBaseRateDAO.update("batchValidStatus",rate);
			}
		}
		
		transactionBaseRateDAO.batchCreate(rates);
	}

	public void setTransactionBaseRateDAO(TransactionBaseRateDAO transactionBaseRateDAO) {
		this.transactionBaseRateDAO = transactionBaseRateDAO;
	}

	@Override
	public List<TransactionBaseRate> findByCriteria(TransactionBaseRate rate,Page page) {
		return (List<TransactionBaseRate>) BeanConvertUtil.convert(
				TransactionBaseRate.class, transactionBaseRateDAO.findByCriteria(
						"findByCriteria",rate, page));
	}
	@Override
	public List<TransactionBaseRate> findByCriteria(TransactionBaseRate rate) {
		return (List<TransactionBaseRate>) BeanConvertUtil.convert(
				TransactionBaseRate.class, transactionBaseRateDAO.findByCriteria(
						"findByCriteria",rate));
	}

	@Override
	public TransactionBaseRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, String status, Date currentDate) {
		TransactionBaseRate rate1 = transactionBaseRateDAO.findCurrentCurrencyRate(sourceCurrency,
				targetCurrency,status,currentDate);
		if(rate1==null){
			rate1 = transactionBaseRateDAO.findCurrentCurrencyRate(sourceCurrency,
					"CNY",status,currentDate);
			if(rate1==null)
				return null;
			else{
				TransactionBaseRate rate2 = transactionBaseRateDAO
						.findCurrentCurrencyRate(targetCurrency,"CNY",status,currentDate);
				if(rate2!=null&&!StringUtil.isEmpty(rate2.getExchangeRate())){
					BigDecimal rate = new BigDecimal(rate1.getExchangeRate())
					                    .divide(new BigDecimal(rate2.getExchangeRate()),7, BigDecimal.ROUND_HALF_UP);
					TransactionBaseRate rate0 = new TransactionBaseRate();
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
	public int updateCurrencyRate(List<TransactionBaseRate> rateList) {
		return transactionBaseRateDAO.updateBatch("batchValidStatus", rateList);
	}

}
