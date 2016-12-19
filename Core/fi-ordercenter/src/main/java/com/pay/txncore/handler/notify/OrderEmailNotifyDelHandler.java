/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.notify;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.service.IOrderEmailNotifyService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 删除商户下单邮件通知配置
 * 
 * @author davis.guo at 2016-08-31
 */
public class OrderEmailNotifyDelHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(OrderEmailNotifyDelHandler.class);

	private IOrderEmailNotifyService orderEmailNotifyService;

	public void setOrderEmailNotifyService(
			IOrderEmailNotifyService orderEmailNotifyService) {
		this.orderEmailNotifyService = orderEmailNotifyService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map resultMap = new HashMap();

		Map<String, String> request = null;
		try {
			request = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		String id = request.get("id");

		if (StringUtil.isEmpty(id)) {
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		boolean result = orderEmailNotifyService.deleteById(Long.valueOf(id));

		if(result)
		{
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		}
		else
		{
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}
}
