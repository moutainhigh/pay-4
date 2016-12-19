package com.pay.txncore.handler.currencycode;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.model.SettlementRate;
import com.pay.util.JSonUtil;

/**
 * 公布查询最新清算汇率的接口
 * @date  2016年8月15日17:38:59
 * @author delin
 *
 */
public class NewSettlementRateHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(NewSettlementRateHandler.class);

	private CurrencyRateService currencyRateService;
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			 logger.info("beforeNewSettlementRateHandler" );
			 
			SettlementRate newSettlementRate = currencyRateService.getNewSettlementRate(paraMap);
			resultMap.put("exchangeRate",newSettlementRate.getExchangeRate());
			 logger.info("aftergetsettlementrate2-"+newSettlementRate.getExchangeRate() );
			resultMap.put("newSettlementRate",newSettlementRate);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		}catch(Exception e){
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
}
