package com.pay.buySettle.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.buySettle.service.BuySettleOrderService;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.BuysettleOrder;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class BuySettleOrderBuyParitiesHandler implements EventHandler {
	private BuySettleOrderService BuySettleOrderServiceImpl;
	Log logger = LogFactory.getLog(BuySettleOrderBuyParitiesHandler.class);
	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("repCode", "0");
		try {
			Map<String, Object> paraMap =JSonUtil.toObject(dataMsg, Map.class);
			paraMap.put("createDate", new Date());
			BuysettleOrder buysettleOrder=MapUtil.map2Object(BuysettleOrder.class, paraMap);
			resultMap.put("repCode", String.valueOf(BuySettleOrderServiceImpl.create(buysettleOrder)));
		} catch (Exception e) {
			logger.error("保存购结汇订单报错",e);
			resultMap.put("repCode","1");
		}
		
		return JSonUtil.toJSonString(resultMap);
	}
	public BuySettleOrderService getBuySettleOrderServiceImpl() {
		return BuySettleOrderServiceImpl;
	}

	public void setBuySettleOrderServiceImpl(BuySettleOrderService buySettleOrderServiceImpl) {
		BuySettleOrderServiceImpl = buySettleOrderServiceImpl;
	}

}
