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
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.model.SettlementRate;
import com.pay.txncore.model.TransactionRate;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 汇率查询
 * 包括交易汇率查询与交易汇率查询
 * 是当天有效的
 * @author peiyu.yang
 */
public class CurrencyRateQueryHandler implements EventHandler {
     
	public final static String DEFAULT_DATE_FROMAT = "yyyy-MM-dd HH:mm:ss";
	public final Log logger = LogFactory
			.getLog(CurrencyRateQueryHandler.class);
	private CurrencyRateService currencyRateService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			String currency = paraMap.get("currency");
			String targetCurrency = paraMap.get("targetCurrency");
			String memberCode = paraMap.get("memberCode");
			String type = paraMap.get("type");
			String status = paraMap.get("status");
			String dateTime = paraMap.get("dateTime");
			//String cardHolderNumber =  paraMap.get("cardHolderNumber");
			//String cardOrg = this.getCardType(cardHolderNumber);
			
			SimpleDateFormat formatter = new SimpleDateFormat(
					DEFAULT_DATE_FROMAT);
			
			Date date = null;
			
			if(!StringUtil.isEmpty(dateTime)){
				date = formatter.parse(dateTime);
			}
            
			//查询交易汇率
			if("1".equals(type)){
				TransactionRate transactionRate = currencyRateService.getTransactionRate(currency, 
						targetCurrency, status, memberCode,date);
				if(transactionRate!=null){
					resultMap.put("hasRate","1");
					resultMap.put("exchangeRate",transactionRate.getExchangeRate());
					resultMap.put("rateType","transaction");
				}else{
					resultMap.put("hasRate","0");
				}
			}else if("2".equals(type)){//清算汇率
				SettlementRate settlementRate = currencyRateService.getSettlementRate(currency,
						targetCurrency, status, memberCode, date);
				if(settlementRate!=null){
					resultMap.put("hasRate","1");
					resultMap.put("exchangeRate",settlementRate.getExchangeRate());
					resultMap.put("rateType","settlement");
				}else{
					resultMap.put("hasRate","0");
				}
			}
			
			resultMap.put("currency",currency);
			resultMap.put("targetCurrency",targetCurrency);
			resultMap.put("memberCode",memberCode);
			resultMap.put("status",status);
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
	
	
	private String getCardType(String cardNo){
		int cardLen = cardNo.length();
		
		if(cardLen == 16){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=400000 && subCard <=499999){
				return "VISA";
			}
			if((subCard>=510000 && subCard <=559999)||(subCard>=222100 && subCard <=272099)){
				return "MASTER";
			}
			if(subCard>=352800 && subCard <=358999){
				return "JCB";
			}
		}
		if(cardLen == 14){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=300000 && subCard <=305999){
				return "DC";
			}
			if(subCard>=309500 && subCard <=309599){
				return "DC";
			}
			if(subCard>=360000 && subCard <=369999){
				return "DC";
			}
			if(subCard>=380000 && subCard <=399999){
				return "DC";
			}
		}
		if(cardLen == 15){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=340000 && subCard <=349999){
				return "AE";
			}
			if(subCard>=370000 && subCard <=379999){
				return "AE";
			}
		}
		return null;
	}

	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}
}
