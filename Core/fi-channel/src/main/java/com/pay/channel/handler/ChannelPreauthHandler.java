package com.pay.channel.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.channel.dto.ChannelPreathResponseDto;
import com.pay.channel.dto.PreauthInfo;
import com.pay.channel.service.ChannelService;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;


/**
 * 渠道预授权
 * @author peiyu.yang
 * @since 2015年6月11日
 */
public class ChannelPreauthHandler implements EventHandler {
    private static Logger logger = LoggerFactory.getLogger(ChannelPreauthHandler.class);
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
		
		PreauthInfo channelRequest = MapUtil.map2Object(PreauthInfo.class,
				paraMap);

		try {
			ChannelPreathResponseDto channelResponseDto = channelService
					.channelPreauth(channelRequest);
			
			logger.info("[channelResponseDto] :"+channelResponseDto);

			resultMap.putAll(MapUtil.bean2map(channelResponseDto));
		} catch (BusinessException e) {
			logger.error("ChannelPayHandler error:", e);
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
