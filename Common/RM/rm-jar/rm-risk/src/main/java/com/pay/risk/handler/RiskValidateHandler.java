/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.risk.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.risk.service.RiskValidateService;
import com.pay.risk.dto.PaymentInfo;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 风控验证
 * @author peiyu.yang
 */
public class RiskValidateHandler implements EventHandler {
	private RiskValidateService riskValidateService;
	public final Log logger = LogFactory
			.getLog(RiskValidateHandler.class);
	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> paraMap = null;
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			PaymentInfo paymentInfo = MapUtil.map2Object(PaymentInfo.class,
					paraMap);
			Map<String,String> respMap = riskValidateService.validate(paymentInfo);
			resultMap.putAll(respMap);
			resultMap.put("responseCode",ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc",ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}
		return JSonUtil.toJSonString(resultMap);
	}
	public void setRiskValidateService(RiskValidateService riskValidateService) {
		this.riskValidateService = riskValidateService;
	}
}
