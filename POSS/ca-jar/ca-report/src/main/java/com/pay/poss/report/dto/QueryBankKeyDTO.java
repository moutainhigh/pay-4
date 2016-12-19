package com.pay.poss.report.dto;

import java.io.Serializable;

public class QueryBankKeyDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String bankName;
	
	private String bankCode;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	

}
