package com.pay.txncore.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.dao.TradeAmountCountDAO;
import com.pay.txncore.model.SettlementRate;
import com.pay.txncore.model.TradeAmountCount;
import com.pay.txncore.service.TradeAmountCountService;

/**
 * 
 * @author Bobby Guo
 * @date 2015年11月20日
 */
public class TradeAmountCountServiceImpl implements TradeAmountCountService{
	
	private CurrencyRateService currencyRateService;

	private TradeAmountCountDAO tradeAmountCountDAO;
	
	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}

	public void setTradeAmountCountDAO(TradeAmountCountDAO tradeAmountCountDAO) {
		this.tradeAmountCountDAO = tradeAmountCountDAO;
	}

	@Override
	public Long save(TradeAmountCount tradeAmountCount) {
		return (Long)tradeAmountCountDAO.create(tradeAmountCount);
	}

	@Override
	public boolean update(TradeAmountCount tradeAmountCount) {
		return tradeAmountCountDAO.update(tradeAmountCount);
	}

	@Override
	public TradeAmountCount query(TradeAmountCount tradeAmountCount) {
		List<TradeAmountCount> tradeAmountCountList = tradeAmountCountDAO.findByCriteria("findByCriteria",tradeAmountCount);
		return tradeAmountCountList != null && !tradeAmountCountList.isEmpty() ? tradeAmountCountList.get(0) : null;
	}

	@Override
	public void saveOrUpdate(TradeAmountCount tradeAmountCount) {
		TradeAmountCount count = new TradeAmountCount();
		count.setPartnerId(tradeAmountCount.getPartnerId());
		count.setCountMonth(tradeAmountCount.getCountMonth());
		TradeAmountCount query = query(count);
		if(query == null){
			save(tradeAmountCount);
		}else{
			//如果统计币种有变化，需要转汇后累加
			if(!query.getTotalCurrencyCode().equalsIgnoreCase(tradeAmountCount.getTotalCurrencyCode())){
				SettlementRate settlementRate = currencyRateService.getSettlementRate(query.getTotalCurrencyCode(), 
						tradeAmountCount.getTotalCurrencyCode(),"1", tradeAmountCount.getPartnerId(), null);
				Long totalAmount = new BigDecimal(settlementRate.getExchangeRate()).multiply(new BigDecimal(query.getTotalAmount())).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
				query.setTotalAmount(totalAmount);
			}
			tradeAmountCount.setTotalAmount(query.getTotalAmount()+tradeAmountCount.getTotalAmount());
			tradeAmountCount.setId(query.getId());
			tradeAmountCount.setUpdateDate(Calendar.getInstance().getTime());
			update(tradeAmountCount);
		}
	}
}
