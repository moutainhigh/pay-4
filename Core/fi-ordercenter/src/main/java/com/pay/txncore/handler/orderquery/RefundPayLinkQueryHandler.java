package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.RefundPayLinkOrder;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class RefundPayLinkQueryHandler implements EventHandler{
	
	public final Log logger = LogFactory
			.getLog(RefundPayLinkQueryHandler.class);
	
	private RefundOrderService refundOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		Map resultMap = new HashMap();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map pageMap = (Map) paraMap.get("page");
			Page page = MapUtil.map2Object(Page.class, pageMap);
			List<RefundPayLinkOrder> refundPayLinkList = refundOrderService.queryRefundPayLinkList(paraMap, page);
			resultMap.put("result", refundPayLinkList);
			resultMap.put("page", page);
		} catch (Exception e) {
			logger.error("PaylinkOrderQueryHandler error:", e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}

}
