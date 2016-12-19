package com.pay.crossBorerPay.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.KfPayTrade;
import com.pay.txncore.model.KfPayTradeDetail;
import com.pay.txncore.service.RemitFailTypingService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class RemitFailTypingQueryHandler implements EventHandler{
	
	private static Logger logger = LoggerFactory.getLogger(RemitFailTypingQueryHandler.class);

	private RemitFailTypingService remitFailTypingService;
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map<String, Object> KfPayTradeDetailMap=(Map<String,Object>)paraMap.get("crossOrder");
			KfPayTradeDetail kfPayTradeDetail = MapUtil.map2Object(KfPayTradeDetail.class,
					KfPayTradeDetailMap);
			List<KfPayTradeDetail> kfPayTrades= null;
			Map pageMap = (Map) paraMap.get("page");
			if(pageMap== null){
				kfPayTrades=remitFailTypingService.findKfPayTradeDetail(kfPayTradeDetail);
			}else{
				Page page = MapUtil.map2Object(Page.class, pageMap);
				kfPayTrades=remitFailTypingService.findKfPayTradeDetail(kfPayTradeDetail,page);
				resultMap.put("page", page);
			}
			resultMap.put("list", kfPayTrades);
		} catch (Exception e) {
			resultMap.put("responseCode", ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

	public void setRemitFailTypingService(
			RemitFailTypingService remitFailTypingService) {
		this.remitFailTypingService = remitFailTypingService;
	}

}
