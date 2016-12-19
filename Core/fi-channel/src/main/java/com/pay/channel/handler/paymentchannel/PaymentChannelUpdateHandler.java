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
 * 渠道处理
 * 
 * @author chma
 */
public class PaymentChannelUpdateHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(PaymentChannelUpdateHandler.class);
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

			String id = paraMap.get("id");
			String code = paraMap.get("code");
			String name = paraMap.get("name");
			String status = paraMap.get("status");
			String operator = paraMap.get("operator");
			String description = paraMap.get("description");

			PaymentChannel channel = new PaymentChannel();
			channel.setId(Long.valueOf(id));
			channel.setCode(code);
			channel.setDescription(description);
			channel.setName(name);
			channel.setStatus(Integer.valueOf(status));
			channel.setOperator(operator);
			channel.setUpdateDate(new Date());
			paymentChannelService.updatePaymentChannel(channel);
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
