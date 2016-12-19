/**
 * 
 */
package com.pay.forpay.client;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;

/**
 * 交易服务接口
 * 
 * @author chaoyue
 *
 */
public class TxncoreClientService {

	private final Log logger = LogFactory.getLog(getClass());
	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	public Map crossApiPay(Map paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_API_ACQUIRE.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		return resultMap;
	}
	
	public Map queryTradeRate(Map paraMap) {
		paraMap.put("status", "1");
		paraMap.put("targetCurrency", "CNY");
		paraMap.put("type", "1");
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CURREBCR_RATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		
		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());
		
		return resultMap;
	}
}
