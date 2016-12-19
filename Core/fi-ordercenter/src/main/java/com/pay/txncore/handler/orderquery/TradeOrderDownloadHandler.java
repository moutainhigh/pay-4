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

/***
 * 下载网关订单数据
 * @date 2016年6月6日 16:07:53
 * @author delin.dong
 */
public class TradeOrderDownloadHandler implements EventHandler{
	
	public final Log logger = LogFactory.getLog(TradeOrderDownloadHandler.class);
	private TradeOrderService tradeOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			TradeOrderDTO tradeOrder = MapUtil.map2Object(TradeOrderDTO.class,
					paraMap);
			List<TradeOrderDTO> tradeOrders = tradeOrderService.downloadTradeOrder(tradeOrder);
			resultMap.put("result", tradeOrders);
		} catch (Exception e) {
			logger.error("api pay error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}
}
