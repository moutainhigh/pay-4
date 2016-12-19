package com.pay.txncore.handler.chargeback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.BouncedReasonMapping;
import com.pay.txncore.service.chargeback.BouncedReasonMappingService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/****
*
* 拒付原因映射关系添加
* @date 2016年5月24日15:18:41
* @author delin.dong
*/
public class BouncedReasonMappingAddHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(BouncedReasonMappingAddHandler.class);

	private BouncedReasonMappingService bouncedReasonMappingService;
	
	public void setBouncedReasonMappingService(
			BouncedReasonMappingService bouncedReasonMappingService) {
		this.bouncedReasonMappingService = bouncedReasonMappingService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map resultMap = new HashMap();
		Map paraMap = null;

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			BouncedReasonMapping bouncedReasonMapping = MapUtil.map2Object(
					BouncedReasonMapping.class, paraMap);
			bouncedReasonMappingService.addBouncedReasonMapping(bouncedReasonMapping);
			resultMap.put("responseCode",
					ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("BouncedReasonMappingAddHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}
		return JSonUtil.toJSonString(resultMap);
	}

}
