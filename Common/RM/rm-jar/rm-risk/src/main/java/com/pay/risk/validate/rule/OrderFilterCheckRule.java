/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.risk.validate.rule;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.cardbin.model.CardBinInfo;
import com.pay.cardbin.service.CardBinInfoService;
import com.pay.inf.model.IpCountryInfo;
import com.pay.inf.rule.MessageRule;
import com.pay.inf.service.IpCountryInfoService;
import com.pay.risk.client.AccountingClientService;
import com.pay.risk.dto.PaymentInfo;
import com.pay.risk.dto.PaymentResult;
import com.pay.rm.orderfilter.dto.OrderFilterRuleDTO;
import com.pay.rm.orderfilter.service.OrderFilterService;
import com.pay.util.IPv4Util;
import com.pay.util.StringUtil;
/**
 * 商户订单过滤
 */
public class OrderFilterCheckRule extends MessageRule {
	
	private static Logger logger = LoggerFactory.getLogger(OrderFilterCheckRule.class);
    private OrderFilterService orderFilterService;
    private CardBinInfoService cardBinInfoService;
    private IpCountryInfoService ipCountryInfoService;
    private AccountingClientService clientService;
    
	public void setOrderFilterService(OrderFilterService orderFilterService) {
		this.orderFilterService = orderFilterService;
	}
	public void setCardBinInfoService(CardBinInfoService cardBinInfoService) {
		this.cardBinInfoService = cardBinInfoService;
	}
	public void setIpCountryInfoService(IpCountryInfoService ipCountryInfoService) {
		this.ipCountryInfoService = ipCountryInfoService;
	}
	public void setClientService(AccountingClientService clientService) {
		this.clientService = clientService;
	}

	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		logger.info("订单过滤开始......");
		PaymentInfo paymentInfo = (PaymentInfo) validateBean;
		PaymentResult paymentResult = paymentInfo.getPaymentResult();
		
		String partnerId = paymentInfo.getPartnerId();
		String orderAmout = paymentInfo.getOrderAmount();
		String customerIp = paymentInfo.getCustomerIP();
		String cardNo = paymentInfo.getCardHolderNumber();
		String cardBin = "";
	    if(!StringUtil.isEmpty(cardNo)){
	    	cardBin = cardNo.substring(0, 6);	
	    }
		logger.info("partnerId: "+partnerId+" ,orderAmout:"
		        +orderAmout+",customerIp:"+customerIp);
		
		CardBinInfo bin = cardBinInfoService.getCardBinInfo(cardBin);
		logger.info("cardbin: "+bin);
		
		if(bin==null){
			return true;
		}
		
		Map<String,Object> parmas = new HashMap<String, Object>();
		parmas.put("memberCode",partnerId);
		parmas.put("orderDate",new Date());

		if(!StringUtil.isEmpty(bin.getCountryCode3())){
			parmas.put("cardCountryCode",bin.getCountryCode3());
		}
		if(!StringUtil.isEmpty(bin.getCardClass())){
			parmas.put("cardType",bin.getCardClass());
		}
		if(!StringUtil.isEmpty(bin.getCurrencyCode())){
			parmas.put("cardCurrencyCode",bin.getCurrencyCode());
		}

		if(!StringUtil.isEmpty(customerIp)){
			long ipInt=0;
			int ipFlg=0;
			try{
			   ipInt = this.ipToLong(customerIp);
			   ipFlg=1;
			}catch(Exception e){
				ipFlg=-1;
			}
			if(ipFlg>0){
				Map<String,Object> params_ = new HashMap<String, Object>();
				params_.put("ip",ipInt);
				IpCountryInfo info = ipCountryInfoService.getCountryInfo(params_);
				logger.info("ipCountryInfo: "+info);
				
				if(info!=null&&!StringUtil.isEmpty(info.getCountryCode3())){
					parmas.put("ipCountryCode",info.getCountryCode3());
				}
			}
		}

		if(!StringUtil.isEmpty(orderAmout)){
			BigDecimal amount = new BigDecimal(orderAmout);
			Map<String,String> queryRateMap = new HashMap<String, String>();
			queryRateMap.put("currency",paymentInfo.getCurrencyCode());
			queryRateMap.put("type","1");
			queryRateMap.put("targetCurrency","CNY");
			queryRateMap.put("status","1");
			Map<String,Object> rateMap = clientService.getBaseRate(queryRateMap); 
			if(rateMap!=null&&!rateMap.isEmpty()){
				String exchangeRate = (String) rateMap.get("exchangeRate");
			    BigDecimal amount_ = amount.multiply(new BigDecimal(exchangeRate))
			    		                   .multiply(new BigDecimal("0.001")); //mack change 0.01 to 0.001
			    parmas.put("orderAmount",amount_);
			}
		}
		
		OrderFilterRuleDTO filterRuleDTO = orderFilterService.getOrderFilterRule(parmas);
		if(filterRuleDTO!=null){
			logger.info("命中的订单过滤规则(可能命中多条规则，这里抽取出来的是多条规则中的第一条)id:【"+ filterRuleDTO.getId() + "】-》" + filterRuleDTO.toString());
			paymentResult.setResponseCode(getMessageId());
			paymentResult.setResponseDesc(getMessage());
			return false;
		}
		return true;
	}
	
	private long ipToLong(String ipAddress) {  
		long result = 0;  
		String[] ipAddressInArray = ipAddress.split("\\.");  
		for (int i = 3; i >= 0; i--) {  
		    long ip = Long.parseLong(ipAddressInArray[3 - i]);  
		    result |= ip << (i * 8);  
		}  
		return result;  
	}

}
