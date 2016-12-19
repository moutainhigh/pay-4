package com.pay.txncore.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.artificial.trade.model.AutoTranRela;
import com.pay.inf.dao.Page;
import com.pay.txncore.dao.TradeBaseDAO;
import com.pay.txncore.dao.TradeOrderDAO;
import com.pay.txncore.dto.AutoTradeOrderDTO;
import com.pay.txncore.dto.PayLinkDTO;
import com.pay.txncore.dto.PayLinkDetailDTO;
import com.pay.txncore.dto.PayLinkTemplateDTO;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.model.TradeBase;
import com.pay.txncore.model.TradeOrder;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.BeanConvertUtil;

/**
 * 网关订单查询API
 * 
 * @author Fred
 *
 */
public class TradeOrderServiceImpl implements TradeOrderService {

	private final Log logger = LogFactory.getLog(getClass());
	private TradeBaseDAO tradeBaseDAO;
	private TradeOrderDAO tradeOrderDAO;

	public void setTradeOrderDAO(TradeOrderDAO tradeOrderDAO) {
		this.tradeOrderDAO = tradeOrderDAO;
	}

	public void setTradeBaseDAO(TradeBaseDAO tradeBaseDAO) {
		this.tradeBaseDAO = tradeBaseDAO;
	}

	@Override
	public boolean updateStatus(Long tradeOrderNo, Integer oldStatus,
			Integer status) {
		return tradeOrderDAO.updateStatus(tradeOrderNo, oldStatus, status);
	}

	public TradeBaseDTO queryTradeBaseById(Long tradeBaseNo) {
		TradeBase tradeBase = tradeBaseDAO.findById(tradeBaseNo);
		return BeanConvertUtil.convert(TradeBaseDTO.class, tradeBase);
	}

	@Override
	public boolean lockedOrderForUpdate(Long tradeOrderNo) {
		return tradeOrderDAO.lockedOrderForUpdate(tradeOrderNo);
	}

	@Override
	public TradeOrderDTO queryTradeOrderById(Long tradeOrderNo) {
		TradeOrder tradeOrder = (TradeOrder) tradeOrderDAO
				.findById(tradeOrderNo);
		return BeanConvertUtil.convert(TradeOrderDTO.class, tradeOrder);
	}

	public List<TradeOrderDTO> queryTradeOrder(TradeOrderDTO tradeOrderDTO,
			Page page) {
		TradeOrder tradeOrder = BeanConvertUtil.convert(TradeOrder.class,
				tradeOrderDTO);
		List<TradeOrder> tradeOrders = tradeOrderDAO.findByCriteria(
				"findByCriteria", tradeOrder, page);

		return (List<TradeOrderDTO>) BeanConvertUtil.convert(
				TradeOrderDTO.class, tradeOrders);
	}
	
	@Override
	public List<TradeOrderDTO> queryTradeOrder(TradeOrderDTO tradeOrderDTO) {
		TradeOrder tradeOrder = BeanConvertUtil.convert(TradeOrder.class,
				tradeOrderDTO);
		List<TradeOrder> tradeOrders = tradeOrderDAO.findByCriteria(
				"findByCriteria", tradeOrder);

		return (List<TradeOrderDTO>) BeanConvertUtil.convert(
				TradeOrderDTO.class, tradeOrders);
	}

	@Override
	public TradeOrderDTO queryTradeOrder(Long partnerId, String orderId) {

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("memberCode", String.valueOf(partnerId));
		paraMap.put("orderId", orderId);

		TradeOrder tradeOrder = tradeOrderDAO.queryTradeOrder(paraMap);

		if (tradeOrder != null) {
			return BeanConvertUtil.convert(TradeOrderDTO.class, tradeOrder);
		}

		return null;
	}
	
	@Override
	public TradeOrderDTO queryTradeOrderByPTO(Long partnerId,Long tradeOrderNo,String orderId) {

		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("memberCode", String.valueOf(partnerId));
		paraMap.put("orderId", orderId);
		paraMap.put("tradeOrderNo",tradeOrderNo);

		TradeOrder tradeOrder = (TradeOrder) tradeOrderDAO.findObjectByCriteria("findByCriteria",paraMap);

		if (tradeOrder != null) {
			return BeanConvertUtil.convert(TradeOrderDTO.class, tradeOrder);
		}

		return null;
	}

