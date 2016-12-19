package com.pay.txncore.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dao.CardBindOrderDAO;
import com.pay.txncore.model.CardBindOrder;
import com.pay.txncore.service.CardBindOrderService;

public class OrderCenterCardBindOrderServiceImpl implements CardBindOrderService {
	
	private CardBindOrderDAO cardBindOrderDAO;

	@Override
	public List<CardBindOrder> getCardBindOrders(Map<String, Object> parmas) {
		return cardBindOrderDAO.findByCriteria(parmas);
	}

	@Override
	public List<CardBindOrder> getCardBindOrders(Map<String, Object> parmas,
			Page<CardBindOrder> page) {
		return cardBindOrderDAO.findByCriteria(parmas, page);
	}

	@Override
	public boolean saveCardBindOrder(CardBindOrder payInfo) {
        Long id= (Long) cardBindOrderDAO.create("CreateCardBindOrder",payInfo);
		return id!=null;
	}

	@Override
	public boolean update(CardBindOrder payInfo) {
		return cardBindOrderDAO.update(payInfo);
	}

	@Override
	public boolean delete(CardBindOrder payInfo) {
		return cardBindOrderDAO.delete(payInfo);
	}

	@Override
	public CardBindOrder getCardBindOrder(Long id) {
		return cardBindOrderDAO.findById(id);
	}

	public void setCardBindOrderDAO(CardBindOrderDAO cardBindOrderDAO) {
		this.cardBindOrderDAO = cardBindOrderDAO;
	}

	@Override
	public CardBindOrder getCardBindOrder(Map<String,Object> params) {
		List<CardBindOrder> list = cardBindOrderDAO.findByCriteria(params);
		if(list!=null&&!list.isEmpty())
			return list.get(0);
		return null;
	}
	
}
