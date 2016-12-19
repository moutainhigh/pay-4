/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.QueryDetailPara;
import com.pay.txncore.service.OrderQueryService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 
 * @author chma
 */
public class CountRefundOrderListHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(CountRefundOrderListHandler.class);
	private OrderQueryService orderQueryService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map queryDetails = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			QueryDetailPara queryDetailPara = MapUtil.map2Object(
					com.pay.txncore.model.QueryDetailPara.class, paraMap);

			queryDetails = orderQueryService
					.countRefundOrderList(queryDetailPara);
		} catch (Exception e) {
			logger.error("query error:", e);
		}

		return JSonUtil.toJSonString(queryDetails);
	}

	public void setOrderQueryService(OrderQueryService orderQueryService) {
		this.orderQueryService = orderQueryService;
	}

}
