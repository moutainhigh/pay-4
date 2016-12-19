/**
 *  <p>File: PayOnlineDetailDTO.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.dto.fi;

import java.io.Serializable;

public class PayOnlineDetailDTO implements Serializable {
	private Long payOnlineId;
	private Long payAmount;
	private Integer sellerAccType;
	private Integer status;
	private Long tradeOrderInfoId;
	private String errorCode;
	private String payType;
	private Long payOnlineDetailId;
	private java.util.Date createDate;
	private Long seller;
	private java.util.Date updateDate;

	public Long getPayOnlineId() {
		return payOnlineId;
	}

	public void setPayOnlineId(Long payOnlineId) {
		this.payOnlineId = payOnlineId;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getSellerAccType() {
		return sellerAccType;
	}

	public void setSellerAccType(Integer sellerAccType) {
		this.sellerAccType = sellerAccType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getTradeOrderInfoId() {
		return tradeOrderInfoId;
	}

	public void setTradeOrderInfoId(Long tradeOrderInfoId) {
		this.tradeOrderInfoId = tradeOrderInfoId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Long getPayOnlineDetailId() {
		return payOnlineDetailId;
	}

	public void setPayOnlineDetailId(Long payOnlineDetailId) {
		this.payOnlineDetailId = payOnlineDetailId;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public Long getSeller() {
		return seller;
	}

	public void setSeller(Long seller) {
		this.seller = seller;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "PayOnlineDetailDTO [payOnlineId=" + payOnlineId
				+ ", payAmount=" + payAmount + ", sellerAccType="
				+ sellerAccType + ", status=" + status + ", tradeOrderInfoId="
				+ tradeOrderInfoId + ", errorCode=" + errorCode + ", payType="
				+ payType + ", payOnlineDetailId=" + payOnlineDetailId
				+ ", createDate=" + createDate + ", seller=" + seller
				+ ", updateDate=" + updateDate + "]";
	}
}
