package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 条件查询网关订单的总金额
 * @author delin.dong
 * @date 2016年6月3日13:47:54
 */
public class TradeOrderSumAmountHandler implements EventHandler{
	public final Log logger = LogFactory.getLog(TradeOrderSumAmountHandler.class);
	private TradeOrderService tradeOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			TradeOrderDTO tradeOrder = MapUtil.map2Object(TradeOrderDTO.class,
					paraMap);
			
			String sumAmount= tradeOrderService.queryTradeOrderSumAmount(tradeOrder);
			 double valueOf = Double.valueOf(sumAmount); //金额精确到元
			resultMap.put("result",(int)valueOf);
		} catch (Exception e) {
			logger.error("api pay error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}
}
