/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.ordercredit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.service.PaymentService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 订单授信提前清算
 * 
 * @author mmzhang
 */
public class LiquidationOrderCreditHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private PaymentService paymentService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map<String,String> resultMap = new HashMap<String,String>();

		paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		
		String settlementCurrencyCode = (String) paraMap.get("creditCurrencyCode");
		String list = (String) paraMap.get("list");
		
		//取要提前清算的订单
		List<Map> partnerSettlementOrders = JSonUtil.toObject(list, new ArrayList<PartnerSettlementOrder>().getClass());		
		
	
		
		if (null != partnerSettlementOrders
				&& !partnerSettlementOrders.isEmpty()) {
			logger.info("partnerSettlementOrders: "+partnerSettlementOrders.size());
			//for(int i=0;i<partnerSettlementOrders.size()-1;i++){
				//PartnerSettlementOrder partnerSettlementOrder=partnerSettlementOrders.get(i);
			for (Map map : partnerSettlementOrders) {
				PartnerSettlementOrder partnerSettlementOrder = MapUtil.map2Object(PartnerSettlementOrder.class, map);
				try {
					
					logger.info("partnerSettlementOrderId: "+partnerSettlementOrder.getId());
					paymentService.settlementRnTx(partnerSettlementOrder,settlementCurrencyCode);
				} catch (Exception e) {
					String result = "订单授信提前清算失败： tradeOrderNo: "+partnerSettlementOrder.getTradeOrderNo()
							+",partnerId="+partnerSettlementOrder.getPartnerId()+",id="+partnerSettlementOrder.getId()+
							",CreateDate="+partnerSettlementOrder.getCreateDate()+",SettlementCurrencyCode="+partnerSettlementOrder.getSettlementCurrencyCode()
							+",SettlementRate="+partnerSettlementOrder.getSettlementRate()+",SettlementFlg="+partnerSettlementOrder.getSettlementFlg()
							+",SettlementAmount="+partnerSettlementOrder.getSettlementAmount();
					logger.info("LiquidationHandler-method: handle ["+result+"]"+e.getMessage());
				}
			}
		}

		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}



}
