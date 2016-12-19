/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.category;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.model.PaymentChannelCategory;
import com.pay.channel.service.PaymentChannelService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

/**
 * 渠道类别更新
 * 
 * @author chma
 */
public class PaymentChannelCategoryUpdateHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(PaymentChannelCategoryUpdateHandler.class);
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

			String id = requestMap.get("id");
			String code = requestMap.get("code");
			String name = requestMap.get("name");
			String status = requestMap.get("status");
			String operator = requestMap.get("operator");
			String description = requestMap.get("description");

			PaymentChannelCategory category = new PaymentChannelCategory();
			category.setCode(code);
			category.setDescription(description);
			category.setName(name);
			category.setOperator(operator);
			category.setStatus(Integer.valueOf(status));
			category.setUpdateDate(new Date());
			category.setId(Long.valueOf(id));
			paymentChannelService.updatePaymentChannelCategory(category);
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
