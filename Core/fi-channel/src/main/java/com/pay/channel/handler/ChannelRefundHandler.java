/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.dto.ChannelRequestDto;
import com.pay.channel.dto.ChannelResponseDto;
import com.pay.channel.service.ChannelService;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 渠道处理
 * 
 * @author chma
 */
public class ChannelRefundHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(ChannelRefundHandler.class);
	private ChannelService channelService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
		} catch (Exception e) {
			logger.error("error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		ChannelRequestDto channelRequest = MapUtil.map2Object(
				ChannelRequestDto.class, paraMap);

		try {
			ChannelResponseDto channelResponseDto = channelService
					.channelRefund(channelRequest);

			resultMap.putAll(MapUtil.bean2map(channelResponseDto));
		} catch (BusinessException e) {
			logger.error("Channel REFUND Handler error:", e);
			ExceptionCodeEnum error = e.getCode();
			resultMap.put("responseCode", error.getCode());
			resultMap.put("responseDesc", error.getDescription());
		} catch (Exception e) {
			logger.error("ChannelPayHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}
}
