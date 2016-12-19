package com.pay.channel.handler.channelitemrcurrency;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.model.ChannelItemRCurrency;
import com.pay.channel.service.ChannelItemRCurrencyService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
/**
 * 支付渠道币种配置
 * @author cuber
 *	@date 2016年6月29日17:44:32
 */
public class ChannelItemRCurrencyUpdateHandler   implements EventHandler{
	
	public final Log logger = LogFactory.getLog(ChannelItemRCurrencyUpdateHandler.class);
	
	private ChannelItemRCurrencyService channelItemRCurrencyService;
	
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
			Map pageMap = (Map) paraMap.get("page");
			ChannelItemRCurrency cc=new ChannelItemRCurrency();
			BeanUtils.copyProperties(cc, paraMap);
			channelItemRCurrencyService.validChannelItemRCurrency(cc);
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

	
	public void setChannelItemRCurrencyService(ChannelItemRCurrencyService channelItemRCurrencyService) {
		this.channelItemRCurrencyService = channelItemRCurrencyService;
	}
}