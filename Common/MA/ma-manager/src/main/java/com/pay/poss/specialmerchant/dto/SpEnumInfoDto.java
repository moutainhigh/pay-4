package com.pay.poss.specialmerchant.dto;
/**
 * 
 * 特约商户枚举信息
 * lishengjin 2012-06-21
 */
public class SpEnumInfoDto {

	private long enumId;   //主键Id
	private String enumName; //枚举名称
	private String enumCode; //枚举Code
	private long enumType; //卡类型 :  1-卡种 , 2-经营范围
	private String value1;   //备用一
	private String value2;   //备用二
	public long getEnumId() {
		return enumId;
	}
	public void setEnumId(long enumId) {
		this.enumId = enumId;
	}
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public String getEnumCode() {
		return enumCode;
	}
	public void setEnumCode(String enumCode) {
		this.enumCode = enumCode;
	}
	public long getEnumType() {
		return enumType;
	}
	public void setEnumType(long enumType) {
		this.enumType = enumType;
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
	
	
}
