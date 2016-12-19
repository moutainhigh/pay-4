/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.websiteconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.model.PartnerWebsiteConfig;
import com.pay.txncore.crosspay.model.PartnerWebsiteConfigCriteria;
import com.pay.txncore.crosspay.service.PartnerWebsiteConfigService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 获取配置网站
 * 
 * @author chma
 */
public class PartnerWebsiteQueryHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(PartnerWebsiteQueryHandler.class);
	private PartnerWebsiteConfigService partnerWebsiteConfigService;

	public void setPartnerWebsiteConfigService(
			PartnerWebsiteConfigService partnerWebsiteConfigService) {
		this.partnerWebsiteConfigService = partnerWebsiteConfigService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map resultMap = new HashMap();

		Map paraMap = null;
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}
		Map pageMap = (Map) paraMap.get("page");
		Page page = MapUtil.map2Object(Page.class, pageMap);
		
			String type=""+paraMap.get("type");
			
			if(StringUtil.isEmpty(type)){
				type="1";
			}
			
			if(type.equals("1")){
				String partnerId = (String) paraMap.get("partnerId");
				String url = (String) paraMap.get("url");
				String status = (String) paraMap.get("status");
				String statusIn = (String) paraMap.get("statusIn") ; //add log 查询域名状态集合 delin  2016年6月16日15:34:22
				PartnerWebsiteConfig websiteConfig = new PartnerWebsiteConfig();
			
				if (!StringUtil.isEmpty(statusIn)) {
					websiteConfig.setStatusIn(statusIn);
				}
				if (!StringUtil.isEmpty(partnerId)) {
					websiteConfig.setPartnerId(partnerId);
				}
				if (!StringUtil.isEmpty(url)) {
					websiteConfig.setUrl(url);
				}
				if (!StringUtil.isEmpty(status)) {
					websiteConfig.setStatus(status);
				}

				List<PartnerWebsiteConfig> websiteConfigs = partnerWebsiteConfigService
						.findWebsiteConfig(websiteConfig, page);

				if (null != websiteConfigs && !websiteConfigs.isEmpty()) {
					resultMap.put("result", websiteConfigs);
				}
				resultMap.put("page", page);

				resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());

				return JSonUtil.toJSonString(resultMap);	
			}else if(type.equals("2")){
				List<Map>  partnerWebsiteConfigs=(List<Map>) paraMap.get("list");		
				List<PartnerWebsiteConfig> websiteConfigs = partnerWebsiteConfigService
						.findWebsiteConfigs(partnerWebsiteConfigs, page);
				if (null != websiteConfigs && !websiteConfigs.isEmpty()) {
					resultMap.put("result", websiteConfigs);
				}
				resultMap.put("page", page);

				resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
				
				return JSonUtil.toJSonString(resultMap);	
			}
		return null;
	}
}
