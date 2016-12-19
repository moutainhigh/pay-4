/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.paymentchannel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.model.PaymentChannel;
import com.pay.channel.service.PaymentChannelService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

/**
 * 渠道添加
 * 
 * @author chma
 */
public class PaymentChannelAddHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(PaymentChannelAddHandler.class);
	private PaymentChannelService paymentChannelService;

	public void setPaymentChannelService(
			PaymentChannelService paymentChannelService) {
		this.paymentChannelService = paymentChannelService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> requestMap = null;
		Map resultMap = new HashMap();

		try {
			requestMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());

			String code = requestMap.get("code");
			String name = requestMap.get("name");
			String operator = requestMap.get("operator");
			String description = requestMap.get("description");

			PaymentChannel channel = new PaymentChannel();
			channel.setCode(code);
			channel.setCreateDate(new Date());
			channel.setDescription(description);
			channel.setName(name);
			channel.setStatus(1);
			channel.setOperator(operator);
			channel.setUpdateDate(new Date());

			paymentChannelService.addPaymentChannel(channel);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}
}
