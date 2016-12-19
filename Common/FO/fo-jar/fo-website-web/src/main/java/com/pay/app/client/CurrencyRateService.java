/**
 * 
 */
package com.pay.app.client;

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
 * 货币汇率服务
 * 
 * @author chaoyue
 *
 */
public class CurrencyRateService {

	private HessianInvokeService invokeService;

	public void setInvokeService(final HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}
	
	/**
	 * 查询清算汇率列表
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public Map<String,Object> settlementRateQuery(final Map<String,Object> params) {
		String reqMsg = JSonUtil.toJSonString(params);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_SETTLEMENTRATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
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
	 * 查询清算汇率
	 * @param currency
	 * @param targetCurrency
	 * @param memberCode
	 * @param status
	 * @return
	 */
	public Map singleSettlementRateQuery(final String currency, final String targetCurrency,
			final String memberCode, final String status) {

		Map<String, Object> paraMap = new HashMap();
		paraMap.put("memberCode",memberCode);
		paraMap.put("currency", currency);
		paraMap.put("type","2");
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("status",status);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.GATEWAY.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CURREBCR_RATE_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.GATEWAY.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		String hasRate = (String) resultMap.get("hasRate");
		
		if("0".equals(hasRate))
			return null;

		
		return resultMap;
	}
	
}
