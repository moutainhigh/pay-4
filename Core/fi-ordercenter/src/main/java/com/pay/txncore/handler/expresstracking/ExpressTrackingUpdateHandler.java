/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.expresstracking;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.model.ExpressTracking;
import com.pay.txncore.crosspay.service.ExpressTrackingService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 
 * @author chma
 */
public class ExpressTrackingUpdateHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(ExpressTrackingUpdateHandler.class);
	private ExpressTrackingService expressTrackingService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			String operator = (String) paraMap.get("operator");
			List<Map> listMap = (List<Map>) paraMap.get("list");

			if (null != listMap && !listMap.isEmpty()) {
				List<ExpressTracking> list = MapUtil.map2List(
						ExpressTracking.class, listMap);
				for (ExpressTracking expressTracking : list) {
					expressTracking.setUpdateDate(new Date());
					expressTracking.setOperator(operator);
					expressTracking.setAuditTime(new Date());
					expressTracking.setUploadeDate(new Date());
					String orderId = expressTracking.getOrderId();
					Long id = expressTracking.getId();
					if(id==null && StringUtils.isEmpty(orderId)){
						resultMap.put("responseCode",
								ResponseCodeEnum.UNDEFINED_ERROR.getCode());
						resultMap.put("responseDesc",
								ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
						return JSonUtil.toJSonString(resultMap);
					}
				}
				int cnt = expressTrackingService.updateExpressTracking(list);
				logger.info("updated cnt:" + cnt);
			} else {

				String id = (String) paraMap.get("id");
				String status = (String) paraMap.get("status");
				String queryUrl = (String) paraMap.get("queryUrl");
				String expressCom = (String) paraMap.get("expressCom");
				String completeFlg = (String) paraMap.get("completeFlg");
				String trackingNo = (String) paraMap.get("trackingNo");
				String remark = (String) paraMap.get("remark");

				if (StringUtil.isEmpty(id)) {
					resultMap.put("responseCode",
							ResponseCodeEnum.INVALID_PARAMETER.getCode());
					resultMap.put("responseDesc",
							ResponseCodeEnum.INVALID_PARAMETER.getDesc());
					return JSonUtil.toJSonString(resultMap);
				}

				ExpressTracking expressTracking = expressTrackingService
						.findById(Long.valueOf(id));

				if (!StringUtil.isEmpty(status)) {
					expressTracking.setStatus(status);
				}

				expressTracking.setUpdateDate(new Date());
				expressTracking.setOperator(operator);
				expressTracking.setQueryUrl(queryUrl);
				expressTracking.setExpressCom(expressCom);
				expressTracking.setCompleteFlg(completeFlg);
				expressTracking.setAuditTime(new Date());

				if (!StringUtil.isEmpty(trackingNo)) {
					expressTracking.setTrackingNo(trackingNo);
					expressTracking.setUploadeDate(new Date());
				}

				expressTrackingService.updateExpressTracking(expressTracking);
			}

			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("query partner error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setExpressTrackingService(
			ExpressTrackingService expressTrackingService) {
		this.expressTrackingService = expressTrackingService;
	}

}
