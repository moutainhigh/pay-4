package com.pay.channel.handler.channelcurrency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.model.ChannelCrrencyReviewed;
import com.pay.channel.service.ChannelCurrencyService;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class ChannelCurrencyReviewedHandler implements EventHandler {
		
	public final Log logger = LogFactory
			.getLog(ChannelCurrencyReviewedHandler.class);

	private ChannelCurrencyService channelCurrencyService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();
			

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
			String id=	paraMap.get("id");
			String status=paraMap.get("status");
			String flag=paraMap.get("flag");
			String channelCurrencyId=paraMap.get("channelCurrencyId");
			channelCurrencyService.updateChannelCurrencyRev(paraMap); //更改审核状态
			if(status.equals("1")){//审核通过需要对不同的数据类型做不同的操作
					if(flag.equals("1")){//新增
						paraMap.put("channelCurrencyRevId", id);
						channelCurrencyService.addChannelCurrency(paraMap);
					}else if(flag.equals("2")){//修改
						channelCurrencyService.updateChannelCurrency(paraMap);
					}else if(flag.equals("3")){//删除
						channelCurrencyService.deleteChannelCurrency(channelCurrencyId);
					}
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
	
	public void setChannelCurrencyService(
			ChannelCurrencyService channelCurrencyService) {
		this.channelCurrencyService = channelCurrencyService;
	}
}
