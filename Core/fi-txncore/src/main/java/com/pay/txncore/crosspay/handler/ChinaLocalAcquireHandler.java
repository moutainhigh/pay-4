package com.pay.txncore.crosspay.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.client.ChannelClientService;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.service.ChinaLocalPayService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class ChinaLocalAcquireHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private ChinaLocalPayService localPayService;
	private ChannelClientService channelClientService;
	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap<String, Object>().getClass());
			if (logger.isInfoEnabled()) {
				logger.info("request map:" + paraMap);
			}
			PaymentInfo paymentInfo = MapUtil.map2Object(PaymentInfo.class, paraMap);
			
			PaymentResult paymentResult = localPayService.chinaLocaleAcquire(paymentInfo);
			paraMap.putAll(MapUtil.bean2map(paymentResult));
			Map<String, Object> hnaPayMap=channelClientService.hnaPay(paraMap);
			resultMap.put("result", MapUtil.bean2map(paymentResult));
			resultMap.put("dataMap", hnaPayMap);
			logger.info("resultMap in CrosspayLocaleAcquireHandler : " + resultMap.toString());

			String responseCode = paymentResult.getResponseCode();
			if (responseCode != null) {
				resultMap.put("responseCode", responseCode);
				resultMap.put("responseDesc", paymentResult.getResponseDesc());
			} else {
				resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			}
		} catch (Exception e) {
			logger.error("locale pay error:", e);
			resultMap.put("responseCode", ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}
	public ChinaLocalPayService getLocalPayService() {
		return localPayService;
	}
	public void setLocalPayService(ChinaLocalPayService localPayService) {
		this.localPayService = localPayService;
	}
	public ChannelClientService getChannelClientService() {
		return channelClientService;
	}
	public void setChannelClientService(ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

}
