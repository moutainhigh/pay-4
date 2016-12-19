/**
 * 
 */
package com.pay.txncore.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.client.HessianProxyFactory;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;

/**
 * 风控服务接口
 * 
 * @author chaoyue
 *
 */
public class RiskClientService {
	private String preServerUrl;
	private long readTimeOut;
	private final Log logger = LogFactory.getLog(getClass());
	
	public void setPreServerUrl(String preServerUrl) {
		this.preServerUrl = preServerUrl;
	}
	public void setReadTimeOut(long readTimeOut) {
		this.readTimeOut = readTimeOut;
	}
	
	/**
	 * 获取指定渠道信息
	 * 
	 * @param partnerId
	 * @return
	 */
	public Map<String,String> validate(Map<String,String> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		logger.info("start-request-risk: "+System.currentTimeMillis());
		
		try{
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setReadTimeout(readTimeOut);
			HessianInvokeService hessianInvokeService = null;
			hessianInvokeService = (HessianInvokeService) factory.create(
						HessianInvokeService.class, preServerUrl);
			String result = hessianInvokeService.invoke(SerCode.RISK_VALIDATE.getCode(), sysTraceNo,
					SystemCodeEnum.TXNCORE.getCode(),
					SystemCodeEnum.RISK.getCode(),
					SystemCodeEnum.RISK.getVersion(), param.getDataLength(),
					param.getMsgCompress(), param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();		
			@SuppressWarnings("unchecked")
			Map<String, String> resultMap = JSonUtil.toObject(result,
					new HashMap<String, String>().getClass());			
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("e: "+e.getMessage());
			Map<String,String> respMap = new HashMap<String, String>();
			respMap.put("desc","ACCEPT");
			return respMap;
		}
	}
}
