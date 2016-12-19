/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.crosspay.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.ChannelResponseDto;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.service.ChannelService;
import com.pay.util.JSonUtil;

/**
 * 系统畅通性测试
 * 
 * @author peiyu.yang
 */
public class SystemDetectedHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private ChannelService channelService;
	private MemberQueryService memberQueryService;


	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();
		
		logger.info("dataMsg: "+dataMsg);
		
		try {
			
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
			
			String memberCode = paraMap.get("memberCode");
			String orgCode = paraMap.get("orgCode");
			
			PaymentInfo paymentInfo = new PaymentInfo();
			
			PaymentResult paymentResult = new PaymentResult();
			
			paymentInfo.setPaymentResult(paymentResult);
			paymentInfo.setPaymentOrderNo("1051509111827023897");
			paymentInfo.setOrderId("1441967172629");
			paymentInfo.setDisplayName("SystemTest");
			paymentInfo.setGoodsDesc("SystemTest");
			paymentInfo.setGoodsName("SystemTest");
			paymentInfo.setSubmitTime("20150911182612");
			paymentInfo.setFailureTime("20160911182612");
			paymentInfo.setCustomerIP("45.56.85.146");
			paymentInfo.setOrderAmount("100");
			paymentInfo.setTradeType("1000");
			paymentInfo.setPayerAccount("18621758398");
			paymentInfo.setPayType("EDC");
			paymentInfo.setCurrencyCode("USD");
			paymentInfo.setBorrowingMarked("0");
			paymentInfo.setCouponFlag("1");
			paymentInfo.setPartnerId(memberCode);
			paymentInfo.setPaymentType("2");
			paymentInfo.setSettlementCurrencyCode("CNY");
			paymentInfo.setVersion("1.0");
			paymentInfo.setSiteId("www.pay.com");
			paymentInfo.setBillName("SystemTest");
			paymentInfo.setBillAddress("上海,中国");
			paymentInfo.setBillCity("上海");
			paymentInfo.setBillEmail("peiyu.yang@ipaylinks.com");
			paymentInfo.setBillPostalCode("200001");
			paymentInfo.setBillStreet("商城路738号");
			paymentInfo.setBillState("shanghai");
			paymentInfo.setCardExpirationMonth("02");
			paymentInfo.setCardExpirationYear("2020");
			paymentInfo.setCardHolderEmail("");
			paymentInfo.setCardHolderFirstName("chao");
			paymentInfo.setCardHolderLastName("yue");
			paymentInfo.setCardHolderNumber("4111111111111111");
			paymentInfo.setSecurityCode("001");
			paymentInfo.setCardHolderPhoneNumber("13600000000");
			paymentInfo.setTransType("EDC");
			paymentInfo.setBillCountryCode("86");
			paymentInfo.setCharset("1");
			paymentInfo.setOrgCode(orgCode);
			
			PaymentOrderDTO paymentOrderDTO = new  PaymentOrderDTO();
			
			MemberBaseInfoBO memberBaseInfoBO = memberQueryService
					.queryMemberBaseInfoByMemberCode(Long.valueOf(memberCode));
			
			paymentOrderDTO.setPayeeName(memberBaseInfoBO.getName());
			paymentOrderDTO.setPayType(paymentInfo.getPayType());
			paymentOrderDTO.setMemberType(memberBaseInfoBO.getMemberType());
			
            
			ChannelResponseDto channelResponse= channelService.systemDetected(paymentOrderDTO,paymentInfo);
			
			resultMap.put("responseCode",channelResponse.getResponseCode());
			resultMap.put("responseDesc",channelResponse.getResponseDesc());
		} catch (Exception e) {
			logger.error("api pay error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}

    
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}


	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}
}
