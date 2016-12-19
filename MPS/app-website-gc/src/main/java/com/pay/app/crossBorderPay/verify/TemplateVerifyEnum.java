package com.pay.app.crossBorderPay.verify;

public enum TemplateVerifyEnum {
	CARGO_TRADE_TEMPLATE("1","[商户订单号, 货物名称, 数量, 货物类型, 付款人姓名, 付款人证件类型, 付款人证件号, 付款账号, 收款人所在国, 收款行swift code, 收款账号, 收款人姓名, 收款人地址, 收款币种, 收款金额（元）, 发票号（选填）, 合同号（选填）, 汇款附言（选填）, 收款行人行行号, 发货方式, 物流公司]"),
	TICKET_TRAVEL_TEMPLATE("2","[商户订单号, 航空公司, 航班号, 航班时间, 数量, 付款人姓名, 付款人证件类型, 付款人证件号, 付款账号, 收款人所在国, 收款行swift code, 收款账号, 收款人姓名, 收款人地址, 收款币种, 收款金额（元）, 汇款附言（选填）]"),
	GROGSHOP_CEASE_TEMPLATE("3","[商户订单号, 酒店名称, 入住时间, 数量, 付款人姓名, 付款人证件类型, 付款人证件号, 付款账号, 收款人所在国, 收款行swift code, 收款账号, 收款人姓名, 收款人地址, 收款币种, 收款金额（元）, 汇款附言（选填）, 收款行人行行号]"),
	STUDY_ABROAD_TEMPLATE("4","[商户订单号, 学校名称, 入学时间, 付款人姓名, 付款人证件类型, 付款人证件号, 付款账号, 收款人所在国, 收款行swift code, 收款账号, 收款人姓名, 收款人地址, 收款币种, 收款金额, 入学通知书图片名, 汇款附言（选填）, 收款行人行行号]");
	String VerifyField;
	String TemplateType;
	TemplateVerifyEnum(String TemplateType,String VerifyField){
		this.TemplateType=TemplateType;
		this.VerifyField=VerifyField;
	}
	public String getVerifyField() {
		return VerifyField;
	}
	public void setVerifyField(String verifyField) {
		VerifyField = verifyField;
	}
	public String getTemplateType() {
		return TemplateType;
	}
	public void setTemplateType(String templateType) {
		TemplateType = templateType;
	}
	
}
