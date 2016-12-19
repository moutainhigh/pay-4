package com.pay.txncore.handler.ordercredit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.OrderCreditDetailDTO;
import com.pay.txncore.service.OrderCreditService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;


/**
 * 订单授信融资订单查询
 * @author Jiangbo.Peng
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderCreditOrderDetailQueryHandler implements EventHandler{
	
	private static Logger logger = LoggerFactory.getLogger(OrderCreditOrderDetailQueryHandler.class);
	
	//注入
	private OrderCreditService orderCreditService ; 
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map pageMap = (Map) paraMap.get("page");
			if(pageMap==null){
				List<OrderCreditDetailDTO> list = (List<OrderCreditDetailDTO>) this.orderCreditService.findByCriteria("findByCriteria_orderDetail", paraMap) ;
				resultMap.put("result", list) ;
			}else{
				Page page = MapUtil.map2Object(Page.class, pageMap);
				List<OrderCreditDetailDTO> list = (List<OrderCreditDetailDTO>) this.orderCreditService.findByCriteria("findByCriteria_orderDetail", paraMap, page) ;
				resultMap.put("result", list) ;
				resultMap.put("page", page);
			}
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query ordercredit order error:", e);
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
