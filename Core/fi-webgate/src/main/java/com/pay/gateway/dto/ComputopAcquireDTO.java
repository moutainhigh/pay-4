package com.pay.gateway.dto;

import java.util.Date;

public class ComputopAcquireDTO {
	private Long id;
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
	private Integer status;
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
	private Long routeAmount;
	/**
	 * 单笔限额
	 */
	private Long singleAmount;
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
	private Integer signFlag;
	/**
	 * 操作员
	 */
	private String operator;
	private Date createDate;
	private Date updateDate;
	/**
	 * 排列序号
	 */
	private Long serialNo;
	/**
	 * 支持卡类型：1-只借，2-只贷，0-不分借贷
	 */
	private Integer cardType;

	private String description;

	/**
	 * 结算参数
	 */
	private String settlement;

	private String pattern;

	// 商户号
	private Long memberCode;

	// 默认渠道时配置类别
	private Integer paymentType;

	// 会员类型
	private Integer memberType;

	// 1-默认2-配置
	private String type;

	private String orgKey;

	private String terminalCode;
	private String accessCode;
	private String transType;
	
	private String merchantBillName;
	//mcc
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

	/**支付渠道配置ID**/
	private String paymentChannelItemConfigId;
	
	/**优先级**/
	private String channelPriority;

	public String getChannelPriority() {
		return channelPriority;
	}

	public void setChannelPriority(String channelPriority) {
		this.channelPriority = channelPriority;
	}

	public String getPaymentChannelItemConfigId() {
		return paymentChannelItemConfigId;
	}

	public void setPaymentChannelItemConfigId(String paymentChannelItemConfigId) {
		this.paymentChannelItemConfigId = paymentChannelItemConfigId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	public Long getRouteAmount() {
		return routeAmount;
	}

	public void setRouteAmount(Long routeAmount) {
		this.routeAmount = routeAmount;
	}

	public Long getSingleAmount() {
		return singleAmount;
	}

	public void setSingleAmount(Long singleAmount) {
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

	public Integer getSignFlag() {
		return signFlag;
	}

	public void setSignFlag(Integer signFlag) {
		this.signFlag = signFlag;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
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
	
	
	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	@Override
	public String toString() {
		return "PaymentChannelItem [id=" + id + ", code=" + code + ", alias="
				+ alias + ", bankCode=" + bankCode + ", name=" + name
				+ ", orgCode=" + orgCode + ", currencyCode=" + currencyCode
				+ ", rate=" + rate + ", protocolType=" + protocolType
				+ ", preServerUrl=" + preServerUrl + ", labelClass="
				+ labelClass + ", status=" + status + ", paymentCategoryCode="
				+ paymentCategoryCode + ", paymentChannelCode="
				+ paymentChannelCode + ", routeAmount=" + routeAmount
				+ ", singleAmount=" + singleAmount + ", orgMerchantCode="
				+ orgMerchantCode + ", frontCallbackUrl=" + frontCallbackUrl
				+ ", backgroundCallbackUrl=" + backgroundCallbackUrl
				+ ", signFlag=" + signFlag + ", operator=" + operator
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", serialNo=" + serialNo + ", cardType=" + cardType
				+ ", description=" + description + ", settlement=" + settlement
				+ ", pattern=" + pattern + ", memberCode=" + memberCode
				+ ", paymentType=" + paymentType + ", memberType=" + memberType
				+ ", type=" + type + ", orgKey=" + orgKey + ", terminalCode="
				+ terminalCode + ", accessCode=" + accessCode + ", transType="
				+ transType + ", merchantBillName=" + merchantBillName
				+ ", mcc=" + mcc + ", paymentChannelItemConfigId="
				+ paymentChannelItemConfigId + ", channelPriority="
				+ channelPriority + "]";
	}
	

}
