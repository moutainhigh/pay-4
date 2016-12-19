/**
 * 
 */
package com.pay.txncore.service;

import java.math.BigDecimal;
import java.util.List;

import com.pay.rm.service.model.RiskOrder;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.model.RefundFeeOrder;

/**
 * 收单、支付服务
 * 
 * @author chaoyue
 *
 */
public interface PaymentService {

	/**
	 * 商户结算
	 * 
	 * @param maxSize
	 */
	void liquidationRnTx(PartnerSettlementOrder partnerSettlementOrder);
	
	/**
	 * 统计风控手续费
	 * 
	 * @param maxSize
	 */
	int countRiskRnTx(RiskOrder riskOrder);

	/**
	 * 商户结算保证金
	 * 
	 * @param maxSize
	 */
	void liquidationAssureRnTx(PartnerSettlementOrder partnerSettlementOrder);
	
	
	List<RiskOrder> findRiskOrderList(RiskOrder riskOrder);
	
	/*
	 * 获取退款手续费订单
	 */
	List<RefundFeeOrder> findRefundFeeOrderList(RefundFeeOrder refundFeeOrder);
	
	/*
	 * 结算退款手续费
	 */
	int countRefundFeeOrderRnTx(RefundFeeOrder refundFeeOrder);
	
	void BuildOrderCredit(PartnerSettlementOrder partnerSettlementOrder,
			String settlementCurrencyCode,String dayRate,Boolean flag);
	/**
	 * msp端首页查询
	 * @param partnerSettlementOrder
	 * @param settlementCurrencyCode
	 * @param dayRate
	 * @param flag
	 * 2016年8月1日   mmzhang     add
	 */
	void BuildOrderCredit2(PartnerSettlementOrder partnerSettlementOrder,
			String settlementCurrencyCode,String dayRate,Boolean flag); 
	void settlementRnTx(PartnerSettlementOrder partnerSettlementOrder,
			String settlementCurrencyCode);
}
