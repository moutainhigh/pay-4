/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.paymentchannelitem;

import java.util.HashMap;
import java.util.List;
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
public class PaymentChannelItemQueryHandler implements EventHandler {

	private final Log logger = LogFactory
			.getLog(PaymentChannelItemQueryHandler.class);
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
			String alias = paraMap.get("alias");
			String bankCode = paraMap.get("bankCode");
			String protocolType = paraMap.get("protocolType");
			String paymentCategoryCode = paraMap.get("paymentCategoryCode");
			String paymentChannelCode = paraMap.get("paymentChannelCode");
			String routeAmount = paraMap.get("routeAmount");
			String singleAmount = paraMap.get("singleAmount");
			String signFlag = paraMap.get("signFlag");
			String serialNo = paraMap.get("serialNo");
			String cardType = paraMap.get("cardType");
			String orgMerchantCode = paraMap.get("orgMerchantCode");
			String merchantBillName = paraMap.get("merchantBillName");

			PaymentChannelItem item = new PaymentChannelItem();
			if (!StringUtil.isEmpty(cardType)) {
				item.setCardType(Integer.valueOf(cardType));
			}
			item.setCode(code);
			item.setAlias(alias);
			item.setName(name);
			item.setOrgCode(orgCode);
			item.setOrgMerchantCode(orgMerchantCode);
			item.setPaymentCategoryCode(paymentCategoryCode);
			item.setPaymentChannelCode(paymentChannelCode);
			item.setProtocolType(protocolType);
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
			
			if(!StringUtil.isEmpty(merchantBillName)){
				item.setMerchantBillName(merchantBillName);
			}
			
			item.setBankCode(bankCode);
			List<PaymentChannelItem> paymentChannelItems = paymentChannelService
					.queryPaymentChannelItem(item);
			resultMap.put("paymentChannelItems", paymentChannelItems);
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
