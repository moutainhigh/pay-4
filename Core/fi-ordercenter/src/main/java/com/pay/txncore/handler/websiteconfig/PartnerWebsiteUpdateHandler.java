/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.websiteconfig;

import java.util.HashMap;
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
public class PartnerWebsiteUpdateHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(PartnerWebsiteUpdateHandler.class);
	private PartnerWebsiteConfigService partnerWebsiteConfigService;

	public void setPartnerWebsiteConfigService(
			PartnerWebsiteConfigService partnerWebsiteConfigService) {
		this.partnerWebsiteConfigService = partnerWebsiteConfigService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map resultMap = new HashMap();

		Map<String, String> request = null;
		try {
			request = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		String id = request.get("id");
		String partnerId = request.get("partnerId");
		String siteId = request.get("siteId");
		String status = request.get("status");
		String ip = request.get("ip");
		String operator = request.get("operator");
		String remark = request.get("remark");
		String sendCredorax = request.get("sendCredorax");
		String bacth=request.get("bacth");
		String category=request.get("category");
		if (StringUtil.isEmpty(id)) {
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}
		if(!StringUtil.isEmpty(bacth)){ //判断是否批次操作 ddl 2016年6月4日12:09:12
			String[] ids = id.split(",");
			for (String  i: ids) {//批量更新状态
				PartnerWebsiteConfig partnerWebsiteConfig = partnerWebsiteConfigService
						.findById(Long.valueOf(i));
				if (!StringUtil.isEmpty(i)) {
					partnerWebsiteConfig.setIp(i);
				}
				if (!StringUtil.isEmpty(category)) {
					partnerWebsiteConfig.setCategory(category);
				}
				if (!StringUtil.isEmpty(operator)) {
					partnerWebsiteConfig.setOperator(operator);
				}
				if (!StringUtil.isEmpty(remark)) {
					partnerWebsiteConfig.setRemark(remark);
				}
				if (!StringUtil.isEmpty(status)) {
					partnerWebsiteConfig.setStatus(status);
				}
				if(!StringUtil.isEmpty(sendCredorax)){
					partnerWebsiteConfig.setSendCredorax(sendCredorax);
				}
				partnerWebsiteConfigService
						.updatePartnerWebsiteConfig(partnerWebsiteConfig);
			}
			
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}
		
		PartnerWebsiteConfig partnerWebsiteConfig = partnerWebsiteConfigService
				.findById(Long.valueOf(id));

		if (null == partnerWebsiteConfig) {
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		if (!StringUtil.isEmpty(ip)) {
			partnerWebsiteConfig.setIp(ip);
		}

		if (!StringUtil.isEmpty(operator)) {
			partnerWebsiteConfig.setOperator(operator);
		}
		if (!StringUtil.isEmpty(partnerId)) {
			partnerWebsiteConfig.setPartnerId(partnerId);
		}
		if (!StringUtil.isEmpty(remark)) {
			partnerWebsiteConfig.setRemark(remark);
		}
		if (!StringUtil.isEmpty(status)) {
			partnerWebsiteConfig.setStatus(status);
		}

		if (!StringUtil.isEmpty(siteId)) {
			partnerWebsiteConfig.setUrl(siteId);
		}
		
		if(!StringUtil.isEmpty(sendCredorax)){
			partnerWebsiteConfig.setSendCredorax(sendCredorax);
		}
		if (!StringUtil.isEmpty(category)) {
			partnerWebsiteConfig.setCategory(category);
		}
		partnerWebsiteConfigService
				.updatePartnerWebsiteConfig(partnerWebsiteConfig);

		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());

		return JSonUtil.toJSonString(resultMap);
	}
}
