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
import com.pay.txncore.crosspay.service.SettlementBaseRateService;
import com.pay.txncore.crosspay.service.TransactionBaseRateService;
import com.pay.txncore.model.SettlementBaseRate;
import com.pay.txncore.model.TransactionBaseRate;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 基本汇率
 * @author peiyu.yang
 */
public class CurrencyBaseRateQueryHandler implements EventHandler {
     
	public final static String DEFAULT_DATE_FROMAT = "yyyy-MM-dd HH:mm:ss";
	public final Log logger = LogFactory
			.getLog(CurrencyBaseRateQueryHandler.class);
	private TransactionBaseRateService transBaseService;
	private SettlementBaseRateService settleBaseService;
	

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map<String,String> resultMap = new HashMap<String,String>();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap<String,String>().getClass());

			String currency = paraMap.get("currency");
			String targetCurrency = paraMap.get("targetCurrency");
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
				
				TransactionBaseRate baseRate = transBaseService
						       .findCurrentCurrencyRate(currency, targetCurrency, status,date);
				
				if(baseRate!=null){
					resultMap.put("hasRate","1");
					resultMap.put("exchangeRate",baseRate.getExchangeRate());
					resultMap.put("rateType","transactionBase");
				}else{
					resultMap.put("hasRate","0");
				}
			}else if("2".equals(type)){//清算汇率
				SettlementBaseRate baseRate =settleBaseService
						     .findCurrentCurrencyRate(currency, targetCurrency, status,date);
				if(baseRate!=null){
					resultMap.put("hasRate","1");
					resultMap.put("exchangeRate",baseRate.getExchangeRate());
					resultMap.put("rateType","settlementBase");
				}else{
					resultMap.put("hasRate","0");
				}
			}
			
			resultMap.put("currency",currency);
			resultMap.put("targetCurrency",targetCurrency);
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


	public void setTransBaseService(TransactionBaseRateService transBaseService) {
		this.transBaseService = transBaseService;
	}
	public void setSettleBaseService(SettlementBaseRateService settleBaseService) {
		this.settleBaseService = settleBaseService;
	}
}
