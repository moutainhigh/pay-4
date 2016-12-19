package com.pay.crossBorerPay.service;

import java.util.List;

import com.pay.txncore.model.KfPayTradeDetail;

public interface KfPayTradeDetailService {
	void batchSave(List<KfPayTradeDetail> kfPayTradeDetail);
}
