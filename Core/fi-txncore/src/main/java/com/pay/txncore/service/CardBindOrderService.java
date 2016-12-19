package com.pay.txncore.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.CardBindOrder;

public interface CardBindOrderService {
	 List<CardBindOrder> getCardBindOrders(Map<String,Object> parmas);
	 List<CardBindOrder> getCardBindOrders(Map<String,Object> parmas,Page<CardBindOrder> page);
	 boolean saveCardBindOrder(CardBindOrder order);
	 boolean update(CardBindOrder order);
	 boolean delete(CardBindOrder order);
	 CardBindOrder getCardBindOrder(Long id);
	 CardBindOrder getCardBindOrder(Map<String, Object> params);
	 Long getCardBindIdByToken(String token);
}
