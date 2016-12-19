/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.paymentchannelitem;

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
 * 渠道处理
 * 
 * @author chma
 */
public class PaymentChannelItemUpdateHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(PaymentChannelItemUpdateHandler.class);
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
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());

			String code = paraMap.get("code");
			String name = paraMap.get("name");
			String orgCode = paraMap.get("orgCode");
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
			String status = paraMap.get("status");
			String alias = paraMap.get("alias");
			String id = paraMap.get("id");
			String pattern = paraMap.get("pattern");
			String currencyCode = paraMap.get("currencyCode");
			String settlement = paraMap.get("settlement");
			String orgKey = paraMap.get("orgKey");
			String terminalCode = paraMap.get("terminalCode");
			String accessCode = paraMap.get("accessCode");
			String transType = paraMap.get("transType");
			String merchantBillName = paraMap.get("merchantBillName");

			PaymentChannelItem item = new PaymentChannelItem();
			item.setBackgroundCallbackUrl(backgroundCallbackUrl);
			if (!StringUtil.isEmpty(cardType)) {
				item.setCardType(Integer.valueOf(cardType));
			}

			item.setCode(code);
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
			item.setAccessCode(accessCode);
			item.setTransType(transType);
			item.setOrgKey(orgKey);
			item.setTerminalCode(terminalCode);
			
			if(!StringUtil.isEmpty(merchantBillName)){
				item.setMerchantBillName(merchantBillName);
			}
			
			if (!StringUtil.isEmpty(rate)) {
				item.setRate(rate);
			}
			if (!StringUtil.isEmpty(routeAmount)) {
				item.setRouteAmount(Long.valueOf(routeAmount));
			}
			if (!StringUtil.isEmpty(singleAmount)) {
				item.setSingleAmount(Long.valueOf(singleAmount));
			}
			if (!StringUtil.isEmpty(serialNo)) {
				item.setSerialNo(Long.valueOf(serialNo));
			}
			if (!StringUtil.isEmpty(signFlag)) {
				item.setSignFlag(Integer.valueOf(signFlag));
			}
			if (!StringUtil.isEmpty(status)) {
				item.setStatus(Integer.valueOf(status));
			}
			
			
			
			item.setSettlement(settlement);
			item.setAlias(alias);

			item.setPattern(pattern);
			item.setUpdateDate(new Date());
			item.setBankCode(bankCode);
			item.setId(Long.valueOf(id));
			item.setCurrencyCode(currencyCode);

			paymentChannelService.updatePaymentChannelItem(item);

			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		return JSonUtil.toJSonString(resultMap);
	}
}
