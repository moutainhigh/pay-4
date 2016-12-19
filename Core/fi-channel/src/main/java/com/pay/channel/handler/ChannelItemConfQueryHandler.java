/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.channel.dao.ChannelConfigDAO;
import com.pay.channel.dao.ChannelSecondRelationDAO;
import com.pay.channel.model.ChannelConfig;
import com.pay.channel.model.ChannelSecondRelation;
import com.pay.channel.model.PaymentChannelItem;
import com.pay.channel.model.PaymentChannelItemConfig;
import com.pay.channel.service.PaymentChannelService;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 获取可用渠道
 * 
 * @author chma
 */
public class ChannelItemConfQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(ChannelItemConfQueryHandler.class);
	private PaymentChannelService paymentChannelService;

	public void setPaymentChannelService(
			PaymentChannelService paymentChannelService) {
		this.paymentChannelService = paymentChannelService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String, String> paraMap = null;

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
		} catch (Exception e) {
			logger.error("ChannelQueryHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		String paymentType = paraMap.get("paymentType");
		String memberCode = paraMap.get("memberCode");
		
		List<PaymentChannelItemConfig> itemList = paymentChannelService.
				           queryPaymentChannelItemConf(paymentType, memberCode);
		resultMap.put("channelItemConfList", itemList);
		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}
}
