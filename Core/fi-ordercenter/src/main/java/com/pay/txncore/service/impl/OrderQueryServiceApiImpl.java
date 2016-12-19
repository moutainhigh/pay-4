package com.pay.txncore.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.dao.TradeOrderDAO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.model.TradeOrder;
import com.pay.txncore.service.OrderQueryServiceApi;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.BeanConvertUtil;

public class OrderQueryServiceApiImpl implements OrderQueryServiceApi {

	private TradeOrderService tradeOrderService;
	private TradeOrderDAO tradeOrderDAO;
	private final Log log = LogFactory.getLog(OrderQueryServiceApiImpl.class);

	@Override
	public TradeOrderDTO querySuccessOrderByNoAndPidForUpdate(Long tradeOrderNo) {
		TradeOrder order = new TradeOrder();
		order.setTradeOrderNo(tradeOrderNo);
		// 4:交易成功
		order.setStatus(TradeOrderStatusEnum.SUCCESS.getCode());
		TradeOrder tradeOrder = tradeOrderDAO.findByIdAndStatusForUpdate(order);
		return BeanConvertUtil.convert(TradeOrderDTO.class, tradeOrder);
	}

	@Override
	public TradeOrderDTO querySecTransOrderForUpdate(Long tradeOrderNo) {
		TradeOrder order = new TradeOrder();
		order.setTradeOrderNo(tradeOrderNo);
		// 4:交易成功
		order.setStatus(TradeOrderStatusEnum.PAYED.getCode());
		TradeOrder tradeOrder = tradeOrderDAO.findByIdAndStatusForUpdate(order);
		return BeanConvertUtil.convert(TradeOrderDTO.class, tradeOrder);
	}

	@Override
	public TradeOrderDTO queryTradeOrderDTOByPIDAndPartnerOrderId(
			String partner, String orderId) {

		/*TradeOrder tradeOrder = tradeOrderDAO.queryTradeOrder(
				Long.valueOf(partner), orderId);

		return BeanConvertUtil.convert(TradeOrderDTO.class, tradeOrder);*/
		
		List<TradeOrder> tradeOrders = tradeOrderDAO.queryTradeOrder(Long.valueOf(partner),
				orderId);
		
		TradeOrder tradeOrder = null;
		
		if(tradeOrders!=null&&tradeOrders.size()>0){
			  tradeOrder = tradeOrders.get(0);
		      return BeanConvertUtil.convert(TradeOrderDTO.class, tradeOrder);
		}
		
		return null;
		
	}

	@Override
	public TradeOrderDTO queryOrderInfoByPIDAndOrderId(String partner,
			String orderId) {
		/*TradeOrder tradeOrder = tradeOrderDAO.queryTradeOrder(
				Long.valueOf(partner), orderId);

		return BeanConvertUtil.convert(TradeOrderDTO.class, tradeOrder);*/
		
		List<TradeOrder> tradeOrders = tradeOrderDAO.queryTradeOrder(Long.valueOf(partner),
				orderId);
		
		TradeOrder tradeOrder = null;
		
		if(tradeOrders!=null&&tradeOrders.size()>0){
			  tradeOrder = tradeOrders.get(0);
		      return BeanConvertUtil.convert(TradeOrderDTO.class, tradeOrder);
		}
		return null;
	}

	@Override
	public TradeOrderDTO queryTradeOrderDTOByTradeOrderNo(Long tradeOrderNo)
			throws BusinessException, NumberFormatException {
		TradeOrderDTO tradeOrderDTO = tradeOrderService
				.queryTradeOrderById(tradeOrderNo);
		return tradeOrderDTO;
	}

	@Override
	public boolean queryTradeOrderExistByPIdAndTradeNo(String partner,
			String tradeNo) {
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setTradeOrderNo(Long.valueOf(tradeNo));
		tradeOrder.setPartnerId(Long.valueOf(partner));
		TradeOrder searchOrder = (TradeOrder) tradeOrderDAO.findById(tradeNo);
		if (searchOrder == null)
			return false;
		return true;
	}

	@Override
	public TradeOrderDTO queryTradeOrderByTradeOrderNo(Long tradeOrderNo) {
		TradeOrder tradeOrder = (TradeOrder) tradeOrderDAO
				.findById(tradeOrderNo);
		// String orderId = tradeOrder.getOrderId();
		return BeanConvertUtil.convert(TradeOrderDTO.class, tradeOrder);
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setTradeOrderDAO(TradeOrderDAO tradeOrderDAO) {
		this.tradeOrderDAO = tradeOrderDAO;
	}

}
