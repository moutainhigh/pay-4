package com.pay.txncore.model;

import java.util.Date;

/**
 * 银行卡绑定订单
 * 
 * @author peiyu
 *
 */
public class TokenPayInfo {
	private Long id;
	private String partnerId;
	private String registerUserId;
	private String status;
	private String cardHolderNumber;
	private String partnerName;
	private Date createDate;
	private Date updateDate;
	private String token;

	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getPartnerId() {
		return partnerId;
	}


	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}


	public String getRegisterUserId() {
		return registerUserId;
	}


	public void setRegisterUserId(String registerUserId) {
		this.registerUserId = registerUserId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCardHolderNumber() {
		return cardHolderNumber;
	}


	public void setCardHolderNumber(String cardHolderNumber) {
		this.cardHolderNumber = cardHolderNumber;
	}


	public String getPartnerName() {
		return partnerName;
	}


	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String toString() {
		return "CardBindOrder [id=" + id + 
				", partnerId=" + partnerId + ", status=" + status
				+ ", registerUserId=" + registerUserId + ", cardHolderNumber=" + cardHolderNumber
				+ ", partnerName=" + partnerName + ", createDate="
				+ createDate + ", updateDate=" + updateDate + "]";
	}
}
