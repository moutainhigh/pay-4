package com.pay.txncore.service.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.dao.PayFileDao;
import com.pay.txncore.model.KfPayTrade;
import com.pay.txncore.service.PayFileService;

public class PayFileServiceImpl implements PayFileService{

	private PayFileDao payFileDao;
	
	public List<KfPayTrade> findKfPayTrade(KfPayTrade kfPayTrade, Page page) {
		return payFileDao.findKfPayTrade(kfPayTrade,page);
	}

	public void setPayFileDao(PayFileDao payFileDao) {
		this.payFileDao = payFileDao;
	}

	@Override
	public List<KfPayTrade> findKfPayTrade(KfPayTrade kfPayTrade) {
		return payFileDao.findKfPayTrade(kfPayTrade);
	}

	@Override
	public void update(KfPayTrade kfPayTrade) {
		payFileDao.updatePayFileDao(kfPayTrade);
		
	}

}
