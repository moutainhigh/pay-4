/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.paymentchannelitem;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.dao.ChannelConfigDAO;
import com.pay.channel.model.ChannelConfig;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 渠道处理
 * 
 * @author chma
 */
public class ChannelConfigUpdateHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(ChannelConfigUpdateHandler.class);
	private ChannelConfigDAO channelConfigDAO;

	public void setChannelConfigDAO(ChannelConfigDAO channelConfigDAO) {
		this.channelConfigDAO = channelConfigDAO;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());

			String id = paraMap.get("id");
			String orgCode = paraMap.get("orgCode");
			String orgMerchantCode = paraMap.get("orgMerchantCode");
			String orgKey = paraMap.get("orgKey");
			String terminalCode = paraMap.get("terminalCode");
			String accessCode = paraMap.get("accessCode");
			String transType = paraMap.get("transType");
			String operator = paraMap.get("operator");
			String keyFilePath = paraMap.get("keyFilePath");
			String status = paraMap.get("status");
			String supportWebsite = paraMap.get("supportWebsite");
			String currencyCode = paraMap.get("currencyCode");
			String pattern = paraMap.get("pattern");
			String requestMerchantName = paraMap.get("requestMerchantName");
			String merchantBillName = paraMap.get("merchantBillName");
			String fitMerchantType = String.valueOf(paraMap.get("fitMerchantType"));
			String aucrFlag = String.valueOf(paraMap.get("aucrFlag"));

			ChannelConfig channelConfig = new ChannelConfig();
			channelConfig.setKeyFilePath(keyFilePath);
			channelConfig.setOperator(operator);
			channelConfig.setOrgCode(orgCode);
			channelConfig.setOrgKey(orgKey);
			channelConfig.setTerminalCode(terminalCode);
			channelConfig.setAccessCode(accessCode);
			channelConfig.setTransType(transType);
			channelConfig.setOrgMerchantCode(orgMerchantCode);
			channelConfig.setCurrencyCode(currencyCode);
			channelConfig.setSupportWebsite(supportWebsite);
			channelConfig.setId(Long.valueOf(id));
			channelConfig.setPattern(pattern);
			channelConfig.setRequestMerchantName(requestMerchantName);
			channelConfig.setMerchantBillName(merchantBillName);
			if (!StringUtil.isEmpty(status)) {
				channelConfig.setStatus(Integer.valueOf(status));
			}
			if(!StringUtil.isEmpty(fitMerchantType)){
				channelConfig.setFitMerchantType(fitMerchantType);
			}
			if(!StringUtil.isEmpty(aucrFlag)){
				channelConfig.setAucrFlag(aucrFlag);
			}
			channelConfigDAO.update(channelConfig);

			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		return JSonUtil.toJSonString(resultMap);
	}
}
