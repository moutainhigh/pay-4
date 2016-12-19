package com.pay.base.model;

public class CardType {

	private Long sp_merchant_id;
	private String card_type_id;
	private String discount_info;
	private String card_name;
	public Long getSp_merchant_id() {
		return sp_merchant_id;
	}
	public void setSp_merchant_id(Long sp_merchant_id) {
		this.sp_merchant_id = sp_merchant_id;
	}
	public String getCard_type_id() {
		return card_type_id;
	}
	public void setCard_type_id(String card_type_id) {
		this.card_type_id = card_type_id;
	}
	public String getDiscount_info() {
		return discount_info;
	}
	public void setDiscount_info(String discount_info) {
		this.discount_info = discount_info;
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
}
