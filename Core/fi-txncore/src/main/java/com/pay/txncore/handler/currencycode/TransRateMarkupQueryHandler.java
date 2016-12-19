/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.currencycode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.TransRateMarkup;
import com.pay.txncore.service.TransRateMarkupService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 
 * @author chma
 */
public class TransRateMarkupQueryHandler implements EventHandler {
	public final Log logger = LogFactory
			.getLog(TransRateMarkupQueryHandler.class);
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
			String startTime = (String)paraMap.get("startTime");
			String endTime = (String)paraMap.get("endTime");
			String status = (String) paraMap.get("status");
			String cardOrg = (String) paraMap.get("cardOrg");
			String cardCountry = (String) paraMap.get("cardCountry");
			String cardCurrencyCode = (String) paraMap.get("cardCurrencyCode");
			String id = (String) paraMap.get("id");
			String memberCode = (String) paraMap.get("memberCode");
			String priority = (String) paraMap.get("priority");
			
			Map pageMap = (Map) paraMap.get("page");
			Page page = MapUtil.map2Object(Page.class, pageMap);

			TransRateMarkup markup = new TransRateMarkup();

			if (!StringUtil.isEmpty(currency)) {
				markup.setCurrency(currency);
			}
			if (!StringUtil.isEmpty(startTime)) {
				markup.setStartTime(startTime);
			}
			if (!StringUtil.isEmpty(endTime)) {
				markup.setEndTime(endTime);
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
			if(!StringUtil.isEmpty(priority)){
				markup.setPriority(Integer.valueOf(priority));
			}
            
			List<TransRateMarkup> list = transRateMarkupService.findByCriteria(markup);
			
			resultMap.put("list", list);
			resultMap.put("page", page);
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
