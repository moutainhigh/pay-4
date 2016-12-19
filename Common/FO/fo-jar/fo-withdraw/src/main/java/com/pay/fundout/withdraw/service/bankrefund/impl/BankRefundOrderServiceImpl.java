/**
 *  File: BankRefundOrderServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-27      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.bankrefund.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.fundout.withdraw.dao.bankrefund.BankRefundOrderDAO;
import com.pay.fundout.withdraw.dto.bankrefund.BankRefundOrderDTO;
import com.pay.fundout.withdraw.model.bankrefund.BackfundOrder;
import com.pay.fundout.withdraw.model.bankrefund.BankRefundOrder;
import com.pay.fundout.withdraw.model.bankrefund.BankRefundOrderQueryResult;
import com.pay.fundout.withdraw.service.bankrefund.BankRefundOrderService;
import com.pay.inf.dao.Page;

/**
 * @author bill_peng
 * 
 */
public class BankRefundOrderServiceImpl implements BankRefundOrderService {

	private BankRefundOrderDAO bankRefundOrderDAO;

	public void setBankRefundOrderDAO(BankRefundOrderDAO bankRefundOrderDAO) {
		this.bankRefundOrderDAO = bankRefundOrderDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.withdraw.service.bankrefund.BankRefundOrderService
	 * #createOrder
	 * (com.pay.fundout.withdraw.dto.bankrefund.BankRefundOrderDTO)
	 */
	@Override
	public Long createOrderRnTx(BankRefundOrderDTO order) {
		BankRefundOrder model = new BankRefundOrder();
		BeanUtils.copyProperties(order, model);
		return bankRefundOrderDAO.createOrder(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.withdraw.service.bankrefund.BankRefundOrderService
	 * #updateOrder
	 * (com.pay.fundout.withdraw.dto.bankrefund.BankRefundOrderDTO)
	 */
	@Override
	public boolean updateOrder(BankRefundOrderDTO order) {
		BankRefundOrder model = new BankRefundOrder();
		BeanUtils.copyProperties(order, model);
		return bankRefundOrderDAO.updateOrder(model);
	}

	@Override
	public Page<BankRefundOrderQueryResult> getAllowRefundList(Page<BankRefundOrderQueryResult> page, BankRefundOrderQueryResult query) {
		return bankRefundOrderDAO.getAllowRefundList(page, query);
	}

	@Override
	public Page<BankRefundOrderQueryResult> getAuditRefundList(Page<BankRefundOrderQueryResult> page, BankRefundOrderQueryResult query) {
		return bankRefundOrderDAO.getAuditRefundList(page, query);
	}

	@Override
	public BankRefundOrderQueryResult getAllowRefundOrderById(String orderId) {
		return this.bankRefundOrderDAO.getAllowRefundOrderById(orderId);
	}

	@Override
	public BankRefundOrderQueryResult getAuditRefundOrderById(String orderId) {
		return this.bankRefundOrderDAO.getAuditRefundOrderById(orderId);
	}

	@Override
	public Page<BankRefundOrderQueryResult> getAuditedRefundList(Page<BankRefundOrderQueryResult> page, Object... params) {
		return this.bankRefundOrderDAO.getAuditedRefundList(page, params);
	}

	@Override
	public BankRefundOrderDTO getOrder(Long orderId) {
		BankRefundOrder order = (BankRefundOrder) bankRefundOrderDAO.findById(orderId);
		BankRefundOrderDTO dto = null;
		if (order != null) {
			dto = new BankRefundOrderDTO();
			BeanUtils.copyProperties(order, dto);
		}
		return dto;
	}

	@Override
	public BankRefundOrderDTO getOrderByTradeOrderId(Long tradeOrderId) {
		BankRefundOrder order = bankRefundOrderDAO.getOrderByTradeOrderId(tradeOrderId);
		BankRefundOrderDTO dto = null;
		if (order != null) {
			dto = new BankRefundOrderDTO();
			BeanUtils.copyProperties(order, dto);
		}
		return dto;
	}

	@Override
	public BackfundOrder queryBackfundOrderByInnerOrderId(String innerOrderId, String outerOrderId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("INNER_ORDER", innerOrderId);
		param.put("STATUS", "111");
		BackfundOrder result = bankRefundOrderDAO.queryBackfundOrderByInnerOrderId(param);
		if (result == null) {
			result = new BackfundOrder();
			result.setInnerOrderId(innerOrderId);
			result.setOuterOrderId(outerOrderId);
			result.setStatus(112);
			return result;
		}else{
			result.setOuterOrderId(outerOrderId);
			return result;
		}
	}

	@Override
	public List<Map<String, String>> getFailDesc() {
		return bankRefundOrderDAO.getFailDesc();
	}

}
