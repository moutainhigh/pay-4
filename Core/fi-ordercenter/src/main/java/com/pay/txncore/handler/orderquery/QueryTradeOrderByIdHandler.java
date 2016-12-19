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
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.JSonUtil;

/**
 * @author JiangboPeng
 * 
 */
public class QueryTradeOrderByIdHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(QueryTradeOrderByIdHandler.class);
	private TradeOrderService tradeOrderService;

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> paraMap = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap<String, Object>().getClass());
			/*TradeOrderDTO tradeOrder = MapUtil.map2Object(TradeOrderDTO.class,
					paraMap);*/
			Long tradeOrderNo = Long.valueOf((String) paraMap.get("tradeOrderNo")) ;
			TradeOrderDTO tradeOrder = tradeOrderService.queryTradeOrderById(tradeOrderNo);
			resultMap.put("result", tradeOrder);
			
		} catch (Exception e) {
			logger.error("api pay error:", e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}
}
