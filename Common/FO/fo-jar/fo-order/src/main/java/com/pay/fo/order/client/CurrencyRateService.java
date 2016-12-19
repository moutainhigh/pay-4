/**
 * 
 */
package com.pay.fo.order.client;

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
	 * 清算汇率查询
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
	
}
