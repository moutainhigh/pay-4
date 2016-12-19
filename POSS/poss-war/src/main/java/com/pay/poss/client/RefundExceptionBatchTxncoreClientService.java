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
 * 渠道服务接口
 * @author PengJiangbo
 * 订单管理核心
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class RefundExceptionBatchTxncoreClientService {

	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}
	/**
	 * 批次及详情添加
	 * @param paraMap
	 * @return
	 */
	public Map<String, String> insertRefundExceptionBatch(Map<String,Object> paraMap) {
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_REFUND_EXCEPTION_BATCH_INSERT.getCode(), sysTraceNo,
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
	/**
	 * 
	 * @param paraMap
	 * @return
	 */
	public Map<String, String> updateRefundExceptionBatchDetail(Map<String,Object> paraMap) {
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_REFUND_EXCEPTION_BATCH_DETAIL_STATUS.getCode(), sysTraceNo,
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
	/**
	 * 
	 * @param paraMap
	 * @return
	 */
	public Map queryRefundExceptionBatch(Map<String,Object> paraMap) {
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_REFUND_EXCEPTION_BATCH_QUERY.getCode(), sysTraceNo,
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
	/**
	 * 
	 * @param paraMap
	 * @return
	 */
	public Map queryRefundExceptionBatchDetail(Map<String,Object> paraMap) {
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_REFUND_EXCEPTION_BATCH_SINGLE_DETAIL.getCode(), sysTraceNo,
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
