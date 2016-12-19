/**
 * 
 */
package com.pay.gateway.dto;

import java.beans.PropertyDescriptor;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.util.StringUtil;

/**
 * @author libo
 *
 */
public class WechatAlipayRequest {

	// 请求版本
	private String version;
	// 请求序列号
	private String orderId;
	// 商户显示名称
	private String displayName;
	// 商品名称
	private String goodsName;
	// 商品描述
	private String goodsDesc;
	// 订单提交时间
	private String submitTime;
	// 订单失效时间
	private String failureTime;
	// 下单域名及IP
	private String customerIP;
	//商户网站域名
	private String siteId;
	// 订单金额
	private String orderAmount;
	// 交易类型
	private String tradeType;
	// 付款账号
	private String payerAccount;
	// 支付方式
	private String payType;
	// 资金机构代码
	private String orgCode;
	// 交易币种
	private String currencyCode;
	//结算币种
	private String settlementCurrencyCode;
	// 是否直连
	private String directFlag;
	// 借贷标识
	private String borrowingMarked;
	// 优惠券标识
	private String couponFlag;
	// 平台商Id
	private String platformId;
	// 前台回调地址
	private String returnUrl;
	// 后台回调地址
	private String noticeUrl;
	// 商户ＩＤ
	private String partnerId;

	private String language;
	
	/**
	 * 001 visa
	 * 002 masted
	 * 003 jcb
	 * 支付卡种限制
	 */
	private String cardLimit;
	
	private String sellerName;
	
	
	/**
	 * 终端标识
	 */
	private String orderTerminal;

	/**
	 * 账单信息
	 */
	private String billName;
	private String billAddress;
	private String billEmail;
	private String billPhoneNumber;
	private String billPostalCode;
	private String billStreet;
	private String billCity;
	private String billState;
	private String billCountryCode;

	/**
	 * 收贷人信息
	 */
	private String fromAddress;
	private String shippingFullname;
	private String shippingMail;
	private String shippingPhone;
	private String shippingZip;
	private String shippingStreet;
	private String shippingCity;
	private String shippingState;
	private String shippingCountryCode;
	private String travDetailsSize;
	private List<TravlDetail> travDetails;
	private String registerUserId;

	// 备注
	private String remark;

	private String charset;

	private String signType;

	private String signMsg;
	
	//本地化支付需要的字段
	private String mcc;
	
	private String payMode;
	
	private String buyerId;

	// 返回对象
	private WechatAlipayResponse wechatAlipayResponseDTO;

	public String getRegisterUserId() {
		return registerUserId;
	}

	public void setRegisterUserId(String registerUserId) {
		this.registerUserId = registerUserId;
	}

	public String getCardLimit() {
		return cardLimit;
	}

	public void setCardLimit(String cardLimit) {
		this.cardLimit = cardLimit;
	}

	public String getOrderTerminal() {
		return orderTerminal;
	}

	public void setOrderTerminal(String orderTerminal) {
		this.orderTerminal = orderTerminal;
	}


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getPayerAccount() {
		return payerAccount;
	}

	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
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

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
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

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
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

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
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

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public String getBillAddress() {
		return billAddress;
	}

	public void setBillAddress(String billAddress) {
		this.billAddress = billAddress;
	}

	public String getBillEmail() {
		return billEmail;
	}

	public void setBillEmail(String billEmail) {
		this.billEmail = billEmail;
	}

	public String getBillPhoneNumber() {
		return billPhoneNumber;
	}

	public void setBillPhoneNumber(String billPhoneNumber) {
		this.billPhoneNumber = billPhoneNumber;
	}

	public String getBillPostalCode() {
		return billPostalCode;
	}

	public void setBillPostalCode(String billPostalCode) {
		this.billPostalCode = billPostalCode;
	}

	public String getBillStreet() {
		return billStreet;
	}

	public void setBillStreet(String billStreet) {
		this.billStreet = billStreet;
	}

	public String getBillCity() {
		return billCity;
	}

	public void setBillCity(String billCity) {
		this.billCity = billCity;
	}

	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}

	public String getBillCountryCode() {
		return billCountryCode;
	}

	public void setBillCountryCode(String billCountryCode) {
		this.billCountryCode = billCountryCode;
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

	public String getTravDetailsSize() {
		return travDetailsSize;
	}

	public void setTravDetailsSize(String travDetailsSize) {
		this.travDetailsSize = travDetailsSize;
	}

	public List<TravlDetail> getTravDetails() {
		return travDetails;
	}

	public void setTravDetails(List<TravlDetail> travDetails) {
		this.travDetails = travDetails;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public WechatAlipayResponse getWechatAlipayResponseDTO() {
		return wechatAlipayResponseDTO;
	}

	public void setWechatAlipayResponseDTO(
			WechatAlipayResponse wechatAlipayResponseDTO) {
		this.wechatAlipayResponseDTO = wechatAlipayResponseDTO;
	}

	public String generateSign() {
		StringBuilder sb = new StringBuilder();
		try {
			BeanWrapper bean = new BeanWrapperImpl(this);
			PropertyDescriptor[] properties = bean.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : properties) {
				String key = propertyDescriptor.getDisplayName();
				Object value = bean.getPropertyValue(key);
				if (!"class".equals(key) && !"signMsg".equals(key)
						&& !"wechatAlipayResponseDTO".equals(key)) {
					if (!StringUtil.isEmpty(value+"")) {
						if(sb.length()<1){
							sb.append(key);
							sb.append("=");
							sb.append(value);
						}else{
							sb.append("&");
							sb.append(key);
							sb.append("=");
							sb.append(value);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		BeanWrapper beanWrapper = new BeanWrapperImpl(this);
		PropertyDescriptor[] propertyDescriptors = beanWrapper
				.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String propertyName = property.getDisplayName();
			Object value = beanWrapper.getPropertyValue(propertyName);
			if ("class".equals(propertyName)) {
				continue;
			}
			sb.append("&");
			sb.append(propertyName);
			sb.append("=");
			sb.append(value);
		}
		return sb.toString();
	}
}
