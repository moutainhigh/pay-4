package com.pay.txncore.handler.ordercredit;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.service.PaymentService;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;


/**
 * 订单授信清算确认
 * @author mmzhang
 */
public class OrderCreditConfirmHandler implements EventHandler{
	
	private static Logger logger = LoggerFactory.getLogger(OrderCreditConfirmHandler.class);
	
	private PaymentService paymentService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			String settlementCurrencyCode = (String) paraMap.get("settlementCurrencyCode");
			String dayRate = (String) paraMap.get("dayRate");
			List<Map> list = (List<Map>) paraMap.get("list");
			//取要提前清算的订单
			List<PartnerSettlementOrder> partnerSettlementOrders = MapUtil.map2List(PartnerSettlementOrder.class, list);		

			
			if (null != partnerSettlementOrders
					&& !partnerSettlementOrders.isEmpty()) {
				for (PartnerSettlementOrder partnerSettlementOrder2 : partnerSettlementOrders) {
					partnerSettlementOrder2.setSettlementDate(new SimpleDateFormat("yyyy-MM-dd").parse(partnerSettlementOrder2.getSsettlementDate()));
					partnerSettlementOrder2.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").parse(partnerSettlementOrder2.getScreateDate()));
					paymentService.BuildOrderCredit(partnerSettlementOrder2,
							settlementCurrencyCode, dayRate, false);
				}

				resultMap.put("list", partnerSettlementOrders);
				resultMap.put("responseCode",
						ResponseCodeEnum.SUCCESS.getCode());
				resultMap.put("responseDesc",
						ResponseCodeEnum.SUCCESS.getDesc());
			}
		} catch (Exception e) {
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}


}
