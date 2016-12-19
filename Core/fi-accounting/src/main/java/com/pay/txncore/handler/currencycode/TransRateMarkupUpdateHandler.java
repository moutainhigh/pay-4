/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.currencycode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.TransRateMarkup;
import com.pay.txncore.service.TransRateMarkupService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 汇率多维度markup修改
 * @author chma
 */
public class TransRateMarkupUpdateHandler implements EventHandler {

	public final static String DEFAULT_DATE_FROMAT = "yyyy-MM-dd HH:mm:ss";
	public final Log logger = LogFactory
			.getLog(TransRateMarkupUpdateHandler.class);
	private TransRateMarkupService transRateMarkupService; 

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			String currency = (String)paraMap.get("currency");
			String targetCurrency =(String) paraMap.get("targetCurrency");
			String startPoint = (String)paraMap.get("startPoint");
			String endPoint = (String)paraMap.get("endPoint");
			String status = (String) paraMap.get("status");
			String cardOrg = (String) paraMap.get("cardOrg");
			String cardCountry = (String) paraMap.get("cardCountry");
			String cardCurrencyCode = (String) paraMap.get("cardCurrencyCode");
			String id = (String) paraMap.get("id");
			String leastTransAmountStr = (String) paraMap.get("leastTransAmount");
			String startAmount = (String) paraMap.get("startAmount");
			String endAmount = (String) paraMap.get("endAmount");
			String memberCode = (String) paraMap.get("memberCode");
			String markupStr = (String) paraMap.get("markup");
			String priority = (String) paraMap.get("priority");

			TransRateMarkup markup = new TransRateMarkup();
			if (!StringUtil.isEmpty(currency)) {
				markup.setCurrency(currency);
			}
			if (!StringUtil.isEmpty(targetCurrency)) {
				markup.setTargetCurrency(targetCurrency);
			}
			if (!StringUtil.isEmpty(status)) {
				markup.setStatus(status);
			}
			if(!StringUtil.isEmpty(cardOrg)){
				markup.setCardOrg(cardOrg);
			}
			if(!StringUtil.isEmpty(memberCode)){
				markup.setMemberCode(memberCode);
			}
			if(!StringUtil.isEmpty(cardCountry)){
				markup.setCardCountry(cardCountry);
			}
			if(!StringUtil.isEmpty(cardCurrencyCode)){
				markup.setCardCurrencyCode(cardCurrencyCode);
			}
			if(!StringUtil.isEmpty(id)){
				markup.setId(Long.valueOf(id));
			}
			if(!StringUtil.isEmpty(markupStr)){
				markup.setMarkup(markupStr);
			}
			if(!StringUtil.isEmpty(startPoint)){
				markup.setStartPoint(Integer.valueOf(startPoint));
			}
			if(!StringUtil.isEmpty(endPoint)){
				markup.setEndPoint(Integer.valueOf(endPoint));
			}
			if(!StringUtil.isEmpty(priority)){
				markup.setPriority(Integer.valueOf(priority));
			}
			if(!StringUtil.isEmpty(leastTransAmountStr)){
				markup.setLeastTransAmount(Double.valueOf(leastTransAmountStr));
			}
			if(!StringUtil.isEmpty(startAmount)){
				markup.setStartAmount(Double.valueOf(startAmount));
			}
			if(!StringUtil.isEmpty(endAmount)){
				markup.setEndAmount(Double.valueOf(endAmount));
			}
			
			markup.setUpdateDate(new Date());
			
			boolean result= transRateMarkupService.updateTransRateMarkup(markup);
            
			resultMap.put("result",result);
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

	public void setTransRateMarkupService(
			TransRateMarkupService transRateMarkupService) {
		this.transRateMarkupService = transRateMarkupService;
	}
}
