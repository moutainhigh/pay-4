package com.pay.txncore.crosspay.dao;

import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.crosspay.model.RefusePaymentOrder;

public interface RefusePaymentOrderDAO extends BaseDAO<RefusePaymentOrder> {

	boolean updateRefusePayment(String sql, Map<String, Object> conMap);

	void createRefusePayment(String sql, Map<String, Object> conMap);
}