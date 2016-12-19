package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PayLinkTemplateDTO;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.JSonUtil;

public class PayLinkTemplateHandler implements EventHandler{

	private final Log logger = LogFactory.getLog(getClass());
	
	private TradeOrderService tradeOrderService;
	
	
	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		Map resultMap = new HashMap();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			List<PayLinkTemplateDTO> list = tradeOrderService.payLinkTemplateDownload(paraMap);
			resultMap.put("result", list);
		} catch (Exception e) {
			logger.error("PayLinkTemplateHandler error:", e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

}
