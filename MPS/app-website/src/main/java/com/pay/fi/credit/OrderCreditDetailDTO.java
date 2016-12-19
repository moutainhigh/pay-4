/**
 * 
 */
package com.pay.fi.credit;

import java.util.Date;

/**
 * 订单授信批次详情
 * @author Jiangbo.Peng
 *
 */
public class OrderCreditDetailDTO {

	/** 授信明细流水号 */
	private Long creditDetailId ;
	
	/** 批次号 */
	private Long creditId ;
	
	/** 会员号 */
	private Long partnerId ;
	
	/** 商户名称 */
	private String partnerName ;
	
	/** 网关订单号 */
	private Long tradeOrderId ;
	
	/** 清算订单号 */
	private Long partnerSettlementOrderId ;
	
	/** 订单金额 */
	private Long orderAmount ;
	
	/** 授信金额 */
	private Long creditAmount ;
	
	/** 服务费 */
	private Long charge ;
	
	/** 入账金额 */
	private Long accountAmount ;
	
	/** 申请时间 */
	private Date applyDate  ;
	private String applyDateStr ;
	/** 入账时间 */
	private Date accountDate  ;
	private String accountDateStr ;
	
	/** 原清算时间 */
	private Date settlementDate ;
	private String settlementDateStr ;
	
	/** 刚创建的时候默认0，授信通过后为1，失败为2 */
	private String status ;
	/** 清算标志 */
	private Integer settlementFlg ;
	
	/** 商户订单号 */
	private String orderId ;
	
	/**交易币种*/
	private String tranCurrencyCode ;
	
	/** 授信币种 */
	private String creditCurrencyCode ;
	
	private String dayRate ;
	
	
	
	/**
	 * @return the dayRate
	 */
	public String getDayRate() {
		return dayRate;
	}

	/**
	 * @param dayRate the dayRate to set
	 */
	public void setDayRate(String dayRate) {
		this.dayRate = dayRate;
	}

	/**
	 * @return the tranCurrencyCode
	 */
	public String getTranCurrencyCode() {
		return tranCurrencyCode;
	}

	/**
	 * @param tranCurrencyCode the tranCurrencyCode to set
	 */
	public void setTranCurrencyCode(String tranCurrencyCode) {
		this.tranCurrencyCode = tranCurrencyCode;
	}

	/**
	 * @return the creditCurrencyCode
	 */
	public String getCreditCurrencyCode() {
		return creditCurrencyCode;
	}

	/**
	 * @param creditCurrencyCode the creditCurrencyCode to set
	 */
	public void setCreditCurrencyCode(String creditCurrencyCode) {
		this.creditCurrencyCode = creditCurrencyCode;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 
	 */
	public OrderCreditDetailDTO() {
		super();
	}

	public Long getCreditDetailId() {
		return creditDetailId;
	}

	public void setCreditDetailId(Long creditDetailId) {
		this.creditDetailId = creditDetailId;
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

	public Long getTradeOrderId() {
		return tradeOrderId;
	}

	public void setTradeOrderId(Long tradeOrderId) {
		this.tradeOrderId = tradeOrderId;
	}

	public Long getPartnerSettlementOrderId() {
		return partnerSettlementOrderId;
	}

	public void setPartnerSettlementOrderId(Long partnerSettlementOrderId) {
		this.partnerSettlementOrderId = partnerSettlementOrderId;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
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

	public Long getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(Long accountAmount) {
		this.accountAmount = accountAmount;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the settlementFlg
	 */
	public Integer getSettlementFlg() {
		return settlementFlg;
	}

	/**
	 * @param settlementFlg the settlementFlg to set
	 */
	public void setSettlementFlg(Integer settlementFlg) {
		this.settlementFlg = settlementFlg;
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

	/**
	 * @return the accountDateStr
	 */
	public String getAccountDateStr() {
		return accountDateStr;
	}

	/**
	 * @param accountDateStr the accountDateStr to set
	 */
	public void setAccountDateStr(String accountDateStr) {
		this.accountDateStr = accountDateStr;
	}

	/**
	 * @return the settlementDateStr
	 */
	public String getSettlementDateStr() {
		return settlementDateStr;
	}

	/**
	 * @param settlementDateStr the settlementDateStr to set
	 */
	public void setSettlementDateStr(String settlementDateStr) {
		this.settlementDateStr = settlementDateStr;
	}

	@Override
	public String toString() {
		return "OrderCreditDetailDTO [creditDetailId=" + creditDetailId
				+ ", creditId=" + creditId + ", partnerId=" + partnerId
				+ ", partnerName=" + partnerName + ", tradeOrderId="
				+ tradeOrderId + ", partnerSettlementOrderId="
				+ partnerSettlementOrderId + ", orderAmount=" + orderAmount
				+ ", creditAmount=" + creditAmount + ", charge=" + charge
				+ ", accountAmount=" + accountAmount + ", applyDate="
				+ applyDate + ", settlementDate=" + settlementDate
				+ ", status=" + status + "]";
	}


	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}
	
	
}
