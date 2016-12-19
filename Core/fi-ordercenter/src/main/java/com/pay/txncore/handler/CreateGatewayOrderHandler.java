/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.util.JSonUtil;

/**
 * 
 * @author chma
 */
public class CreateGatewayOrderHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(CreateGatewayOrderHandler.class);

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		PaymentOrderDTO paymentOrder = null;
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		} catch (Exception e) {
			logger.error("api pay error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}
}