	@Override
	public TradeOrderDTO queryCompletedTradeOrder(Map<String, String> paraMap) {

		TradeOrder tradeOrder = tradeOrderDAO.queryTradeOrder(paraMap);
		return BeanConvertUtil.convert(TradeOrderDTO.class, tradeOrder);
	}

	@Override
	public Long saveTradeOrderRnTx(TradeBaseDTO tradeBaseDTO,
			TradeOrderDTO tradeOrderDTO) {
		TradeBase tradeBase = BeanConvertUtil.convert(TradeBase.class,
				tradeBaseDTO);
		Long tradeBaseNo = (Long) tradeBaseDAO.create(tradeBase);

		TradeOrder tradeOrder = BeanConvertUtil.convert(TradeOrder.class,
				tradeOrderDTO);

		tradeOrder.setTradeBaseNo(tradeBaseNo);
		Long traderOrderNo = (Long) tradeOrderDAO.create(tradeOrder);
		tradeBaseDTO.setTradeBaseNo(tradeBaseNo);
		tradeOrderDTO.setTradeOrderNo(traderOrderNo);
		return traderOrderNo;
	}

	@Override
	public boolean updateTradeOrderRnTx(TradeOrderDTO tradeOrderDTO,
			Integer oldStatus) {
		TradeOrder tradeOrder = BeanConvertUtil.convert(TradeOrder.class,
				tradeOrderDTO);
		tradeOrder.setOldStatus(oldStatus);
		return tradeOrderDAO.update(tradeOrder);
	}

	@Override
	public boolean updateTradeOrderRnTx(TradeOrderDTO tradeOrderDTO) {
		TradeOrder tradeOrder = BeanConvertUtil.convert(TradeOrder.class,
				tradeOrderDTO);
		return tradeOrderDAO.update(tradeOrder);
	}

	@Override
	public boolean updateForTradeOrderLock(Long tradeOrderNo) {
		return tradeOrderDAO.updateForTradeOrderLock(tradeOrderNo);
	}

	@Override
	public boolean updateForTradeOrderUNLock(Long tradeOrderNo) {
		return tradeOrderDAO.updateForTradeOrderUNLock(tradeOrderNo);
	}

	@Override
	public boolean updateAddRefundAmount(Long tradeOrderNo,
			String refundAmount, Integer status) {
		return tradeOrderDAO.updateAddRefundAmount(tradeOrderNo, refundAmount,
				status);
	}

	@Override
	public boolean updateSubRefundAmount(Long tradeOrderNo,
			String refundAmount, Integer status) {
		return tradeOrderDAO.updateSubRefundAmount(tradeOrderNo, refundAmount,
				status);
	}

	@Override
	public TradeOrder findByOrderInfo(Map<String, Object> map) {
		TradeOrder tradeOrder = (TradeOrder) tradeOrderDAO
				.findObjectByCriteria("findByOrderInfo", map);
		return tradeOrder;
	}

	@Override
	public List<TradeOrderDTO> queryTradeOrder(Long partnerId,
			String beginTime, String endTime) {
		Map paraMap = new HashMap();
		paraMap.put("partnerId", partnerId);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);

