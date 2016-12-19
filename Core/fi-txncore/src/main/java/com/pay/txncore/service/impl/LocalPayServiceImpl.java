package com.pay.txncore.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fi.commons.PaymentWayEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.txncore.commons.OrderBuilder;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.service.LocalPayService;
import com.pay.txncore.service.PaymentService;


/**
 * 本地化支付
 * @author peiyu.yang
 * @since 2016年6月30日22:48:06
 */
public class LocalPayServiceImpl implements LocalPayService {
	private static Logger logger = LoggerFactory.getLogger(LocalPayServiceImpl.class);
	private PaymentService paymentService;

	@Override
	public PaymentResult crossLocaleAcquire(PaymentInfo paymentInfo) {
		PaymentResult paymentResult = new PaymentResult();
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", paymentInfo.getOrderId());
		params.put("memberCode", paymentInfo.getPartnerId());
		params.put("status", "1,2,3,4,20");

		TradeOrderDTO tradeOrderDTO = paymentService.getTradeOrderService()
				.queryCompletedTradeOrder(params);
		// 检查商户订单号对应的是否有已成功交易的。
		if (null != tradeOrderDTO) {
			throw new BusinessException("tradeOrder was exists",
					ExceptionCodeEnum.ORDER_IS_COMPLETED_OR_SUCCESS);
		}
		// 设置清算币种
		//TODO
		paymentService.setSettlementCurrencyCode(paymentInfo);
		
		// first setup save traderOrder
		TradeBaseDTO tradeBaseDTO = OrderBuilder.buildTradeBase(paymentInfo);
		tradeOrderDTO = OrderBuilder.buildTradeOrder(paymentInfo);

		Long tradeOrderNo = paymentService.getTradeOrderService().saveTradeOrderRnTx(tradeBaseDTO,
				tradeOrderDTO);
		tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
		paymentInfo.setTradeOrderNo(tradeOrderNo);//支付信息中添加交易订单号 by tom 2016年7月8日17:40:47

		// 保存额外信息
		paymentService.saveExtendsInfo(paymentInfo, tradeOrderDTO);
        
		//风控校验
		paymentService.riskValidate(paymentInfo, tradeOrderDTO, paymentResult);
        String desc = paymentResult.getRiskDesc();
        if(!"ACCEPT".equals(desc)){
        	return paymentResult;
        }
		
		try {
			paymentResult = paymentService.payByLocaleChannels(paymentInfo, tradeOrderDTO,
					tradeBaseDTO, PaymentWayEnum.API.getCode());
		} catch (Exception e) {
			logger.error("payment error:", e);
			paymentResult.setResponseCode("3099");
			paymentResult.setResponseDesc("Other Error:其他异常");

			tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
			tradeOrderDTO.setRespCode("3099");
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setRespMsg("Other Error:其他异常");
			paymentService.getTradeOrderService().updateTradeOrderRnTx(tradeOrderDTO);
			return paymentResult;
		}

		return paymentResult;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
}
