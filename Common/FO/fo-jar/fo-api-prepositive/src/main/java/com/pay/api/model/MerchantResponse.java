package com.pay.api.model;

import java.util.Date;

public class MerchantResponse {
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
	private String resCode;

	/**
     */
	private Date createDate;

	/**
	 * 
	 * @return the value of MERCHANT_RESPONSE.SEQUENCE_ID
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
	 * @return the value of MERCHANT_RESPONSE.MERCHANT_ID
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
	 * @return the value of MERCHANT_RESPONSE.ORDER_ID
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
	 * @return the value of MERCHANT_RESPONSE.CONTENT
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
	 * @return the value of MERCHANT_RESPONSE.RES_CODE
	 */
	public String getResCode() {
		return resCode;
	}

	/**
     *
     */
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	/**
	 * 
	 * @return the value of MERCHANT_RESPONSE.CREATE_DATE
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
     *
     */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}