package com.pay.channel.handler.channelcurrency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.model.ChannelCurrency;
import com.pay.channel.service.ChannelCurrencyService;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
/**
 * 渠道币种配置改
 * @author delin
 *	@date 2016年6月21日17:44:32
 */
public class ChannelCurrencyUpdateHandler   implements EventHandler{
	
	public final Log logger = LogFactory.getLog(ChannelCurrencyUpdateHandler.class);
	
	private ChannelCurrencyService channelCurrencyService;
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
			Map pageMap = (Map) paraMap.get("page");
			ChannelCurrency cc=new ChannelCurrency();
			BeanUtils.copyProperties(cc, paraMap);
			channelCurrencyService.updateChannelCurrency(cc);
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