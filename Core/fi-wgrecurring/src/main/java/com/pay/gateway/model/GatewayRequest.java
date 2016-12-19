/**
 *
 * auto generated by ibatis tools 
 *
 **/
package com.pay.gateway.model;

/**
 * GATEWAY_REQUEST
 */

public class GatewayRequest implements java.io.Serializable {

	private String orderId;
	private Integer status;
	private Integer signType;
	private Long partnerId;
	private String serviceVersion;
	private String businessType;
	private String requestContext;
	private Long id;
	private String signMsg;
	private String errorCode;
	private String fromDomain;
	private java.util.Date createDate;
	private String requestIp;
	private Integer charset;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSignType() {
		return signType;
	}

	public void setSignType(Integer signType) {
		this.signType = signType;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getRequestContext() {
		return requestContext;
	}

	public void setRequestContext(String requestContext) {
		this.requestContext = requestContext;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getFromDomain() {
		return fromDomain;
	}

	public void setFromDomain(String fromDomain) {
		this.fromDomain = fromDomain;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public Integer getCharset() {
		return charset;
	}

	public void setCharset(Integer charset) {
		this.charset = charset;
	}

}