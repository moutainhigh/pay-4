package com.pay.txncore.preauth.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PreauthInfo;
import com.pay.txncore.dto.PreauthResult;
import com.pay.txncore.service.PreauthRevokeService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;


/**
 * 信息卡预授权撤销API
 * @author peiyu.yang
 * @since 2015年6月11日
 */
public class PreauthRevokeApiHandler implements EventHandler{
	
	private static Logger logger = LoggerFactory.getLogger(PreauthRevokeApiHandler.class);
	
	private PreauthRevokeService preauthRevokeService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		
		logger.info("handler-dataMsg: "+dataMsg);
		
		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());

			PreauthInfo preauthInfo = MapUtil.map2Object(PreauthInfo.class,
					paraMap);

			PreauthResult paymentResult = preauthRevokeService.preauthRevoke(preauthInfo);

			resultMap.putAll(MapUtil.bean2map(paymentResult));

			resultMap.put("responseCode", paymentResult.getResponseCode());
			resultMap.put("responseDesc", paymentResult.getResponseDesc());
		} catch (BusinessException e) {
			logger.error("CrosspayCashierPayHandler error:", e);
			ExceptionCodeEnum error = e.getCode();
			resultMap.put("responseCode", error.getCode());
			resultMap.put("responseDesc", error.getDescription());
		} catch (Exception e) {
			logger.error("api pay error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}
		
		String result = JSonUtil.toJSonString(resultMap);
		logger.info("result: "+result);
		
		return result;
	}

	public void setPreauthRevokeService(PreauthRevokeService preauthRevokeService) {
		this.preauthRevokeService = preauthRevokeService;
	}

    
}
