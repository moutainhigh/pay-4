/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.risk.validate.rule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import com.pay.inf.rule.MessageRule;
import com.pay.risk.commons.RiskBlackWhiteListService;
import com.pay.risk.dto.PaymentInfo;
import com.pay.risk.dto.PaymentResult;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListDto;
import com.pay.util.IPUtil;
import com.pay.util.IPv4Util;
import com.pay.util.StringUtil;

/**
 * 白名单判断
 */
public class RiskWhiteListCheckRule extends MessageRule {
	
	private static Logger logger = LoggerFactory.getLogger(RiskWhiteListCheckRule.class);

	private RiskBlackWhiteListService  riskBlackListService;
	
	public void setRiskBlackListService(RiskBlackWhiteListService riskBlackListService) {
		this.riskBlackListService = riskBlackListService;
	}

	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentInfo paymentInfo = (PaymentInfo) validateBean;
		PaymentResult paymentResult = paymentInfo.getPaymentResult();

		List<BlackWhiteListDto> blackWhiteListDtos = null;
		String ip = paymentInfo.getCustomerIP();
		
		// 校验IP
		blackWhiteListDtos = riskBlackListService.getBlackWhiteList("3-1");
		if (null != blackWhiteListDtos && !blackWhiteListDtos.isEmpty()
				&&!StringUtil.isEmpty(ip)&&IPUtil.validateIp(ip)) {
			logger.info("ip: "+ip);
			for (BlackWhiteListDto dto : blackWhiteListDtos) {
				// 全匹配
				if (dto.getPartType() == 1) {
					if (dto.getContent().equals(ip)) {
						paymentResult.setResponseCode(getMessageId());
						paymentResult.setResponseDesc("IP白名单");
						logger.info("IP白名单,全匹配,IP: "+ip);
						return false;
					}
				} else if (dto.getPartType() == 2) {//半匹配
					if (ip.startsWith(dto.getContent())) {
						paymentResult.setResponseCode(getMessageId());
						paymentResult.setResponseDesc("IP白名单");
						logger.info("IP白名单,半匹配,content: "+dto.getContent());
						return false;
					}
				}else if(dto.getPartType() == 3){
					boolean rst = ip.contains(dto.getContent());
					if(rst){
						paymentResult.setResponseCode(getMessageId());
						paymentResult.setResponseDesc("IP白名单");
						logger.info("IP白名单,正则表达式,content: "+dto.getContent());
						return false;
					}
				}
			}
		}
		
		// 校验IP
		blackWhiteListDtos = riskBlackListService.getBlackWhiteList("11-1");
		if (null != blackWhiteListDtos && !blackWhiteListDtos.isEmpty()
				&&!StringUtil.isEmpty(ip)&&IPUtil.validateIp(ip)) {
			logger.info("ip: "+ip);
			for (BlackWhiteListDto dto : blackWhiteListDtos) {
				 if(dto.getPartType() == 4&&dto.getValue1()!=null
						&&dto.getValue2()!=null){//范围
					int ipInt = IPv4Util.ipToInt(ip);
					int ip1 = IPv4Util.ipToInt(dto.getValue1());
					int ip2 = IPv4Util.ipToInt(dto.getValue2());
					if(ipInt>=ip1&&ipInt<=ip2){
						paymentResult.setResponseCode(getMessageId());
						paymentResult.setResponseDesc("IP白名单");
						logger.info("IP白名单,IP地址段：value1: "
	                               +dto.getValue1()+",value2: "+dto.getValue2());
						return false;
					}
				}
			}
		}
		
