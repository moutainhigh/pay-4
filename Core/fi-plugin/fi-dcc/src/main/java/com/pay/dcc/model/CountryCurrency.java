package com.pay.dcc.model;

import java.util.Date;

/**
 * 国家币种配置
 * @author peiyu.yang
 * @since 2015年11月23日22:59:03
 */
public class CountryCurrency {
	
	private Long Id;
	private String countryCode;
	private String countryName;
	private String currencyCode;
	private String createUser;
	private Date createTime;
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return "CountryCurrency [Id=" + Id + ", countryCode=" + countryCode
				+ ", countryName=" + countryName + ", currencyCode="
				+ currencyCode + ", createUser=" + createUser + ", createTime="
				+ createTime + "]";
	}
}
