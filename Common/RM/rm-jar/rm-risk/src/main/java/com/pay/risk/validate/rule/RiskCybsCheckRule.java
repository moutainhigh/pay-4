/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.risk.validate.rule;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.caucho.hessian.client.HessianProxyFactory;
import com.pay.acc.service.member.MemberProductService;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.rule.MessageRule;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.risk.dto.PaymentInfo;
import com.pay.risk.dto.PaymentResult;
import com.pay.util.CountryCodeEnum;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * cybs判断
 */
public class RiskCybsCheckRule extends MessageRule {
	private final Log logger = LogFactory.getLog(getClass());
	static String PRODUCT_CODE = "CYBS_CODE";
	protected MemberProductService memberProductService;
	private String preServerUrl;
	private long readTimeOut;
	private MemberQueryService memberQueryService;

	public void setMemberProductService(
			MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}

	public void setPreServerUrl(String preServerUrl) {
		this.preServerUrl = preServerUrl;
	}
	public void setReadTimeOut(long readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentInfo paymentInfo = (PaymentInfo) validateBean;
		PaymentResult paymentResult = paymentInfo.getPaymentResult();

		String memberCode = paymentInfo.getPartnerId();
		
		logger.info("start to cybs");

		boolean isHaveProduct = memberProductService.isHaveProduct(
				Long.valueOf(memberCode), PRODUCT_CODE);

		if (!isHaveProduct) {
			paymentResult.setResponseCode("3200");
			paymentResult.setResponseDesc("没有开通cybs产品");
			return true;
		}
		
		Long mcc = memberQueryService.queryEnterpriseMccCode(Long.valueOf(paymentInfo.getPartnerId()));

		Map<String, String> paraMap = new HashMap<String, String>();
		
		if(StringUtil.isEmpty(paymentInfo.getBillCity())
				||StringUtil.isEmpty(paymentInfo.getBillCountryCode())
				||StringUtil.isEmpty(paymentInfo.getBillState())
				||StringUtil.isEmpty(paymentInfo.getBillPostalCode())
				||StringUtil.isEmpty(paymentInfo.getBillStreet())){
			paraMap.put("billTo_city","Mountain View");
			paraMap.put("billTo_country","US");
			paraMap.put("billTo_state","CA");
			paraMap.put("billTo_postalCode","94043");
			paraMap.put("billTo_street1","1295 Charleston Rd");
		}else{
			String billCountryCode = paymentInfo.getBillCountryCode();
			CountryCodeEnum currencyByCode = CountryCodeEnum.getCurrencyByCode(billCountryCode.toUpperCase()); //add logs delin 2016年7月4日16:10:41 送cbys从之前的国家三字码改为国家二字码

			if(currencyByCode != null ){
				billCountryCode = currencyByCode.getCodeII();
			}
			paraMap.put("billTo_city", paymentInfo.getBillCity());
			paraMap.put("billTo_country", billCountryCode);

			paraMap.put("billTo_state", paymentInfo.getBillState());
			paraMap.put("billTo_postalCode", paymentInfo.getBillPostalCode());
			paraMap.put("billTo_street1", paymentInfo.getBillStreet());
		}

		if(StringUtil.isEmpty(paymentInfo.getBillEmail())){
			paraMap.put("billTo_email","null@cybersource.com");
		}else{
		    paraMap.put("billTo_email", paymentInfo.getBillEmail());
		}
		
		if(StringUtil.isEmpty(paymentInfo.getCustomerIP())){
			paraMap.put("billTo_ipAddress","10.7.111.111/127.0.0.1");
		}else{
			paraMap.put("billTo_ipAddress", paymentInfo.getCustomerIP());
		}
		
		if(StringUtil.isEmpty(paymentInfo.getBillPhoneNumber())){
			paraMap.put("billTo_phoneNumber","");
		}else{
			paraMap.put("billTo_phoneNumber", paymentInfo.getBillPhoneNumber());
		}

		if(StringUtil.isEmpty(paymentInfo.getCardHolderFirstName())
				||StringUtil.isEmpty(paymentInfo.getCardHolderLastName())){
			paraMap.put("billTo_firstName","noreal");
			paraMap.put("billTo_lastName","name");
		}else if(paymentInfo.getCardHolderFirstName().equals(paymentInfo.getCardHolderLastName())){  // add logs delin 2016年7月4日 14:20:27 新增如果持卡人的firstName 和lastName 一样的话送默认值
			paraMap.put("billTo_firstName","noreal");
			paraMap.put("billTo_lastName","name");
		}else if(paymentInfo.getCardHolderFirstName().equals("CHEN") //add delin 2016年7月4日16:33:43 如果出現這些字段則送默認的字段
				|| paymentInfo.getCardHolderLastName().equals("CHEN") 
				|| paymentInfo.getCardHolderFirstName().equals("yang")
				|| paymentInfo.getCardHolderLastName().equals("yang") 
				|| paymentInfo.getCardHolderFirstName().equals("马")
				|| paymentInfo.getCardHolderLastName().equals("马") 
				|| paymentInfo.getCardHolderFirstName().equals("BillFristNmae.BillLastNme")
				|| paymentInfo.getCardHolderLastName().equals("BillFristNmae.BillLastNme") 
				|| paymentInfo.getCardHolderFirstName().equals("HarryCOM Terry")
				|| paymentInfo.getCardHolderLastName().equals("HarryCOM Terry") 
				|| paymentInfo.getCardHolderFirstName().equals("HarryCOM zacks")
				|| paymentInfo.getCardHolderLastName().equals("HarryCOM zacks") 
				|| paymentInfo.getCardHolderFirstName().equals("ajkhfAJLFKJA DAFASD")
				|| paymentInfo.getCardHolderLastName().equals("ajkhfAJLFKJA DAFASD") 
				|| paymentInfo.getCardHolderFirstName().equals("ABC")
				|| paymentInfo.getCardHolderLastName().equals("ABC") 
				|| paymentInfo.getCardHolderFirstName().equals("test test")
				|| paymentInfo.getCardHolderLastName().equals("test test") 
		){
			paraMap.put("billTo_firstName","noreal");
			paraMap.put("billTo_lastName","name");
		}else{
			paraMap.put("billTo_firstName", paymentInfo.getCardHolderFirstName());
			paraMap.put("billTo_lastName", paymentInfo.getCardHolderLastName());
		}
		
		paraMap.put("card_accountNumber", paymentInfo.getCardHolderNumber());
		paraMap.put("card_expirationMonth",
				paymentInfo.getCardExpirationMonth());
		paraMap.put("card_expirationYear", paymentInfo.getCardExpirationYear());
		
		if(!StringUtil.isEmpty(paymentInfo.getShippingCountryCode())){
			paraMap.put("shipTo_country", paymentInfo.getShippingCountryCode());
		}
		if(!StringUtil.isEmpty(paymentInfo.getShippingState())){
			paraMap.put("shipTo_state", paymentInfo.getShippingState());
		}
		if(!StringUtil.isEmpty(paymentInfo.getShippingCity())){
			paraMap.put("shipTo_city", paymentInfo.getShippingCity());
		}
		
		if(!StringUtil.isEmpty(paymentInfo.getShippingStreet())){
			paraMap.put("shipTo_street1", paymentInfo.getShippingStreet());
		}
		
		if(!StringUtil.isEmpty(paymentInfo.getShippingPostalCode())){
			paraMap.put("shipTo_postalCode", paymentInfo.getShippingPostalCode());
		}
		
		if(!StringUtil.isEmpty(paymentInfo.getShippingPhoneNumber())){
			paraMap.put("shipTo_phoneNumber", paymentInfo.getShippingPhoneNumber());
		}
		
	/*	if(!StringUtil.isEmpty(paymentInfo.getShippingName())){
			paraMap.put("shipTo_firstName", paymentInfo.getShippingName()); // add logs delin 2016年7月4日 14:20:27 暂时获取不到这两个字段 不送。
		}
		
		if(!StringUtil.isEmpty(paymentInfo.getShippingName())){
			paraMap.put("shipTo_lastName", paymentInfo.getShippingName());
		}		*/
		paraMap.put("deviceFingerprintID", paymentInfo.getDeviceFingerprintId());
		paraMap.put("merchantReferenceCode", paymentInfo.getOrderId());
		paraMap.put("purchaseTotals_currency", paymentInfo.getCurrencyCode());
		paraMap.put("item_0_unitPrice",
				new BigDecimal(paymentInfo.getOrderAmount()).divide(
						new BigDecimal(100)).toString());
		paraMap.put("merchantDefinedData_mddField_1", paymentInfo.getPartnerId());
		paraMap.put("merchantDefinedData_mddField_2", paymentInfo.getGoodsDesc());
		paraMap.put("merchantDefinedData_mddField_3", ""+mcc);
		paraMap.put("merchantDefinedData_mddField_4", paymentInfo.getSiteId());
		
		if(!StringUtil.isEmpty(paymentInfo.getRegisterUserId())){
			paraMap.put("merchantDefinedData_mddField_5", paymentInfo.getRegisterUserId());
			paraMap.put("billTo_customerID", paymentInfo.getRegisterUserId());
		}

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);

		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		
		String decision="";
		
