/**
 * 
 */
package com.pay.gateway.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.gateway.dto.ExpressTracking;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 商户运单
 * 
 * @author chaoyue
 *
 */
public class ExpressTrackingService {

	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	/**
	 * 获取商户运单域名
	 * 
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public List<Map> expressTrackingQuery(ExpressTracking expressTracking) {

		Map<String, String> paraMap = MapUtil.bean2map(expressTracking);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.GATEWAY.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_EXPRESS_TRACKING_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.GATEWAY.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		List<Map> returnMap = (List<Map>) resultMap.get("list");

		return returnMap;
	}

}
