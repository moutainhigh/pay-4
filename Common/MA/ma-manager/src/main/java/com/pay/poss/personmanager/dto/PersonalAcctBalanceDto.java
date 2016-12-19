package com.pay.poss.personmanager.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PersonalAcctBalanceDto implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;


	private String payDate;            //日期
	private String orderNumber;		   //交易流水号
	private String dealType;		   //交易类型
	
	private Double pay;                //支出
	
	private Double revenue;		       //收入
	private String strPay;                //支出 页面格式显示
	
	private String strRevenue;		       //收入页面格式显示
	private String strBalance;		   //余额
	
	private String strAmount;		   //金额
	private String another;            //支付对方
	private String fee;                //费用
	
	
	
	private String loginName;           //登录名
	private String type;               //账户类型
	
	
	private Integer pageEndRow;			// 结束行
	private Integer pageStartRow;		// 起始行
	
	
	private Double numberPay;
	private Double numberFee;
	private Double numberRevenue;
	private Double numberBalance;
	private String acctCode;            //账户号
	
	private Long dealCode;
	
	public Long getDealCode() {
		return dealCode;
	}
	public void setDealCode(Long dealCode) {
		this.dealCode = dealCode;
	}
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	public Double getNumberPay() {
		return numberPay;
	}
	public void setNumberPay(Double numberPay) {
		this.numberPay = numberPay;
	}
	public Double getNumberFee() {
		return numberFee;
	}
	public void setNumberFee(Double numberFee) {
		this.numberFee = numberFee;
	}
	public Double getNumberRevenue() {
		return numberRevenue;
	}
	public void setNumberRevenue(Double numberRevenue) {
		this.numberRevenue = numberRevenue;
	}
	public Double getNumberBalance() {
		return numberBalance;
	}
	public void setNumberBalance(Double numberBalance) {
		this.numberBalance = numberBalance;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAnother() {
		return another;
	}
	public void setAnother(String another) {
		this.another = another;
	}
	public String getStrPay() {
		return strPay;
	}
	public void setStrPay(String strPay) {
		this.strPay = strPay;
	}
	public String getStrRevenue() {
		return strRevenue;
	}
	public void setStrRevenue(String strRevenue) {
		this.strRevenue = strRevenue;
	}
	public Double getPay() {
		return pay;
	}
	public void setPay(Double pay) {
		this.pay = pay;
	}
	public Double getRevenue() {
		return revenue;
	}
	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public String getStrBalance() {
		return strBalance;
	}
	public void setStrBalance(String strBalance) {
		this.strBalance = strBalance;
	}
	public String getStrAmount() {
		return strAmount;
	}
	public void setStrAmount(String strAmount) {
		this.strAmount = strAmount;
	}
	public Integer getPageEndRow() {
		return pageEndRow;
	}
	public void setPageEndRow(Integer pageEndRow) {
		this.pageEndRow = pageEndRow;
	}
	public Integer getPageStartRow() {
		return pageStartRow;
	}
	public void setPageStartRow(Integer pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
}
