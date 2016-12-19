/**
 * 
 */
package com.pay.txncore.dto;

import java.util.Date;

/**
 * 异常卡明细信息记录
 * @author Jiangbo.peng
 *
 */
public class ExceptionCardDetailMsgDTO {

	/** 会员号 */
	private Long memberCode ;
	
	/** 商户订单号 */
	private String orderId ;
	
	/** 网关订单号 */
	private Long tradeOrderNo ;
	
	/** 支付订单号 */
	private Long paymentOrderNo ;
	
	/** 渠道订单号 */
	private long channelOrderNo ;
	
	/** 渠道号 */
	private String orgCode ;
	
	/** 交易金额 */
	private Long orderAmount ;
	
	/** 交易币种 */
	private String currencyCode ;
	
	/** 交易汇率 */
	private String currencyRate ;
	
	/** 渠道结果 */
	private String channelResult ;
	
	/** 网关结果 */
	private String tradeResult ;
	
	/** 创建时间 */
	private Date createTime ;
	
	/**  */
	//时间是个什么鬼
	
	/** 支付金额 */
	private Long payAmount ;
	
	/** 支付币种 */
	private String payCurrencyCode ;
	
	/** 二级商户号 */
	private String merchantNo ;
	
	/** 网址 */
	private String site ;
	
	/** 卡号 */
	private String cardNo ;
	
	/** 持卡人姓名 */
	private String cardHolderName ;
	
	/** ip */
	private String ip ;
	
	/** 邮箱 */
	private String email ;
	
	private String shippingAddress ;
	
	/** 手机 */
	private String cardHolderMobile ;
	
	/** 网关返回码 */
	private String tradeRespCode ;
	
	/** 网关返回描述 */
	private String tradeRespDesc ;
	
	/** 渠道返回码 */
	private String channelRespCode ;
	
	/** 渠道返回原因 */
	private String channelRespDesc ;

	/**
	 * @return the tradeOrderNo
	 */
	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	/**
	 * @param tradeOrderNo the tradeOrderNo to set
	 */
	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	/**
	 * @return the paymentOrderNo
	 */
	public Long getPaymentOrderNo() {
		return paymentOrderNo;
	}

	/**
	 * @param paymentOrderNo the paymentOrderNo to set
	 */
	public void setPaymentOrderNo(Long paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	/**
	 * @return the channelOrderNo
	 */
	public long getChannelOrderNo() {
		return channelOrderNo;
	}

	/**
	 * @param channelOrderNo the channelOrderNo to set
	 */
	public void setChannelOrderNo(long channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

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
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the currencyRate
	 */
	public String getCurrencyRate() {
		return currencyRate;
	}

	/**
	 * @param currencyRate the currencyRate to set
	 */
	public void setCurrencyRate(String currencyRate) {
		this.currencyRate = currencyRate;
	}

	/**
	 * @return the channelResult
	 */
	public String getChannelResult() {
		return channelResult;
	}

	/**
	 * @param channelResult the channelResult to set
	 */
	public void setChannelResult(String channelResult) {
		this.channelResult = channelResult;
	}

	/**
	 * @return the tradeResult
	 */
	public String getTradeResult() {
		return tradeResult;
	}

	/**
	 * @param tradeResult the tradeResult to set
	 */
	public void setTradeResult(String tradeResult) {
		this.tradeResult = tradeResult;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the payAmount
	 */
	public Long getPayAmount() {
		return payAmount;
	}

	/**
	 * @param payAmount the payAmount to set
	 */
	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	/**
	 * @return the payCurrencyCode
	 */
	public String getPayCurrencyCode() {
		return payCurrencyCode;
	}

	/**
	 * @param payCurrencyCode the payCurrencyCode to set
	 */
	public void setPayCurrencyCode(String payCurrencyCode) {
		this.payCurrencyCode = payCurrencyCode;
	}

	/**
	 * @return the merchantNo
	 */
	public String getMerchantNo() {
		return merchantNo;
	}

	/**
	 * @param merchantNo the merchantNo to set
	 */
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	/**
	 * @return the site
	 */
	public String getSite() {
		return site;
	}

	/**
	 * @param site the site to set
	 */
	public void setSite(String site) {
		this.site = site;
	}

	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the cardHolderName
	 */
	public String getCardHolderName() {
		return cardHolderName;
	}

	/**
	 * @param cardHolderName the cardHolderName to set
	 */
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the cardHolderMobile
	 */
	public String getCardHolderMobile() {
		return cardHolderMobile;
	}

	/**
	 * @param cardHolderMobile the cardHolderMobile to set
	 */
	public void setCardHolderMobile(String cardHolderMobile) {
		this.cardHolderMobile = cardHolderMobile;
	}

	/**
	 * @return the tradeRespCode
	 */
	public String getTradeRespCode() {
		return tradeRespCode;
	}

	/**
	 * @param tradeRespCode the tradeRespCode to set
	 */
	public void setTradeRespCode(String tradeRespCode) {
		this.tradeRespCode = tradeRespCode;
	}

	/**
	 * @return the tradeRespDesc
	 */
	public String getTradeRespDesc() {
		return tradeRespDesc;
	}

	/**
	 * @param tradeRespDesc the tradeRespDesc to set
	 */
	public void setTradeRespDesc(String tradeRespDesc) {
		this.tradeRespDesc = tradeRespDesc;
	}

	/**
	 * @return the channelRespCode
	 */
	public String getChannelRespCode() {
		return channelRespCode;
	}

	/**
	 * @param channelRespCode the channelRespCode to set
	 */
	public void setChannelRespCode(String channelRespCode) {
		this.channelRespCode = channelRespCode;
	}

	/**
	 * @return the channelRespDesc
	 */
	public String getChannelRespDesc() {
		return channelRespDesc;
	}

	/**
	 * @param channelRespDesc the channelRespDesc to set
	 */
	public void setChannelRespDesc(String channelRespDesc) {
		this.channelRespDesc = channelRespDesc;
	}

	
	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode the memberCode to set
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
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
	 * @return the shippingAddress
	 */
	public String getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @param shippingAddress the shippingAddress to set
	 */
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@Override
	public String toString() {
		return "ExceptionCardDetailMsgDTO [memberCode=" + memberCode
				+ ", orderId=" + orderId + ", tradeOrderNo=" + tradeOrderNo
				+ ", paymentOrderNo=" + paymentOrderNo + ", channelOrderNo="
				+ channelOrderNo + ", orgCode=" + orgCode + ", orderAmount="
				+ orderAmount + ", currencyCode=" + currencyCode
				+ ", currencyRate=" + currencyRate + ", channelResult="
				+ channelResult + ", tradeResult=" + tradeResult
				+ ", createTime=" + createTime + ", payAmount=" + payAmount
				+ ", payCurrencyCode=" + payCurrencyCode + ", merchantNo="
				+ merchantNo + ", site=" + site + ", cardNo=" + cardNo
				+ ", cardHolderName=" + cardHolderName + ", ip=" + ip
				+ ", email=" + email + ", cardHolderMobile=" + cardHolderMobile
				+ ", tradeRespCode=" + tradeRespCode + ", tradeRespDesc="
				+ tradeRespDesc + ", channelRespCode=" + channelRespCode
				+ ", channelRespDesc=" + channelRespDesc + "]";
	}

	

}
