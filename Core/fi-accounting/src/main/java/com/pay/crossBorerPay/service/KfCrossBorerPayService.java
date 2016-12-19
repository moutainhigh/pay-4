package com.pay.crossBorerPay.service;

import com.pay.txncore.model.KfPayTradeDetail;

public interface KfCrossBorerPayService {
	void crossBorerApply(KfPayTradeDetail kfPayTradeDetail);
	void RemitFailTypingReviewed(String status,String detailNos);
}
