/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler;

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
import com.pay.util.MapUtil;

/**
 * 获取可用渠道
 * 
 * @author chma
 */
public class ChannelSpicQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(ChannelSpicQueryHandler.class);
	private PaymentChannelService paymentChannelService;

	public void setPaymentChannelService(
			PaymentChannelService paymentChannelService) {
		this.paymentChannelService = paymentChannelService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map resultMap = new HashMap();
		Map<String, String> paraMap = null;

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
		} catch (Exception e) {
			logger.error("ChannelSpicQueryHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		String orgCode = paraMap.get("orgCode");
		String orgMerchantCode = paraMap.get("orgMerchantCode");

		// 获取配置渠道
		PaymentChannelItem item = paymentChannelService
				.queryPaymentChannelItemByOrgCode(orgCode, orgMerchantCode);

		Map itemConfigMap = MapUtil.bean2map(item);

		return JSonUtil.toJSonString(itemConfigMap);
	}

}
