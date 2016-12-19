/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.currencycode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.cardbin.model.CardBinInfo;
import com.pay.cardbin.service.CardBinInfoService;
import com.pay.dcc.model.PartnerDCCConfig;
import com.pay.dcc.service.ConfigurationDCCService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.service.TransactionBaseRateService;
import com.pay.txncore.model.TransactionBaseRate;
import com.pay.util.JSonUtil;

/**
 * DCC 汇率查询
 * @author peiyu.yang
 */
public class DccRateQueryHandler implements EventHandler {
	public final Log logger = LogFactory.getLog(DccRateQueryHandler.class);
	private TransactionBaseRateService transBaseService;
	private CardBinInfoService cardBinInfoService;
	private ConfigurationDCCService dccService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map<String,String> resultMap = new HashMap<String,String>();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			String currency = paraMap.get("currencyCode");
			String cardNo = paraMap.get("cardHolderNumber");
			String memberCode = paraMap.get("partnerId");
			String type = paraMap.get("type");
			
			String cardBin = cardNo.substring(0, 6);
			CardBinInfo binInfo = cardBinInfoService.getCardBinInfo(cardBin);
			logger.info("cardBinInfo: "+binInfo);
			String cardCurrency=null;
			if(binInfo!=null){
				cardCurrency = binInfo.getCurrencyCode();
				resultMap.put("cardCurrency",cardCurrency);
				
				TransactionBaseRate baseRate = transBaseService.findCurrentCurrencyRate(currency, 
						cardCurrency,"1",null);
				logger.info("baseRate: "+baseRate);
			    if(baseRate!=null){
					Map<String, Object> param_ = new HashMap<String, Object>();
					param_.put("partnerId", memberCode);
					param_.put("currencyCode",cardCurrency);

					PartnerDCCConfig dccConfig = dccService.getDccConfig(param_);
					String markupStr="0";
					if (dccConfig != null) {
						markupStr = dccConfig.getMarkUp();
					}
					BigDecimal rateTmp = new BigDecimal(baseRate.getExchangeRate());
					BigDecimal rate = rateTmp.add(rateTmp.multiply(new BigDecimal(markupStr)
					             .multiply(new BigDecimal("0.01"))));
					resultMap.put("exchangeRate",rate.toString());
					resultMap.put("responseCode","4000");
					resultMap.put("responseDesc","查询汇率成功");
			    }else{
					resultMap.put("responseCode","4001");
					resultMap.put("responseDesc","查询汇率失败");
			    } 
			}else{
				resultMap.put("responseCode","4001");
				resultMap.put("responseDesc","查询汇率失败");
			}
			resultMap.put("currency",currency);
			resultMap.put("partnerId",memberCode);
		} catch (Exception e) {
			resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

	public void setTransBaseService(TransactionBaseRateService transBaseService) {
		this.transBaseService = transBaseService;
	}

	public void setCardBinInfoService(CardBinInfoService cardBinInfoService) {
		this.cardBinInfoService = cardBinInfoService;
	}

	public void setDccService(ConfigurationDCCService dccService) {
		this.dccService = dccService;
	}
}
