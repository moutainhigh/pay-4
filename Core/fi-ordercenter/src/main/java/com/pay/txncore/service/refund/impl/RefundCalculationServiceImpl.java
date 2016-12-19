package com.pay.txncore.service.refund.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.txncore.commons.RefundTypeEnum;
import com.pay.txncore.service.refund.RefundCalculationService;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.util.StringFormatUtil;

public class RefundCalculationServiceImpl implements RefundCalculationService {

	private RefundOrderService refundOrderService;

	private final Log logger = LogFactory
			.getLog(RefundCalculationServiceImpl.class);

	/**
	 * 计算出网关订单商户可退金额 支付金额 - 支付手续费 - 已经退款金额 - 系统退手续费
	 * 
	 * @param paymentAmount
	 * @param payeeFee
	 * @param tradeOrderNo
	 * @return
	 */
	@Override
	public BigDecimal calcPartnerCanRefundAmount(Long paymentAmount,
			Long payeeFee, Long paymentOrderNo) {
		BigDecimal bPaymentAmount = new BigDecimal(paymentAmount);
		BigDecimal bPayeeFee = new BigDecimal(payeeFee);
		Long refundAmount = 0L;
		BigDecimal bRefundAmount = null;

		refundAmount = refundOrderService
				.queryPartnerRefundAmount(paymentOrderNo);
		bRefundAmount = new BigDecimal(refundAmount);

		return bPaymentAmount.subtract(bPayeeFee).subtract(bRefundAmount);
	}

	/**
	 * 根据退款金额计算本次需要的手续费用
	 * 
	 * @param refundAmount
	 * @param TradeOrderNo
	 * @return
	 * @throws Exception
	 */
	@Override
	public Long calcRefundPayeeFee(Long refundAmount, Long paymentAmount,
			Long payeeFee, String refundType, Long orderCanRefundAmount,
			Long paymentNo) throws Exception {
		// 计算规则 : (退款金额/订单金额) * 手续费=（33.5/100）*1=0.335元

		// 计算可退金额的时候，如果可退金额大于退款金额，算法依旧，
		// 如果可退金额 小于 退款金额 退款失败。
		// 如果相等,并且 计算手续费的动作是用共付的手续费减去已经付的。

		BigDecimal bRefundAmount = new BigDecimal(refundAmount);
		BigDecimal bPayeeFee = new BigDecimal(payeeFee);
		BigDecimal bOrderRefundAmount = new BigDecimal(orderCanRefundAmount);
		BigDecimal bPaymentAmount = new BigDecimal(paymentAmount);
		BigDecimal bRate = new BigDecimal(10);

		// 商户总共可退金额
		BigDecimal bPartnerCanRefundAmount = calcPartnerCanRefundAmount(
				paymentAmount, payeeFee, paymentNo);

		// 加入一套防止商户退费超出已收费用分支。
		// -1 小于 0 等于 1 大于
		// 如果商户可退金额大于退款金额 或者 商户退款金额为负数时[特殊情况]
		if (bPartnerCanRefundAmount.compareTo(bRefundAmount) >= 0
				|| bPartnerCanRefundAmount.longValue() < 0L) {
			if (bOrderRefundAmount.compareTo(bRefundAmount) == 0
					|| RefundTypeEnum.FULL_REFUND.getStringCode().equals(
							refundType)) {
				return this.calcPayeeFeeByPaymnetOrderNo(paymentNo, payeeFee);
			}

			if (RefundTypeEnum.RATE_REFUND.getStringCode().equals(refundType)
					|| RefundTypeEnum.PART_REFUND.getStringCode().equals(
							refundType)) {
				// [退款金额 /支付金额 ]=比例值 * 手续费
				return bRefundAmount
						.divide(bPaymentAmount, 2, BigDecimal.ROUND_DOWN)
						.multiply(bPayeeFee)
						.divide(bRate, 0, BigDecimal.ROUND_DOWN)
						.multiply(bRate).longValue();
			}
		} else if (bPartnerCanRefundAmount.compareTo(bRefundAmount) == -1) {
			return bRefundAmount.subtract(bPartnerCanRefundAmount).longValue();
		}
		logger.error(StringFormatUtil.format(
				"退款-收款方手续费用计算异常,REFUNDTYPE:{0},PAYMENTNO:{1}", refundType,
				paymentNo));
		throw new Exception("退款-收款方手续费用计算异常");
	}

