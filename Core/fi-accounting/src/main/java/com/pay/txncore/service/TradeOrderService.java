package com.pay.txncore.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dto.AutoTradeOrderDTO;
import com.pay.txncore.dto.PayLinkDTO;
import com.pay.txncore.dto.PayLinkDetailDTO;
import com.pay.txncore.dto.PayLinkTemplateDTO;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.model.TradeOrder;

public interface TradeOrderService {

	int ADD = 1;
	int SUB = 0;

	/**
	 * 
	 * @param tradeOrderDTO
	 * @return
	 */
	Long saveTradeOrderRnTx(TradeBaseDTO tradeBase, TradeOrderDTO tradeOrderDTO);

	/**
	 * 
	 * @param tradeOrderDTO
	 * @return
	 */
	boolean updateTradeOrderRnTx(TradeOrderDTO tradeOrderDTO, Integer oldStatus);

	/**
	 * 
	 * @param tradeOrderDTO
	 * @return
	 */
	boolean updateTradeOrderRnTx(TradeOrderDTO tradeOrderDTO);

	/**
	 * 更新网关订单状态
	 * 
	 * @param tradeOrderNo
	 * @param oldStatus
	 * @param status
	 * @return
	 */
	boolean updateStatus(Long tradeOrderNo, Integer oldStatus, Integer status);

	/**
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	boolean lockedOrderForUpdate(Long tradeOrderNo);

	/**
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	TradeOrderDTO queryTradeOrderById(Long tradeOrderNo);

	/**
	 * 
	 * @param tradeOrder
	 * @return
	 */
	List<TradeOrderDTO> queryTradeOrder(TradeOrderDTO tradeOrder, Page page);

	List<TradeOrderDTO> queryTradeOrder(TradeOrderDTO tradeOrder);
	/**
	 * 
	 * @param tradeBaseNo
	 * @return
	 */
	TradeBaseDTO queryTradeBaseById(Long tradeBaseNo);

	/**
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	TradeOrderDTO queryTradeOrder(Long partnerId, String orderId);

	/**
	 * 
	 * @param partnerId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<TradeOrderDTO> queryTradeOrder(Long partnerId, String beginTime,
			String endTime);

	/**
	 * 更新网关订单为已锁定
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	boolean updateForTradeOrderLock(Long tradeOrderNo);

	/**
	 * 解锁网关订单
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	boolean updateForTradeOrderUNLock(Long tradeOrderNo);

	/**
	 * 增加网关可退款金额
	 * 
	 * @param tradeOrderNo
	 * @param refundAmount
	 * @param status
	 * @return
	 */
	boolean updateAddRefundAmount(Long tradeOrderNo, String refundAmount,
			Integer status);

	/**
	 * 减少网关可退款金额
	 * 
	 * @param tradeOrderNo
	 * @param refundAmount
	 * @param status
	 * @return
	 */
	boolean updateSubRefundAmount(Long tradeOrderNo, String refundAmount,
			Integer status);

	public TradeOrder findByOrderInfo(Map<String, Object> map);

	public List<TradeOrder> findByOrderListInfo(Map<String, Object> map);

	public boolean updateRefundAmount(Long tradeOrderNo, Long refundAmount,
			Long canRefundAmount, int operate);

	/**
	 * 
	 * @param tradeOrderNo
	 * @param refundAmount
	 * @param canRefundAmount
	 * @return
	 */
	boolean updateRefundAmount(Long tradeOrderNo, Long refundAmount,
			String status);

	boolean updateRefundAmount(Long tradeOrderNo, Long refundAmount);

	/**
	 * 
	 * @param tradeOrderNo
	 * @param refundAmount
	 * @param canRefundAmount
	 * @return
	 */
	boolean updateRefundAmountRnTx(Long tradeOrderNo, Long refundAmount,
			String status);

	public TradeOrderDTO queryCompletedTradeOrder(Map<String, String> paraMap);

	/**
	 * 统计商户时间段内交易总笔数
	 * 
	 * @param partnerId
	 * @param startDate
	 *            : yyyy-MM-dd
	 * @return
	 */
	public int countTradeOrders(Long partnerId, String startDate, String endDate);
	
	TradeOrderDTO queryTradeOrderByPTO(Long partnerId,Long tradeOrderNo,String orderId);
	/**
	 * 查询自动下单的交易信息
	 * @param tradeOrder
	 * @return
	 */
	List<AutoTradeOrderDTO> queryAutoTradeOrder(AutoTradeOrderDTO tradeOrder,Page page);
	
	List<PayLinkDTO> queryPayLink(Map<String, Object> map,Page page);
	
	public List<PayLinkDTO> queryPayLink(Map<String, Object> map);
	
	public PayLinkDetailDTO queryPayLinkDetail(Map<String, String> data);
	
	public List<PayLinkTemplateDTO> payLinkTemplateDownload(
			Map<String, String> prameters);
}
