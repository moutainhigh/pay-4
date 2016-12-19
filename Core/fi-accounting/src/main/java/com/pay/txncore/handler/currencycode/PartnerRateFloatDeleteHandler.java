/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.currencycode;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.service.PartnerRateFloatService;
import com.pay.txncore.model.PartnerRateFloat;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 商户汇率浮动删除
 * @author chma
 */
public class PartnerRateFloatDeleteHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(PartnerRateFloatDeleteHandler.class);
	private PartnerRateFloatService partnerRateFloatService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			String id = (String) paraMap.get("id");
			
			PartnerRateFloat rateFloat = new PartnerRateFloat();
			
			if(!StringUtil.isEmpty(id)){
				rateFloat.setId(Long.valueOf(id));
			}
			
			boolean result = partnerRateFloatService.delete(rateFloat);
			
			resultMap.put("result",result);
			
		} catch (Exception e) {
			logger.error("query partner error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setPartnerRateFloatService(
			PartnerRateFloatService partnerRateFloatService) {
		this.partnerRateFloatService = partnerRateFloatService;
	}
	
}
