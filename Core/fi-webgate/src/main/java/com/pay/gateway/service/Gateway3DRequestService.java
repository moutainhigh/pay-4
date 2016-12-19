/**
 * 
 */
package com.pay.gateway.service;


import java.util.Map;











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

/**
 * @author liu
 *
 */
public interface Gateway3DRequestService {

	public Map<String, String> getSalesParameters(Map<String, String> requestData);
	public String hashAllFields(Map fields,String SECURE_SECRET);
	public Map<String, String> getConfigure();
	public String getResponeForm(Map resultMap);
	public String getfailResponeForm(CrosspayApiResponse crosspayApiResponse,CrosspayApiRequest crosspayApiRequest);
	public String getfailResponeForm(CrosspayResponse crosspayApiResponse,CrosspayRequest crosspayApiRequest);
	public String getfailResponeForm(ChinaLocalPayResponse crosspayApiResponse,ChinaLocalPayRequest crosspayApiRequest);
	
	public String getfailResponeForm(TokenCrosspayApiResponse crosspayApiResponse,TokenCrosspayApiRequest crosspayApiRequest);
	public String getfailResponeForm(TokenCrosspayResponse crosspayApiResponse,TokenCrosspayRequest crosspayApiRequest);
}
