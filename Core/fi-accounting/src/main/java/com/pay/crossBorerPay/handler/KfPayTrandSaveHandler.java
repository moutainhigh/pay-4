package com.pay.crossBorerPay.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.crossBorerPay.service.KfPayTradeService;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.KfPayTrade;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class KfPayTrandSaveHandler implements EventHandler {
	
	private KfPayTradeService kfPayTradeServiceImpl;
	Log logger = LogFactory.getLog(KfPayTrandSaveHandler.class);
	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("repCode", "0");
		try {
			Map<String, Object> paraMap =JSonUtil.toObject(dataMsg, Map.class);
			KfPayTrade kfPayTrade=MapUtil.map2Object(KfPayTrade.class, (Map<String, Object>)paraMap.get("entity"));
			kfPayTrade.setCreateDate(new Date());
			resultMap.put("batchNo", kfPayTradeServiceImpl.save(kfPayTrade));
		} catch (Exception e) {
			logger.error("保存跨境付款订单报错",e);
			resultMap.put("repCode","1");
		}
		
		return JSonUtil.toJSonString(resultMap);
	}
	public KfPayTradeService getKfPayTradeServiceImpl() {
		return kfPayTradeServiceImpl;
	}
	public void setKfPayTradeServiceImpl(KfPayTradeService kfPayTradeServiceImpl) {
		this.kfPayTradeServiceImpl = kfPayTradeServiceImpl;
	}
	

}
