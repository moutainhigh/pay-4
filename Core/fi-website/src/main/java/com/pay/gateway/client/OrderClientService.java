/**
 * 
 */
package com.pay.gateway.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;

/**
 * 订单中心服务接口
 * @author JiangboPeng
 *
 */
public class OrderClientService {

	private Log logger = LogFactory.getLog(getClass());
	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	/**
	 * 跨境API收单
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryTradeOrderById(Map<String, Object> paraMap) {
		logger.info("method queryTradeOrderById start...");
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				"fi-ordercenter.queryTradeOrderByIdHandler", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> resultMap = JSonUtil.toObject(result, new HashMap<String, Object>().getClass());
		return resultMap;
	}
	
	
}
