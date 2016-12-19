package com.pay.txncore.crosspay.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.service.LocalPayService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class CrosspayLocaleAcquireHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	
	private LocalPayService localPayService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();
		try{
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, Object>().getClass());
			if (logger.isInfoEnabled()) {
				logger.info("request map:" + paraMap);
			}	
			PaymentInfo paymentInfo = MapUtil.map2Object(PaymentInfo.class,
					paraMap);
			if(paymentInfo.getPayType()==null)
				paymentInfo.setPayType("ALL");
			if(paymentInfo.getBorrowingMarked()==null)
				paymentInfo.setBorrowingMarked("0");
			if(paymentInfo.getDirectFlag()==null)
				paymentInfo.setDirectFlag("0");
			PaymentResult paymentResult = localPayService
					.crossLocaleAcquire(paymentInfo);
			resultMap.put("result", MapUtil.bean2map(paymentResult));
			
			logger.info("resultMap in CrosspayLocaleAcquireHandler : " + resultMap.toString());
			
			String responseCode=paymentResult.getResponseCode();
			if(responseCode!=null){
				resultMap.put("responseCode", responseCode);
				resultMap.put("responseDesc", paymentResult.getResponseDesc());
			}else{
				resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			}
		}catch(Exception e){
			logger.error("locale pay error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}

	public void setLocalPayService(LocalPayService localPayService) {
		this.localPayService = localPayService;
	}
}
