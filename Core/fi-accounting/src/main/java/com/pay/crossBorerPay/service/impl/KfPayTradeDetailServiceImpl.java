package com.pay.crossBorerPay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pay.crossBorerPay.dao.KfPayTradeDetailDao;
import com.pay.crossBorerPay.service.KfPayTradeDetailService;
import com.pay.txncore.model.KfPayTradeDetail;

@Service(value="kfPayTradeDetailServiceImpl")
public class KfPayTradeDetailServiceImpl implements KfPayTradeDetailService{
	@Autowired
	@Qualifier(value="kfPayTradeDetailDaoImpl")
	private KfPayTradeDetailDao kfPayTradeDetailDaoImpl;
	@Override
	public void batchSave(List<KfPayTradeDetail> kfPayTradeDetail) {
		kfPayTradeDetailDaoImpl.batchCreate(kfPayTradeDetail);
	}
}
