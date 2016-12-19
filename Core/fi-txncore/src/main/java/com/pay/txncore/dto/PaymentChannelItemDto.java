package com.pay.txncore.dto;

import com.pay.util.StringUtil;

import java.math.BigDecimal;

/**
 * payment_channel_item
 */

public class PaymentChannelItemDto {

	private String id;
	private String code;
	private String alias;
	private String bankCode;
	private String name;
	/**
	 * 科目后缀
	 */
	private String orgCode;

	private String currencyCode;
	/**
	 * 渠道费率
	 */
	private String rate;
	/**
	 * 通讯方式：HTTP，SOCKET，HESSIAN
	 */
	private String protocolType;
	/**
	 * 银行前置通讯地址
	 */
	private String preServerUrl;
	/**
	 * 银行图标
	 */
	private String labelClass;
	/**
	 * 状态：1-有效，0-无效
	 */
	private String status;
	/**
	 * 渠道类别
	 */
	private String paymentCategoryCode;
	/**
	 * 渠道编号
	 */
	private String paymentChannelCode;
	/**
	 * 路由金额
	 */
	private String routeAmount;
	/**
	 * 单笔限额
	 */
	private String singleAmount;
	/**
	 * 平台在机构的商户号
	 */
	private String orgMerchantCode;
	/**
	 * 前台回调地址
	 */
	private String frontCallbackUrl;
	/**
	 * 后台回调地址
	 */
	private String backgroundCallbackUrl;
	/**
	 * 是否需要签约：0-不需要，1-需要
	 */
	private String signFlag;
	/**
	 * 操作员
	 */
	private String operator;
	/**
	 * 排列序号
	 */
	private String serialNo;
	/**
	 * 支持卡类型：1-只借，2-只贷，0-不分借贷
	 */
	private String cardType;

	private String description;

	/**
	 * 结算参数
	 */
	private String settlement;

	private String pattern;

	// 商户号
	private String memberCode;

	private String secondMemberCode;

	// 默认渠道时配置类别
	private String paymentType;

	// 会员类型
	private String memberType;

	private String orgKey;

	private String terminalCode;
	private String accessCode;
	private String transType;
	
	/**优先级**/
	private String channelPriority;
	
	private String merchantBillName;
	
	private String mcc;

	private String memberCurrentConnectId;

	private String getGcCurrentFlag;

	private String channelConfigId;

	public String getChannelConfigId() {
		return channelConfigId;
	}

	public void setChannelConfigId(String channelConfigId) {
		this.channelConfigId = channelConfigId;
	}

	public String getMemberCurrentConnectId() {
		return memberCurrentConnectId;
	}

	public void setMemberCurrentConnectId(String memberCurrentConnectId) {
		this.memberCurrentConnectId = memberCurrentConnectId;
	}

	public String getGetGcCurrentFlag() {
		return getGcCurrentFlag;
	}

	public void setGetGcCurrentFlag(String getGcCurrentFlag) {
		this.getGcCurrentFlag = getGcCurrentFlag;
	}

	public String getMerchantBillName() {
		return merchantBillName;
	}

	public void setMerchantBillName(String merchantBillName) {
		this.merchantBillName = merchantBillName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public String getPreServerUrl() {
		return preServerUrl;
	}

	public void setPreServerUrl(String preServerUrl) {
		this.preServerUrl = preServerUrl;
	}

	public String getLabelClass() {
		return labelClass;
	}

	public void setLabelClass(String labelClass) {
		this.labelClass = labelClass;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentCategoryCode() {
		return paymentCategoryCode;
	}

	public void setPaymentCategoryCode(String paymentCategoryCode) {
		this.paymentCategoryCode = paymentCategoryCode;
	}

	public String getPaymentChannelCode() {
		return paymentChannelCode;
	}

	public void setPaymentChannelCode(String paymentChannelCode) {
		this.paymentChannelCode = paymentChannelCode;
	}

	public String getRouteAmount() {
		return routeAmount;
	}

	public void setRouteAmount(String routeAmount) {
		this.routeAmount = routeAmount;
	}

	public String getSingleAmount() {
		return singleAmount;
	}

	public void setSingleAmount(String singleAmount) {
		this.singleAmount = singleAmount;
	}

	public String getOrgMerchantCode() {
		return orgMerchantCode;
	}

	public void setOrgMerchantCode(String orgMerchantCode) {
		this.orgMerchantCode = orgMerchantCode;
	}

	public String getFrontCallbackUrl() {
		return frontCallbackUrl;
	}

	public void setFrontCallbackUrl(String frontCallbackUrl) {
		this.frontCallbackUrl = frontCallbackUrl;
	}

	public String getBackgroundCallbackUrl() {
		return backgroundCallbackUrl;
	}

	public void setBackgroundCallbackUrl(String backgroundCallbackUrl) {
		this.backgroundCallbackUrl = backgroundCallbackUrl;
	}

	public String getSignFlag() {
		return signFlag;
	}

	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getMemberCode() {
		if (!StringUtil.isEmpty(memberCode)) {
			return memberCode.trim();
		} else {
			return memberCode;
		}
	}

	public void setMemberCode(String memberCode) {

		if (!StringUtil.isEmpty(memberCode)) {
			this.memberCode = memberCode.trim();
		} else {
			this.memberCode = memberCode;
		}
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getSecondMemberCode() {
		return secondMemberCode;
	}

	public void setSecondMemberCode(String secondMemberCode) {
		this.secondMemberCode = secondMemberCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getOrgKey() {
		return orgKey;
	}

	public void setOrgKey(String orgKey) {
		this.orgKey = orgKey;
	}

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getChannelPriority() {
		return channelPriority;
	}

	public void setChannelPriority(String channelPriority) {
		this.channelPriority = channelPriority;
	}
	
	

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	@Override
	public String toString() {
		return "PaymentChannelItemDto [id=" + id + ", code=" + code
				+ ", alias=" + alias + ", bankCode=" + bankCode + ", name="
				+ name + ", orgCode=" + orgCode + ", currencyCode="
				+ currencyCode + ", rate=" + rate + ", protocolType="
				+ protocolType + ", preServerUrl=" + preServerUrl
				+ ", labelClass=" + labelClass + ", status=" + status
				+ ", paymentCategoryCode=" + paymentCategoryCode
				+ ", paymentChannelCode=" + paymentChannelCode
				+ ", routeAmount=" + routeAmount + ", singleAmount="
				+ singleAmount + ", orgMerchantCode=" + orgMerchantCode
				+ ", frontCallbackUrl=" + frontCallbackUrl
				+ ", backgroundCallbackUrl=" + backgroundCallbackUrl
				+ ", signFlag=" + signFlag + ", operator=" + operator
				+ ", serialNo=" + serialNo + ", cardType=" + cardType
				+ ", description=" + description + ", settlement=" + settlement
				+ ", pattern=" + pattern + ", memberCode=" + memberCode
				+ ", secondMemberCode=" + secondMemberCode + ", paymentType="
				+ paymentType + ", memberType=" + memberType + ", orgKey="
				+ orgKey + ", terminalCode=" + terminalCode + ", accessCode="
				+ accessCode + ", transType=" + transType + ", memberCurrentConnectId=" + memberCurrentConnectId
				+ ", channelPriority=" + channelPriority + ", getGcCurrentFlag=" + getGcCurrentFlag
				+ ", merchantBillName=" + merchantBillName + ", mcc=" + mcc
				+ "]";
	}
	
	

}