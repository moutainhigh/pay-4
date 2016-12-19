package com.pay.txncore.model.report;


/**
 * 基本交易分析
 * @author peiyu.yang
 * @since 2016年6月30日00:14:29
 */
public class BaseTradeAnalyse {
    private Long id;
    private String sessionId;
    //会员号
    private String partnerId;
    //商户名称
    private String merchantName;
    //卡组织
    private String cardOrg;
    //总交易笔数
    private String allTradeCount;
    //成功交易笔数
    private String successTradeCount;
    //成功交易率
    private String successTradeRate;
    //总交易金额
    private String allTradeAmount;
    //客单价
    private String price;
    //
    private String startTime;
    //
    private String endTime;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getCardOrg() {
		return cardOrg;
	}
	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
	}
	public String getAllTradeCount() {
		return allTradeCount;
	}
	public void setAllTradeCount(String allTradeCount) {
		this.allTradeCount = allTradeCount;
	}
	public String getSuccessTradeCount() {
		return successTradeCount;
	}
	public void setSuccessTradeCount(String successTradeCount) {
		this.successTradeCount = successTradeCount;
	}
	public String getSuccessTradeRate() {
		return successTradeRate;
	}
	public void setSuccessTradeRate(String successTradeRate) {
		this.successTradeRate = successTradeRate;
	}
	public String getAllTradeAmount() {
		return allTradeAmount;
	}
	public void setAllTradeAmount(String allTradeAmount) {
		this.allTradeAmount = allTradeAmount;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
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
}
