package com.pay.acc.constant;



public enum CertIdCardEnum {
	
	MAINLAND_ID("0", "身份证"),
	PASSPORT("1", "护照"),
	ARMY_ID("2", "军人身份证"),
	COMPANY_REGISTER("3", "工商登记证"),
	TAX_REGISTER("4", "税务登记证"),
	SHAREHOLDER_CODE_ID("5", "股东代码证"),
	
	DOMAINNAME_REGISTER("6", "域名注册证"),
	ORGANIZATION_CODE("7", "组织机构代码"),
	BUSINESS_CERTIFICATE("8", "企业营业执照"),
	CORPORATION_CODE_ID("9", "法人代码证"),
	ARMED_POLICE_ID("A", "武警身份证"),
	HONGKONG_PASSPORT("B", "港澳居民往来内地通行证"),
	TAIWAN_PASSPORT("C", "台湾居民来往大陆通行证"),
	FOREIGN_PASSPORT("D", "外国公民护照"),
	HUKOU("E", "户口本"),
	
	TEMPORARY_ID("F", "临时身份证"),
	POLICE_ID("G", "警察证"),
	OTHER_CERTIFICATE("Z", "其它证件");
	
	
	private final String code;
	private final String description;
	
	public static final CertIdCardEnum[] SEARCH_TYPES = new CertIdCardEnum[]{COMPANY_REGISTER,TAX_REGISTER,
		ORGANIZATION_CODE,BUSINESS_CERTIFICATE,
		CORPORATION_CODE_ID,OTHER_CERTIFICATE};
	
//	public static final CertIdCardEnum[] SEARCH_TYPES = new CertIdCardEnum[]{MAINLAND_ID,PASSPORT,ARMY_ID,
//		COMPANY_REGISTER,TAX_REGISTER,SHAREHOLDER_CODE_ID,DOMAINNAME_REGISTER,ORGANIZATION_CODE,BUSINESS_CERTIFICATE,
//		CORPORATION_CODE_ID,ARMED_POLICE_ID,HONGKONG_PASSPORT,TAIWAN_PASSPORT,FOREIGN_PASSPORT,HUKOU,TEMPORARY_ID,POLICE_ID,
//		OTHER_CERTIFICATE};
	
	CertIdCardEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}


	public String getCode() {
		return code;
	}


	public String getDescription() {
		return description;
	}
	
	public static CertIdCardEnum getByCode(String code) {
		for (CertIdCardEnum ce : values()) {
			if (ce.getCode() == code) {
				return ce;
			}
		}
		return null;
	}
	
	public static String getByDesCode(String code) {
		for (CertIdCardEnum ce : values()) {
			if (ce.getCode() == code) {
				return ce.getDescription();
			}
		}
		return null;
	}
}
