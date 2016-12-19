package com.pay.txncore.handler.settlement;

import java.util.HashMap;
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
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;


/**
 * 清算金额统计包括 已清算统计和未清算统计
 * @author peiyu.yang
 * @since 2015年6月1日11:09:40
 */
public class SettlementCountHandler implements EventHandler{
	
	private static Logger logger = LoggerFactory.getLogger(SettlementCountHandler.class);
	
	private PartnerSettlementOrderService partnerSettlementOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			Map pageMap = (Map) resultMap.get("page");
			Page page = MapUtil.map2Object(Page.class, pageMap);

			PartnerSettlementOrder partnerSettlementOrder = MapUtil.map2Object(
					PartnerSettlementOrder.class, paraMap);

			List<PartnerSettlementOrder> list = partnerSettlementOrderService
					.querySettlementAmountCount(partnerSettlementOrder);

			resultMap.put("list", list);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
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

}
