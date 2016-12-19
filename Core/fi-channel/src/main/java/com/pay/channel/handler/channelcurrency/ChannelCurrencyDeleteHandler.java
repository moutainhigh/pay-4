package com.pay.channel.handler.channelcurrency;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.service.ChannelCurrencyService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
/**
 * 渠道币种配置删
 * @author delin
 *	@date 2016年6月21日17:44:32
 */
public class ChannelCurrencyDeleteHandler implements EventHandler{

	public final Log logger = LogFactory.getLog(ChannelCurrencyDeleteHandler.class);
	
	private ChannelCurrencyService channelCurrencyService;
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
			String id=paraMap.get("id")+"";
			channelCurrencyService.deleteChannelCurrency(id);
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

	
	
	public void setChannelCurrencyService(
			ChannelCurrencyService channelCurrencyService) {
		this.channelCurrencyService = channelCurrencyService;
	}
}
