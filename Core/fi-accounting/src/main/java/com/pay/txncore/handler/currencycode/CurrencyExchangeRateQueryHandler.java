/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.currencycode;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.service.CurrencyExchangeRateService;
import com.pay.txncore.model.CurrencyExchangeRate;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 
 * @author chma
 */
public class CurrencyExchangeRateQueryHandler implements EventHandler {

	public final static String DEFAULT_DATE_FROMAT = "yyyy-MM-dd HH:mm:ss";
	public final Log logger = LogFactory
			.getLog(CurrencyExchangeRateQueryHandler.class);
	private CurrencyExchangeRateService currencyExchangeRateService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			String currency = paraMap.get("currency");
			String targetCurrency = paraMap.get("targetCurrency");
			String effectDate = paraMap.get("effectDate");
			String expireDate = paraMap.get("expireDate");
			String status = paraMap.get("status");

			CurrencyExchangeRate exchangeRate = new CurrencyExchangeRate();

			if (!StringUtil.isEmpty(currency)) {
				exchangeRate.setCurrency(currency);
			}
			SimpleDateFormat formatter = new SimpleDateFormat(
					DEFAULT_DATE_FROMAT);

			if (!StringUtil.isEmpty(effectDate)) {
				exchangeRate.setEffectDate(formatter.parse(effectDate));
			}
			if (!StringUtil.isEmpty(effectDate)) {
				exchangeRate.setExpireDate(formatter.parse(expireDate));
			}

			if (!StringUtil.isEmpty(targetCurrency)) {
				exchangeRate.setTargetCurrency(targetCurrency);
			}
			
			if (!StringUtil.isEmpty(status)) {
				exchangeRate.setStatus(status);
			}

			List<CurrencyExchangeRate> list = currencyExchangeRateService
					.findByCriteria(exchangeRate);

			resultMap.put("list", list);
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

	public void setCurrencyExchangeRateService(
			CurrencyExchangeRateService currencyExchangeRateService) {
		this.currencyExchangeRateService = currencyExchangeRateService;
	}

}
