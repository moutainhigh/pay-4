/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.currencycode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.service.OrgExchangeRateService;
import com.pay.txncore.model.OrgCurrencyExchangeRate;
import com.pay.util.JSonUtil;

/**
 * 
 * @author chma
 */
public class OrgExchangeRateCreateHandler implements EventHandler {

	public final static String DEFAULT_DATE_FROMAT = "yyyy-MM-dd HH:mm:ss";
	public final Log logger = LogFactory
			.getLog(OrgExchangeRateCreateHandler.class);
	private OrgExchangeRateService orgExchangeRateService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			String targetCurrency = (String) paraMap.get("targetCurrency");
			String currencyUnit = (String) paraMap.get("currencyUnit");
			String orgCode = (String) paraMap.get("orgCode");
			String operator = (String) paraMap.get("operator");
			List<Map> listMap = (List<Map>) paraMap.get("list");

			List<OrgCurrencyExchangeRate> rates = null;
			if (null != listMap && !listMap.isEmpty()) {
				rates = new ArrayList<OrgCurrencyExchangeRate>();
				for (Map map : listMap) {
					String currency = (String) map.get("currency");
					String exchangeRate = (String) map.get("exchangeRate");
					String reverseExchangeRate = (String) map
							.get("reverseExchangeRate");
					String effectDate = (String) map.get("effectDate");
					String expireDate = (String) map.get("expireDate");
					OrgCurrencyExchangeRate rate = new OrgCurrencyExchangeRate();

					rate.setCreateDate(new Date());
					rate.setCurrency(currency);
					rate.setCurrencyUnit(Integer.valueOf(currencyUnit));

					SimpleDateFormat formatter = new SimpleDateFormat(
							DEFAULT_DATE_FROMAT);
					rate.setEffectDate(formatter.parse(effectDate));
					rate.setExchangeRate(exchangeRate);

					rate.setExpireDate(formatter.parse(expireDate));
					rate.setOperator(operator);
					rate.setReverseExchangeRate(reverseExchangeRate);
					rate.setStatus("1");
					rate.setTargetCurrency(targetCurrency);
					rate.setUpdateDate(new Date());
					rate.setOrgCode(orgCode);
					rates.add(rate);
				}
			}

			orgExchangeRateService.batchCreate(rates);

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

	public void setOrgExchangeRateService(
			OrgExchangeRateService orgExchangeRateService) {
		this.orgExchangeRateService = orgExchangeRateService;
	}

}
