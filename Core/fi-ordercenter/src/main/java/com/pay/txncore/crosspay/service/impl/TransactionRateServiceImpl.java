/**
 * 
 */
package com.pay.txncore.crosspay.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.dao.TransactionRateDAO;
import com.pay.txncore.crosspay.service.TransactionRateService;
import com.pay.txncore.model.TransactionRate;
import com.pay.util.BeanConvertUtil;

/**
 * 交易汇率
 * @author peiyu.yang
 * @since 2015年6月29日
 */
public class TransactionRateServiceImpl implements
		TransactionRateService {

	private TransactionRateDAO transactionRateDAO;

	@Override
	public boolean updateCurrencyRate(TransactionRate rate) {
		return transactionRateDAO.update(rate);
	}

	@Override
	public void batchCreate(List<TransactionRate> rates) {	
		if(rates!=null&&rates.size()>0){
			for(TransactionRate rate:rates){
				transactionRateDAO.update("batchValidStatus",rate);
				transactionRateDAO.create(rate);
			}
		}
	}
	
	public TransactionRate  getTransactionRate(TransactionRate rate){
		TransactionRate rate_ = transactionRateDAO.findObjectByCriteria("findByCriteria",rate);
		return rate_;
	}

	public void setTransactionRateDAO(TransactionRateDAO transactionRateDAO) {
		this.transactionRateDAO = transactionRateDAO;
	}

	@Override
	public List<TransactionRate> findByCriteria(TransactionRate rate,Page page) {
		return (List<TransactionRate>) BeanConvertUtil.convert(
				TransactionRate.class, transactionRateDAO.findByCriteria(
						"findByCriteria",rate, page));
	}
	
	@Override
	public List<TransactionRate> findByCriteria(TransactionRate rate) {
		return (List<TransactionRate>) BeanConvertUtil.convert(
				TransactionRate.class, transactionRateDAO.findByCriteria(
						"findByCriteria",rate));
	}

	@Override
	public TransactionRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, String status,String memberCode, Date currentDate) {
		return transactionRateDAO.findCurrentCurrencyRate(sourceCurrency,
				targetCurrency,status,memberCode,currentDate);
	}
	
	@Override
	public TransactionRate findCurrencyRate(Map<String,Object> paramMap) {
		return transactionRateDAO.newFindCurrentCurrencyRate(paramMap);
	}

	@Override
	public int updateCurrencyRate(List<TransactionRate> rateList) {
		return transactionRateDAO.updateBatch("updateRate", rateList);
	}

	@Override
	public int updateCurrencyRateStatus(List<TransactionRate> rateList) {
		return transactionRateDAO.updateBatch("batchValidStatus", rateList);
	}

}
