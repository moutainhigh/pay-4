/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.websiteconfig;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.model.PartnerWebsiteConfig;
import com.pay.txncore.crosspay.service.PartnerWebsiteConfigService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 获取配置网站
 * 
 * @author chma
 */
public class PartnerWebsiteAddHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(PartnerWebsiteAddHandler.class);
	private PartnerWebsiteConfigService partnerWebsiteConfigService;

	public void setPartnerWebsiteConfigService(
			PartnerWebsiteConfigService partnerWebsiteConfigService) {
		this.partnerWebsiteConfigService = partnerWebsiteConfigService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map resultMap = new HashMap();

		Map paramMap = null;
		try {
			paramMap = JSonUtil.toObject(dataMsg,
					new HashMap().getClass());
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}
		String type=""+paramMap.get("type");
		if(StringUtil.isEmpty(type)){
			type="1";
		}
		if(type.equals("1")){
			Map<String, String> request=new HashMap<String, String>();
			request.putAll(paramMap);
			String partnerId = request.get("partnerId");
			String siteId = request.get("url");
			String ip = request.get("ip");
			String operator = request.get("operator");
			String remark = request.get("remark");

			PartnerWebsiteConfig partnerWebsiteConfig = new PartnerWebsiteConfig();
			partnerWebsiteConfig.setCreateDate(new Date());
			partnerWebsiteConfig.setIp(ip);
			partnerWebsiteConfig.setOperator(operator);
			partnerWebsiteConfig.setPartnerId(partnerId);
			partnerWebsiteConfig.setRemark(remark);
			partnerWebsiteConfig.setRemark(remark);
			partnerWebsiteConfig.setStatus("0");
			partnerWebsiteConfig.setUrl(siteId);
			partnerWebsiteConfigService
					.createPartnerWebsiteConfig(partnerWebsiteConfig);

			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());

			return JSonUtil.toJSonString(resultMap);
		}else if(type.equals("2")){
			List<Map>  partnerWebsiteConfigs=(List<Map>) paramMap.get("list");
			partnerWebsiteConfigService.createPartnerWebsiteConfig(partnerWebsiteConfigs);
			
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}
		return null;
	}
}
