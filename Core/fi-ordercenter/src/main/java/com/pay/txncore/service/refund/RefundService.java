/**
 * 
 */
package com.pay.txncore.service.refund;

import com.pay.txncore.dto.ChannelPaymentResult;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceResultDTO;

/**
 * @author chaoyue
 *
 */
public interface RefundService {

	/**
	 * 
	 * @param paramDTO
	 * @return
	 */
	RefundTransactionServiceResultDTO refundRdTx(
			RefundTransactionServiceParamDTO paramDTO) throws Exception;

	/**
	 * 退款结果通知
	 * 
	 * @param refundOrderNo
	 * @param responseCode
	 */
	void refundHandle(String refundOrderNo, ChannelPaymentResult refundResult)
			throws Exception;

	RefundOrderDTO queryRefundOrderDTO(String parnterID,
			String partnerRefundOrderId);

	/**
	 * 农行退款销账，由对账时触发
	 * 
	 * @param orgCode
	 * @param refundOrderNo
	 * @param refundAmount
	 * @param currencyCode
	 * @param settlementCurrencyCode
	 * @param settlementRate
	 * @throws Exception
	 */
	public void doAbcAccounting(String orgCode, Long refundOrderNo,
			Long refundAmount, String currencyCode,
			String settlementCurrencyCode, String settlementRate,String merchantOrderId)
			throws Exception;
	
	public void doAccounting_500_508(String orgCode, String orderId,
			Long amount, String currencyCode, String rate,String merchantOrderId) throws Exception;
	
	public void doAccounting_500_509(String orgCode, String orderId,
			Long amount, String currencyCode,String merchantOrderId) throws Exception;
	
	public void doAccounting_500_510(String orgCode, String orderId,
			Long amount, String currencyCode, String transferRate,String merchantOrderId)
			throws Exception;
	
	public void doAccounting_500_512(String orderId, Long amount,
			String currencyCode,String partnerSettlementRate,String merchantOrderId) throws Exception;
	
	public void doAccounting_500_513(String orderId, Long amount,
			String currencyCode,String merchantOrderId) throws Exception;
	
	public int doAccounting_500_514(Long partnerId, String orderId,Long orderAmount,Long settlementAmount,Long assureAmount,
			Long refundAmount, String settlementCurrencyCode,
			String rate, Long fee,String merchantOrderId) throws Exception;
	
	public int doAccounting_500_515(Long partnerId, String orderId,
			Long amount, String currencyCode, String rate,String merchantOrderId) throws Exception;
}
