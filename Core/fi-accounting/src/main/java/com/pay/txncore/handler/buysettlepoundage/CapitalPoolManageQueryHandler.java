package com.pay.txncore.handler.buysettlepoundage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.CapitalPoolManage;
import com.pay.txncore.service.CapitalPoolManageService;
import com.pay.util.JSonUtil;


/**
 * 结购资金池查询 2016年8月4日14:24:15 delin 
 * @author delin
 * @Date 2016年8月4日14:31:21
 */
public class CapitalPoolManageQueryHandler implements EventHandler {
	public final Log logger = LogFactory.getLog(getClass());

	private CapitalPoolManageService capitalPoolManageService;
	public void setCapitalPoolManageService(
			CapitalPoolManageService capitalPoolManageService) {
		this.capitalPoolManageService = capitalPoolManageService;
	}
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> paraMap = null;
		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, Object>().getClass());
			List<CapitalPoolManage> capitalPoolManages=capitalPoolManageService.queryCapitalPool(paraMap);
			resultMap.put("list", capitalPoolManages);
		} catch (Exception e) {
			logger.error("CapitalPoolManageHandler error:", e);
		}
		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}

}
