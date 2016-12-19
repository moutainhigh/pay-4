/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.currencycode;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.crosspay.service.TransactionBaseRateService;
import com.pay.txncore.model.SettlementRate;
import com.pay.txncore.model.TransRateMarkup;
import com.pay.txncore.model.TransactionBaseRate;
import com.pay.txncore.service.TransRateMarkupService;
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
	private TransRateMarkupService transRateMarkupService;
	private TransactionBaseRateService transBaseService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map<String,String> resultMap = new HashMap<String,String>();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			String currency = paraMap.get("currency");
			String targetCurrency = paraMap.get("targetCurrency");
			String memberCode = paraMap.get("memberCode");
			String type = paraMap.get("type");
			String status = paraMap.get("status");
			String dateTime = paraMap.get("dateTime");

			SimpleDateFormat formatter = new SimpleDateFormat(
					DEFAULT_DATE_FROMAT);		
			Date date = null;
			if(!StringUtil.isEmpty(dateTime)){
				date = formatter.parse(dateTime);
			}
            
			//查询交易汇率
			if("1".equals(type)){
				TransactionBaseRate baseRate = transBaseService.findCurrentCurrencyRate(currency, targetCurrency, 
						status,null);
				logger.info("baseRate: "+baseRate);
				if(baseRate==null){
					resultMap.put("hasRate","0");
				}else{
					resultMap.put("hasRate","1");
					resultMap.put("rateType","transaction");
					Map<String,Object> map0 = new HashMap<String,Object>();
					map0.put("currency",currency);
					map0.put("targetCurrency",targetCurrency);
					map0.put("memberCode",memberCode);
					map0.put("status",status);
					map0.put("point",this.getTime());
					
					TransRateMarkup markup = transRateMarkupService.findTransRateMarkup(map0);
					logger.info("markup:"+markup);
					
					if(markup==null){
						resultMap.put("exchangeRate",baseRate.getExchangeRate());
					}else{
						BigDecimal rateTmp = new BigDecimal(baseRate.getExchangeRate());
						BigDecimal rate = rateTmp.add(rateTmp.multiply(new BigDecimal(markup.getMarkup())
						             .multiply(new BigDecimal("0.01"))));
						resultMap.put("exchangeRate",rate.toString());
					}
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
	
	private double getTime(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        double s = min/100.0;
        double rst = hour+s;
        
        return rst;
	}
	
	
	private String getCardType(String cardNo){
		int cardLen = cardNo.length();
		
		if(cardLen == 16){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=400000 && subCard <=499999){
				return "VISA";
			}
			//Mack comment below line and add nee line
			//if(subCard>=510000 && subCard <=559999){
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

	public void setTransRateMarkupService(
			TransRateMarkupService transRateMarkupService) {
		this.transRateMarkupService = transRateMarkupService;
	}

	public void setTransBaseService(TransactionBaseRateService transBaseService) {
		this.transBaseService = transBaseService;
	}
}
