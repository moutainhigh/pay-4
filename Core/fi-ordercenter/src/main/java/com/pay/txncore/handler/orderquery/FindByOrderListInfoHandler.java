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

import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.TradeOrder;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.JSonUtil;

/**
 * 
 * @author chma
 */
public class FindByOrderListInfoHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(FindByOrderListInfoHandler.class);
	private TradeOrderService tradeOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			List<TradeOrder> tradeOrders = tradeOrderService
					.findByOrderListInfo(paraMap);
			resultMap.put("list", tradeOrders);
		} catch (Exception e) {
			logger.error("api pay error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}
}
