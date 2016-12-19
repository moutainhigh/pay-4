package com.pay.txncore.handler.bounced;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.BouncedFlowVO;
import com.pay.txncore.service.chargeback.BouncedFlowService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 
 * 拒付操作记录表查询
 * @author delin.dong
 * @date  2016年5月28日11:26:49
 */
public class BouncedFlowQueryHandler implements EventHandler{

	public final Log logger = LogFactory
			.getLog(BouncedFlowQueryHandler.class);
	private BouncedFlowService bouncedFlowService;
	
	public void setBouncedFlowService(BouncedFlowService bouncedFlowService) {
		this.bouncedFlowService = bouncedFlowService;
	}



	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map resultMap = new HashMap();
		Map paraMap = null;
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		} catch (Exception e) {
			logger.error("BouncedFlowQueryHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}//BouncedFlowVO
		BouncedFlowVO bouncedFlowVO = MapUtil.map2Object(
				BouncedFlowVO.class, paraMap);
	
		List<BouncedFlowVO> bouncedFlowVOs = bouncedFlowService
				.queryBouncedOrders(bouncedFlowVO);
		
		resultMap.put("result", bouncedFlowVOs);

		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}
	
}
