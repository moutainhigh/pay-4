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
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.service.PartnerSettlementOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 
 * @author chma
 */
public class PartnerSettlementOrderQueryHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(PartnerSettlementOrderQueryHandler.class);
	private PartnerSettlementOrderService partnerSettlementOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			PartnerSettlementOrder partnerSettlementOrder = MapUtil.map2Object(
					PartnerSettlementOrder.class, paraMap);
			Map pageMap = (Map) paraMap.get("page");
			if(pageMap == null){
				List<PartnerSettlementOrder> partnerSettlementOrders = partnerSettlementOrderService
						.queryPartnerSettlementOrder(partnerSettlementOrder);
				resultMap.put("result", partnerSettlementOrders);
			}else{
				Page page = MapUtil.map2Object(Page.class, pageMap);
				List<PartnerSettlementOrder> partnerSettlementOrders = partnerSettlementOrderService
						.queryPartnerSettlementOrder(partnerSettlementOrder, page);
				resultMap.put("result", partnerSettlementOrders);
				resultMap.put("page", page);
			}
		} catch (Exception e) {
			logger.error("query error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}

}
