/**
 * 
 */
package com.pay.txncore.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.service.preauth.PreAuth2Service;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * @author yanshichuan
 *
 */
public class CancelPreAuthHandler implements EventHandler{
	
	private static Logger logger = LoggerFactory.getLogger(CancelPreAuthHandler.class);
	private PreAuth2Service preAuth2Service;
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> paraMap = null;
		Map<String,String> resultMap = new HashMap<String,String>();
		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
			PaymentInfo paymentInfo = MapUtil.map2Object(PaymentInfo.class,paraMap);
			PaymentResult paymentResult = preAuth2Service.preAuthVoid(paymentInfo);
			resultMap.putAll(MapUtil.bean2map(paymentResult));
			resultMap.put("responseCode", paymentResult.getResponseCode());
			resultMap.put("responseDesc", paymentResult.getResponseDesc());
		} catch (BusinessException e) {
			logger.error("CrosspayCashierPayHandler error:", e);
			ExceptionCodeEnum error = e.getCode();
			resultMap.put("responseCode", error.getCode());
			resultMap.put("responseDesc", error.getDescription());
		} catch (Exception e) {
			logger.error("card bind error:", e);
			resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}
	public PreAuth2Service getPreAuth2Service() {
		return preAuth2Service;
	}
	public void setPreAuth2Service(PreAuth2Service preAuth2Service) {
		this.preAuth2Service = preAuth2Service;
	}

}
