/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.risk.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.risk.commons.RiskBlackWhiteListService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 黑白名单刷新接口 也可以删除某一个键值的LIST
 * 
 * @author peiyu.yang
 */
public class BlackWihteListRefreshHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(BlackWihteListRefreshHandler.class);

	private RiskBlackWhiteListService riskBlackListService;

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			String type = paraMap.get("type");
			String key = paraMap.get("key");

			if ("1".equals(type) || StringUtil.isEmpty(type)) {
				Map<String, String> map = riskBlackListService.refresh();
				resultMap.putAll(map);
			} else if ("2".equals(type)) {
				riskBlackListService.remove(key);
			}

			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setRiskBlackListService(
			RiskBlackWhiteListService riskBlackListService) {
		this.riskBlackListService = riskBlackListService;
	}
}
