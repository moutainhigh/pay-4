/**
 * 
 */
package com.pay.txncore.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.service.PartnerSettlementOrderService;

/**
 * @author chaoyue
 *
 */
public class PartnerSettlementOrderServiceImpl implements
		PartnerSettlementOrderService {

	private BaseDAO partnerSettlementOrderDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.txncore.service.PartnerSettlementOrderService#
	 * createPartnerSettlementOrder
	 * (com.pay.txncore.model.PartnerSettlementOrder)
	 */
	@Override
	public Long createPartnerSettlementOrder(PartnerSettlementOrder order) {
		// TODO Auto-generated method stub
		return (Long) partnerSettlementOrderDAO.create(order);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.txncore.service.PartnerSettlementOrderService#
	 * updatePartnerSettlementOrder
	 * (com.pay.txncore.model.PartnerSettlementOrder)
	 */
	@Override
	public boolean updatePartnerSettlementOrder(PartnerSettlementOrder order) {
		// TODO Auto-generated method stub
		return partnerSettlementOrderDAO.update(order);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.txncore.service.PartnerSettlementOrderService#
	 * queryPartnerSettlementOrder(java.lang.Long, java.lang.Integer,
	 * com.pay.inf.dao.Page)
	 */
	@Override
	public List<PartnerSettlementOrder> queryPartnerSettlementOrder(
			PartnerSettlementOrder order, Page page) {

		return partnerSettlementOrderDAO.findByCriteria("findByCriteria",
				order, page);
	}
	
	@Override
	public List<PartnerSettlementOrder> queryPartnerSettlementOrders(
			PartnerSettlementOrder order) {
		return partnerSettlementOrderDAO.findByCriteria("findByCriteria",order);
	}
	
	@Override
	public List<PartnerSettlementOrder> queryPartnerSettlementOrder(
			PartnerSettlementOrder order) {

		return partnerSettlementOrderDAO.findByCriteria("findSettlementList",
				order);
	}

	public Map<String, Object> countSettlementOrderListSize(
			PartnerSettlementOrder queryDetailPara) {
		return (Map<String, Object>) partnerSettlementOrderDAO
				.findObjectByCriteria("findByCriteriaListSize", queryDetailPara);
	}

	public void setPartnerSettlementOrderDAO(BaseDAO partnerSettlementOrderDAO) {
		this.partnerSettlementOrderDAO = partnerSettlementOrderDAO;
	}

	@Override
	public void createPartnerSettlementOrder(List<PartnerSettlementOrder> orders) {

		partnerSettlementOrderDAO.batchCreate(orders);
	}

	@Override
	public List<PartnerSettlementOrder> queryUnSettlementPartnerSettlementOrder(
			Date settlementDate, Integer maxSize,Integer settlementFlg) {

		Map paraMap = new HashMap();
		paraMap.put("settlementDate", settlementDate);
		paraMap.put("maxSize", maxSize);
		paraMap.put("settlementFlg",settlementFlg);

		return partnerSettlementOrderDAO.findByCriteria(
				"queryUnSettlementPartnerSettlementOrder", paraMap);
	}

	@Override
	public List<PartnerSettlementOrder> queryUnSettlementPartnerSettlementOrder(
			Long partnerId, Integer settlementFlg) {

		PartnerSettlementOrder order = new PartnerSettlementOrder();
		order.setPartnerId(partnerId);
		order.setSettlementFlg(settlementFlg);

		return partnerSettlementOrderDAO
				.findByCriteria("findByCriteria", order);
	}

	@Override
	public boolean isFirstSettlementOrder(Long partnerId, Long paymentOrderNo) {

		PartnerSettlementOrder order = new PartnerSettlementOrder();
		order.setPartnerId(partnerId);
		order.setPaymentOrderNo(paymentOrderNo);

		List<PartnerSettlementOrder> orders = partnerSettlementOrderDAO
				.findByCriteria("findByCriteria", order);

		if (null != orders && !orders.isEmpty()) {
			for (PartnerSettlementOrder f : orders) {
				if (f.getSettlementFlg() == 1) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public List<PartnerSettlementOrder> queryUnSettlementAssurePartnerSettlementOrder(
			Integer maxSize,Integer assureSettlementFlg) {
		Map paraMap = new HashMap();
		paraMap.put("maxSize", maxSize);
        paraMap.put("assureSettlementFlg",assureSettlementFlg);
		return partnerSettlementOrderDAO.findByCriteria(
				"queryUnSettlementAssurePartnerSettlementOrder", paraMap);
	}

	@Override
	public List<PartnerSettlementOrder> querySettlementAmountCount(
			PartnerSettlementOrder order) {
		return partnerSettlementOrderDAO.findByCriteria(
				"querySettlementAmountCount", order);
	}

	@Override
	public List<PartnerSettlementOrder> querySettlementOrder(Long partnerId,
			Long paymentOrderNo) {
		PartnerSettlementOrder order = new PartnerSettlementOrder();
		order.setPartnerId(partnerId);
		order.setPaymentOrderNo(paymentOrderNo);

		List<PartnerSettlementOrder> orders = partnerSettlementOrderDAO
				.findByCriteria("findByCriteria", order);
		return orders;
	}

	@Override
	public PartnerSettlementOrder querySettlementOrderByTradeOrderNo(
			Long partnerId, Long tradeOrderNo) {
		PartnerSettlementOrder order = new PartnerSettlementOrder();
		order.setPartnerId(partnerId);
		order.setTradeOrderNo(tradeOrderNo);

		List<PartnerSettlementOrder> orders = partnerSettlementOrderDAO
				.findByCriteria("findByCriteria", order);
		return orders.get(0);
	}
	
	@Override
	public PartnerSettlementOrder querySettlementOrderByOrderId(
			Long partnerId, String orderId) {
		PartnerSettlementOrder order = new PartnerSettlementOrder();
		order.setPartnerId(partnerId);
		order.setOrderId(orderId);

		List<PartnerSettlementOrder> orders = partnerSettlementOrderDAO
				.findByCriteria("findByCriteria", order);
		return orders.get(0);
	}
	
	@Override
	public List<Map> querySettlementOrder(
			Long partnerId, String startTime,String endTime) {
		PartnerSettlementOrder order = new PartnerSettlementOrder();
		order.setPartnerId(partnerId);
		order.setStartTime(startTime);
		order.setEndTime(endTime);

		List<Map> orders = partnerSettlementOrderDAO
				.findByCriteria("findByCriteria_", order);
		return orders;
	}

}
