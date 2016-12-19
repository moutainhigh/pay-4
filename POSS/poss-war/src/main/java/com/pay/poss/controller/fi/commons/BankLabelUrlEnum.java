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
public enum BankLabelUrlEnum {

	MOCK("International","模拟银行"),
	ICBC("International","中国工商银行"),
	ABC("International","中国农业银行"),
	CCB("International","中国建设银行"),
	BCM("International","交通银行"),
	CMB("International","招商银行"),
	BOC("International","中国银行"),
	boleto("m_Boleto","Boleto"),
	ct_boleto("b_Boleto","CTBoleto"),
	creditCard("b_CreditCard","CreditCard"),
	eps("b_EPS","EPS"),
	giropay("b_Giropay","Giropay"),
	ideal("b_IDEAL","Ideal"),
	poli("b_POLI","Poli"),
	qiwi("b_QIWI","QIWI"),
	safetyPay("b_SafetyPay","SafetyPay"),
	sepa("b_SEPA","SEPA"),
	sofort("b_Sofort","Sofort Banking"),
	teleingreso("b_Teleingreso","Teleingreso"),
	trustPay("b_TrustPay","TrustPay"),
	p24("b_P24","Przelewy24"),
	otf("b_OTF","Online Banking Transfer"),
	pagDebitCard("b_PagDebitCard","Debit Card"),
	cashu("b_cashu","CASHU")
	;
	
	private String labelurl;
	
	private String description;
	
	
	BankLabelUrlEnum(String labelurl,String description){
		this.labelurl=labelurl;
		this.description=description;
	} 
	public void setDescription(String description) {
		this.description = description;
	}



	public String getLabelurl() {
		return labelurl;
	}



	public void setLabelurl(String labelurl) {
		this.labelurl = labelurl;
	}



	public String getDescription() {
		return description;
	}
	
}
