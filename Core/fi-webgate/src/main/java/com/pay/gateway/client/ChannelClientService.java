/**
 * 
 */
package com.pay.gateway.client;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.caucho.hessian.client.HessianProxyFactory;
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
	public Map<String,Object> getPaymentChannel(String partnerId, String paymentType,
			String memberType, String transType) {
		Map<String, String> paraMap = new HashMap<String, String>();
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

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	/**
	 * 根据指定的渠道编号获取商户对应的支付渠道
	 * 
	 * @param partnerId
	 * @return
	 */
	public Map<String,Object> getPaymentChannelByPayChannel(String partnerId, String paymentType,
			String memberType, String transType,String payChannel) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("memberCode", partnerId);
		paraMap.put("paymentType", paymentType);
		paraMap.put("memberType", memberType);
		paraMap.put("transType", transType);
		if(!StringUtils.isBlank(payChannel)){
			paraMap.put("labelClass", "b_"+payChannel);
		}
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

		Map<String,Object> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	
	public Map channelPay(Map paraMap) {
		Map para=(Map) paraMap.get("parameters");
		String preServerUrl=para.get("preServerUrl")+"";
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		try {
			HessianProxyFactory factory = new HessianProxyFactory();
			HessianInvokeService hessianInvokeService = null;		
			hessianInvokeService = (HessianInvokeService) factory.create(HessianInvokeService.class,
					preServerUrl);
			String result = hessianInvokeService.invoke(SerCode.PRE_PAY.getCode(),sysTraceNo,
					SystemCodeEnum.CHANNEL.getCode(),
					SystemCodeEnum.PRE.getCode(),
					SystemCodeEnum.PRE.getVersion(), param.getDataLength(),
					param.getMsgCompress(), param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();

			Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
			System.out.println(resultMap);
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
