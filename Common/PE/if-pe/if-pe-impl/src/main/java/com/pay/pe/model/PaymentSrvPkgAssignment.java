package com.pay.pe.model;

import java.util.List;

import com.pay.inf.model.Model;





public class PaymentSrvPkgAssignment implements Model{

	private Integer paymentServiceCode;
	private Integer paymentServicePkgCode;
	
	 

	public PaymentSrvPkgAssignment() {
		super();
	}

	

	public Integer getPaymentServiceCode() {
		return paymentServiceCode;
	}



	public void setPaymentServiceCode(Integer paymentServiceCode) {
		this.paymentServiceCode = paymentServiceCode;
	}



	public Integer getPaymentServicePkgCode() {
		return paymentServicePkgCode;
	}



	public void setPaymentServicePkgCode(Integer paymentServicePkgCode) {
		this.paymentServicePkgCode = paymentServicePkgCode;
	}



	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("paymentService->" + paymentServiceCode + ";");
		sb.append("paymentServicePKG->" + paymentServicePkgCode + ";");

		return sb.toString();
	}


}
