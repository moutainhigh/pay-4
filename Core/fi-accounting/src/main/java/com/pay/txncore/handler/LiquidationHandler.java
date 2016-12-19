/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.service.PartnerSettlementOrderService;
import com.pay.txncore.service.PaymentService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 商户清算
 * 
 * @author chma
 */
public class LiquidationHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private PaymentService paymentService;
	private PartnerSettlementOrderService partnerSettlementOrderService;
	
	private int maxSize = 100;   // 此变量暂无用处

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map<String,String> resultMap = new HashMap<String,String>();

		paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		
		String settlementFlgStr = (String) paraMap.get("settlementFlg");
		
		int settlementFlg=0;
		
		if(!StringUtil.isEmpty(settlementFlgStr)){
			settlementFlg = Integer.valueOf(settlementFlgStr);
			if(settlementFlg==1){
				resultMap.put("responseCode", ResponseCodeEnum.INVALID_PARAMETER.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.INVALID_PARAMETER.getDesc());
				return JSonUtil.toJSonString(resultMap);
			}
		}

		List<PartnerSettlementOrder> partnerSettlementOrders = partnerSettlementOrderService
				.queryUnSettlementPartnerSettlementOrder(new Date(), maxSize,settlementFlg);
		
		logger.info("partnerSettlementOrders: "+partnerSettlementOrders.size());
		
		if (null != partnerSettlementOrders
				&& !partnerSettlementOrders.isEmpty()) {
			for (PartnerSettlementOrder partnerSettlementOrder : partnerSettlementOrders) {
				try {
					logger.info("partnerSettlementOrderId: "+partnerSettlementOrder.getId());
					paymentService.liquidationRnTx(partnerSettlementOrder);
				} catch (Exception e) {
					String result = "清算失败： tradeOrderNo: "+partnerSettlementOrder.getTradeOrderNo()
							+",partnerId="+partnerSettlementOrder.getPartnerId()+",id="+partnerSettlementOrder.getId()+
							",CreateDate="+partnerSettlementOrder.getCreateDate()+",SettlementCurrencyCode="+partnerSettlementOrder.getSettlementCurrencyCode()
							+",SettlementRate="+partnerSettlementOrder.getSettlementRate()+",SettlementFlg="+partnerSettlementOrder.getSettlementFlg()
							+",SettlementAmount="+partnerSettlementOrder.getSettlementAmount();
					logger.info("LiquidationHandler-method: handle ["+result+"]"+e.getMessage());
				}
			}
		}

		/**
		 * add by mmzhang 订单授信清算失败重新清算,为清算重新清算
		 */
		PartnerSettlementOrder order=new PartnerSettlementOrder();
		order.setAdvanceFlag("1");
		order.setSettlementFlg(settlementFlg);
		List<PartnerSettlementOrder> partnerSettlementOrders2 = partnerSettlementOrderService
				.queryOrderCreditSettlementOrder(order);
		
		logger.info("订单授信提前重新清算，partnerSettlementOrders: "+partnerSettlementOrders2.size());
		
		if (null != partnerSettlementOrders2
				&& !partnerSettlementOrders2.isEmpty()) {
			for (PartnerSettlementOrder partnerSettlementOrder : partnerSettlementOrders2) {
				try {
					logger.info("partnerSettlementOrderId: "+partnerSettlementOrder.getId());
					paymentService.settlementRnTx(partnerSettlementOrder,"CNY");
				} catch (Exception e) {
					String result = "订单授信提前重新清算失败： tradeOrderNo: "+partnerSettlementOrder.getTradeOrderNo()
							+",partnerId="+partnerSettlementOrder.getPartnerId()+",id="+partnerSettlementOrder.getId()+
							",CreateDate="+partnerSettlementOrder.getCreateDate()+",SettlementCurrencyCode="+partnerSettlementOrder.getSettlementCurrencyCode()
							+",SettlementRate="+partnerSettlementOrder.getSettlementRate()+",SettlementFlg="+partnerSettlementOrder.getSettlementFlg()
							+",SettlementAmount="+partnerSettlementOrder.getSettlementAmount();
					logger.info("LiquidationHandler-method: handle ["+result+"]"+e.getMessage());
				}
			}
		}
		//
		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}

}
