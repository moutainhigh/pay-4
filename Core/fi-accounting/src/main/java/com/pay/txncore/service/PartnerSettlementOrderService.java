/**
 * 
 */
package com.pay.txncore.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.CreditFilterOrder;
import com.pay.txncore.model.PartnerSettlementOrder;

/**
 * @author chaoyue
 *
 */
public interface PartnerSettlementOrderService {

	/**
	 * 保存清算订单
	 * 
	 * @param order
	 * @return
	 */
	Long createPartnerSettlementOrder(PartnerSettlementOrder order);

	void createPartnerSettlementOrder(List<PartnerSettlementOrder> orders);

	/**
	 * 
	 * @param order
	 * @return
	 */
	boolean updatePartnerSettlementOrder(PartnerSettlementOrder order);

	public Map<String, Object> countSettlementOrderListSize(
			PartnerSettlementOrder partnerSettlementOrder);

	/**
	 * 查询清算订单
	 * 
	 * @param settlementFlg
	 * @param maxSize
	 * @return
	 */
	List<PartnerSettlementOrder> queryUnSettlementPartnerSettlementOrder(
			Date settlementDate, Integer maxSize,Integer settlementFlg);
	

	/**
	 * 统计一个商户的各个币种的清算情况 包括已清算和未清算
	 * 
	 * @param order
	 * @return
	 */
	List<PartnerSettlementOrder> querySettlementAmountCount(
			PartnerSettlementOrder order);

	/**
	 * 查询商户订单
	 * 
	 * @param partnerId
	 * @param settlementFlg
	 * @return
	 */
	List<PartnerSettlementOrder> queryUnSettlementPartnerSettlementOrder(
			Long partnerId, Integer settlementFlg);

	/**
	 * 查询未退还保证金
	 * 
	 * @param partnerId
	 * @param settlementFlg
	 * @return
	 */
	List<PartnerSettlementOrder> queryUnSettlementAssurePartnerSettlementOrder(
			Integer maxSize,Integer assureSettlementFlg);

	/**
	 * 获取未结算订单
	 * 
	 * @param partnerId
	 * @param settlementFlg
	 * @return
	 */
	List<PartnerSettlementOrder> queryPartnerSettlementOrder(
			PartnerSettlementOrder order, Page page);
	
	List<PartnerSettlementOrder> queryPartnerSettlementOrders(
			PartnerSettlementOrder order);

	/**
	 * 是否首次结算
	 * 
	 * @param partnerId
	 * @param paymentOrderNo
	 * @return
	 */
	boolean isFirstSettlementOrder(Long partnerId, Long paymentOrderNo);

	/**
	 * 
	 * @param partnerId
	 * @param paymentOrderNo
	 * @return
	 */
	List<PartnerSettlementOrder> querySettlementOrder(Long partnerId,
			Long paymentOrderNo);

	PartnerSettlementOrder querySettlementOrderByTradeOrderNo(Long partnerId,
			Long tradeOrderNo);
	
	public List<PartnerSettlementOrder> queryPartnerSettlementOrder(
			PartnerSettlementOrder order);
	
	PartnerSettlementOrder querySettlementOrderByOrderId(Long partnerId,
			String orderId);
	
	List<Map> querySettlementOrder(
			Long partnerId, String startTime,String endTime);
	List<PartnerSettlementOrder> querySettlementOrderForCredit(
			Map paraMap);
	List<PartnerSettlementOrder> querySettlementOrderForTotal(
			Map paraMap);
	List<CreditFilterOrder> queryChargeBackForCredit(String beginDate,String endDate,String creditFlag);
	
	/**
	 * 查询可授信订单
	 * 
	 * @param settlementFlg
	 * @param maxSize
	 * @return
	 */
	List<PartnerSettlementOrder> queryOrderCreditSettlementOrder(
			PartnerSettlementOrder order);
	/**
	 * 查询可授信订单
	 * 
	 * @param settlementFlg
	 * @param maxSize
	 * @return
	 */
	List<PartnerSettlementOrder> queryOrderCreditSettlementOrder(
			PartnerSettlementOrder order,Page page);
}
