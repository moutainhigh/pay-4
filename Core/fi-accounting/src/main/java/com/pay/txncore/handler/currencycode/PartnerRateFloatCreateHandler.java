/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.currencycode;

import java.text.SimpleDateFormat;
import java.util.Date;
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

/**
 * 商户汇率浮动创建
 * @author chma
 */
public class PartnerRateFloatCreateHandler implements EventHandler {

	public final static String DEFAULT_DATE_FROMAT = "yyyy-MM-dd HH:mm:ss";
	public final Log logger = LogFactory
			.getLog(PartnerRateFloatCreateHandler.class);
	private PartnerRateFloatService partnerRateFloatService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			
			SimpleDateFormat formatter = new SimpleDateFormat(
					DEFAULT_DATE_FROMAT);

			String endPoint = (String) paraMap.get("endPoint");
			String startPoint = (String) paraMap.get("startPoint");
			String operator = (String) paraMap.get("operator");
			String partnerId = (String) paraMap.get("partnerId");
			
			PartnerRateFloat rateFloat = new PartnerRateFloat();
			rateFloat.setEndPoint(endPoint);
			rateFloat.setCreateDate(formatter.format(new Date()));
			rateFloat.setPartnerId(partnerId);
			rateFloat.setOperator(operator);
			rateFloat.setStartPoint(startPoint);
			
			Long result = partnerRateFloatService.save(rateFloat);
			
			if(result==null||result.longValue()<=0){
				resultMap.put("result",false);
			}else{
				resultMap.put("result",true);
			}
			
			

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
