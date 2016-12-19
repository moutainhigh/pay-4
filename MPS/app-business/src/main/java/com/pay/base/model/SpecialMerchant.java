package com.pay.base.model;

import java.util.List;

public class SpecialMerchant implements Model{

	private Long sp_merchant_id;
	private String sp_merchant_name;
	private String province_code;
	private Long range_id;
	private String range_name;
	private String merchant_intro;
	private String sp_merchant_logo;
	private String addr;
	private String tel;
	private String website_url;
	private String value1;
	private String value2;
	private String card_type_id;
	private String discount_info;
	
	private List<CardType> cardTypeList;
	
	public void setPrimaryKey(Long id) {
		setSp_merchant_id(id);
	}
	
	public Long getSp_merchant_id() {
		return sp_merchant_id;
	}
	public void setSp_merchant_id(Long sp_merchant_id) {
		this.sp_merchant_id = sp_merchant_id;
	}
	public String getSp_merchant_name() {
		return sp_merchant_name;
	}
	public void setSp_merchant_name(String sp_merchant_name) {
		this.sp_merchant_name = sp_merchant_name;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public Long getRange_id() {
		return range_id;
	}
	public void setRange_id(Long range_id) {
		this.range_id = range_id;
	}
	
	public String getRange_name() {
		return range_name;
	}

	public void setRange_name(String range_name) {
		this.range_name = range_name;
	}

	public String getMerchant_intro() {
		return merchant_intro;
	}
	public void setMerchant_intro(String merchant_intro) {
		this.merchant_intro = merchant_intro;
	}

	public String getSp_merchant_logo() {
		return sp_merchant_logo;
	}
	public void setSp_merchant_logo(String sp_merchant_logo) {
		this.sp_merchant_logo = sp_merchant_logo;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getWebsite_url() {
		return website_url;
	}
	public void setWebsite_url(String website_url) {
		this.website_url = website_url;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
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

	public List<CardType> getCardTypeList() {
		return cardTypeList;
	}

	public void setCardTypeList(List<CardType> cardTypeList) {
		this.cardTypeList = cardTypeList;
	}
	
}
