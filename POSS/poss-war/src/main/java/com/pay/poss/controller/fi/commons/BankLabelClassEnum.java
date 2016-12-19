/**
 * 
 */
package com.pay.poss.controller.fi.commons;


/**
 * 银行图标大全
 * 
 * @author chaoyue
 *
 */
public enum BankLabelClassEnum {

	MOCK("mock","模拟银行"),
	ICBC("icbc","中国工商银行"),
	ABC("agricultural","中国农业银行"),
	CCB("construction","中国建设银行"),
	BCM("communications","交通银行"),
	CMB("merchants","招商银行"),
	BOC("china","中国银行")
	
	;
	
	private String labelclass;
	
	private String description;
	
	private BankLabelClassEnum(String labelclass,String description){
		this.labelclass = labelclass;
		this.description = description;
	}

	public String getLabelclass() {
		return labelclass;
	}

	public String getDescription() {
		return description;
	}
	
}
