package com.pay.txncore.service.refund;

import java.math.BigDecimal;

import com.pay.txncore.dto.AccountInfoValidateDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;

public interface RefundHelperService {

	/**
	 * 根据商户原始订单
	 * 
	 * @param orderid
	 * @param partnerId
	 * @return
	 */
	public RefundTransactionServiceParamDTO constractRefundParam(
			String orderId, String partner, String remark, String refundAmount,
			String refundType);

	/**
	 * @author Fred
	 * @param orderId
	 * @param partner
	 * @param remark
	 * @param refundAmount
	 * @param refundType
	 * @return
	 */
	public RefundTransactionServiceParamDTO constractSplitRefundParam(
			String orderId, String partner, String remark, String refundAmount,
			String refundType);

	/**
	 * 构建订单
	 * 
	 * @param orderId
	 * @param detailId
	 * @param partner
	 * @param remark
	 * @param refundAmount
	 * @param refundType
	 * @param refundDest
	 * @return
	 */
	public RefundTransactionServiceParamDTO constractRefundParam(
			String orderId, String detailId, String partner, String remark,
			String refundAmount, String refundType, String refundDest);

	/**
	 * 查询商户可用余额
	 * 
	 * @param memberCode
	 * @return
	 */
	public BigDecimal queryBalanceByMemberCode(String memberCode);

	/**
	 * 商户支付密码等信息验证
	 * 
	 * @param accountInfo
	 * @return
	 */
	public boolean validateAccountInfo(AccountInfoValidateDTO accountInfo);

}
