package com.pay.poss.personmanager.dto;

import java.io.Serializable;

public class PersonalAcctTradeTotalDto implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	private String totalPay;           //支出
	private String totalRevenue;	  //收入
	private String totalBalance;      //总余额
	
	public String getTotalPay() {
		return totalPay;
	}
	public void setTotalPay(String totalPay) {
		this.totalPay = totalPay;
	}
	public String getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(String totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public String getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}

}
