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
 * 拒付单项登记
 *  File: RouncedRegisterHandler.java
 *  Description:
 *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016年5月11日   mmzhang     Create
 *
 */
public class BouncedRegisterHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(BouncedRegisterHandler.class);
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
			String tradeOrderNo = String.valueOf(paraMap.get("tradeOrderNo"));
			String channelOrderNo = String.valueOf(paraMap.get("channelOrderNo"));
			String cardNo = String.valueOf(paraMap.get("cardNo"));
			String orgCode = String.valueOf(paraMap.get("orgCode"));
			String tranDate = String.valueOf(paraMap.get("tranDate"));
			String authorisation = String.valueOf(paraMap.get("authorisation"));
			String cardNoLike = String.valueOf(paraMap.get("cardNoLike"));

			
			List<BouncedResultDTO> bouncedResults = null;
			bouncedResults = bouncedQueryDAO.bouncedQuery(tradeOrderNo, channelOrderNo, cardNo,cardNoLike,authorisation, tranDate,orgCode);
			
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
