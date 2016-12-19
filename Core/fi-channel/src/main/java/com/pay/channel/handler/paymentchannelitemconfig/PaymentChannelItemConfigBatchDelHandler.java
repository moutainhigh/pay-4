/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.paymentchannelitemconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.model.PaymentChannelItemConfig;
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
public class PaymentChannelItemConfigBatchDelHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(PaymentChannelItemConfigBatchDelHandler.class);
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
			String memberCode = paraMap.get("memberCode");
			String paymentType = paraMap.get("paymentType");
			String paymentChannelItemId = paraMap.get("paymentChannelItemId");

			List<PaymentChannelItemConfig> paymentChannelItemConfigs = new ArrayList<PaymentChannelItemConfig>();
			PaymentChannelItemConfig itemConfig = new PaymentChannelItemConfig();
			itemConfig.setMemberCode(Long.valueOf(memberCode));
			itemConfig.setPaymentChannelItemId(Long
					.valueOf(paymentChannelItemId));
			if (!StringUtil.isEmpty(paymentType)) {
				itemConfig.setPaymentType(Integer.valueOf(paymentType));
			}
			paymentChannelItemConfigs.add(itemConfig);
			paymentChannelService
					.delPaymentChannelItemConfig(paymentChannelItemConfigs);
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
