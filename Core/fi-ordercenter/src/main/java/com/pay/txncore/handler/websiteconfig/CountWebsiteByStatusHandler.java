package com.pay.txncore.handler.websiteconfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.service.ExpressTrackingService;
import com.pay.txncore.crosspay.service.PartnerWebsiteConfigService;
import com.pay.txncore.handler.expresstracking.CountExpressByStatusHandler;
import com.pay.util.JSonUtil;
/**
 * 通过一个或多个状态查询域名的数量 
 * @author delin.dong
 * @date 2016年6月8日15:09:54
 */
public class CountWebsiteByStatusHandler   implements EventHandler{
	
	public final Log logger = LogFactory
			.getLog(CountWebsiteByStatusHandler.class);
	private PartnerWebsiteConfigService partnerWebsiteConfigService;

	public void setPartnerWebsiteConfigService(
			PartnerWebsiteConfigService partnerWebsiteConfigService) {
		this.partnerWebsiteConfigService = partnerWebsiteConfigService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			String pendingCount=partnerWebsiteConfigService.countWebsiteByStatus(paraMap);
			resultMap.put("pendingCount",pendingCount);
		} catch (Exception e) {
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}
		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());		
		return JSonUtil.toJSonString(resultMap);
	}

	
}
