/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.crosspay.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.model.FrozenOrder;
import com.pay.txncore.crosspay.service.FrozenOrderService;
import com.pay.util.JSonUtil;

/**
 * 查询冻结交易
 * 
 * @author chma
 */
public class FrozenOrderQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private FrozenOrderService frozenOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, Object>().getClass());

			Page<FrozenOrder> page = new Page<FrozenOrder>();
			Page<FrozenOrder> result = frozenOrderService
					.queryFrozenOrderForPage(paraMap, page);
			resultMap.put("result", result);
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
