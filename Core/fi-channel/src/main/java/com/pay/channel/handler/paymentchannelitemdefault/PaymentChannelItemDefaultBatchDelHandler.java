/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.paymentchannelitemdefault;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.model.PaymentChannelItemDefault;
import com.pay.channel.service.PaymentChannelService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

/**
 * 渠道处理
 * 
 * @author chma
 */
public class PaymentChannelItemDefaultBatchDelHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(PaymentChannelItemDefaultBatchDelHandler.class);
	private PaymentChannelService paymentChannelService;

	public void setPaymentChannelService(
			PaymentChannelService paymentChannelService) {
		this.paymentChannelService = paymentChannelService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			String operator = String.valueOf(paraMap.get("operator"));
			List<String> paymentChannelItemIds = (List<String>) paraMap
					.get("paymentChannelItemIds");
			List<PaymentChannelItemDefault> paymentChannelItemDefaults = null;
			if (null != paymentChannelItemIds
					&& !paymentChannelItemIds.isEmpty()) {
				paymentChannelItemDefaults = new ArrayList<PaymentChannelItemDefault>();
				for (String paymentChannelItemId : paymentChannelItemIds) {
					PaymentChannelItemDefault itemDefault = new PaymentChannelItemDefault();
					itemDefault.setCreateDate(new Date());
					itemDefault.setOperator(operator);
					itemDefault.setPaymentChannelItemId(Long
							.valueOf(paymentChannelItemId));
					paymentChannelItemDefaults.add(itemDefault);
				}
				paymentChannelService
						.delPaymentChannelItemDefault(paymentChannelItemDefaults);
			}

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
