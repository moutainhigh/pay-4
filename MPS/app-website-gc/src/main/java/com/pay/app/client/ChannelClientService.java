package com.pay.app.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;

public class ChannelClientService {

	private Log logger = LogFactory.getLog(getClass());
	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	// 订单管理核心
	public Map<String, Object> queryHnaRate(Map<String, Object> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.WEBSITE.getCode());
		String result = invokeService.invoke("fi-channel.hnaRateQueryHandler", sysTraceNo,
				SystemCodeEnum.WEBSITE.getCode(), SystemCodeEnum.CHANNEL.getCode(), SystemCodeEnum.CHANNEL.getVersion(),
				param.getDataLength(), param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		Map<String, Object> returnMap = (Map) resultMap.get("result");
		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
			logger.info("returnMap:" + returnMap);
		}
		return returnMap;
	}
}
