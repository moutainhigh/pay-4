package com.pay.txncore.dao;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.KfPayTrade;

public interface PayFileDao {

	List<KfPayTrade> findKfPayTrade(KfPayTrade kfPayTrade, Page page);

	List<KfPayTrade> findKfPayTrade(KfPayTrade kfPayTrade);

	boolean updatePayFileDao(KfPayTrade kfPayTrade);
}
