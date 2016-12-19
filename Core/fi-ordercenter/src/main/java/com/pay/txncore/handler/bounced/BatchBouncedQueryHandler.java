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
public class BatchBouncedQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(BatchBouncedQueryHandler.class);
	private BouncedQueryDAO bouncedQueryDAO;


	public void setBouncedQueryDAO(BouncedQueryDAO bouncedQueryDAO) {
		this.bouncedQueryDAO = bouncedQueryDAO;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
			String batchNO = String.valueOf(paraMap.get("batchNO"));

			
			List<BouncedResultDTO> bouncedResults = null;
			bouncedResults = bouncedQueryDAO.batchBouncedQuery(batchNO);
			
			resultMap.put("bouncedResults", bouncedResults);
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
