/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.JSonUtil;

/**
 * 获取指定时间以后，最大的网关订单号
 * 
 * @author davis.guo at 2016-09-03
 */
public class MaxTradeOrderNoHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(MaxTradeOrderNoHandler.class);

	private TradeOrderService tradeOrderService;

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map resultMap = new HashMap();

		Map paramMap = null;
		try {
			paramMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		String startDate = (String)paramMap.get("startDate");
		long maxTradeOrderNo = tradeOrderService.maxTradeOrderNo(startDate, "");
		if(maxTradeOrderNo>0)
		{
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			resultMap.put("maxTradeOrderNo", maxTradeOrderNo);
		}
		else
		{
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
		
		
	}
}