		List<TradeOrder> tradeOrders = tradeOrderDAO.findByCriteria(paraMap);
		return (List<TradeOrderDTO>) BeanConvertUtil.convert(
				TradeOrderDTO.class, tradeOrders);
	}

	@Override
	public List<TradeOrderDTO> downloadTradeOrder(TradeOrderDTO tradeOrderDTO) {
		TradeOrder tradeOrder = BeanConvertUtil.convert(TradeOrder.class,
				tradeOrderDTO);
		List<TradeOrder> tradeOrders = tradeOrderDAO.findByCriteria(
				"downloadTradeOrder", tradeOrder);

		return (List<TradeOrderDTO>) BeanConvertUtil.convert(
				TradeOrderDTO.class, tradeOrders);

	}

	@Override
	public List<TradeOrder> findByOrderListInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<TradeOrder> tradeOrderList = tradeOrderDAO.findByCriteria(
				"findByOrderListInfo", map);
		return tradeOrderList;
	}

	@Override
	public boolean updateRefundAmount(Long tradeOrderNo, Long refundAmount,
			Long canRefundAmount, int operate) {
		String status = null;
		if (operate == 1) {
			status = "4";
			return tradeOrderDAO.updateAddRefundAmount(tradeOrderNo,
					refundAmount.toString(), status);
		} else if (operate == 0) {
			if (canRefundAmount - refundAmount == 0)
				status = "3";
			return tradeOrderDAO.updateSubRefundAmount(tradeOrderNo,
					refundAmount.toString(), status);
		}
		return false;
	}

	@Override
	public boolean updateRefundAmount(Long tradeOrderNo, Long refundAmount,
			String status) {
		return tradeOrderDAO.updateAddRefundAmount(tradeOrderNo,
				refundAmount.toString(), status);
	}

	@Override
	public boolean updateRefundAmount(Long tradeOrderNo, Long refundAmount) {
		return tradeOrderDAO.updateAddRefundAmount(tradeOrderNo,
				refundAmount.toString());
	}

	@Override
	public boolean updateRefundAmountRnTx(Long tradeOrderNo, Long refundAmount,
			String status) {
		return tradeOrderDAO.updateAddRefundAmount(tradeOrderNo,
				refundAmount.toString(), status);
	}
	
	/***
	 * 获取指定时间以后，最大的网关订单号，在邮件通知中要用到
	 * @author Davis.guo at 2016-09-03
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public long maxTradeOrderNo(String startDate, String endDate) {
		Map paraMap = new HashMap();
		paraMap.put("startDate", startDate);
		//paraMap.put("endDate", endDate);
		return tradeOrderDAO.maxByCriteria("getMaxTradeOrderNo", paraMap);
	}

	@Override
	public int countTradeOrders(Long partnerId, String startDate, String endDate) {

		Map paraMap = new HashMap();
		paraMap.put("partnerId", partnerId);
		paraMap.put("startDate", startDate);
		paraMap.put("endDate", endDate);
		return tradeOrderDAO.countByCriteria("countByMonth", paraMap);
	}
	
	/**
	 * 条件查询trderOrder的总金额
	 */
	@Override
	public String queryTradeOrderSumAmount(TradeOrderDTO tradeOrder) {
		Object sumAmount = tradeOrderDAO.findObjectByCriteria("queryTradeOrderSumAmount", tradeOrder);
		return  String.valueOf(sumAmount);
	}
	
	@Override
	public List<PayLinkDTO> queryPayLink(Map<String, Object> map, Page page) {
		return tradeOrderDAO.findByCriteria("queryPayLink", map, page);
	}
	
	@Override
	public List<PayLinkDTO> queryPayLink(Map<String, Object> map) {
		return tradeOrderDAO.findByCriteria("queryPayLink", map);
	}
	
	@Override
	public PayLinkDetailDTO queryPayLinkDetail(Map<String, String> data) {
		List<PayLinkDetailDTO> list = tradeOrderDAO.getPayLinkDetail(data);
		return list!=null && !list.isEmpty()?list.get(0):null;
	}
	
	@Override
	public List<PayLinkTemplateDTO> payLinkTemplateDownload(
			Map<String, String> prameters) {
		return tradeOrderDAO.payLinkTemplateDownload(prameters);
	}

	@Override
	public List<AutoTradeOrderDTO> queryAutoTradeOrder(
			AutoTradeOrderDTO tradeOrder,Page page) {
		return tradeOrderDAO.queryAutoTradeOrder(tradeOrder,page);
	}

	@Override
	public void createAutoTranRela(AutoTranRela autoTranRela) {
		tradeOrderDAO.createAutoTranRela(autoTranRela);
	}

}
