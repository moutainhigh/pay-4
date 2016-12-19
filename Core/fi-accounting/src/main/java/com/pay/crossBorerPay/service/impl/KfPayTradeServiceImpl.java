package com.pay.crossBorerPay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pay.crossBorerPay.dao.KfPayTradeDao;
import com.pay.crossBorerPay.service.KfPayTradeService;
import com.pay.txncore.model.KfPayTrade;

@Service(value="kfPayTradeServiceImpl")
public class KfPayTradeServiceImpl implements KfPayTradeService{
	@Autowired
	@Qualifier(value="kfPayTradeDaoImpl")
	private KfPayTradeDao kfPayTradeDaoImpl;
	@Override
	public String save(KfPayTrade kfPayTrade) {
		return (String) kfPayTradeDaoImpl.create(kfPayTrade);
	}
	
}
