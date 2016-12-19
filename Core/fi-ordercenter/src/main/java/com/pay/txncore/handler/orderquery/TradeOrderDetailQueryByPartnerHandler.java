package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.TradeOrderDetailForNotifyDTO;
import com.pay.txncore.service.TradeOrderDetailService;
import com.pay.util.JSonUtil;

/**
 * 查找指定订单号以后的交易记录信息，在邮件通知中用到
 * 
 * @author davis.guo at 2016-09-03
 */
public class TradeOrderDetailQueryByPartnerHandler implements EventHandler{
	
	public final Log logger = LogFactory.getLog(TradeOrderDetailQueryByPartnerHandler.class);
	
	private TradeOrderDetailService tradeOrderDetailService;
	
	public void setTradeOrderDetailService(
			TradeOrderDetailService tradeOrderDetailService) {
		this.tradeOrderDetailService = tradeOrderDetailService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		List<TradeOrderDetailForNotifyDTO> tradeOrderDetails = null;
		Map resultMap = new HashMap();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			logger.info("TradeOrderDetailQueryByPartnerHandler dataMsg : "+JSonUtil.toJSonString(paraMap));
			tradeOrderDetails = tradeOrderDetailService.getTradeOrderDetailByPartner(paraMap);
			if(tradeOrderDetails == null || tradeOrderDetails.isEmpty())
				return JSonUtil.toJSonString(resultMap);
			if(tradeOrderDetails!=null && !tradeOrderDetails.isEmpty()){
			}
		} catch (Exception e) {
			logger.error("api pay error:", e);
		}
		resultMap.put("result", tradeOrderDetails);
		
		return JSonUtil.toJSonString(resultMap);
	}

}
