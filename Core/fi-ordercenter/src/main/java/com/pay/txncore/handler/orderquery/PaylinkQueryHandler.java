package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PayLinkDTO;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class PaylinkQueryHandler implements EventHandler{
	
	public final Log logger = LogFactory
			.getLog(PaylinkQueryHandler.class);
	
	private TradeOrderService tradeOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		Map resultMap = new HashMap();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map pageMap = (Map) paraMap.get("page");
			if(pageMap == null){
				List<PayLinkDTO> payLinks = tradeOrderService.queryPayLink(paraMap);
				resultMap.put("result", payLinks);
			}else{
				Page page = MapUtil.map2Object(Page.class, pageMap);
				List<PayLinkDTO> payLinks = tradeOrderService.queryPayLink(paraMap, page);
				resultMap.put("result", payLinks);
				resultMap.put("page", page);				
			}
		} catch (Exception e) {
			logger.error("PaylinkQueryHandler error:", e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

}
