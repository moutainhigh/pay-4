package com.pay.api.model;

import java.util.Date;

public class MerchantRequest {
	/**
     */
	private Long sequenceId;

	/**
     */
	private String merchantId;

	/**
     */
	private String orderId;

	/**
     */
	private String content;

	/**
     */
	private Date requestDate;

	/**
     */
	private String requestIp;

	/**
     */
	private String versionNo;

	/**
	 * 
	 * @return the value of MERCHANT_REQUEST.SEQUENCE_ID
	 */
	public Long getSequenceId() {
		return sequenceId;
	}

	/**
     *
     */
	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	/**
	 * 
	 * @return the value of MERCHANT_REQUEST.MERCHANT_ID
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
     *
     */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 
	 * @return the value of MERCHANT_REQUEST.ORDER_ID
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
     *
     */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 
	 * @return the value of MERCHANT_REQUEST.CONTENT
	 */
	public String getContent() {
		return content;
	}

	/**
     *
     */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 
	 * @return the value of MERCHANT_REQUEST.REQUEST_DATE
	 */
	public Date getRequestDate() {
		return requestDate;
	}

	/**
     *
     */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	/**
	 * 
	 * @return the value of MERCHANT_REQUEST.REQUEST_IP
	 */
	public String getRequestIp() {
		return requestIp;
	}

	/**
     *
     */
	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	/**
	 * 
	 * @return the value of MERCHANT_REQUEST.VERSION_NO
	 */
	public String getVersionNo() {
		return versionNo;
	}

	/**
     *
     */
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
}