/**
 * 
 */
package com.pay.fi.hession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fi.dto.ChargeBackOrder;
import com.pay.fi.dto.ExpressTracking;
import com.pay.fi.dto.RefundParamDTO;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 运单管理
 * 
 * @author chaoyue
 *
 */
public class RefundOrderService {

	private HessianInvokeService invokeService;
	private HessianInvokeService txnCoreService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}
	
	public void setTxnCoreService(HessianInvokeService txnCoreService) {
		this.txnCoreService = txnCoreService;
	}

	/**
	 * 跨境业务退款申请
	 * 
	 * @param crosspayRequestDTO
	 */
	public Map refund(RefundParamDTO refundApiRequest) {

		Map<String, String> paraMap = MapUtil.bean2map(refundApiRequest);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		//String result = invokeService.invoke( delete by LIBO for refund optimizing at 20161021
		String result = txnCoreService.invoke(
				SerCode.TXNCORE_CROSSPAY_ORDER_REFUND.getCode(), sysTraceNo,
				SystemCodeEnum.WEBSITE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}
	
	/*
	 * 
	 * @xiaodai
	 * 判断拒付订单service
	 * 
	 */
	
	//拒付网关订单查询service
	public Map queryChargeBackOrder(ChargeBackOrder chargeBackCondition,Page page) {
		Map paraMap = MapUtil.bean2map(chargeBackCondition);
		paraMap.put("page", page);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CHARGEBACK_ORDER_QUERY.getCode(), sysTraceNo,
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
