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
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 
 * @author chma
 */
public class TradeOrderQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(TradeOrderQueryHandler.class);
	private TradeOrderService tradeOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			TradeOrderDTO tradeOrder = MapUtil.map2Object(TradeOrderDTO.class,
					paraMap);
			
			Map pageMap = (Map) paraMap.get("page");
			if(pageMap == null){
				List<TradeOrderDTO> tradeOrders = tradeOrderService.queryTradeOrder(tradeOrder);
				resultMap.put("result", tradeOrders);
			}else{
				Page page = MapUtil.map2Object(Page.class, pageMap);
				List<TradeOrderDTO> tradeOrders = tradeOrderService
						.queryTradeOrder(tradeOrder, page);
				resultMap.put("result", tradeOrders);
				
				System.out.println("page: "+page);
				resultMap.put("page", page);				
			}
		} catch (Exception e) {
			logger.error("api pay error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}
}
