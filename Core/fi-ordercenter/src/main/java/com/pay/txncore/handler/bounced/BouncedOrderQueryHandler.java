package com.pay.txncore.handler.bounced;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.BouncedOrderVO;
import com.pay.txncore.service.chargeback.ChargeBackOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
/**
 * 拒付调单查询
 * @date 2016年5月25日10:24:42
 * @author delin.dong
 *
 */
public class BouncedOrderQueryHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(BouncedOrderQueryHandler.class);
	private ChargeBackOrderService chargeBackOrderService;

	public void setChargeBackOrderService(
			ChargeBackOrderService chargeBackOrderService) {
		this.chargeBackOrderService = chargeBackOrderService;
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		
		Map resultMap = new HashMap();
		Map paraMap = null;
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		} catch (Exception e) {
			logger.error("BouncedOrderQueryHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}
		BouncedOrderVO bouncedOrderVO = MapUtil.map2Object(
				BouncedOrderVO.class, paraMap);

		Map pageMap = (Map) paraMap.get("page");
		if(pageMap!=null){
			Page page = MapUtil.map2Object(Page.class, pageMap);
			
			List<BouncedOrderVO> bouncedOrderVOs = chargeBackOrderService
					.queryBouncedOrders(bouncedOrderVO, page);
			resultMap.put("result", bouncedOrderVOs);
			resultMap.put("page", page);

		}else{
			
			List<BouncedOrderVO> bouncedOrderVOs = chargeBackOrderService
					.queryBouncedOrders(bouncedOrderVO);
			resultMap.put("result", bouncedOrderVOs);
		}
		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}

}
