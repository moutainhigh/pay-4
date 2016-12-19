/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.bounced;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dao.BouncedQueryDAO;
import com.pay.txncore.dto.BouncedResultDTO;
import com.pay.txncore.model.ChargeBackOrder;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 拒付订单查询
 * 
 * @author chma
 */
public class BouncedResultTempAddHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(BouncedResultTempAddHandler.class);
	private BouncedQueryDAO bouncedQueryDAO;


	public void setBouncedQueryDAO(BouncedQueryDAO bouncedQueryDAO) {
		this.bouncedQueryDAO = bouncedQueryDAO;
	}

	

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map resultMap = new HashMap();
		Map paraMap = null;

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		} catch (Exception e) {
			logger.error("BouncedResultAddHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		List<Map> listMap = (List<Map>) paraMap.get("bouncedResults");
		List<BouncedResultDTO> chargeBackOrders = MapUtil.map2List(
				BouncedResultDTO.class, listMap);

		String resultCode = null;
		String resultMsg = null;


		if (!chargeBackOrders.isEmpty()) {
			bouncedQueryDAO.batchCreate("createTemp", chargeBackOrders);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} else {
			resultMap.put("responseCode", resultCode);
			resultMap.put("responseDesc", resultMsg);
		}

		return JSonUtil.toJSonString(resultMap);
	}
	
	private boolean isChargeback(List<ChargeBackOrder> existsChargeBackOrders){
		if(null == existsChargeBackOrders || existsChargeBackOrders.isEmpty()){
			return false;
		}
		for(ChargeBackOrder chargeBackOrder:existsChargeBackOrders){
			if(chargeBackOrder.getStatus() != 2){
				return true;
			}
		}
		return false;
	}
}
