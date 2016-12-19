package com.pay.channel.model;

import java.util.Date;

public class ChannelMidAmount {

	private String mid;
	
	private String cardOrg;
	
	private String currMonth;
	
	private String sumAmount;
	
	private Date createDate;
	
	private Date  updateDate;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getCardOrg() {
		return cardOrg;
	}

	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
	}

	public String getCurrMonth() {
		return currMonth;
	}

	public void setCurrMonth(String currMonth) {
		this.currMonth = currMonth;
	}

	public String getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
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
	
}
