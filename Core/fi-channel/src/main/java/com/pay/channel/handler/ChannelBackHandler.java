/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;

/**
 * 渠道处理
 * 
 * @author chma
 */
public class ChannelBackHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(ChannelBackHandler.class);
	private Map<String, HessianInvokeService> invokeServices;

	public void setInvokeServices(
			Map<String, HessianInvokeService> invokeServices) {
		this.invokeServices = invokeServices;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		Map parameters = (Map) paraMap.get("parameters");
		String bankCode = (String) paraMap.get("bankCode");

		Map signatureRespDTO = new HashMap();
		HessianInvokeService invokeService = invokeServices.get(bankCode);

		if (invokeService == null) {
			logger.error("无法获取选择的银行服务");
			signatureRespDTO.put("responseCode",
					ResponseCodeEnum.UNDEFINED_SERVICE.getCode());
			signatureRespDTO.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_SERVICE.getDesc());
			return JSonUtil.toJSonString(signatureRespDTO);
		}

		String paraMsg = JSonUtil.toJSonString(parameters);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(paraMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
		String targetNo = bankCode.substring(0, 3);

		logger.info("targetNo:" + targetNo);
		if ("000".equals(targetNo)) {
			targetNo = "299";
		}

		String result = invokeService.invoke(targetNo + "02", sysTraceNo,
				SystemCodeEnum.CHANNEL.getCode(), targetNo,
				SystemCodeEnum.MOCK.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		signatureRespDTO = JSonUtil.toObject(result, new HashMap().getClass());

		return JSonUtil.toJSonString(signatureRespDTO);
	}

}
