package com.pay.risk.model;

import java.util.Map;

import com.pay.risk.dto.PaymentResult;


/**
 * 风控验证请求
 * @author peiyu.yang
 * @since 2016年6月30日14:03:08
 */
public class RiskValidateRequest {
	//请求参数
    private Map<String,String> reqParameter;
    //返回参数
    private PaymentResult paymentResult;
    //请求ID
	private String requestId;
	//过滤参数，列如有些风控部校验等。
	private Map<String,String> filterParameter;

	public PaymentResult getPaymentResult() {
		return paymentResult;
	}
	public void setPaymentResult(PaymentResult paymentResult) {
		this.paymentResult = paymentResult;
	}
	public Map<String, String> getReqParameter() {
		return reqParameter;
	}
	public void setReqParameter(Map<String, String> reqParameter) {
		this.reqParameter = reqParameter;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	public Map<String, String> getFilterParameter() {
		return filterParameter;
	}
	
	public void setFilterParameter(Map<String, String> filterParameter) {
		this.filterParameter = filterParameter;
	}
}
