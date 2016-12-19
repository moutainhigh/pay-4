package com.pay.txncore.model.report;

/**
 * 交易基本情况分析
 * 
 * @author peiyu.yang
 * @since 2016年6月29日22:18:21
 */
public class TradeBaseInfo {
	
	private String sessionId;
	//会员号
	private String partnerId;
	//渠道名称
	private String channelName;
	//商户名称
	private String merchantName;
	//交易金额
	private Long amount;
	//卡组织
	private String cardOrg;
	//交易币种
	private String currencyCode;
	//交易状态
	private int status;
	//交易笔数
	private Long tradeCount;
	//返回错误编码
	private String respCode;
	//返回错误消息
	private String respMsg;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getCardOrg() {
		return cardOrg;
	}

	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
    
	public Long getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(Long tradeCount) {
		this.tradeCount = tradeCount;
	}

	@Override
	public String toString() {
		return "TradeBaseInfo [sessionId=" + sessionId + ", partnerId="
				+ partnerId + ", channelName=" + channelName
				+ ", merchantName=" + merchantName + ", amount=" + amount
				+ ", cardOrg=" + cardOrg + ", currencyCode=" + currencyCode
				+ ", status=" + status + ", tradeCount=" + tradeCount
				+ ", respCode=" + respCode + ", respMsg=" + respMsg + "]";
	}
}
