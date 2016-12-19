/**
 * 
 */
package com.pay.gateway.client;

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
public class ChannelClientService {

	private final Log logger = LogFactory.getLog(getClass());
	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	/**
	 * 获取商户支付渠道
	 * 
	 * @param partnerId
	 * @return
	 */
	public Map getPaymentChannel(String partnerId, String paymentType,
			String memberType, String transType) {
		Map<String, String> paraMap = new HashMap();

		paraMap.put("memberCode", partnerId);
		paraMap.put("paymentType", paymentType);
		paraMap.put("memberType", memberType);
		paraMap.put("transType", transType);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.CHANNEL_QUERY_MEMBER_CONFIG.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());

		return resultMap;
	}

}
