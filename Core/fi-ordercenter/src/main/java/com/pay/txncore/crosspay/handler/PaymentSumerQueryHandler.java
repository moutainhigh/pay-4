/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.crosspay.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.service.PaymentOrderService;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.JSonUtil;

/**
 * 跨境API收单
 * 
 * @author chma
 */
public class PaymentSumerQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private PaymentOrderService paymentOrderService;
	private TradeOrderService tradeOrderService;

	public void setPaymentOrderService(PaymentOrderService paymentOrderService) {
		this.paymentOrderService = paymentOrderService;
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());

			String tradeOrderNo = paraMap.get("tradeOrderNo");

			List<PaymentOrderDTO> paymentOrders = paymentOrderService
					.queryByTradeOrderNo(Long.valueOf(tradeOrderNo));

			long couponValue = 0;
			long paymentedAmount = 0;
			for (PaymentOrderDTO paymentOrder : paymentOrders) {
				paymentedAmount += paymentOrder.getPaymentAmount();
				if (null != paymentOrder.getCouponValue()) {
					couponValue += paymentOrder.getCouponValue();
				}
			}
			resultMap.put("paymentedAmount", paymentedAmount + "");
			resultMap.put("couponValue", couponValue + "");
			TradeOrderDTO tradeOrderDTO = tradeOrderService
					.queryTradeOrderById(Long.valueOf(tradeOrderNo));
			resultMap.put("orderAmount", tradeOrderDTO.getOrderAmount()
					.toString());

		} catch (BusinessException e) {
			logger.error("CrosspayCashierPayHandler error:", e);
			ExceptionCodeEnum error = e.getCode();
			resultMap.put("responseCode", error.getCode());
			resultMap.put("responseDesc", error.getDescription());
		} catch (Exception e) {
			logger.error("api pay error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}
}
