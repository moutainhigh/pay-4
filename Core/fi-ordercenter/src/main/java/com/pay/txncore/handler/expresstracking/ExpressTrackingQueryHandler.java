/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.expresstracking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.model.ExpressTracking;
import com.pay.txncore.crosspay.service.ExpressTrackingService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 
 * @author chma
 */
public class ExpressTrackingQueryHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(ExpressTrackingQueryHandler.class);
	private ExpressTrackingService expressTrackingService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map pageMap = (Map) paraMap.get("page");
			ExpressTracking expressTracking = MapUtil.map2Object(
					ExpressTracking.class, paraMap);
			if(pageMap == null){
				List<ExpressTracking> list = expressTrackingService.findByCriteria(expressTracking);
				resultMap.put("list", list);
			}else{
				Page page = MapUtil.map2Object(Page.class, pageMap);
				List<ExpressTracking> list = expressTrackingService.findByCriteria(
						expressTracking, page);
				logger.info("page: "+page);
				resultMap.put("list", list);
				resultMap.put("page", page);
			}
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
