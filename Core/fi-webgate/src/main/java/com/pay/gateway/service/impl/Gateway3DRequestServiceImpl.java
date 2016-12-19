/**
 * 
 */
package com.pay.gateway.service.impl;


import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;









import com.pay.gateway.dto.ChinaLocalPayRequest;
import com.pay.gateway.dto.ChinaLocalPayResponse;
import com.pay.gateway.dto.CrosspayApiRequest;
import com.pay.gateway.dto.CrosspayApiResponse;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.gateway.dto.TokenCrosspayApiRequest;
import com.pay.gateway.dto.TokenCrosspayApiResponse;
import com.pay.gateway.dto.TokenCrosspayRequest;
import com.pay.gateway.dto.TokenCrosspayResponse;
import com.pay.gateway.service.Gateway3DRequestService;


/**
 * @author chaoyue
 *
 */

public class Gateway3DRequestServiceImpl implements Gateway3DRequestService {
	private final Log logger = LogFactory
			.getLog(Gateway3DRequestServiceImpl.class);
    static final char[] HEX_TABLE = new char[] {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	private String reqUrl;
	private String vpc_Version;
	private String hashKeys = new String();
	private  String hashValues = new String();
	
	public String hashAllFields(Map fields,String SECURE_SECRET) {

	    hashKeys = "";
	    hashValues = "";
	
	    // create a list and sort it
	    List fieldNames = new ArrayList(fields.keySet());
	    Collections.sort(fieldNames);
	
	    // create a buffer for the md5 input and add the secure secret first
	    StringBuffer buf = new StringBuffer();
	    buf.append(SECURE_SECRET);
	
	    // iterate through the list and add the remaining field values
	    Iterator itr = fieldNames.iterator();
	
	    while (itr.hasNext()) {
	        String fieldName = (String) itr.next();
	        String fieldValue = (String) fields.get(fieldName);
	            hashKeys += fieldName + ", ";
	        if ((fieldValue != null) && (fieldValue.length() > 0)) {
	            buf.append(fieldValue);
	        }
	    }
	
	    MessageDigest md5 = null;
	    byte[] ba = null;
	
	    // create the md5 hash and ISO-8859-1 encode it
	    try {
	        md5 = MessageDigest.getInstance("MD5");
	        ba = md5.digest(buf.toString().getBytes("ISO-8859-1"));
	    } catch (Exception e) {} // wont happen
	
	    hashValues = buf.toString();
	    return hex(ba);
	
	} 
	
	private String hex(byte[] input) {
        // create a StringBuffer 2x the size of the hash array
        StringBuffer sb = new StringBuffer(input.length * 2);

        // retrieve the byte array data, convert it to hex
        // and add it to the StringBuffer
        for (int i = 0; i < input.length; i++) {
            sb.append(HEX_TABLE[(input[i] >> 4) & 0xf]);
            sb.append(HEX_TABLE[input[i] & 0xf]);
        }
        return sb.toString();
    }
	
	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public String getVpc_Version() {
		return vpc_Version;
	}

	public void setVpc_Version(String vpc_Version) {
		this.vpc_Version = vpc_Version;
	}

	public Map<String, String> getSalesParameters(Map<String, String> requestData){
		Map<String, String> parameters = new HashMap<String, String>();
		String cardOrg = requestData.get("cardOrg");
		String vpc_card = null;
		if("MASTER".equals(cardOrg))vpc_card="Mastercard";
		if("VISA".equals(cardOrg))vpc_card="Visa";
		parameters.put("vpc_Version", requestData.get("version"));
//		parameters.put("submit", "Continue");
		parameters.put("vpc_Command","pay");
		parameters.put("vpc_AccessCode", requestData.get("accessCode"));
		parameters.put("vpc_MerchTxnRef",String.valueOf(requestData.get("channelOrderNo")));
		parameters.put("vpc_Merchant", requestData.get("orgMerchantCode"));
		parameters.put("vpc_gateway", "ssl");
		if(requestData.get("currencyCode")!=null){parameters.put("vpc_currency", requestData.get("currencyCode"));}
		parameters.put("vpc_card", vpc_card);
		parameters.put("vpc_OrderInfo", "ORDER"+String.valueOf(requestData.get("channelOrderNo")));
		parameters.put("vpc_Locale", requestData.get("language"));
		parameters.put("vpc_ReturnURL", requestData.get("returnUrl"));
		
		//传入金额为厘 需要转换成分
		String orderAmount = String.valueOf(requestData.get("orderAmount"));
		orderAmount = new BigDecimal(orderAmount).divide(new BigDecimal("10")).longValue() + "";
		parameters.put("vpc_Amount",orderAmount);
	
		parameters.put("vpc_CardNum", String.valueOf(requestData.get("cardHolderNumber")));
		parameters.put("vpc_CardExp", requestData.get("cardExp"));
		
		parameters.put("vpc_CardSecurityCode", String.valueOf(requestData.get("securityCode")));
		
		
		
		return parameters;
		
	}
	
	public Map<String, String> getConfigure(){
		Map configure = new HashMap();
		configure.put("reqUrl", reqUrl);
		configure.put("vpc_Version", vpc_Version);
		return configure;
		
		
	}
	
	
	
	public String getResponeForm(Map resultMap){
	
		
		Map<String, String> parameters =getSalesParameters(resultMap);
		String cardOrg =null;
		String vpc_card = null;
		if(resultMap.get("cardOrg")!=null)cardOrg=resultMap.get("cardOrg").toString();
		if(cardOrg!=null&&"MASTER".equals(cardOrg))vpc_card="Mastercard";
		if(cardOrg!=null&&"VISA".equals(cardOrg))vpc_card="Visa";
		
			logger.info("3d migs下单请求报文parameters:"+parameters);//添加请求报文log
			 String secureHash = hashAllFields(parameters,resultMap.get("orgKey").toString());
			 StringBuffer submitform=new StringBuffer();
		
		
			submitform.append("<html><head></head><BODY onload='document.forms[0].submit();'>");
			submitform.append("<form action='");
			submitform.append(getReqUrl());
			submitform.append("' method='post'>");
			submitform.append("<input type='hidden' name='vpc_AccessCode' value='");
			submitform.append(resultMap.get("accessCode"));
			submitform.append("' />");
//			submitform.append("<input type='hidden' name='submit' value='");
//			submitform.append("Continue");
//			submitform.append("' />");
			if(resultMap.get("currencyCode")!=null){
				submitform.append("<input type='hidden' name='vpc_currency' value='");
				submitform.append(resultMap.get("currencyCode"));
				submitform.append("' />");
			}
			
			submitform.append("<input type='hidden' name='vpc_Command' value='");
			submitform.append("pay");
			submitform.append("' />");
			submitform.append("<input type='hidden' name='vpc_CardSecurityCode' value='");
			submitform.append(resultMap.get("securityCode"));
			submitform.append("' />");
			submitform.append("<input type='hidden' name='vpc_CardExp' value='");
			submitform.append(resultMap.get("cardExp"));
			submitform.append("' />");
			submitform.append("<input type='hidden' name='vpc_CardNum' value='");
			submitform.append(resultMap.get("cardHolderNumber"));
			submitform.append("' />");
			submitform.append("<input type='hidden' name='vpc_ReturnURL' value='");
			submitform.append(resultMap.get("returnUrl"));
			submitform.append("' />");
			submitform.append("<input type='hidden' name='vpc_gateway' value='");
			//submitform.append("threeDSecure");
			submitform.append("ssl");
			submitform.append("' />");
			submitform.append("<input type='hidden' name='vpc_Version' value='");
			submitform.append(resultMap.get("version"));
			submitform.append("' />");
			submitform.append("<input type='hidden' name='vpc_card' value='");
			submitform.append(vpc_card);
			submitform.append("' />");
			submitform.append("<input type='hidden' name='vpc_Locale' value='");
			submitform.append(resultMap.get("language"));
			submitform.append("' />");
			submitform.append("<input type='hidden' name='vpc_Merchant' value='");
			submitform.append(resultMap.get("orgMerchantCode"));
			submitform.append("' />");
			submitform.append("<input type='hidden' name='vpc_Amount' value='");
			String orderAmount = String.valueOf(resultMap.get("orderAmount"));
			orderAmount = new BigDecimal(orderAmount).divide(new BigDecimal("10")).longValue() + "";
			submitform.append(orderAmount);
			submitform.append("' />");
//		
			submitform.append("<input type='hidden' name='vpc_MerchTxnRef' value='");
			submitform.append(resultMap.get("channelOrderNo"));
			submitform.append("' />");
			submitform.append("<input type='hidden' name='vpc_OrderInfo' value='");
			submitform.append("ORDER"+resultMap.get("channelOrderNo"));
			submitform.append("' />");
			submitform.append("<input type='hidden' name='vpc_SecureHash' value='");
			submitform.append(secureHash);
			submitform.append("' />");
//			submitform.append("<input type='submit' id='submit' ");
//			
//			submitform.append("' />");
			submitform.append("</form></BODY></html>");
			return submitform.toString();
			
		
	}
	
	
	public String getfailResponeForm(CrosspayApiResponse crosspayApiResponse,CrosspayApiRequest crosspayApiRequest){
		
		 StringBuffer submitform=new StringBuffer();
	StringBuffer sb =new StringBuffer();
	
		submitform.append("<html><head></head><BODY onload='document.forms[0].submit();'>");
		submitform.append("<form action='");
		submitform.append(crosspayApiRequest.getReturnUrl());
		submitform.append("' method='post'>");
		if(crosspayApiResponse.getAcquiringTime()!=null){
			submitform.append("<input type='hidden' name='acquiringTime' value='");
			submitform.append(crosspayApiResponse.getAcquiringTime());
			submitform.append("' />");
		}
		if(crosspayApiResponse.getCharset()!=null){
			submitform.append("<input type='hidden' name='charset' value='");
			submitform.append(crosspayApiResponse.getCharset());
			submitform.append("' />");
		}if(crosspayApiResponse.getCompleteTime()!=null){
			submitform.append("<input type='hidden' name='completeTime' value='");
			submitform.append(crosspayApiResponse.getCompleteTime());
			submitform.append("' />");
		}if(crosspayApiResponse.getCurrencyCode()!=null){
			submitform.append("<input type='hidden' name='currencyCode' value='");
			submitform.append(crosspayApiResponse.getCurrencyCode());
			submitform.append("' />");
		}if(crosspayApiResponse.getDealId()!=null){
			submitform.append("<input type='hidden' name='dealId' value='");
			submitform.append(crosspayApiResponse.getDealId());
			submitform.append("' />");
		}if(crosspayApiResponse.getMerchantBillName()!=null){
			submitform.append("<input type='hidden' name='merchantBillName' value='");
			submitform.append(crosspayApiResponse.getMerchantBillName());
			submitform.append("' />");
		}if(crosspayApiResponse.getOrderAmount()!=null){
			submitform.append("<input type='hidden' name='orderAmount' value='");
			submitform.append(crosspayApiResponse.getOrderAmount());
			submitform.append("' />");
		}if(crosspayApiResponse.getOrderId()!=null){
			submitform.append("<input type='hidden' name='orderId' value='");
			submitform.append(crosspayApiResponse.getOrderId());
			submitform.append("' />");
		}if(crosspayApiResponse.getPartnerId()!=null){
			submitform.append("<input type='hidden' name='partnerId' value='");
			submitform.append(crosspayApiResponse.getPartnerId());
			submitform.append("' />");
		}if(crosspayApiResponse.getPayAmount()!=null){
			submitform.append("<input type='hidden' name='payAmount' value='");
			submitform.append(crosspayApiResponse.getPayAmount());
			submitform.append("' />");
		}if(crosspayApiResponse.getRemark()!=null){
			submitform.append("<input type='hidden' name='remark' value='");
			submitform.append(crosspayApiResponse.getRemark());
			submitform.append("' />");
		}if(crosspayApiResponse.getResultCode()!=null){
			submitform.append("<input type='hidden' name='resultCode' value='");
			submitform.append(crosspayApiResponse.getResultCode());
			submitform.append("' />");
		}if(crosspayApiResponse.getResultMsg()!=null){
			submitform.append("<input type='hidden' name='resultMsg' value='");
			submitform.append(crosspayApiResponse.getResultMsg());
			submitform.append("' />");
		}if(crosspayApiResponse.getSignType()!=null){
			submitform.append("<input type='hidden' name='signType' value='");
			submitform.append(crosspayApiResponse.getSignType());
			submitform.append("' />");
		}if(crosspayApiResponse.getSignMsg()!=null){
			submitform.append("<input type='hidden' name='signMsg' value='");
			submitform.append(crosspayApiResponse.getSignMsg());
			submitform.append("' />");
		}if(crosspayApiResponse.getSettlementCurrencyCode()!=null){
			submitform.append("<input type='hidden' name='settlementCurrencyCode' value='");
			submitform.append(crosspayApiResponse.getSettlementCurrencyCode());
			submitform.append("' />");
		}		
		
		submitform.append("</form></BODY></html>");
		
		return submitform.toString();
		
	
}
	
	public String getfailResponeForm(TokenCrosspayApiResponse crosspayApiResponse,TokenCrosspayApiRequest crosspayApiRequest){
		
		 StringBuffer submitform=new StringBuffer();
	StringBuffer sb =new StringBuffer();
	
		submitform.append("<html><head></head><BODY onload='document.forms[0].submit();'>");
		submitform.append("<form action='");
		submitform.append(crosspayApiRequest.getReturnUrl());
		submitform.append("' method='post'>");
		if(crosspayApiResponse.getAcquiringTime()!=null){
			submitform.append("<input type='hidden' name='acquiringTime' value='");
			submitform.append(crosspayApiResponse.getAcquiringTime());
			submitform.append("' />");
		}
		if(crosspayApiResponse.getCharset()!=null){
			submitform.append("<input type='hidden' name='charset' value='");
			submitform.append(crosspayApiResponse.getCharset());
			submitform.append("' />");
		}if(crosspayApiResponse.getCompleteTime()!=null){
			submitform.append("<input type='hidden' name='completeTime' value='");
			submitform.append(crosspayApiResponse.getCompleteTime());
			submitform.append("' />");
		}if(crosspayApiResponse.getCurrencyCode()!=null){
			submitform.append("<input type='hidden' name='currencyCode' value='");
			submitform.append(crosspayApiResponse.getCurrencyCode());
			submitform.append("' />");
		}if(crosspayApiResponse.getDealId()!=null){
			submitform.append("<input type='hidden' name='dealId' value='");
			submitform.append(crosspayApiResponse.getDealId());
			submitform.append("' />");
		}if(crosspayApiResponse.getMerchantBillName()!=null){
			submitform.append("<input type='hidden' name='merchantBillName' value='");
			submitform.append(crosspayApiResponse.getMerchantBillName());
			submitform.append("' />");
		}if(crosspayApiResponse.getOrderAmount()!=null){
			submitform.append("<input type='hidden' name='orderAmount' value='");
			submitform.append(crosspayApiResponse.getOrderAmount());
			submitform.append("' />");
		}if(crosspayApiResponse.getOrderId()!=null){
			submitform.append("<input type='hidden' name='orderId' value='");
			submitform.append(crosspayApiResponse.getOrderId());
			submitform.append("' />");
		}if(crosspayApiResponse.getPartnerId()!=null){
			submitform.append("<input type='hidden' name='partnerId' value='");
			submitform.append(crosspayApiResponse.getPartnerId());
			submitform.append("' />");
		}if(crosspayApiResponse.getPayAmount()!=null){
			submitform.append("<input type='hidden' name='payAmount' value='");
			submitform.append(crosspayApiResponse.getPayAmount());
			submitform.append("' />");
		}if(crosspayApiResponse.getRemark()!=null){
			submitform.append("<input type='hidden' name='remark' value='");
			submitform.append(crosspayApiResponse.getRemark());
			submitform.append("' />");
		}if(crosspayApiResponse.getResultCode()!=null){
			submitform.append("<input type='hidden' name='resultCode' value='");
			submitform.append(crosspayApiResponse.getResultCode());
			submitform.append("' />");
		}if(crosspayApiResponse.getResultMsg()!=null){
			submitform.append("<input type='hidden' name='resultMsg' value='");
			submitform.append(crosspayApiResponse.getResultMsg());
			submitform.append("' />");
		}if(crosspayApiResponse.getSignType()!=null){
			submitform.append("<input type='hidden' name='signType' value='");
			submitform.append(crosspayApiResponse.getSignType());
			submitform.append("' />");
		}if(crosspayApiResponse.getSignMsg()!=null){
			submitform.append("<input type='hidden' name='signMsg' value='");
			submitform.append(crosspayApiResponse.getSignMsg());
			submitform.append("' />");
		}if(crosspayApiResponse.getSettlementCurrencyCode()!=null){
			submitform.append("<input type='hidden' name='settlementCurrencyCode' value='");
			submitform.append(crosspayApiResponse.getSettlementCurrencyCode());
			submitform.append("' />");
		}		
		
		submitform.append("</form></BODY></html>");
		
		return submitform.toString();
		
	
}

	public String getfailResponeForm(ChinaLocalPayResponse crosspayApiResponse,ChinaLocalPayRequest crosspayApiRequest){
		
		 StringBuffer submitform=new StringBuffer();
	StringBuffer sb =new StringBuffer();
	
		submitform.append("<html><head></head><BODY onload='document.forms[0].submit();'>");
		submitform.append("<form action='");
		submitform.append(crosspayApiRequest.getReturnUrl());
		submitform.append("' method='post'>");
		if(crosspayApiResponse.getAcquiringTime()!=null){
			submitform.append("<input type='hidden' name='acquiringTime' value='");
			submitform.append(crosspayApiResponse.getAcquiringTime());
			submitform.append("' />");
		}
		if(crosspayApiResponse.getCharset()!=null){
			submitform.append("<input type='hidden' name='charset' value='");
			submitform.append(crosspayApiResponse.getCharset());
			submitform.append("' />");
		}if(crosspayApiResponse.getCompleteTime()!=null){
			submitform.append("<input type='hidden' name='completeTime' value='");
			submitform.append(crosspayApiResponse.getCompleteTime());
			submitform.append("' />");
		}if(crosspayApiResponse.getCurrencyCode()!=null){
			submitform.append("<input type='hidden' name='currencyCode' value='");
			submitform.append(crosspayApiResponse.getCurrencyCode());
			submitform.append("' />");
		}if(crosspayApiResponse.getDealId()!=null){
			submitform.append("<input type='hidden' name='dealId' value='");
			submitform.append(crosspayApiResponse.getDealId());
			submitform.append("' />");
		}if(crosspayApiResponse.getOrderAmount()!=null){
			submitform.append("<input type='hidden' name='orderAmount' value='");
			submitform.append(crosspayApiResponse.getOrderAmount());
			submitform.append("' />");
		}if(crosspayApiResponse.getOrderId()!=null){
			submitform.append("<input type='hidden' name='orderId' value='");
			submitform.append(crosspayApiResponse.getOrderId());
			submitform.append("' />");
		}if(crosspayApiResponse.getPartnerId()!=null){
			submitform.append("<input type='hidden' name='partnerId' value='");
			submitform.append(crosspayApiResponse.getPartnerId());
			submitform.append("' />");
		}if(crosspayApiResponse.getRemark()!=null){
			submitform.append("<input type='hidden' name='remark' value='");
			submitform.append(crosspayApiResponse.getRemark());
			submitform.append("' />");
		}if(crosspayApiResponse.getResultCode()!=null){
			submitform.append("<input type='hidden' name='resultCode' value='");
			submitform.append(crosspayApiResponse.getResultCode());
			submitform.append("' />");
		}if(crosspayApiResponse.getResultMsg()!=null){
			submitform.append("<input type='hidden' name='resultMsg' value='");
			submitform.append(crosspayApiResponse.getResultMsg());
			submitform.append("' />");
		}if(crosspayApiResponse.getSignType()!=null){
			submitform.append("<input type='hidden' name='signType' value='");
			submitform.append(crosspayApiResponse.getSignType());
			submitform.append("' />");
		}if(crosspayApiResponse.getSignMsg()!=null){
			submitform.append("<input type='hidden' name='signMsg' value='");
			submitform.append(crosspayApiResponse.getSignMsg());
			submitform.append("' />");
		}
		
		submitform.append("</form></BODY></html>");
		
		return submitform.toString();
}

	@Override
	public String getfailResponeForm(CrosspayResponse crosspayApiResponse,
			CrosspayRequest crosspayApiRequest) {
		 StringBuffer submitform=new StringBuffer();
	StringBuffer sb =new StringBuffer();
	
		submitform.append("<html><head></head><BODY onload='document.forms[0].submit();'>");
		submitform.append("<form action='");
		submitform.append(crosspayApiRequest.getReturnUrl());
		submitform.append("' method='post'>");
		if(crosspayApiResponse.getAcquiringTime()!=null){
			submitform.append("<input type='hidden' name='acquiringTime' value='");
			submitform.append(crosspayApiResponse.getAcquiringTime());
			submitform.append("' />");
		}
		if(crosspayApiResponse.getCharset()!=null){
			submitform.append("<input type='hidden' name='charset' value='");
			submitform.append(crosspayApiResponse.getCharset());
			submitform.append("' />");
		}if(crosspayApiResponse.getCompleteTime()!=null){
			submitform.append("<input type='hidden' name='completeTime' value='");
			submitform.append(crosspayApiResponse.getCompleteTime());
			submitform.append("' />");
		}if(crosspayApiResponse.getCurrencyCode()!=null){
			submitform.append("<input type='hidden' name='currencyCode' value='");
			submitform.append(crosspayApiResponse.getCurrencyCode());
			submitform.append("' />");
		}if(crosspayApiResponse.getDealId()!=null){
			submitform.append("<input type='hidden' name='dealId' value='");
			submitform.append(crosspayApiResponse.getDealId());
			submitform.append("' />");
		}if(crosspayApiResponse.getMerchantBillName()!=null){
			submitform.append("<input type='hidden' name='merchantBillName' value='");
			submitform.append(crosspayApiResponse.getMerchantBillName());
			submitform.append("' />");
		}if(crosspayApiResponse.getOrderAmount()!=null){
			submitform.append("<input type='hidden' name='orderAmount' value='");
			submitform.append(crosspayApiResponse.getOrderAmount());
			submitform.append("' />");
		}if(crosspayApiResponse.getOrderId()!=null){
			submitform.append("<input type='hidden' name='orderId' value='");
			submitform.append(crosspayApiResponse.getOrderId());
			submitform.append("' />");
		}if(crosspayApiResponse.getPartnerId()!=null){
			submitform.append("<input type='hidden' name='partnerId' value='");
			submitform.append(crosspayApiResponse.getPartnerId());
			submitform.append("' />");
		}if(crosspayApiResponse.getRemark()!=null){
			submitform.append("<input type='hidden' name='remark' value='");
			submitform.append(crosspayApiResponse.getRemark());
			submitform.append("' />");
		}if(crosspayApiResponse.getResultCode()!=null){
			submitform.append("<input type='hidden' name='resultCode' value='");
			submitform.append(crosspayApiResponse.getResultCode());
			submitform.append("' />");
		}if(crosspayApiResponse.getResultMsg()!=null){
			submitform.append("<input type='hidden' name='resultMsg' value='");
			submitform.append(crosspayApiResponse.getResultMsg());
			submitform.append("' />");
		}if(crosspayApiResponse.getSignType()!=null){
			submitform.append("<input type='hidden' name='signType' value='");
			submitform.append(crosspayApiResponse.getSignType());
			submitform.append("' />");
		}if(crosspayApiResponse.getSignMsg()!=null){
			submitform.append("<input type='hidden' name='signMsg' value='");
			submitform.append(crosspayApiResponse.getSignMsg());
			submitform.append("' />");
		}if(crosspayApiResponse.getSettlementCurrencyCode()!=null){
			submitform.append("<input type='hidden' name='settlementCurrencyCode' value='");
			submitform.append(crosspayApiResponse.getSettlementCurrencyCode());
			submitform.append("' />");
		}		
		
		submitform.append("</form></BODY></html>");
		
		return submitform.toString();
	}

	@Override
	public String getfailResponeForm(TokenCrosspayResponse crosspayApiResponse,
			TokenCrosspayRequest crosspayApiRequest) {
		StringBuffer submitform=new StringBuffer();
		StringBuffer sb =new StringBuffer();
		
			submitform.append("<html><head></head><BODY onload='document.forms[0].submit();'>");
			submitform.append("<form action='");
			submitform.append(crosspayApiRequest.getReturnUrl());
			submitform.append("' method='post'>");
			if(crosspayApiResponse.getAcquiringTime()!=null){
				submitform.append("<input type='hidden' name='acquiringTime' value='");
				submitform.append(crosspayApiResponse.getAcquiringTime());
				submitform.append("' />");
			}
			if(crosspayApiResponse.getCharset()!=null){
				submitform.append("<input type='hidden' name='charset' value='");
				submitform.append(crosspayApiResponse.getCharset());
				submitform.append("' />");
			}if(crosspayApiResponse.getCompleteTime()!=null){
				submitform.append("<input type='hidden' name='completeTime' value='");
				submitform.append(crosspayApiResponse.getCompleteTime());
				submitform.append("' />");
			}if(crosspayApiResponse.getCurrencyCode()!=null){
				submitform.append("<input type='hidden' name='currencyCode' value='");
				submitform.append(crosspayApiResponse.getCurrencyCode());
				submitform.append("' />");
			}if(crosspayApiResponse.getDealId()!=null){
				submitform.append("<input type='hidden' name='dealId' value='");
				submitform.append(crosspayApiResponse.getDealId());
				submitform.append("' />");
			}if(crosspayApiResponse.getMerchantBillName()!=null){
				submitform.append("<input type='hidden' name='merchantBillName' value='");
				submitform.append(crosspayApiResponse.getMerchantBillName());
				submitform.append("' />");
			}if(crosspayApiResponse.getOrderAmount()!=null){
				submitform.append("<input type='hidden' name='orderAmount' value='");
				submitform.append(crosspayApiResponse.getOrderAmount());
				submitform.append("' />");
			}if(crosspayApiResponse.getOrderId()!=null){
				submitform.append("<input type='hidden' name='orderId' value='");
				submitform.append(crosspayApiResponse.getOrderId());
				submitform.append("' />");
			}if(crosspayApiResponse.getPartnerId()!=null){
				submitform.append("<input type='hidden' name='partnerId' value='");
				submitform.append(crosspayApiResponse.getPartnerId());
				submitform.append("' />");
			}if(crosspayApiResponse.getRemark()!=null){
				submitform.append("<input type='hidden' name='remark' value='");
				submitform.append(crosspayApiResponse.getRemark());
				submitform.append("' />");
			}if(crosspayApiResponse.getResultCode()!=null){
				submitform.append("<input type='hidden' name='resultCode' value='");
				submitform.append(crosspayApiResponse.getResultCode());
				submitform.append("' />");
			}if(crosspayApiResponse.getResultMsg()!=null){
				submitform.append("<input type='hidden' name='resultMsg' value='");
				submitform.append(crosspayApiResponse.getResultMsg());
				submitform.append("' />");
			}if(crosspayApiResponse.getSignType()!=null){
				submitform.append("<input type='hidden' name='signType' value='");
				submitform.append(crosspayApiResponse.getSignType());
				submitform.append("' />");
			}if(crosspayApiResponse.getSignMsg()!=null){
				submitform.append("<input type='hidden' name='signMsg' value='");
				submitform.append(crosspayApiResponse.getSignMsg());
				submitform.append("' />");
			}if(crosspayApiResponse.getSettlementCurrencyCode()!=null){
				submitform.append("<input type='hidden' name='settlementCurrencyCode' value='");
				submitform.append(crosspayApiResponse.getSettlementCurrencyCode());
				submitform.append("' />");
			}		
			
			submitform.append("</form></BODY></html>");
			
			return submitform.toString();
	}
	
	
}
