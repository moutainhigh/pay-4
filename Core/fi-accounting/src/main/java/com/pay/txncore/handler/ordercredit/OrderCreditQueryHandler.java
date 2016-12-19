package com.pay.txncore.handler.ordercredit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.service.PartnerSettlementOrderService;
import com.pay.txncore.service.PaymentService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;


/**
 * 订单授信清算查询
 * @author mmzhang
 *
 */
public class OrderCreditQueryHandler implements EventHandler{
	
	private static Logger logger = LoggerFactory.getLogger(OrderCreditQueryHandler.class);
	
	private PartnerSettlementOrderService partnerSettlementOrderService;
	private PaymentService paymentService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			Map pageMap = (Map) paraMap.get("page");
			Page page = MapUtil.map2Object(Page.class, pageMap);

			PartnerSettlementOrder partnerSettlementOrder = MapUtil.map2Object(
					PartnerSettlementOrder.class, paraMap);

			
					List<PartnerSettlementOrder> list=null;
			if(pageMap==null){
				list = partnerSettlementOrderService
						.queryOrderCreditSettlementOrder(partnerSettlementOrder);
				resultMap.put("list", list);
			}else{
				list = partnerSettlementOrderService
						.queryOrderCreditSettlementOrder(partnerSettlementOrder,page);
				resultMap.put("list", list);
				resultMap.put("page", page);
			}
			if (null != list && !list.isEmpty()) {	
				Iterator<PartnerSettlementOrder> it = list.iterator();
				while(it.hasNext()){
				    PartnerSettlementOrder partnerSettlementOrder2= it.next();
					paymentService.BuildOrderCredit(partnerSettlementOrder2, partnerSettlementOrder.getCreditCurrencyCode(),partnerSettlementOrder.getDayRate(), false);
				    if(partnerSettlementOrder2.getRecordedAmount()<20000L){
				        it.remove();
				        //页码的总页数减1
				        page.setTotalCount(page.getTotalCount()-1);
				    }
				}
				
			
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
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

	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}

	public PartnerSettlementOrderService getPartnerSettlementOrderService() {
		return partnerSettlementOrderService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

}
