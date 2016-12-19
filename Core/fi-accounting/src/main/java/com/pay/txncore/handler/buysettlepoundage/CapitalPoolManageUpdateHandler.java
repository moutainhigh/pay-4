package com.pay.txncore.handler.buysettlepoundage;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.service.CapitalPoolManageService;
import com.pay.util.JSonUtil;
/**
 * 结购资金池update 2016年8月9日11:00:05 delin 
 * @author delin
 */
public class CapitalPoolManageUpdateHandler implements EventHandler {
	
	public final Log logger = LogFactory
			.getLog(CapitalPoolManageUpdateHandler.class);

	private CapitalPoolManageService capitalPoolManageService;
	public void setCapitalPoolManageService(
			CapitalPoolManageService capitalPoolManageService) {
		this.capitalPoolManageService = capitalPoolManageService;
	}
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paraMap = null;
		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, Object>().getClass());
			capitalPoolManageService.update(paraMap);
			resultMap.put("responseCode",
					ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("CapitalPoolManageUpdateHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.FAIL.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.FAIL.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}
		return JSonUtil.toJSonString(resultMap);
    }
}
