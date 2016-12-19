package com.pay.txncore.service.refund;

import java.math.BigDecimal;

public interface RefundCalculationService {

	/**
	 * 计算出网关订单商户可退金额 支付金额 - 支付手续费 - 已经退款金额 - 系统退手续费
	 * 
	 * @param paymentAmount
	 * @param payeeFee
	 * @param tradeOrderNo
	 * @return
	 */
	public BigDecimal calcPartnerCanRefundAmount(Long paymentAmount,
			Long payeeFee, Long paymentOrderNo);

	/**
	 * 获取收款方可退手续费
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	public Long calcPayeeFeeByPaymnetOrderNo(Long paymentOrderNo, Long payeeFee);

	/**
	 * 获取付款方可退手续费
	 * 
	 * @param paymentOrderNo
	 * @param payerFee
	 * @return
	 */
	public Long calcPayerFeeByPaymnetOrderNo(Long paymentOrderNo, Long payerFee);

	/**
	 * 根据退款类型计算本次需要退款的金额
	 * 
	 * @param refundType
	 * @param refundAmoun
	 * @return
	 * @throws Exception
	 */
	public Long calcRefundAmount(String refundType, Long refundAmoun,
			Long paymnetAmount) throws Exception;

	/**
	 * 计算网关已经退的手续费用
	 * 
	 * @return
	 */
	public Long calcRetiredRefundFeeByTradeOrderNo(Long tradeOrderNo);

	/**
	 * 根据退款金额计算本次需要的手续费用
	 * 
	 * @param refundAmount
	 * @param TradeOrderNo
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public Long calcRefundPayeeFee(Long refundAmount, Long paymentAmount,
			Long payeeFee, String refundType, Long orderCanRefundAmount,
			Long paymentNo) throws Exception;

	public Long calcRefundPayerFee(Long refundAmount, Long paymentAmount,
			Long payerFee, String refundType, Long orderCanRefundAmount,
			Long paymentNo) throws Exception;
}
