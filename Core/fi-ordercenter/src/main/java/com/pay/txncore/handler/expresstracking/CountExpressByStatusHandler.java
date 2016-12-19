package com.pay.txncore.handler.expresstracking;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.service.ExpressTrackingService;
import com.pay.util.JSonUtil;

/**
 * 统计运单某个状态下的数量
 * @author delin.dong
 * @date 2016年6月8日14:19:10
 */
public class CountExpressByStatusHandler implements EventHandler {
	
	public final Log logger = LogFactory
			.getLog(CountExpressByStatusHandler.class);
	private ExpressTrackingService expressTrackingService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			String pendingCount=expressTrackingService.countExpressByStatus(paraMap);
			resultMap.put("pendingCount",pendingCount);
		} catch (Exception e) {
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}
		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());		
		return JSonUtil.toJSonString(resultMap);
	}

	public void setExpressTrackingService(
			ExpressTrackingService expressTrackingService) {
		this.expressTrackingService = expressTrackingService;
	}
}
