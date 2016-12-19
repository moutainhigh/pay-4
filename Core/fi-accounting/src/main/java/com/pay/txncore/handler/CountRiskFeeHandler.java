/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import com.pay.util.JSonUtil;

/**
 * 计算风控手续费
 * 
 * @author peiyu.yang
 */
public class CountRiskFeeHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private PaymentService paymentService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

		try {

			RiskOrder riskOrder = new RiskOrder();
			riskOrder.setFeeFlg(0);
			
			List<String> results = null;
			
			List<RiskOrder> riskOrderList = paymentService.findRiskOrderList(riskOrder);
			
			if(riskOrderList!=null&&!riskOrderList.isEmpty()){
				results = new ArrayList<String>();
				for(RiskOrder order:riskOrderList){
					logger.info("开始计算tradeOrderId:" + order.getTradeOrderNo() + ":风控服务费");
					int rt = paymentService.countRiskRnTx(order);
					if(rt!=1){
						String tradeOrderNo = String.valueOf(order.getTradeOrderNo());
						results.add(tradeOrderNo);
					}
					logger.info("结束计算tradeOrderId:" + order.getTradeOrderNo() + ":风控服务费");
				}
			}
			resultMap.put("list",results);
		} catch (Exception e) {
			logger.error("settlement error:", e);
		}
        	
		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

}