		logger.info("start-request-cybs: "+System.currentTimeMillis());
		
		try{
			HessianProxyFactory factory = new HessianProxyFactory();

			factory.setReadTimeout(readTimeOut);
			HessianInvokeService hessianInvokeService = null;
			
			hessianInvokeService = (HessianInvokeService) factory.create(
						HessianInvokeService.class, preServerUrl);

			String result = hessianInvokeService.invoke("7010203", sysTraceNo,
					SystemCodeEnum.TXNCORE.getCode(), SystemCodeEnum.PRE.getCode(),
					SystemCodeEnum.PRE.getVersion(), param.getDataLength(),
					param.getMsgCompress(), param.getDataMsg());
			
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();
			
			@SuppressWarnings("unchecked")
			Map<String, String> resultMap = JSonUtil.toObject(result,
					new HashMap<String, String>().getClass());

			decision = resultMap.get("decision");
			
		}catch(Exception e){
			e.printStackTrace();
			return true;
		}
		
		logger.info("start-request-cybs2: "+System.currentTimeMillis());

		if (logger.isInfoEnabled()) {
			logger.info("cybs result:" + decision + ",orderId:"
					+ paymentInfo.getOrderId());
		}
		boolean resultFlg = "ACCEPT".equals(decision)
				|| "REVIEW".equals(decision);
		if (!resultFlg) {
			paymentResult.setResponseCode(getMessageId());
			paymentResult.setResponseDesc(getMessage());
			return false;
		} else {
			paymentResult.setResponseCode("3200");
			paymentResult.setResponseDesc("cybs 风控通过!");
			return true;
		}
	}
}
