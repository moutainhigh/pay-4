package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.TradeOrderDetailDTO;
import com.pay.txncore.service.TradeOrderDetailService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class TradeOrderDetailQueryHandler implements EventHandler{
	
	public final Log logger = LogFactory.getLog(TradeOrderDetailQueryHandler.class);
	
	private TradeOrderDetailService tradeOrderDetailService;
	
	
	public void setTradeOrderDetailService(
			TradeOrderDetailService tradeOrderDetailService) {
		this.tradeOrderDetailService = tradeOrderDetailService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		TradeOrderDetailDTO tradeOrderDetail = null;
		Map resultMap = new HashMap();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			logger.info("TradeOrderDetailQueryHandler dataMsg : "+JSonUtil.toJSonString(paraMap));
			tradeOrderDetail = tradeOrderDetailService.getTradeOrderDetail(paraMap);
			if(tradeOrderDetail==null)
				return JSonUtil.toJSonString(resultMap);
			//解析报文中的账单信息
			String tradeRequest = tradeOrderDetail.getRequestContext();
			if(StringUtils.isNotEmpty(tradeRequest)){
				String[] data = tradeRequest.split("&");
				for(String parameter:data){
					String[] kv = StringUtils.split(parameter, "=", 2);
					if(kv.length > 1) {
						resultMap.put(kv[0], kv[1]);
					}else{
						if(kv.length == 1 && StringUtils.isNotEmpty(kv[0]))
							resultMap.put(kv[0], null);
					}
				}
				//报文字段已经不需要了
				tradeOrderDetail.setRequestContext(null);
			}
		} catch (Exception e) {
			logger.error("api pay error:", e);
		}
		Map map = MapUtil.bean2map(tradeOrderDetail);
		map.putAll(resultMap);
		return JSonUtil.toJSonString(map);
	}

}
