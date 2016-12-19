package com.pay.txncore.handler.ordercredit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.OrderCreditDTO;
import com.pay.txncore.dto.OrderCreditDetailDTO;
import com.pay.txncore.service.OrderCreditService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;


/**
 * 融资订单及融资订单详情创建
 * @author Jiangbo.Peng
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderCreateHandler implements EventHandler{
	
	private static Logger logger = LoggerFactory.getLogger(OrderCreateHandler.class);
	//注入
	private OrderCreditService orderCreditService ;
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			//融资订单
			Map orderCreditDTOMap = (Map) paraMap.get("orderCreditDTO");
			OrderCreditDTO orderCreditDTO = MapUtil.map2Object(OrderCreditDTO.class, orderCreditDTOMap) ;
			//融资订单详情
			List<Map> listMap = (List<Map>) paraMap.get("list");
			List<OrderCreditDetailDTO> list = MapUtil.map2List(OrderCreditDetailDTO.class, listMap) ;
			this.orderCreditService.createOrder(orderCreditDTO, list);
			resultMap.put("responseCode",
					ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	/**
	 * @param orderCreditService the orderCreditService to set
	 */
	public void setOrderCreditService(OrderCreditService orderCreditService) {
		this.orderCreditService = orderCreditService;
	}

}
