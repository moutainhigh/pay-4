/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.crosspay.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.ChannelPaymentResult;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.service.PaymentService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 跨境API收单
 * 
 * @author chma
 */
public class PaymentCallbackHandler implements EventHandler {


	public final Log logger = LogFactory.getLog(getClass());
	private PaymentService paymentService;

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> paraMap = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
			ChannelPaymentResult channelPaymentResult = MapUtil.map2Object(ChannelPaymentResult.class,
					paraMap);
			PaymentResult paymentResult = paymentService.paymentCallback(channelPaymentResult);	
			
			resultMap.put("dataMap",paymentResult.getDataMap());
			resultMap.put("responseCode", paymentResult.getResponseCode());
			resultMap.put("responseDesc", paymentResult.getResponseDesc());
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
