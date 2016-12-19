/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dao.TradeCountDAO;
import com.pay.txncore.dto.TradeCountDTO;
import com.pay.txncore.dto.TradeRespCount;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 商户交易统计查询
 * @author chma
 */
public class MerchantTradeCountQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(MerchantTradeCountQueryHandler.class);
	private TradeCountDAO tradeCountDAO;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String,Object> paraMap = null;
		Map<String,Object> resultMap = new HashMap<String, Object>();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			String partnerId = (String) paraMap.get("partnerId");
			String startTime = (String) paraMap.get("startTime");
			String endTime = (String) paraMap.get("endTime");
			
			
			Map pageMap = (Map) paraMap.get("page");
			String type=(String) paraMap.get("type");
			
			Map<String,Object> map = new HashMap<String, Object>();
			
			if(!StringUtil.isEmpty(partnerId)){
				map.put("partnerId",partnerId);
			}
			
			if(!StringUtil.isEmpty(startTime)){
				map.put("beginTime",startTime);
			}
			
			if(!StringUtil.isEmpty(endTime)){
				map.put("endTime",endTime);
			}
			
			if("1".equals(type)){
				Collection<TradeCountDTO> list = null;
					list = tradeCountDAO.getCountDTOs(map);
				resultMap.put("result", list);
			}else if("2".equals(type)){
				Collection<TradeRespCount> list = tradeCountDAO.getTradeRespCounts(map);
				resultMap.put("result", list);
			}
		} catch (Exception e) {
			logger.error("query error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setTradeCountDAO(TradeCountDAO tradeCountDAO) {
		this.tradeCountDAO = tradeCountDAO;
	}
	
	

}
