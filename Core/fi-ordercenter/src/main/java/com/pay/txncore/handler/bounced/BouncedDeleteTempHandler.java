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
public class BouncedDeleteTempHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(BouncedDeleteTempHandler.class);
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
			bouncedQueryDAO.delete("deleteTemp", null);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("BouncedDeleteTempHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		return JSonUtil.toJSonString(resultMap);
	}
	
}
