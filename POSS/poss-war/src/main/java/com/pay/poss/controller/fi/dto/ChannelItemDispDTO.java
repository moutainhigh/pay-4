package com.pay.poss.controller.fi.dto;

public class ChannelItemDispDTO {

	private String dispNameCn;//显示工商银行等，对应于payment_channel_item中的item_name
	
	private String itemNo;//在hidden-对应于payment_channel_item的ID
	
	private String alias;//在直连渠道中别名
	
	private String catogory;//b2c, b2b

	public String getDispNameCn() {
		return dispNameCn;
	}

	public void setDispNameCn(String dispNameCn) {
		this.dispNameCn = dispNameCn;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCatogory() {
		return catogory;
	}

	public void setCatogory(String catogory) {
		this.catogory = catogory;
	}

	@Override
	public String toString() {
		return "ChannelItemDispDTO [alias=" + alias + ", catogory=" + catogory
				+ ", dispNameCn=" + dispNameCn + ", itemNo=" + itemNo + "]";
	}
	
}
