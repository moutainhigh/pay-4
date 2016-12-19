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
import com.pay.txncore.service.chargeback.ChargeBackOrderService;
import com.pay.util.JSonUtil;

/**
 * 批量配对查询
 *  File: BatchBouncedQueryHandler.java
 *  Description:
 *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016年7月21日   mmzhang     Create
 *
 */
public class BouncedKeyQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(BouncedKeyQueryHandler.class);
	private ChargeBackOrderService chargeBackOrderService;


	public ChargeBackOrderService getChargeBackOrderService() {
		return chargeBackOrderService;
	}


	public void setChargeBackOrderService(ChargeBackOrderService chargeBackOrderService) {
		this.chargeBackOrderService = chargeBackOrderService;
	}


	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			Long bouncedKeyQuery = chargeBackOrderService.bouncedKeyQuery();
			resultMap.put("bouncedKeyQuery", bouncedKeyQuery);
		} catch (Exception e) {
			logger.error("BouncedRegisterHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}

}
