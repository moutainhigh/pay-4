/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.service.PaymentOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 
 * @author chma
 */
public class PaymentOrderQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(PaymentOrderQueryHandler.class);
	private PaymentOrderService paymentOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			PaymentOrderDTO paymentOrderDTO = MapUtil.map2Object(
					PaymentOrderDTO.class, paraMap);
			Map pageMap = (Map) paraMap.get("page");
			Page page = MapUtil.map2Object(Page.class, pageMap);
			List<PaymentOrderDTO> paymentOrders = paymentOrderService
					.queryPaymentOrder(paymentOrderDTO, page);
			resultMap.put("result", paymentOrders);
			resultMap.put("page", page);
		} catch (Exception e) {
			logger.error("query error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setPaymentOrderService(PaymentOrderService paymentOrderService) {
		this.paymentOrderService = paymentOrderService;
	}

}
