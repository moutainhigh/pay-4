package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PayLinkDetailDTO;
import com.pay.txncore.service.TradeOrderDetailService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class PayLinkDetailQueryHandler implements EventHandler{
	
	public final Log logger = LogFactory.getLog(PayLinkDetailQueryHandler.class);
	
	private TradeOrderDetailService tradeOrderDetailService;
	
	public void setTradeOrderDetailService(
			TradeOrderDetailService tradeOrderDetailService) {
		this.tradeOrderDetailService = tradeOrderDetailService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		Map resultMap = new HashMap();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			PayLinkDetailDTO payLinkDetail = tradeOrderDetailService.getPayLinkDetail(paraMap);
			resultMap = MapUtil.bean2map(payLinkDetail);
		} catch (Exception e) {
			logger.error("PayLinkDetailQueryHandler error:", e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

}
