package com.pay.txncore.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.KfPayTrade;

public interface PayFileService {

	List<KfPayTrade> findKfPayTrade(KfPayTrade kfPayTrade, Page page);

	List<KfPayTrade> findKfPayTrade(KfPayTrade kfPayTrade);
	
	void update(KfPayTrade kfPayTrade);
}
