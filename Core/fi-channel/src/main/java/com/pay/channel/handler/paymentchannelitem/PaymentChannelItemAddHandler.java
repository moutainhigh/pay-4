/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.paymentchannelitem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.model.PaymentChannelItem;
import com.pay.channel.service.PaymentChannelService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 通道添加
 * 
 * @author chma
 */
public class PaymentChannelItemAddHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(PaymentChannelItemAddHandler.class);
	private PaymentChannelService paymentChannelService;

	public void setPaymentChannelService(
			PaymentChannelService paymentChannelService) {
		this.paymentChannelService = paymentChannelService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			String code = paraMap.get("code");
			String status = paraMap.get("status");
			String name = paraMap.get("name");
			String orgCode = paraMap.get("orgCode");
			String alias = paraMap.get("alias");
			String bankCode = paraMap.get("bankCode");
			String rate = paraMap.get("rate");
			String protocolType = paraMap.get("protocolType");
			String preServerUrl = paraMap.get("preServerUrl");
			String labelClass = paraMap.get("labelClass");
			String paymentCategoryCode = paraMap.get("paymentCategoryCode");
			String paymentChannelCode = paraMap.get("paymentChannelCode");
			String routeAmount = paraMap.get("routeAmount");
			String singleAmount = paraMap.get("singleAmount");
			String orgMerchantCode = paraMap.get("orgMerchantCode");
			String frontCallbackUrl = paraMap.get("frontCallbackUrl");
			String backgroundCallbackUrl = paraMap.get("backgroundCallbackUrl");
			String signFlag = paraMap.get("signFlag");
			String serialNo = paraMap.get("serialNo");
			String cardType = paraMap.get("cardType");
			String operator = paraMap.get("operator");
			String description = paraMap.get("description");
			String settlement = paraMap.get("settlement");
			String currencyCode = paraMap.get("currencyCode");
			String orgKey = paraMap.get("orgKey");
			String terminalCode = paraMap.get("terminalCode");
			String accessCode = paraMap.get("accessCode");
			String transType = paraMap.get("transType");
			String pattern = paraMap.get("pattern");
			String merchantBillName = paraMap.get("merchantBillName");
			
			PaymentChannelItem item = new PaymentChannelItem();
			item.setBackgroundCallbackUrl(backgroundCallbackUrl);
			item.setCardType(Integer.valueOf(cardType));
			item.setCode(code);
			item.setAlias(alias);
			item.setCreateDate(new Date());
			item.setDescription(description);
			item.setFrontCallbackUrl(frontCallbackUrl);
			item.setLabelClass(labelClass);
			item.setName(name);
			item.setOperator(operator);
			item.setOrgCode(orgCode);
			item.setOrgMerchantCode(orgMerchantCode);
			item.setPaymentCategoryCode(paymentCategoryCode);
			item.setPaymentChannelCode(paymentChannelCode);
			item.setPreServerUrl(preServerUrl);
			item.setProtocolType(protocolType);
			item.setRate(rate);
			item.setCurrencyCode(currencyCode);
			item.setAccessCode(accessCode);
			item.setTransType(transType);
			item.setOrgKey(orgKey);
			item.setTerminalCode(terminalCode);
			item.setPattern(pattern);
			item.setMerchantBillName(merchantBillName);

			if (!StringUtil.isEmpty(routeAmount)) {
				item.setRouteAmount(new BigDecimal(routeAmount).multiply(
						new BigDecimal("1000")).longValue());
			}
			if (!StringUtil.isEmpty(singleAmount)) {
				item.setSingleAmount(new BigDecimal(singleAmount).multiply(
						new BigDecimal("1000")).longValue());
			}
			item.setSerialNo(StringUtil.isEmpty(serialNo)?null:Long.valueOf(serialNo));
			item.setSignFlag(StringUtil.isEmpty(signFlag)?null:Integer.valueOf(signFlag));
			item.setStatus(Integer.valueOf(status));
			item.setUpdateDate(new Date());
			item.setBankCode(bankCode);
			item.setSettlement(settlement);

			paymentChannelService.addPaymentChannelItem(item);

			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}
}
