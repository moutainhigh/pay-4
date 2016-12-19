/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.TradeOrder;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 
 * @author chma
 */
public class FindByOrderInfoHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(FindByOrderInfoHandler.class);
	private TradeOrderService tradeOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			TradeOrder tradeOrder = tradeOrderService.findByOrderInfo(paraMap);
			resultMap = MapUtil.bean2map(tradeOrder);
		} catch (Exception e) {
			logger.error("api pay error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}
}
