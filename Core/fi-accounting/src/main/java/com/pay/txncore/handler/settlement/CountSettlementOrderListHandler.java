/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.settlement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
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
public class CountSettlementOrderListHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(CountSettlementOrderListHandler.class);
	private PartnerSettlementOrderService partnerSettlementOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			Map pageMap = (Map) resultMap.get("page");
			Page page = MapUtil.map2Object(Page.class, pageMap);

			PartnerSettlementOrder partnerSettlementOrder = MapUtil.map2Object(
					PartnerSettlementOrder.class, paraMap);

			Map<String,Object> list = partnerSettlementOrderService.countSettlementOrderListSize(partnerSettlementOrder);

			resultMap.put("list", list);
			resultMap.put("page", page);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}

}
