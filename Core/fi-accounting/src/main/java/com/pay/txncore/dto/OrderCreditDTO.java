/**
 * 
 */
package com.pay.txncore.dto;

import java.util.Date;

/**
 * 订单授信批次DTO
 * @author Jiangbo.Peng
 *
 */
public class OrderCreditDTO {

	//批次号
	private Long creditId ;
	//会员号
	private Long partnerId ;
	//商户名称
	private String partnerName ;
	//授信方式
	private String creditType ;
	//申请日期
	private Date applyDate ;
	private String applyDateStr ;
	//入账时间
	private Date accountDate ;
	//授信笔数
	private Long creditCount ;
	//授信币种
	private String currencyCode ;
	//日利率
	private String dayRate ;
	//入账金额
	private Long accountAmount ;
	//授信金额
	private Long creditAmount ;
	//服务费用
	private Long charge ;
	//刚创建的时候默认0，授信通过后为1，失败为2
	private String status ;
	//
	private String partnerRegisterName ;
	
	/** 订单金额 */
	private Long orderAmount ;
	
	/**
	 * @return the orderAmount
	 */
	public Long getOrderAmount() {
		return orderAmount;
	}

	/**
	 * @param orderAmount the orderAmount to set
	 */
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	/**
	 * 
	 */
	public OrderCreditDTO() {
		super();
	}

	public Long getCreditId() {
		return creditId;
	}

	public void setCreditId(Long creditId) {
		this.creditId = creditId;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	public Long getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(Long creditCount) {
		this.creditCount = creditCount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDayRate() {
		return dayRate;
	}

	public void setDayRate(String dayRate) {
		this.dayRate = dayRate;
	}

	public Long getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(Long accountAmount) {
		this.accountAmount = accountAmount;
	}

	public Long getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Long creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Long getCharge() {
		return charge;
	}

	public void setCharge(Long charge) {
		this.charge = charge;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPartnerRegisterName() {
		return partnerRegisterName;
	}

	public void setPartnerRegisterName(String partnerRegisterName) {
		this.partnerRegisterName = partnerRegisterName;
	}

	/**
	 * @return the applyDateStr
	 */
	public String getApplyDateStr() {
		return applyDateStr;
	}

	/**
	 * @param applyDateStr the applyDateStr to set
	 */
	public void setApplyDateStr(String applyDateStr) {
		this.applyDateStr = applyDateStr;
	}

	@Override
	public String toString() {
		return "OrderCreditDTO [creditId=" + creditId + ", partnerId="
				+ partnerId + ", partnerName=" + partnerName + ", creditType="
				+ creditType + ", applyDate=" + applyDate + ", accountDate="
				+ accountDate + ", creditCount=" + creditCount
				+ ", currencyCode=" + currencyCode + ", dayRate=" + dayRate
				+ ", accountAmount=" + accountAmount + ", creditAmount="
				+ creditAmount + ", charge=" + charge + ", status=" + status
				+ ", partnerRegisterName=" + partnerRegisterName + "]";
	}
	
}
