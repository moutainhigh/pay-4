/**
 * 
 */
package com.pay.risk.client;

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
public class AccountingClientService {

	private Log logger = LogFactory.getLog(getClass());
	private HessianInvokeService invokeService;
	private HessianInvokeService txncoreInvokeService;

	public void setTxncoreInvokeService(HessianInvokeService txncoreInvokeService) {
		this.txncoreInvokeService = txncoreInvokeService;
	}


	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	
	/**
	 * 基本汇率获取
	 * 
	 * @return
	 */
	public Map<String,Object> getSettlementRate(Map<String, Object> paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.RISK.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CURREBCR_RATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.RISK.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());	
		String hasRate = (String) resultMap.get("hasRate");		
		if("0".equals(hasRate))
			return null;
		return resultMap;
	}
	
	/**
	 * delin  2016年8月15日17:43:58 查询最新的清算汇率接口
	 * @param paraMap
	 * @return
	 */
	public Map<String,Object> getNewSSettlementRate(Map<String, Object> paraMap) {
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.RISK.getCode());
		String result = txncoreInvokeService.invoke(
				"txncore.newSettlementRateHandler", sysTraceNo,
				SystemCodeEnum.RISK.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());	
		return resultMap;
	}
	
	/**
	 * 基本汇率获取
	 * 
	 * @return
	 */
	public Map<String,Object> getBaseRate(Map<String, String> paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.GATEWAY.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_BASE_RATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.GATEWAY.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		String hasRate = (String) resultMap.get("hasRate");
		
		if("0".equals(hasRate))
			return null;
		return resultMap;
	}
}
