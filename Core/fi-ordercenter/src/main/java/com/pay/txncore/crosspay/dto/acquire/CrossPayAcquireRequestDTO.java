/**
 * 
 */
package com.pay.txncore.crosspay.dto.acquire;

import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 在线订单请求参数 完全和商户下单接口参数一致，在控制器层经过转换得到
 * 
 * @author huhb
 *
 */
public class CrossPayAcquireRequestDTO {

	private List<CrossPayAcquireOrderDetailDTO> acquireDetails;

	@NotNull
	private String orderDetails;

	/**
	 * 版本
	 */
	@Size(max = 4)
	@NotNull
	private String version;

	/**
	 * 请求序列号
	 */
	@Size(max = 32)
	@NotNull
	private String serialID;

	/**
	 * 订单提交时间
	 */
	@Size(max = 14)
	@NotNull
	private String submitTime;

	/**
	 * 订单失效时间
	 */
	@Size(max = 14)
	private String failureTime;

	/**
	 * 客户下单域名及IP
	 */
	@Size(max = 128)
	private String customerIP;

	@Size(max = 256)
	private String customerUrl;

	/**
	 * 订单总金额
	 */
	@Digits(integer = 18, fraction = 0)
	@NotNull
	private String totalAmount;

	/**
	 * 交易类型
	 */
	@Size(max = 4)
	@NotNull
	private String type;

	/**
	 * 付款方新生账户号
	 */
	@Size(max = 64)
	private String buyerMarked;

	/**
	 * 付款方式
	 */
	@Size(max = 128)
	private String payType;

	/**
	 * 目标资金机构代码
	 */
	@Size(max = 12)
	private String orgCode;

	/**
	 * 交易币种
	 */
	@Size(max = 1)
	private String currencyCode;

	@Size(max = 1)
	private String settlementCurrencyCode;

	/**
	 * 是否直连
	 */
	@Size(max = 1)
	private String directFlag;

	/**
	 * 资金来源借贷标识
	 */
	@Size(max = 1)
	private String borrowingMarked;

	/**
	 * 优惠券标识
	 */
	@Size(max = 2)
	private String couponFlag;

	/**
	 * 平台商ID
	 */
	@Size(max = 32)
	private String platformID;

	/**
	 * 商户回调地址
	 */
	@Size(max = 256)
	@NotNull
	private String returnUrl;

	/**
	 * 商户通知地址
	 */
	@Size(max = 256)
	@NotNull
	private String noticeUrl;

	/**
	 * 商户ID
	 */
	@Size(max = 32)
	@NotNull
	private String partnerID;

	/**
	 * 扩展字段
	 */
	@Size(max = 256)
	@NotNull
	private String remark;

	/**
	 * 编码方式
	 */
	@Size(max = 1)
	@NotNull
	private String charset;

	/**
	 * 签名类型
	 */
	@Size(max = 1)
	@NotNull
	private String signType;

	/**
	 * 签名字符串
	 */
	@Size(max = 256)
	@NotNull
	private String signMsg;

	@Size(max = 1)
	private String language;

	@Size(max = 128)
	private String firstName;

	@Size(max = 128)
	private String surName;

	@Size(max = 128)
	private String email;

	@Size(max = 64)
	private String phoneNumber;

	@Size(max = 32)
	private String zip;

	@Size(max = 128)
	private String street;

	@Size(max = 128)
	private String city;

	@Size(max = 128)
	private String state;

	@Size(max = 64)
	private String countryCode;

	@Size(max = 128)
	private String fromAddress;

	@Size(max = 128)
	private String shippingFullname;

	@Size(max = 128)
	private String shippingMail;

	@Size(max = 64)
	private String shippingPhone;

	@Size(max = 32)
	private String shippingZip;

	@Size(max = 128)
	private String shippingStreet;

	@Size(max = 128)
	private String shippingCity;

	@Size(max = 128)
	private String shippingState;

	@Size(max = 64)
	private String shippingCountryCode;

	@Size(max = 128)
	private String payerName;

	@Size(max = 1)
	private String payerContactType;

	@Size(max = 128)
	private String payerContact;

	@Size(max = 64)
	private String payerIdentityCard;

	@Size(max = 1)
	private String payerIdType;

	@Size(max = 64)
	private String cardNo;

	/**
	 * 请求历史流水号，作为外键使用
	 */
	private Long requestNo;

	private Long tradeBaseNo;

	private boolean isExsits;

	public boolean isExsits() {
		return isExsits;
	}

	public void setExsits(boolean isExsits) {
		this.isExsits = isExsits;
	}

	public Long getTradeBaseNo() {
		return tradeBaseNo;
	}

