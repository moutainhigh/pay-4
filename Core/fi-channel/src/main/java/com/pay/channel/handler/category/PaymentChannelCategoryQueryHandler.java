/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.category;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.pay.channel.service.PaymentChannelService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

/**
 * 查询所有渠道类别
 * 
 * @author chma
 */
public class PaymentChannelCategoryQueryHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(PaymentChannelCategoryQueryHandler.class);
	private PaymentChannelService paymentChannelService;

	public void setPaymentChannelService(
			PaymentChannelService paymentChannelService) {
		this.paymentChannelService = paymentChannelService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String,String> requestMap = null;
		Map resultMap = new HashMap();

		try {
			requestMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map map=new HashMap();
			String name= requestMap.get("name");
			String id = requestMap.get("id");
			if(!StringUtils.isEmpty(name)){
				map.put("name", name);
			}
			if(!StringUtils.isEmpty(id)){
				map.put("id",id);
			}
			resultMap.put("paymentChannelCategorys",
					paymentChannelService.queryPaymentChannelCategory(map));
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
