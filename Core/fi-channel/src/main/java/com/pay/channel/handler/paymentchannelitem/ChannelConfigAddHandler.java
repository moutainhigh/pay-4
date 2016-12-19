/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.paymentchannelitem;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.dao.ChannelConfigDAO;
import com.pay.channel.model.ChannelConfig;
import com.pay.channel.model.ChannelSndrelationDate;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

/**
 * 通道添加
 * 
 * @author chma
 */
public class ChannelConfigAddHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(ChannelConfigAddHandler.class);
	private ChannelConfigDAO channelConfigDAO;

	public void setChannelConfigDAO(ChannelConfigDAO channelConfigDAO) {
		this.channelConfigDAO = channelConfigDAO;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			String orgCode = paraMap.get("orgCode");
			String orgMerchantCode = paraMap.get("orgMerchantCode");
			String orgKey = paraMap.get("orgKey");
			String operator = paraMap.get("operator");
			String terminalCode = paraMap.get("terminalCode");
			String accessCode = paraMap.get("accessCode");
			String transType = paraMap.get("transType");
			String keyFilePath = paraMap.get("keyFilePath");
			String mcc = paraMap.get("mcc");
			String merchantBillName = paraMap.get("merchantBillName");
			String currencyCode = paraMap.get("currencyCode");
			String pattern = paraMap.get("pattern");
			String requestMerchantName = paraMap.get("requestMerchantName");
			String supportWebsite = paraMap.get("supportWebsite");
			String fitMerchantType = paraMap.get("fitMerchantType");

			ChannelConfig channelConfig = new ChannelConfig();
			channelConfig.setCreateDate(new Date());
			channelConfig.setKeyFilePath(keyFilePath);
			channelConfig.setOperator(operator);
			channelConfig.setOrgCode(orgCode);
			channelConfig.setOrgKey(orgKey);
			channelConfig.setTerminalCode(terminalCode);
			channelConfig.setAccessCode(accessCode);
			channelConfig.setTransType(transType);
			channelConfig.setOrgMerchantCode(orgMerchantCode);
			channelConfig.setStatus(1);
			channelConfig.setCurrencyCode(currencyCode);
			channelConfig.setMcc(mcc);
			channelConfig.setPattern(pattern);
			channelConfig.setMerchantBillName(merchantBillName);
			channelConfig.setRequestMerchantName(requestMerchantName);
			channelConfig.setSupportWebsite(supportWebsite);
			channelConfig.setFitMerchantType(fitMerchantType);
			channelConfigDAO.create(channelConfig);
			channelConfigDAO.create("createChannelSndrelationDate", channelConfig);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}
}
