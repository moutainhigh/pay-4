package com.pay.crossBorerPay.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.crossBorerPay.service.KfPayTradeDetailService;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.KfPayResource;
import com.pay.txncore.model.KfPayTradeDetail;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class KfPayTrandDeatilSaveHandler implements EventHandler {
	
	private KfPayTradeDetailService kfPayTradeDetailServiceImpl;
	Log logger = LogFactory.getLog(KfPayTrandDeatilSaveHandler.class);
	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("repCode", "0");
		try {
			Map<String, Object> paraMap =JSonUtil.toObject(dataMsg, Map.class);
			List<Map<String, Object>> kfPayTradeDetailMap=(List<Map<String, Object>>) paraMap.get("entity");
			List<KfPayTradeDetail> kfPayTradeDetailList=new ArrayList<KfPayTradeDetail>();
			for (Map<String, Object> item : kfPayTradeDetailMap) {
				KfPayTradeDetail kfPayResource=MapUtil.map2Object(KfPayTradeDetail.class, item);
				kfPayTradeDetailList.add(kfPayResource);
			}
			for (int i = 0; i < kfPayTradeDetailList.size(); i++) {
				kfPayTradeDetailList.get(i).setCreateDate(new Date());
			}
			kfPayTradeDetailServiceImpl.batchSave(kfPayTradeDetailList);
		} catch (Exception e) {
			logger.error("保存跨境付款订单报错",e);
			resultMap.put("repCode","1");
		}
		
		return JSonUtil.toJSonString(resultMap);
	}
	public KfPayTradeDetailService getKfPayTradeDetailServiceImpl() {
		return kfPayTradeDetailServiceImpl;
	}
	public void setKfPayTradeDetailServiceImpl(KfPayTradeDetailService kfPayTradeDetailServiceImpl) {
		this.kfPayTradeDetailServiceImpl = kfPayTradeDetailServiceImpl;
	}
	
	

}
