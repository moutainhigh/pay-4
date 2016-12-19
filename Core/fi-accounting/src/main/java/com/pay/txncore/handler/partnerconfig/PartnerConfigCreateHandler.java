/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.partnerconfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.service.PartnerConfigService;
import com.pay.util.JSonUtil;

/**
 * 
 * @author chma
 */
public class PartnerConfigCreateHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(PartnerConfigCreateHandler.class);
	private PartnerConfigService partnerConfigService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			String partnerId = paraMap.get("partnerId");
			String publicKeyValue = paraMap.get("publicKeyValue");

			Boolean b = partnerConfigService.createPartnerConfigRnTx(partnerId,
					publicKeyValue);

			resultMap.put("result", b);
		} catch (Exception e) {
			logger.error("query partner error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setPartnerConfigService(
			PartnerConfigService partnerConfigService) {
		this.partnerConfigService = partnerConfigService;
	}

}
