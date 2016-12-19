package com.pay.crossBorerPay.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.crossBorerPay.service.KfCrossBorerPayService;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.KfPayTradeDetail;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class KfCrossBorerPayApplyHandler implements EventHandler{
	
	private KfCrossBorerPayService kfCrossBorerPayService;
	
	Log logger = LogFactory.getLog(KfCrossBorerPayApplyHandler.class);

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap<String, Object>().getClass());
			KfPayTradeDetail kfPayTradeDetail=MapUtil.map2Object(KfPayTradeDetail.class,  (Map<String, Object>)paraMap.get("entity"));
			kfPayTradeDetail.setBatchNo(kfPayTradeDetail.getBatchNo().trim());
			kfCrossBorerPayService.crossBorerApply(kfPayTradeDetail);
			resultMap.put("result", "1");
		} catch (Exception e) {
			logger.error("跨境付款下载文件 error:", e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

	public void setKfCrossBorerPayService(KfCrossBorerPayService kfCrossBorerPayService) {
		this.kfCrossBorerPayService = kfCrossBorerPayService;
	}

	
}
