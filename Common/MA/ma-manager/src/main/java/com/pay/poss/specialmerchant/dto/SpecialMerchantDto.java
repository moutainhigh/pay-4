package com.pay.poss.specialmerchant.dto;

import java.util.Date;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		SpecialMerchantDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 */
public class SpecialMerchantDto {
	private Long sp_merchant_id;
	private String sp_merchant_name;
	private String province_code;
	private Long range_id;
	private String merchant_intro;
	private String card_type_code;
	private String discount_info;
	private String sp_merchant_logo;
	private String addr;
	private String tel;
	private String website_url;
	private String value1;
	private String value2;
	private String enum_name;
	private String sp_merchant_logo_big;
	private Date create_date;
	private Date update_date;
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
	public String getMerchant_intro() {
		return merchant_intro;
	}
	public void setMerchant_intro(String merchant_intro) {
		this.merchant_intro = merchant_intro;
	}
	public String getCard_type_code() {
		return card_type_code;
	}
	public void setCard_type_code(String card_type_code) {
		this.card_type_code = card_type_code;
	}
	public String getDiscount_info() {
		return discount_info;
	}
	public void setDiscount_info(String discount_info) {
		this.discount_info = discount_info;
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
	public String getEnum_name() {
		return enum_name;
	}
	public void setEnum_name(String enum_name) {
		this.enum_name = enum_name;
	}
	public String getSp_merchant_logo_big() {
		return sp_merchant_logo_big;
	}
	public void setSp_merchant_logo_big(String sp_merchant_logo_big) {
		this.sp_merchant_logo_big = sp_merchant_logo_big;
	}
	
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	

	
	
}
