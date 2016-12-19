/*
 * author: sch
 * 2016-05-09 
 * 参考CountRiskFeeHandler.java 来做的
 */
package com.pay.txncore.handler;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.rm.service.model.RiskOrder;
import com.pay.txncore.service.PaymentService;
import com.pay.txncore.model.RefundFeeOrder;
import com.pay.util.JSonUtil;

/**
 * 计算退款手续费
 */
public class CountRefundFeeHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private PaymentService paymentService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		logger.info("退款手续费清算开始");
		
		Map paraMap = null;
		Map resultMap = new HashMap();

		paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

		try {

			RefundFeeOrder refundFeeOrder = new RefundFeeOrder();
			refundFeeOrder.setFeeFlg(0);
			
			List<String> results = null;
			
			List<RefundFeeOrder> refundFeeOrderList = paymentService.findRefundFeeOrderList(refundFeeOrder);
			
			if(refundFeeOrderList!=null&&!refundFeeOrderList.isEmpty()){
				results = new ArrayList<String>();
				for(RefundFeeOrder order:refundFeeOrderList){
					
					logger.info("refundFeeOrderList:=" + order.getRefundOrderNo());
					
					int rt = paymentService.countRefundFeeOrderRnTx(order);
					if(rt!=1){
						String refundOrderNo = String.valueOf(order.getRefundOrderNo());
						results.add(refundOrderNo);
					}
				}
			}
			resultMap.put("list",results);
			
		} catch (Exception e) {
			logger.error("CountRefundFeeHandler error:", e);
		}
        	
		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		
		logger.info("退款手续费清算结束");
		
		return JSonUtil.toJSonString(resultMap);
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

}
