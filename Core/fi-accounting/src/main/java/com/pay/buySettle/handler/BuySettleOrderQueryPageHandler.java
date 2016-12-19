package com.pay.buySettle.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.buySettle.service.BuySettleOrderService;
import com.pay.inf.dao.Page;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.BuysettleOrder;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class BuySettleOrderQueryPageHandler implements EventHandler {
	private BuySettleOrderService BuySettleOrderServiceImpl;
	Log logger = LogFactory.getLog(BuySettleOrderQueryPageHandler.class);
	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap<String, Object>().getClass());
			BuysettleOrder busettleOrder = MapUtil.map2Object(BuysettleOrder.class, paraMap);
			Map<String, Object> pageMap = (Map<String, Object>) paraMap.get("page");
			Page<BuysettleOrder> page = MapUtil.map2Object(Page.class, pageMap);
			List<BuysettleOrder> busettleOrderList = BuySettleOrderServiceImpl.queryByConditionsPage(busettleOrder, page);
			resultMap.put("result", busettleOrderList);
			resultMap.put("page", page);
		} catch (Exception e) {
			logger.error("api pay error:", e);
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
