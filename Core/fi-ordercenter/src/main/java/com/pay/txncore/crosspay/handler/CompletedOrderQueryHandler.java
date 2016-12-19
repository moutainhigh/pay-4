/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.crosspay.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 已完成订单查询
 * 
 * @author chma
 */
public class CompletedOrderQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private TradeOrderService tradeOrderService;
	

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();
		
		logger.info("dataMsg: "+dataMsg);

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());

			String orderId = paraMap.get("orderId");
			String partnerId = paraMap.get("partnerId");
			String status = paraMap.get("status");
			String tradeOrderNo = paraMap.get("tradeOrderNo");
			
			
			Map<String,String> params = new HashMap<String, String>();
			params.put("orderId",orderId);
            params.put("memberCode",partnerId);
            params.put("status",status);
            
            if(!StringUtil.isEmpty(tradeOrderNo)){
            	params.put("tradeOrderNo",tradeOrderNo);
            }
            
            TradeOrderDTO tradeOrderDTO = tradeOrderService.queryCompletedTradeOrder(params);
            Map<String,String> map = new HashMap<String,String>();
            if(tradeOrderDTO!=null){
            	resultMap.put("hasTradeOrder","1");
            	map.put("status",""+tradeOrderDTO.getStatus());
            	map.put("orderAmount",""+tradeOrderDTO.getOrderAmount());
            	map.put("refundAmount",""+tradeOrderDTO.getRefundAmount());
            	map.put("tradeOrderNo",String.valueOf(tradeOrderDTO.getTradeOrderNo()));
            	map.put("currencyCode",tradeOrderDTO.getCurrencyCode());
            	
            	resultMap.put("mapInfo",map);
            }else{
            	resultMap.put("hasTradeOrder","0");
            }

			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("api pay error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}
}