	public void setTradeBaseNo(Long tradeBaseNo) {
		this.tradeBaseNo = tradeBaseNo;
	}

	public Long getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(Long requestNo) {
		this.requestNo = requestNo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSerialID() {
		return serialID;
	}

	public void setSerialID(String serialID) {
		this.serialID = serialID;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getFailureTime() {
		return failureTime;
	}

	public void setFailureTime(String failureTime) {
		this.failureTime = failureTime;
	}

	public String getCustomerIP() {
		return customerIP;
	}

	public void setCustomerIP(String customerIP) {
		this.customerIP = customerIP;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBuyerMarked() {
		return buyerMarked;
	}

	public void setBuyerMarked(String buyerMarked) {
		this.buyerMarked = buyerMarked;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDirectFlag() {
		return directFlag;
	}

	public void setDirectFlag(String directFlag) {
		this.directFlag = directFlag;
	}

	public String getBorrowingMarked() {
		return borrowingMarked;
	}

	public void setBorrowingMarked(String borrowingMarked) {
		this.borrowingMarked = borrowingMarked;
	}

	public String getCouponFlag() {
		return couponFlag;
	}

	public void setCouponFlag(String couponFlag) {
		this.couponFlag = couponFlag;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public String getPartnerID() {
		return partnerID;
	}

	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getPlatformID() {
		return platformID;
	}

	public void setPlatformID(String platformID) {
		this.platformID = platformID;
	}

	public List<CrossPayAcquireOrderDetailDTO> getAcquireDetails() {
		return acquireDetails;
	}

	public void setAcquireDetails(
			List<CrossPayAcquireOrderDetailDTO> acquireDetails) {
		this.acquireDetails = acquireDetails;
	}

	public String getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(String orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getCustomerUrl() {
		return customerUrl;
	}

	public void setCustomerUrl(String customerUrl) {
		this.customerUrl = customerUrl;
	}

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getShippingFullname() {
		return shippingFullname;
	}

	public void setShippingFullname(String shippingFullname) {
		this.shippingFullname = shippingFullname;
	}

	public String getShippingMail() {
		return shippingMail;
	}

	public void setShippingMail(String shippingMail) {
		this.shippingMail = shippingMail;
	}

	public String getShippingPhone() {
		return shippingPhone;
	}

	public void setShippingPhone(String shippingPhone) {
		this.shippingPhone = shippingPhone;
	}

	public String getShippingZip() {
		return shippingZip;
	}

	public void setShippingZip(String shippingZip) {
		this.shippingZip = shippingZip;
	}

	public String getShippingStreet() {
		return shippingStreet;
	}

	public void setShippingStreet(String shippingStreet) {
		this.shippingStreet = shippingStreet;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public String getShippingState() {
		return shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public String getShippingCountryCode() {
		return shippingCountryCode;
	}

	public void setShippingCountryCode(String shippingCountryCode) {
		this.shippingCountryCode = shippingCountryCode;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerContactType() {
		return payerContactType;
	}

	public void setPayerContactType(String payerContactType) {
		this.payerContactType = payerContactType;
	}

	public String getPayerContact() {
		return payerContact;
	}

	public void setPayerContact(String payerContact) {
		this.payerContact = payerContact;
	}

	public String getPayerIdentityCard() {
		return payerIdentityCard;
	}

	public void setPayerIdentityCard(String payerIdentityCard) {
		this.payerIdentityCard = payerIdentityCard;
	}

	public String getPayerIdType() {
		return payerIdType;
	}

	public void setPayerIdType(String payerIdType) {
		this.payerIdType = payerIdType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Override
	public String toString() {
		return "CrossPayAcquireRequestDTO [acquireDetails=" + acquireDetails
				+ "borrowingMarked=" + borrowingMarked + ", buyerMarked="
				+ buyerMarked + ", charset=" + charset + ", couponFlag="
				+ couponFlag + ", currencyCode=" + currencyCode
				+ ", customerIP=" + customerIP + ", directFlag=" + directFlag
				+ ", failureTime=" + failureTime + ", isExsits=" + isExsits
				+ ", noticeUrl=" + noticeUrl + ", orderDetails=" + orderDetails
				+ ", orgCode=" + orgCode + ", partnerID=" + partnerID
				+ ", payType=" + payType + ", platformID=" + platformID
				+ ", remark=" + remark + ", requestNo=" + requestNo
				+ ", returnUrl=" + returnUrl + ", serialID=" + serialID
				+ ", signMsg=" + signMsg + ", signType=" + signType
				+ ", submitTime=" + submitTime + ", totalAmount=" + totalAmount
				+ ", tradeBaseNo=" + tradeBaseNo + ", type=" + type
				+ ", version=" + version + "]";
	}

}
