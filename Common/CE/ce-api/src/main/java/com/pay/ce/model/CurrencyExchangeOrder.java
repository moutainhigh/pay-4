package com.pay.ce.model;

import java.util.Date;

/**
 * 货币兑换
 * @author peiyu.yang
 * @since 2015年9月7日
 */
public class CurrencyExchangeOrder{
	/**
	 * 订单号
	 */
     private Long ceOrderNo;
     /**
      * 原币种
      */
     private String currency;
     /**
      * 目标币种
      */
     private String targetCurrency;
     /**
      * 汇率
      */
     private String rate;
     /**
      * 订单状态
      */
     private String status;
     /**
      * 手续费
      */
     private String fee;
     /**
      * 创建日期
      */
     private Date createDate;
     /**
      * 更新日期
      */
     private Date updateTime;
     /**
      * 会员标识
      */
     private String partnerId;
     /**
      * 兑换额度
      */
     private String ceAmount;
     /**
      * 结出金额
      */
     private String ceOutAmount;
     
     private String startTime;
     private String endTime;
     

     
     
    
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getCeOrderNo() {
		return ceOrderNo;
	}
	public void setCeOrderNo(Long ceOrderNo) {
		this.ceOrderNo = ceOrderNo;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTargetCurrency() {
		return targetCurrency;
	}
	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getCeAmount() {
		return ceAmount;
	}
	public void setCeAmount(String ceAmount) {
		this.ceAmount = ceAmount;
	}
	
	public String getCeOutAmount() {
		return ceOutAmount;
	}
	public void setCeOutAmount(String ceOutAmount) {
		this.ceOutAmount = ceOutAmount;
	}
	@Override
	public String toString() {
		return "CurrencyExchangeOrder [ceOrderNo=" + ceOrderNo + ", currency="
				+ currency + ", targetCurrency=" + targetCurrency + ", rate="
				+ rate + ", status=" + status + ", fee=" + fee
				+ ", createDate=" + createDate + ", updateTime=" + updateTime
				+ ", partnerId=" + partnerId + ", ceAmount=" + ceAmount + "]";
	}
}
