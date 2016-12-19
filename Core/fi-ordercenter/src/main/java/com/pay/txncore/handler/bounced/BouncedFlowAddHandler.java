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
 * 拒付操作记录表添加
 * @author delin.dong
 * @date  2016年5月28日11:26:49
 */
public class BouncedFlowAddHandler  implements EventHandler{

	public final Log logger = LogFactory
			.getLog(BouncedFlowAddHandler.class);
	private BouncedFlowService bouncedFlowService ;

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
			logger.error("BouncedFlowAddHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		List<Map> listMap = (List<Map>) paraMap.get("bouncedFlowVOs");
		List<BouncedFlowVO> bouncedFlowVOs = MapUtil.map2List(
				BouncedFlowVO.class, listMap);

		String resultCode = null;
		String resultMsg = null;


		if (bouncedFlowVOs!=null&&!bouncedFlowVOs.isEmpty()) {
			bouncedFlowService.batchAddBouncedFlowOrder(bouncedFlowVOs);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} else {
			resultMap.put("responseCode", resultCode);
			resultMap.put("responseDesc", resultMsg);
		}
		return JSonUtil.toJSonString(resultMap);
	}
}
