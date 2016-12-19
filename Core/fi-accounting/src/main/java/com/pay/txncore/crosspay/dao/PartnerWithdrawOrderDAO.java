package com.pay.txncore.crosspay.dao;

import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.crosspay.model.PartnerWithdrawOrder;

public interface PartnerWithdrawOrderDAO extends BaseDAO<PartnerWithdrawOrder> {

	boolean updatePartnerWithDraw(String sql, Map<String, Object> paramMap);
}