		// 校验邮箱
		String billEmail = paymentInfo.getBillEmail();
		String shippingMail = paymentInfo.getShippingMail();
		String cardHolderEmail = paymentInfo.getCardHolderEmail();
		blackWhiteListDtos = riskBlackListService.getBlackWhiteList("2-1");
		if (null != blackWhiteListDtos && !blackWhiteListDtos.isEmpty()) {
			for (BlackWhiteListDto dto : blackWhiteListDtos) {
				// 账单邮箱，全匹配
				if (dto.getPartType() == 1) {
					if (dto.getContent().equalsIgnoreCase(billEmail)) {
						paymentResult.setResponseCode(getMessageId());
						paymentResult.setResponseDesc("账单邮箱白名单");
						return false;
					}
					if (dto.getContent().equalsIgnoreCase(shippingMail)) {
						paymentResult.setResponseCode(getMessageId());
						paymentResult.setResponseDesc("收件邮箱白名单");
						return false;
					}
					if (dto.getContent().equalsIgnoreCase(cardHolderEmail)) {
						paymentResult.setResponseCode(getMessageId());
						paymentResult.setResponseDesc("支付邮箱白名单");
						return false;
					}
				} else if (dto.getPartType() == 2) {					
					String content=null;
					if(!StringUtil.isEmpty(dto.getContent())){
						content = dto.getContent().toLowerCase();
					}
					if(!StringUtil.isEmpty(billEmail)){
						billEmail = billEmail.toLowerCase();
						if (billEmail.startsWith(content)) {
							paymentResult.setResponseCode(getMessageId());
							paymentResult.setResponseDesc("账单邮箱白名单");
							return false;
						}
					}
					if(!StringUtil.isEmpty(shippingMail)){
						shippingMail = shippingMail.toLowerCase();
						if (shippingMail.startsWith(content)) {
							paymentResult.setResponseCode(getMessageId());
							paymentResult.setResponseDesc("收件邮箱白名单");
							return false;
						}
					}
					if(!StringUtil.isEmpty(cardHolderEmail)){
						cardHolderEmail = cardHolderEmail.toLowerCase();
						if (cardHolderEmail.startsWith(content)) {
							paymentResult.setResponseCode(getMessageId());
							paymentResult.setResponseDesc("支付邮箱白名单");
							return false;
						}
					}
				}else if (dto.getPartType() == 3) {					
					String content=null;
					if(!StringUtil.isEmpty(dto.getContent())){
						content = dto.getContent().toLowerCase();
					}
					if(!StringUtil.isEmpty(billEmail)){
						billEmail = billEmail.toLowerCase();
						boolean rst = billEmail.contains(content);
						if (rst) {
							paymentResult.setResponseCode(getMessageId());
							paymentResult.setResponseDesc("账单邮箱白名单");
							return false;
						}
					}
					if(!StringUtil.isEmpty(shippingMail)){
						shippingMail = shippingMail.toLowerCase();
						boolean rst = shippingMail.contains(content);
						if (rst) {
							paymentResult.setResponseCode(getMessageId());
							paymentResult.setResponseDesc("收件邮箱白名单");
							return false;
						}
					}
					if(!StringUtil.isEmpty(cardHolderEmail)){
						cardHolderEmail = cardHolderEmail.toLowerCase();
						boolean rst = cardHolderEmail.contains(content);
						if (rst) {
							paymentResult.setResponseCode(getMessageId());
							paymentResult.setResponseDesc("支付邮箱白名单");
							return false;
						}
					}
				}
			}
		}

		String billAddress = paymentInfo.getBillAddress();
		String shippingAddress = paymentInfo.getShippingAddress();
		blackWhiteListDtos =riskBlackListService.getBlackWhiteList("8-1");
		if (null != blackWhiteListDtos && !blackWhiteListDtos.isEmpty()) {
			for (BlackWhiteListDto dto : blackWhiteListDtos) {
				// 账单邮箱，全匹配
				if (dto.getPartType() == 1) {
					if (dto.getContent().equals(shippingAddress)) {
						paymentResult.setResponseCode(getMessageId());
						paymentResult.setResponseDesc("收件地址黑名单");
						return false;
					}

				}
			}
		}
		
		blackWhiteListDtos =riskBlackListService.getBlackWhiteList("9-1");
		if (null != blackWhiteListDtos && !blackWhiteListDtos.isEmpty()) {
			for (BlackWhiteListDto dto : blackWhiteListDtos) {
				// 账单邮箱，全匹配
				if (dto.getPartType() == 1) {
					if (dto.getContent().equals(billAddress)) {
						paymentResult.setResponseCode(getMessageId());
						paymentResult.setResponseDesc("账单地址黑名单");
						return false;
					}
				}
			}
		}

		String cardHolderNumber = paymentInfo.getCardHolderNumber();
		// 判断卡号
		blackWhiteListDtos =riskBlackListService.getBlackWhiteList("1-1");
		if (null != blackWhiteListDtos&&!blackWhiteListDtos.isEmpty()
				                    &&!StringUtil.isEmpty(cardHolderNumber)) {
			for (BlackWhiteListDto dto : blackWhiteListDtos) {
				// 账单邮箱，全匹配
				if (dto.getPartType() == 1) {
					if (dto.getContent().equals(cardHolderNumber)) {
						paymentResult.setResponseCode(getMessageId());
						paymentResult.setResponseDesc("银行卡号白名单");
						return false;
					}

				}else if (dto.getPartType() == 2) {
					if (cardHolderNumber.startsWith(dto.getContent())) {
						paymentResult.setResponseCode(getMessageId());
						paymentResult.setResponseDesc("银行卡号白名单");
						return false;
					}
				}else if(dto.getPartType() == 3){
					boolean rst = cardHolderNumber.contains(dto.getContent());
					if(rst){
						paymentResult.setResponseCode(getMessageId());
						paymentResult.setResponseDesc("银行卡号白名单");
						logger.info("银行卡号白名单,正则表达式,content: "+dto.getContent());
						return false;
					}
				}
			}
		}
		
		blackWhiteListDtos =riskBlackListService.getBlackWhiteList("10-1");
		if (null != blackWhiteListDtos && !blackWhiteListDtos.isEmpty()
				                        &&!StringUtil.isEmpty(cardHolderNumber)) {
			for (BlackWhiteListDto dto : blackWhiteListDtos) {
				if(dto.getPartType() == 4){
					long cardNo = Long.valueOf(cardHolderNumber);
					long cardNo1 =  Long.valueOf(dto.getValue1());
					long cardNo2 =  Long.valueOf(dto.getValue2());
					if(cardNo>=cardNo1&&cardNo<=cardNo2){
						paymentResult.setResponseCode(getMessageId());
						paymentResult.setResponseDesc("银行卡号白名单");
						logger.info("银行卡号白名单,IP地址段：value1: "
	                               +dto.getValue1()+",value2: "+dto.getValue2());
						return false;
					}
				}
			}
		}
		return true;
	}

}
