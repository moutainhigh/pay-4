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

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dao.BouncedFraudQueryDAO;
import com.pay.txncore.dto.BouncedFraudResultDTO;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 拒付单项登记
 *  File: RouncedRegisterHandler.java
 *  Description:
 *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016年5月11日   mmzhang     Create
 *
 */
public class BouncedFraudHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(BouncedFraudHandler.class);
	private BouncedFraudQueryDAO bouncedFraudQueryDAO;



	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, Object>().getClass());
			Map pageMap = (Map) paraMap.get("page");
			Page page = MapUtil.map2Object(Page.class, pageMap);
			List<BouncedFraudResultDTO> bouncedResults = null;
			
			bouncedResults = bouncedFraudQueryDAO.bouncedFraudQuery(paraMap);
			
			resultMap.put("bouncedResults", bouncedResults);
			resultMap.put("page", page);
		} catch (Exception e) {
			logger.error("BouncedOrderQueryHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}



	public BouncedFraudQueryDAO getBouncedFraudQueryDAO() {
		return bouncedFraudQueryDAO;
	}



	public void setBouncedFraudQueryDAO(BouncedFraudQueryDAO bouncedFraudQueryDAO) {
		this.bouncedFraudQueryDAO = bouncedFraudQueryDAO;
	}

}
