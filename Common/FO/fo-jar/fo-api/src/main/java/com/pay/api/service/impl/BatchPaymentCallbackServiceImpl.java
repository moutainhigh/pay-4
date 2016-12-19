/**
 *  File: BatchPaymentCallbackServiceImpl.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 26, 2011   ch-ma     Create
 *
 */
package com.pay.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.api.dto.BatchPaymentItemResult;
import com.pay.api.dto.BatchPaymentResult;
import com.pay.api.model.MerchantConfigure;
import com.pay.api.model.MerchantConfigureCriteria;
import com.pay.api.service.BatchPaymentCallbackService;
import com.pay.api.service.ISecurityProvider;
import com.pay.api.util.FreeMarkerUtil;
import com.pay.api.util.ParameterXmlParserUtil;
import com.pay.inf.dao.BaseDAO;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.Base64Util;
import com.pay.util.MD5Util;

/**
 * 
 */
public class BatchPaymentCallbackServiceImpl implements
		BatchPaymentCallbackService {
	
	private JmsSender jmsSender;
	private BaseDAO<MerchantConfigure> merchantConfigureDao;
	private Integer notify_templateId;
	private ISecurityProvider securityProvider;
	private String responseXmlTemplate;
	private Log logger = LogFactory
			.getLog(BatchPaymentCallbackServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.api.service.BatchPaymentCallbackService#notifyHandle(com.pay
	 * .api.dto.BatchPaymentResult)
	 */
	@Override
	public void notifyHandle(BatchPaymentResult result) {
		String merchantCode = String.valueOf(result.getMerchantCode());

		MerchantConfigureCriteria criteria = new MerchantConfigureCriteria();
		criteria.createCriteria().andMerchantCodeEqualTo(merchantCode);
		MerchantConfigure merchantConfigure = (MerchantConfigure) merchantConfigureDao
				.findObjectByCriteria(criteria);
		String notifyUrl = merchantConfigure.getNotifyUrl();
		String resultXml = generateResultXml(result);
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("responseParameters", new String(Base64Util.encode(resultXml.getBytes())));

		HttpNotifyRequest httpNotifyRequest = new HttpNotifyRequest();
		httpNotifyRequest.setMerchantCode(Long.valueOf(merchantCode));
		httpNotifyRequest.setData(paraMap);
		httpNotifyRequest.setUrl(notifyUrl);
		httpNotifyRequest.setTemplateId(notify_templateId);
		try {
			jmsSender.send(httpNotifyRequest);
		} catch (Exception e) {
			logger.error("notify merchant error:", e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		String resultXml = "aaaaa";
		String b1 = new String(Base64Util.encode(resultXml.getBytes()));
		System.out.println( b1);
		System.out.println( new String(Base64Util.decode(b1.getBytes()))); 
	}
	
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	public void setMerchantConfigureDao(
			BaseDAO<MerchantConfigure> merchantConfigureDao) {
		this.merchantConfigureDao = merchantConfigureDao;
	}

	public void setNotify_templateId(Integer notifyTemplateId) {
		notify_templateId = notifyTemplateId;
	}

	public void setSecurityProvider(ISecurityProvider securityProvider) {
		this.securityProvider = securityProvider;
	}

	public void setResponseXmlTemplate(String responseXmlTemplate) {
		this.responseXmlTemplate = responseXmlTemplate;
	}

	private String generateResultXml(BatchPaymentResult result) {
		Map parameterMap = new HashMap();
		parameterMap.put("BIZ_NO", result.getBizNo());
		parameterMap.put("TOTAL_AMOUNT", result.getTotalAmount());
		parameterMap.put("TOTAL_COUNT", result.getTotalCount());
		parameterMap.put("SUCCESS_AMOUNT", result.getSuccessAmount());
		parameterMap.put("SUCCESS_COUNT", result.getSuccessCount());
		parameterMap.put("ERROR_CODE", result.getErrorCode());
		parameterMap.put("ERROR_MSG", result.getErrorMsg());
		
		MerchantConfigureCriteria criteria = new MerchantConfigureCriteria();
		criteria.createCriteria().andMerchantCodeEqualTo(String.valueOf(result.getMerchantCode()));
		MerchantConfigure merchantConfigure = (MerchantConfigure) merchantConfigureDao
				.findObjectByCriteria(criteria);
		String srcData = buildSignMsg(result);
		String signvalue = MD5Util.md5Hex(srcData + merchantConfigure.getPublicKey());
		parameterMap.put("SIGNVALUE", signvalue);
		
		//parameterMap.put("SIGNVALUE", securityProvider.generateSignature(srcData));

		List<BatchPaymentItemResult> itemList = result.getItemList();
		parameterMap.put("itemList", itemList);

		String resultXml = FreeMarkerUtil.parser(responseXmlTemplate.trim(),
				parameterMap);

		return ParameterXmlParserUtil.xmlFormat(resultXml);
	}
	private String buildSignMsg(BatchPaymentResult result) {
		StringBuilder sb = new StringBuilder();
		sb.append(result.getBizNo());
		sb.append(result.getTotalAmount());
		sb.append(result.getTotalCount());
		sb.append(result.getSuccessAmount());
		sb.append(result.getSuccessCount());
		sb.append(result.getTotalFee());
		sb.append(result.getErrorCode());
		return sb.toString();
	}
}
