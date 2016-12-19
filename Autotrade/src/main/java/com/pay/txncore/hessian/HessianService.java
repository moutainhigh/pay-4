/**
 * 
 */
package com.pay.txncore.hessian;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.trace.ThreadLocalLog;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;
import com.pay.util.ZipUtil;

/**
 * @author chaoyue
 * 
 */
public class HessianService implements HessianInvokeService {

	private final Log logger = LogFactory.getLog(HessianService.class);

	private Map<String, EventHandler> eventHandlerMap;

	public void setEventHandlerMap(Map<String, EventHandler> eventHandlerMap) {
		this.eventHandlerMap = eventHandlerMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.inf.service.HessianInvokeService#invoke(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * int, int, java.lang.String)
	 */
	@Override
	public String invoke(String serCode, String sysTraceNo, String originNo,
			String targetNo, String versionNo, int dataLength, int msgCompress,
			String dataMsg) {
		String[] traceInfo=sysTraceNo.split("-");
		if(traceInfo!=null&&traceInfo.length>2){
			ThreadLocalLog.setTrace(traceInfo[1], Integer.parseInt(traceInfo[2]));
		}
		logger.info("requet auth system:" + "serCode:" + serCode
				+ "sysTraceNo:" + sysTraceNo + "originNo:" + originNo
				+ "targetNo:" + targetNo + "versionNo:" + versionNo);
		//logger.info("dataMsg:" + dataMsg); Mack comment this line 2016年5月30日

		Map<String, String> result = new HashMap<String, String>();
		try {
			// 验证请求参数
			HessianInvokeHelper.validateReqParam(serCode, sysTraceNo, originNo,
					targetNo, versionNo, dataLength, msgCompress, dataMsg);
			// 验证请求服务代码是否正确
			EventHandler handler = eventHandlerMap.get(serCode);
			if (handler == null) {
				throw new HessianInvokeException(
						ResponseCodeEnum.UNDEFINED_SERVICE.getCode(),
						ResponseCodeEnum.UNDEFINED_SERVICE.getDesc());
			}

			// 验证目标系统编码
			HessianInvokeHelper.validateTargetNo(targetNo,
					SystemCodeEnum.TXNCORE.getCode());
			// 验证请求消息正文内容长度
			HessianInvokeHelper.validateDataMsgSize(dataLength, dataMsg);

			String reqMsg = dataMsg;
			// 判断是否需要解压请求消息正文内容
			if (msgCompress == 1) {
				try {
					reqMsg = ZipUtil.uncompress(dataMsg);
				} catch (IOException e) {
					throw new HessianInvokeException(
							ResponseCodeEnum.UNCOMPRESS_FAILURE.getCode(),
							ResponseCodeEnum.UNCOMPRESS_FAILURE.getDesc(), e);
				}
			}
			logger.info("reqMsg:" + reqMsg);
			String rsp = handler.handle(reqMsg);
			return HessianInvokeHelper.buildResponse(serCode, sysTraceNo,
					SystemCodeEnum.TXNCORE.getCode(), originNo, versionNo, rsp);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(e.getMessage(), e);
			result.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}
		return HessianInvokeHelper.buildResponse(serCode, sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(), originNo, versionNo,
				JSonUtil.toJSonString(result));
	}

}
