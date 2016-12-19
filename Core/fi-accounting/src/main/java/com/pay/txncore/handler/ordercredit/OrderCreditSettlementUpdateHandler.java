package com.pay.txncore.handler.ordercredit;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.service.DayRateService;
import com.pay.txncore.service.PartnerSettlementOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;


/**
 * 日利率修改
 * @author Jiangbo.Peng
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderCreditSettlementUpdateHandler implements EventHandler{
	
	private static Logger logger = LoggerFactory.getLogger(OrderCreditSettlementUpdateHandler.class);
	//注入
	private PartnerSettlementOrderService partnerSettlementOrderService ;
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map dayRateMap = (Map) paraMap.get("partnerSettlementOrder") ;
			PartnerSettlementOrder partnerSettlementOrder = MapUtil.map2Object(PartnerSettlementOrder.class, dayRateMap) ;
			boolean boo = partnerSettlementOrderService.updatePartnerSettlementOrder(partnerSettlementOrder) ;
			if(boo){
				resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			}else{
				resultMap.put("responseCode", ResponseCodeEnum.FAIL.getCode()) ;
				resultMap.put("responseDesc", ResponseCodeEnum.FAIL.getDesc()) ;
			}
			
		} catch (Exception e) {
			resultMap.put("responseCode", ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}


}
