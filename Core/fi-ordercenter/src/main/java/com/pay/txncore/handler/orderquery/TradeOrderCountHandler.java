package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.QueryDetailPara;
import com.pay.txncore.model.TradeOrderCount;
import com.pay.txncore.service.OrderQueryService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;


/**
 * 统计商户总的交易
 * @author qiyun10
 *
 */
public class TradeOrderCountHandler implements EventHandler{
	
	private static Logger logger  = LoggerFactory.getLogger(TradeOrderCountHandler.class);
	
	private OrderQueryService orderQueryService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			QueryDetailPara queryDetailPara = MapUtil.map2Object(
					QueryDetailPara.class, paraMap);

			List<TradeOrderCount> queryDetails = orderQueryService.queryTradeOrder(queryDetailPara);
					;
			resultMap.put("list", queryDetails);
			
			return JSonUtil.toJSonString(resultMap);
		} catch (Exception e) {
			logger.error("query error:", e);
		}
		return null;
	}

	public void setOrderQueryService(OrderQueryService orderQueryService) {
		this.orderQueryService = orderQueryService;
	}
}
