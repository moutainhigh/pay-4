package com.pay.pe.dao.exchangerate.impl;

import org.springframework.util.Assert;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.exchangerate.ExchangeRateDAO;
import com.pay.pe.model.ExchangeRate;
import com.pay.util.MfDateTime;

public class ExchangeRateDAOImpl extends IbatisDAOSupport implements
		ExchangeRateDAO {

	public ExchangeRate findExchangeRate(String currencyCodeFrom,
			String currencyCodeTo, MfDateTime date) {
		Assert.notNull(currencyCodeFrom);
		Assert.notNull(currencyCodeTo);
		Assert.notNull(date);
		
		
		ExchangeRate exchangeRate = new ExchangeRate();
		exchangeRate.setCurrencyCodeFrom(currencyCodeFrom);
		
//		
//		
//
//		ExpressionType exp1 = ExpressionTypeFactory.equal("currencyCodeFrom",
//				currencyCodeFrom);
//		ExpressionType exp2 = ExpressionTypeFactory.equal("currencyCodeTo",
//				currencyCodeTo);
//		ExpressionType exp3 = ExpressionTypeFactory.lessThanEqual("startDate",
//				date.toString());
//		ExpressionType exp4 = ExpressionTypeFactory.greaterThanEqual("endDate",
//				date.toString());
//
//		//FX:可能返回list
//		Object obj = super.findObjectByExpTypes(ExchangeRate.class,
//				new ExpressionType[] { exp1, exp2, exp3, exp4 });
//		if(obj!=null){
//			return (ExchangeRate)obj;
//		}
		return null;
	}

}
