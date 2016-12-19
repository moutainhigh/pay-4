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
import com.pay.txncore.crosspay.service.FrozenOrderService;
import com.pay.util.JSonUtil;

/**
 * 查询冻结交易
 * 
 * @author chma
 */
public class QueryOrderDetailHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private FrozenOrderService frozenOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());

			String tradeOrderNo = paraMap.get("tradeOrderNo");

			Map<String, Object> tradeOrderMap = frozenOrderService
					.getTradeOrder(tradeOrderNo);
			resultMap.put("result", tradeOrderMap);
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

	public void setFrozenOrderService(FrozenOrderService frozenOrderService) {
		this.frozenOrderService = frozenOrderService;
	}

}
