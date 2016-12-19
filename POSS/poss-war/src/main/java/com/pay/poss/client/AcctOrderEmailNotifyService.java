/**
 * 
 */
package com.pay.poss.client;

import java.util.HashMap;
import java.util.Map;

import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;

/**
 * 商户下单邮件通知服务
 * @author davis.guo at 2016-08-31
 * 调用fi-ordercenter
 */
public class AcctOrderEmailNotifyService {

	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}
	
	public Map orderEmailNotifyQuery(Map<String, Object> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());//GATEWAY
		String result = invokeService.invoke(
				SerCode.TXNCORE_ORDER_EMAIL_NOTIFY_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),//GATEWAY
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		return resultMap;
	}

	/***
	 * 商户邮件通知配置数据添加
	 * @param paraMap
	 * @return
	 */
	public Map orderEmailNotifyCreate(Map<String, Object> paraMap) {
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_ORDER_EMAIL_NOTIFY_AAD.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		return resultMap;
	}

	
	/***
	 * 商户邮件通知配置数据删除
	 * @param paraMap
	 * @return
	 */
	public Map orderEmailNotifyDelete(Map<String, Object> paraMap) {
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_ORDER_EMAIL_NOTIFY_DEL.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		return resultMap;
	}
	
	/***
	 * 商户邮件通知配置数据修改
	 * @param paraMap
	 * @return
	 */
	public Map orderEmailNotifyUpdate(Map<String, Object> paraMap) {
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_ORDER_EMAIL_NOTIFY_UPDATE.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		return resultMap;
	}
	
}