	@Override
	public Long calcRefundPayerFee(Long refundAmount, Long paymentAmount,
			Long payerFee, String refundType, Long orderCanRefundAmount,
			Long paymentNo) throws Exception {
		// 计算规则 : (退款金额/订单金额) * 手续费=（33.5/100）*1=0.335元

		// 计算可退金额的时候，如果可退金额大于退款金额，算法依旧，
		// 如果可退金额 小于 退款金额 退款失败。
		// 如果相等,并且 计算手续费的动作是用共付的手续费减去已经付的。

		BigDecimal bRefundAmount = new BigDecimal(refundAmount);
		BigDecimal bPayerFee = new BigDecimal(payerFee);
		BigDecimal bOrderRefundAmount = new BigDecimal(orderCanRefundAmount);
		BigDecimal bPaymentAmount = new BigDecimal(paymentAmount);
		BigDecimal bRate = new BigDecimal(10);

		// 全部退完或者可退金额=退款金额时，退还所有付款方手续费
		if (bOrderRefundAmount.compareTo(bRefundAmount) == 0
				|| RefundTypeEnum.FULL_REFUND.getStringCode()
						.equals(refundType)) {
			return this.calcPayerFeeByPaymnetOrderNo(paymentNo, payerFee);
		}

		if (RefundTypeEnum.RATE_REFUND.getStringCode().equals(refundType)
				|| RefundTypeEnum.PART_REFUND.getStringCode()
						.equals(refundType)) {
			// [退款金额 /支付金额 ]=比例值 * 手续费
			return bRefundAmount
					.divide(bPaymentAmount, 2, BigDecimal.ROUND_DOWN)
					.multiply(bPayerFee)
					.divide(bRate, 0, BigDecimal.ROUND_DOWN).multiply(bRate)
					.longValue();
		}
		logger.error(StringFormatUtil.format(
				"退款-付款方手续费用计算异常,REFUNDTYPE:{0},PAYMENTNO:{1}", refundType,
				paymentNo));
		throw new Exception("退款-付款方手续费用计算异常");
	}

	@Override
	public Long calcRefundAmount(String refundType, Long refundAmount,
			Long paymnetAmount) throws Exception {
		BigDecimal bPaymentAmount = new BigDecimal(paymnetAmount);
		BigDecimal bRefundAmount = new BigDecimal(refundAmount);
		BigDecimal rate = new BigDecimal(100000);

		if (RefundTypeEnum.RATE_REFUND.getStringCode().equals(refundType)) {
			// 付金支额 * 退款比例 / 10000[比例位数]
			return bPaymentAmount.multiply(bRefundAmount)
					.divide(rate, 0, BigDecimal.ROUND_DOWN)
					.multiply(new BigDecimal(10)).longValue();
		} else if (RefundTypeEnum.PART_REFUND.getStringCode()
				.equals(refundType)) {
			// *10到厘处理
			return bRefundAmount.multiply(new BigDecimal(10)).longValue();
		} else if (RefundTypeEnum.FULL_REFUND.getStringCode()
				.equals(refundType)) {
			// *10到厘处理
			return bRefundAmount.multiply(new BigDecimal(10)).longValue();
		}
		throw new Exception("@Refund:计算退款金额异常.");
	}

	@Override
	public Long calcPayeeFeeByPaymnetOrderNo(Long paymentOrderNo, Long payeeFee) {
		Map<String, Long> mapFee = new HashMap<String, Long>();
		mapFee = refundOrderService
				.getRefundFeeByPaymentOrderNo(paymentOrderNo);
		if (mapFee == null)
			return null;
		BigDecimal bPayeeFee = new BigDecimal(payeeFee);
		BigDecimal bRefundPayeeFee = new BigDecimal(mapFee.get("TotalPayeeFee"));
		return bPayeeFee.subtract(bRefundPayeeFee).longValue();
	}

	@Override
	public Long calcPayerFeeByPaymnetOrderNo(Long paymentOrderNo, Long payerFee) {
		Map<String, Long> mapFee = new HashMap<String, Long>();
		mapFee = refundOrderService
				.getRefundFeeByPaymentOrderNo(paymentOrderNo);
		if (mapFee == null)
			return null;
		BigDecimal bPayerFee = new BigDecimal(payerFee);
		BigDecimal bRefundPayerFee = new BigDecimal(mapFee.get("TotalPayerFee"));
		return bPayerFee.subtract(bRefundPayerFee).longValue();
	}

	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}

	@Override
	public Long calcRetiredRefundFeeByTradeOrderNo(Long tradeOrderNo) {
		// TODO Auto-generated method stub
		return null;
	}
}
