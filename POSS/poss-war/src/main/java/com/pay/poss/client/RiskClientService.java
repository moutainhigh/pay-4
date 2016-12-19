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
 * 收单核心调用客户端
 * @author peiyu.yang
 * @since 2016年6月16日19:45:10
 */
public class RiskClientService {

	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	/**
	 * 查询渠道类别
	 * 
	 * @return
	 */
	public Map<String,String> refreshBlackWhiteList() {
		Map<String,String> paraMap = new HashMap<String,String>();
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_BLACKLIST_REFRESH.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.RISK.getCode(),
				SystemCodeEnum.RISK.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		@SuppressWarnings("unchecked")
		Map<String,String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());
		return resultMap;
	}
}
