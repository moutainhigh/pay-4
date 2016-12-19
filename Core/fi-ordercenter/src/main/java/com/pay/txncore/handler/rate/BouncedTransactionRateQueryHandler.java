/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.rate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.cardbin.model.CardBinInfo;
import com.pay.cardbin.service.CardBinInfoService;
import com.pay.fi.commons.CountryCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.model.TransactionRate;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

import freemarker.template.SimpleDate;

/**
 * 商户交易汇率查询
 * @author peiyu.yang
 */
public class BouncedTransactionRateQueryHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(BouncedTransactionRateQueryHandler.class);
	private CurrencyRateService currencyRateService;
	private CardBinInfoService cardBinInfoService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String,Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map<String,Object> params=new HashMap<String, Object>();
			
			// status为1是当天的汇率，置为有效，取某一天的这字段不传，传交易日期
			params.put("memberCode", paraMap.get("memberCode"));
			params.put("cardOrg", paraMap.get("cardOrg"));
			params.put("orderAmount", paraMap.get("orderAmount"));
			params.put("ltaCurrencyCode", "USD");
			
			params.put("point", paraMap.get("point"));
			params.put("currentDate", paraMap.get("currentDate"));
			params.put("isIgnoreMarkup", paraMap.get("isIgnoreMarkup"));//Mack add to control markup
			
			String currencyCode=paraMap.get("sourceCurrency").toString();
			String targetCurrencyCode=paraMap.get("targetCurrency").toString();
			Date currentDate=null;
			if(null!=paraMap.get("currentDate"))
			{
				currentDate= new SimpleDateFormat("yyyy-MM-dd").parse((String)paraMap.get("currentDate"));
			}
			if(null!=paraMap.get("status"))
			{
				params.put("status", paraMap.get("status"));
			}
			
			if(null!=paraMap.get("cardNo"))
			{
				String cardNo=paraMap.get("cardNo").toString();
				String cardBin = cardNo.substring(0, 6);
				
				
				CardBinInfo info = cardBinInfoService.getCardBinInfo(cardBin);
				if(info!=null){
					if(!CountryCodeEnum.isExists(info.getCountryCode3())){
						params.put("cardCountry","OOO");
					}else{
						params.put("cardCountry",info.getCountryCode3());
					}
					if(!StringUtil.isEmpty(info.getCurrencyCode())){
						if("EUR".equals(info.getCurrencyCode())){
							params.put("cardCountry","EUR");
						}
						params.put("cardCurrencyCode",info.getCurrencyCode());
					}
					if(null==paraMap.get("cardOrg") || "".equals(paraMap.get("cardOrg")))
					{
						params.put("cardOrg",info.getCardOrg());
					}
				}
			}
			
			TransactionRate transactionRate = currencyRateService.getBoncedTransactionRate(params,currencyCode,targetCurrencyCode,currentDate);

			
			resultMap.put("transactionRate", transactionRate);
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


	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}


	public CardBinInfoService getCardBinInfoService() {
		return cardBinInfoService;
	}


	public void setCardBinInfoService(CardBinInfoService cardBinInfoService) {
		this.cardBinInfoService = cardBinInfoService;
	}


	public CurrencyRateService getCurrencyRateService() {
		return currencyRateService;
	}
}
