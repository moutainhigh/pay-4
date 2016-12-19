/**
 * 
 */
package com.pay.account.hessian;

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
 * 浼氬憳璐︽埛銆佽处鍔℃湇鍔�
 * 
 * @author chaoyue
 * 
 */
public class HessianService implements HessianInvokeService {

	private Log logger = LogFactory.getLog(HessianService.class);

	private Map<String, EventHandler> eventHandlerMap;

	public void setEventHandlerMap(Map<String, EventHandler> eventHandlerMap) {
		this.eventHandlerMap = eventHandlerMap;
	}

	
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
		logger.info("dataMsg:" + dataMsg);

		Map<String, String> result = new HashMap<String, String>();
		try {
			// 楠岃瘉璇锋眰鍙傛暟
			HessianInvokeHelper.validateReqParam(serCode, sysTraceNo, originNo,
					targetNo, versionNo, dataLength, msgCompress, dataMsg);
			// 楠岃瘉璇锋眰鏈嶅姟浠ｇ爜鏄惁姝ｇ‘
			EventHandler handler = eventHandlerMap.get(serCode);
			if (handler == null) {
				throw new HessianInvokeException(
						ResponseCodeEnum.UNDEFINED_SERVICE.getCode(),
						ResponseCodeEnum.UNDEFINED_SERVICE.getDesc());
			}

			// 楠岃瘉鐩爣绯荤粺缂栫爜
			HessianInvokeHelper.validateTargetNo(targetNo,
					SystemCodeEnum.ACCOUNT.getCode());
			// 楠岃瘉璇锋眰娑堟伅姝ｆ枃鍐呭闀垮害
			HessianInvokeHelper.validateDataMsgSize(dataLength, dataMsg);

			String reqMsg = dataMsg;
			// 鍒ゆ柇鏄惁闇�瑕佽В鍘嬭姹傛秷鎭鏂囧唴瀹�
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
					SystemCodeEnum.ACCOUNT.getCode(), originNo, versionNo, rsp);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(e.getMessage(), e);
			result.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}
		return HessianInvokeHelper.buildResponse(serCode, sysTraceNo,
				SystemCodeEnum.ACCOUNT.getCode(), originNo, versionNo,
				JSonUtil.toJSonString(result));
	}

}
