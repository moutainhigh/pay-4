/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 
 * @author chma
 */
public class ChannelOrderQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(ChannelOrderQueryHandler.class);
	private ChannelOrderService channelOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			ChannelOrderDTO channelOrderDTO = MapUtil.map2Object(
					ChannelOrderDTO.class, paraMap);
			Map pageMap = (Map) paraMap.get("page");
			List<ChannelOrderDTO> channelOrders = null;
			if(pageMap==null){
				channelOrders = channelOrderService
						.queryChannelOrder(channelOrderDTO);
			}else{
				Page page = MapUtil.map2Object(Page.class, pageMap);
				channelOrders = channelOrderService
						.queryChannelOrder(channelOrderDTO, page);				
				resultMap.put("page", page);
			}
			resultMap.put("result", channelOrders);
		} catch (Exception e) {
			logger.error("query error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setChannelOrderService(ChannelOrderService channelOrderService) {
		this.channelOrderService = channelOrderService;
	}

}
