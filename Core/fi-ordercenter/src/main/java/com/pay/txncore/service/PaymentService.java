/**
 * 
 */
package com.pay.txncore.service;

import java.util.List;

import com.pay.rm.service.model.RiskOrder;
import com.pay.txncore.dto.ChannelPaymentResult;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.model.PartnerSettlementOrder;

/**
 * 收单、支付服务
 * 
 * @author chaoyue
 *
 */
public interface PaymentService {

	/**
	 * 收银台收单
	 * 
	 * @param paymentInfo
	 * @return
	 */
	PaymentResult crossCashierAcquire(PaymentInfo paymentInfo);

	/**
	 * 收银台支付
	 * 
	 * @param paymentInfo
	 * @return
	 */
	PaymentResult crossCashierPay(PaymentInfo paymentInfo) throws Exception;

	/**
	 * 跨境API支付
	 * 
	 * @param paymentInfo
	 * @return
	 */
	PaymentResult crossApiPay(PaymentInfo paymentInfo);

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
	
	List<PartnerSettlementOrder> buildSettlementOrder(
			Long paymentOrderNo, TradeBaseDTO tradeBaseDTO,
			TradeOrderDTO tradeOrder, String settlementCurrencyCode,String cardOrg);
	
	List<RiskOrder> findRiskOrderList(RiskOrder riskOrder);
	
	
	/**
	 * 支付回调
	 * @return
	 */
	PaymentResult paymentCallback(ChannelPaymentResult channelPaymentResult);
}
