package com.pay.txncore.handler.refund;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.RefundExceptionMonitor;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class RefundExceptionMonitorHandler implements EventHandler{

	public final Log logger = LogFactory.getLog(RefundExceptionMonitorHandler.class);
	
	private RefundOrderService refundOrderService;
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		Map resultMap = new HashMap();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map pageMap = (Map) paraMap.get("page");
			Page page = MapUtil.map2Object(Page.class, pageMap);
			List<RefundExceptionMonitor> refundExceptionMonitorList = refundOrderService.getRefundExceptionMonitorList(paraMap,page);
			resultMap.put("page", page);
			resultMap.put("result", refundExceptionMonitorList);
		} catch (Exception e) {
			logger.error("调用退款异常监控失败", e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

	public RefundOrderService getRefundOrderService() {
		return refundOrderService;
	}

	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}

}
