/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.paymentchannelitem;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.pay.channel.redis.model.ChannelObj;
import com.pay.channel.service.AutoFitEngineService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.dao.ChannelConfigDAO;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

/**
 * 渠道处理
 * 
 * @author chma
 */
public class ChannelConfigDelHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(ChannelConfigDelHandler.class);
	private ChannelConfigDAO channelConfigDAO;
	private AutoFitEngineService autoFitEngineService;

	public void setAutoFitEngineService(AutoFitEngineService autoFitEngineService) {
		this.autoFitEngineService = autoFitEngineService;
	}

	public void setChannelConfigDAO(ChannelConfigDAO channelConfigDAO) {
		this.channelConfigDAO = channelConfigDAO;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Long id = Long.valueOf(((String) paraMap.get("id")));
			ChannelObj obj = new ChannelObj();
			obj.setChannelConfigId(new BigDecimal(id));
			obj.setDealType("3");
			obj.setSubDealType("1");
			autoFitEngineService.product2Redis(obj);
			channelConfigDAO.delete(id);
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
