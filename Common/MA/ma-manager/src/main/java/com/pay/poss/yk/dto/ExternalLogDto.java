package com.pay.poss.yk.dto;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * 易卡充值日志业务表
 * @author DDR
 * 2011-6-2
 *
 */

public  class ExternalLogDto implements java.io.Serializable {

	// Fields

	private Long id; 
	private String orderNo;
	private Integer externalType;
	private String cardNo;
	private Integer processStatus;
	private BigDecimal amount;
	private Date createDate;
	private Date updateDate;
	private String remark;
	private Integer currencyCode;
	private Integer tradingWay;
	private String payerMemberCode;
	private String value1;
	private String value2;
	private Integer externalProcessStatus;

	// Constructors

	/** default constructor */
	public ExternalLogDto() {
	}

	/** minimal constructor */
	public ExternalLogDto(Long id, String orderNo,
			Integer externalType, Integer processStatus, BigDecimal amount,
			Date createDate) {
		this.id = id;
		this.orderNo = orderNo;
		this.externalType = externalType;
		this.processStatus = processStatus;
		this.amount = amount;
		this.createDate = createDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getExternalType() {
		return externalType;
	}

	public void setExternalType(Integer externalType) {
		this.externalType = externalType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Integer getTradingWay() {
		return tradingWay;
	}

	public void setTradingWay(Integer tradingWay) {
		this.tradingWay = tradingWay;
	}

	public String getPayerMemberCode() {
		return payerMemberCode;
	}

	public void setPayerMemberCode(String payerMemberCode) {
		this.payerMemberCode = payerMemberCode;
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

	public Integer getExternalProcessStatus() {
		return externalProcessStatus;
	}

	public void setExternalProcessStatus(Integer externalProcessStatus) {
		this.externalProcessStatus = externalProcessStatus;
	}
	
	public double getAmountDouble(){
		return amount.doubleValue();
	}

	public String getProcessStatusDesc(){
		String desc = "";
		if(processStatus!=null){
			//desc = RechargeProcessStatus.get(processStatus).getMessage();;
		}
		return desc;
	}
	public String getExternalProcessStatusDesc(){
		String desc = "";
		if(externalProcessStatus!=null){
			//desc = RechargeExternalProcessStatus.get(externalProcessStatus).getMessage();;
		}
		return desc;
	}
	/**
	 * 
	 * 返回是否可以退款
	 * @return
	 */
	public boolean isRefundAble(){
		if(processStatus == 1 || processStatus == 4){//付款成功或是退款中
			if(externalProcessStatus !=null && externalProcessStatus != 1 ){//充值不成功可退款
				return true;
			}
		}
		return false;
	}
	
	
	
}