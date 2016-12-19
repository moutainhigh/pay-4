/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.paymentchannelitem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.dao.ChannelConfigDAO;
import com.pay.channel.model.ChannelConfig;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 渠道处理
 * 
 * @author chma
 */
public class ChannelConfigQueryHandler implements EventHandler {

	private final Log logger = LogFactory
			.getLog(ChannelConfigQueryHandler.class);
	private ChannelConfigDAO channelConfigDAO;

	public void setChannelConfigDAO(ChannelConfigDAO channelConfigDAO) {
		this.channelConfigDAO = channelConfigDAO;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
			Map pageMap = (Map) paraMap.get("page");
			String orgCode = String.valueOf(paraMap.get("orgCode"));
			String orgMerchantCode = String.valueOf(paraMap.get("orgMerchantCode"));
			String orgKey = String.valueOf(paraMap.get("orgKey"));
			String operator = String.valueOf(paraMap.get("operator"));
			String keyFilePath = String.valueOf(paraMap.get("keyFilePath"));
			String id = String.valueOf(paraMap.get("id"));
			String status = String.valueOf(paraMap.get("status"));
			String mcc = String.valueOf(paraMap.get("mcc"));
			String currencyCode = String.valueOf(paraMap.get("currencyCode"));
			String merchantBillName = String.valueOf(paraMap.get("merchantBillName"));
			String fitMerchantType = String.valueOf(paraMap.get("fitMerchantType"));
			String aucrFlag = String.valueOf(paraMap.get("aucrFlag"));

			ChannelConfig channelConfig = new ChannelConfig();
			
			if(!StringUtil.isEmpty(orgCode)){
				channelConfig.setOrgCode(orgCode);
			}
			if(!StringUtil.isEmpty(orgKey)){
				channelConfig.setOrgKey(orgKey);
			}
			if(!StringUtil.isEmpty(orgMerchantCode)){
				channelConfig.setOrgMerchantCode(orgMerchantCode);
			}
			if(!StringUtil.isEmpty(operator)){
				channelConfig.setOperator(operator);
			}
			if(!StringUtil.isEmpty(currencyCode)){
				channelConfig.setCurrencyCode(currencyCode);
			}
			if (!StringUtil.isEmpty(id)) {
				channelConfig.setId(Long.valueOf(id));
			}
			if (!StringUtil.isEmpty(status)) {
				channelConfig.setStatus(Integer.valueOf(status));
			}
			if(!StringUtil.isEmpty(merchantBillName)){
				channelConfig.setMerchantBillName(merchantBillName);
			}
			if(!StringUtil.isEmpty(mcc)){
				channelConfig.setMcc(mcc);
			}
			if(!StringUtil.isEmpty(fitMerchantType)){
				channelConfig.setFitMerchantType(fitMerchantType);
			}
			if(!StringUtil.isEmpty(aucrFlag)){
				channelConfig.setAucrFlag(aucrFlag);
			}
			List<ChannelConfig> channelConfigs = null;
			if(pageMap == null){
				channelConfigs = channelConfigDAO.findByCriteria(channelConfig);
			}else{
				Page page = MapUtil.map2Object(Page.class, pageMap);
				channelConfigs = channelConfigDAO.findByCriteria(channelConfig, page);
				resultMap.put("page", page);
			}
			
			resultMap.put("channelConfigs", channelConfigs);
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
