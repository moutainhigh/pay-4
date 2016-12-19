package com.pay.crossBorerPay.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.KfPayTrade;
import com.pay.txncore.model.KfPayTradeDetail;
import com.pay.txncore.service.PayFileService;
import com.pay.txncore.service.RemitFailTypingService;
import com.pay.util.JSonUtil;

public class KfPayTrandUpdateHandler implements EventHandler {
	
	private RemitFailTypingService remitFailTypingService;
	private PayFileService payFileService;
	Log logger = LogFactory.getLog(KfPayTrandUpdateHandler.class);
	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paraMap =JSonUtil.toObject(dataMsg, Map.class);
			String batchNo=paraMap.get("batchNo").toString().trim();
			KfPayTradeDetail kfPayTradeDetail=new KfPayTradeDetail();
			kfPayTradeDetail.setBatchNo(batchNo);
			List<KfPayTradeDetail> kfPayTrades= remitFailTypingService.findKfPayTradeDetail(kfPayTradeDetail);
			KfPayTrade kfPayTrade=new KfPayTrade();
			kfPayTrade.setStatus("0");
			kfPayTrade.setCompleteDate(new Date());
			kfPayTrade.setBatchNo(batchNo);
			payFileService.update(kfPayTrade);
			remitFailTypingService.batchUpdate(kfPayTrades);
		} catch (Exception e) {
			logger.error("保存跨境付款订单报错",e);
			resultMap.put("repCode","1");
		}
		
		return JSonUtil.toJSonString(resultMap);
	}
	public RemitFailTypingService getRemitFailTypingService() {
		return remitFailTypingService;
	}
	public void setRemitFailTypingService(RemitFailTypingService remitFailTypingService) {
		this.remitFailTypingService = remitFailTypingService;
	}
	public PayFileService getPayFileService() {
		return payFileService;
	}
	public void setPayFileService(PayFileService payFileService) {
		this.payFileService = payFileService;
	}
	
}
