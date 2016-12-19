/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.paymentchannelitemconfig;

import java.util.ArrayList;
import java.util.Date;
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

/**
 * 渠道处理
 * 
 * @author chma
 */
public class PaymentChannelItemConfigBatchAddHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(PaymentChannelItemConfigBatchAddHandler.class);
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

			Long memberCode = Long
					.valueOf(paraMap.get("memberCode").toString());
			Integer paymentType = Integer.valueOf(paraMap.get("paymentType")
					.toString());
			String operator = String.valueOf(paraMap.get("operator"));
			Object ids = paraMap.get("paymentChannelItemIds");
			String auto = (String)paraMap.get("auto");
			List<String> paymentChannelItemIds = (List<String>) ids;
			List<PaymentChannelItemConfig> paymentChannelItemConfigs = null;
			if (null != paymentChannelItemIds
					&& !paymentChannelItemIds.isEmpty()) {
				paymentChannelItemConfigs = new ArrayList<PaymentChannelItemConfig>();
				for (String paymentChannelItemId : paymentChannelItemIds) {
					PaymentChannelItemConfig itemConfig = new PaymentChannelItemConfig();
					itemConfig.setMemberCode(memberCode);
					itemConfig.setPaymentChannelItemId(Long
							.valueOf(paymentChannelItemId));
					itemConfig.setPaymentType(paymentType);
					if(paymentChannelService.checkPaymentChannelItemConfigExists(itemConfig)){
						continue;
					}
					itemConfig.setCreateDate(new Date());
					itemConfig.setOperator(operator);
					itemConfig.setPaymentType(paymentType);
					paymentChannelItemConfigs.add(itemConfig);
				}
				paymentChannelService
						.delPaymentChannelItemConfig(paymentChannelItemConfigs);
				paymentChannelService
						.addPaymentChannelItemConfig(paymentChannelItemConfigs,auto);
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
