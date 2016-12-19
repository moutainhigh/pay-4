/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.crosspay.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 交易订单状态更新
 * 
 * @author peiyu.yang
 */
public class TradeOrderUpdateHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private TradeOrderService tradeOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
            
			String tradeOrderNo = paraMap.get("tradeOrderNo");
			String status = paraMap.get("status");
			String oldStatus = paraMap.get("oldStatus");
			String resultCode = paraMap.get("resultCode");
			String resultMsg = paraMap.get("resultMsg");
			
			TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
			
			if(!StringUtil.isEmpty(tradeOrderNo)){
				tradeOrderDTO.setTradeOrderNo(Long.valueOf(tradeOrderNo));
			}
			
			if(!StringUtil.isEmpty(status)){
				tradeOrderDTO.setStatus(Integer.valueOf(status));
			}
			if(!StringUtil.isEmpty(resultMsg)){
				tradeOrderDTO.setRespMsg(resultMsg);
			}
			if(!StringUtil.isEmpty(resultCode)){
				tradeOrderDTO.setRespCode(resultCode);
			}
			
			tradeOrderDTO.setCompleteDate(new Date());
			
			boolean result = false;
			
			if(StringUtil.isEmpty(oldStatus)){
				result = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
			}else{
				result = tradeOrderService.updateTradeOrderRnTx
				                 (tradeOrderDTO,Integer.valueOf(oldStatus));
			}
			
			if(result){
				resultMap.put("responseCode","0000");
				resultMap.put("responseDesc","交易订单更新成功");
			}else{
				resultMap.put("responseCode","9999");
				resultMap.put("responseDesc","交易订单更新失败");
			}
			
		} catch (BusinessException e) {
			logger.error("CrosspayCashierPayHandler error:", e);
			ExceptionCodeEnum error = e.getCode();
			resultMap.put("responseCode", error.getCode());
			resultMap.put("responseDesc", error.getDescription());
		} catch (Exception e) {
			logger.error("api pay error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}
	
	
}
