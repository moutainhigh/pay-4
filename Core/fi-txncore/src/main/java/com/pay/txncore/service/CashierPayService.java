package com.pay.txncore.service;

import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeExtendsDTO;
import com.pay.txncore.dto.TradeOrderDTO;

public interface CashierPayService {
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
	
	
	/**收银台支付-创建Token并支付，支付成功则会绑卡
	 * @param paymentInfo
	 * @param tradeOrderDTO
	 * @param tradeBaseDTO
	 * @return
	 * @throws Exception
	 */
	PaymentResult crossCashierTokenBindCardPay(PaymentInfo paymentInfo,TradeOrderDTO tradeOrderDTO,TradeBaseDTO tradeBaseDTO,TradeExtendsDTO tradeExtendsDTO) throws Exception;
}
