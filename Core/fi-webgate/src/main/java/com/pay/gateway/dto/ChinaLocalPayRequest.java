/**
 * 
 */
package com.pay.gateway.dto;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.util.StringUtil;

/**
 * @author peiyu.yang
 * @since 2016年5月31日09:38:27
 */
public class ChinaLocalPayRequest {

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
	// 商品数量
	private String goodsCount;
	//商品类型
	/*
	 * 服装 00 
	 * 食品 01 
	 * 电子产品 02 
	 * 其他 99
	 */
	private String goodsType;
	// 订单提交时间
	private String submitTime;
	// 订单失效时间
	private String failureTime;
	// 下单域名及IP
	private String customerIP;
	// 商户网站
	private String siteId;
	// 订单金额
	private String orderAmount;
	// 交易类型 1001：api即时支付
	// 交易类型 1002：收银台即时支付
	private String tradeType;
	// 行业类型 0004 货物贸易，0005 酒店住宿
	// 0006 机票旅游  0007 留学教育
	private String mcc;
	// 付款账号
	private String payerAccount;
	// 支付方式
	private String payType;
	// 资金机构代码
	private String orgCode;
	// 交易币种
	private String currencyCode;
	// 结算币种
	private String settlementCurrencyCode;
	// 借贷标识
	private String borrowingMarked;
	// 优惠券标识
	private String couponFlag;
	// 优惠号码
	private String couponNumber;
	// 平台商Id
	private String platformId;
	// 后台回调地址
	private String noticeUrl;
	// 后台回调地址
	private String returnUrl;
	private String language;
	// 商户ＩＤ
	private String partnerId;
	// 收单方式，A：API；C：收银台；P：支付链
	private String paymentWay ;
	
	// 是否3D，Y：是3D add by liu.quanhong
	private String if3d ;
	
	private String directFlag;
	private String idCardNo;//身份证编号
	/**
	 * 购买人姓名
	 */
	private String buyerName;
	/**
	 * 安全信息
	 */
	private String deviceFingerprintId;
	private String registerUserId;
	private String registerUserEmail;
	private String orderTerminal;
	//货运机构
	private String freightOrg;
	//货运机构网址
	private String freightWebsite;
	//运单号
	private String waybillNo;
	//返货方式 0：邮寄，1：收货人自取
	private String deliverMode;
	//收款人名称
	private String payeeName;
	//收款人国家地区英文码
	private String regionEnCode;
	//收款行swift
	private String payeeSwift;
	//收款人行行号
	private String payeeBanknum;
	//收款人银行账号
	private String payeeAccount;
	//收款人地址
	private String payeeAddress;
	//收款币种
	private String payCurrency;
	//交易的凭证， ftp 地址或者可访问的网络路径。
	private String attachment;
	// 备注
	private String remark;

	private String charset;

	private String signType;

	private String signMsg;

	private ChinaLocalPayResponse chinaLocalPayResponse;
	
	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getIf3d() {
		return if3d;
	}

	public void setIf3d(String if3d) {
		this.if3d = if3d;
	}
	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
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

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
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

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
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

	public String getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
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

	public String getDeviceFingerprintId() {
		return deviceFingerprintId;
	}

	public void setDeviceFingerprintId(String deviceFingerprintId) {
		this.deviceFingerprintId = deviceFingerprintId;
	}

	public String getRegisterUserId() {
		return registerUserId;
	}

	public void setRegisterUserId(String registerUserId) {
		this.registerUserId = registerUserId;
	}

	public String getRegisterUserEmail() {
		return registerUserEmail;
	}

	public void setRegisterUserEmail(String registerUserEmail) {
		this.registerUserEmail = registerUserEmail;
	}

	public String getOrderTerminal() {
		return orderTerminal;
	}

	public void setOrderTerminal(String orderTerminal) {
		this.orderTerminal = orderTerminal;
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
	
	public ChinaLocalPayResponse getChinaLocalPayResponse() {
		return chinaLocalPayResponse;
	}

	public void setChinaLocalPayResponse(ChinaLocalPayResponse chinaLocalPayResponse) {
		this.chinaLocalPayResponse = chinaLocalPayResponse;
	}
	public String getDirectFlag() {
		return directFlag;
	}

	public void setDirectFlag(String directFlag) {
		this.directFlag = directFlag;
	}

	public String getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(String paymentWay) {
		this.paymentWay = paymentWay;
	}
	public String getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(String goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getFreightOrg() {
		return freightOrg;
	}

	public void setFreightOrg(String freightOrg) {
		this.freightOrg = freightOrg;
	}

	public String getFreightWebsite() {
		return freightWebsite;
	}

	public void setFreightWebsite(String freightWebsite) {
		this.freightWebsite = freightWebsite;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getDeliverMode() {
		return deliverMode;
	}

	public void setDeliverMode(String deliverMode) {
		this.deliverMode = deliverMode;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getRegionEnCode() {
		return regionEnCode;
	}

	public void setRegionEnCode(String regionEnCode) {
		this.regionEnCode = regionEnCode;
	}

	public String getPayeeSwift() {
		return payeeSwift;
	}

	public void setPayeeSwift(String payeeSwift) {
		this.payeeSwift = payeeSwift;
	}

	public String getPayeeBanknum() {
		return payeeBanknum;
	}

	public void setPayeeBanknum(String payeeBanknum) {
		this.payeeBanknum = payeeBanknum;
	}

	public String getPayeeAccount() {
		return payeeAccount;
	}

	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}

	public String getPayeeAddress() {
		return payeeAddress;
	}

	public void setPayeeAddress(String payeeAddress) {
		this.payeeAddress = payeeAddress;
	}

	public String getPayCurrency() {
		return payCurrency;
	}

	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
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
						&& !"chinaLocalPayResponse".equals(key)) {
					
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
		try {
			BeanWrapper bean = new BeanWrapperImpl(this);
			int i = 0;
			PropertyDescriptor[] properties = bean.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : properties) {
				i++;
				String key = propertyDescriptor.getDisplayName();
				Object value = bean.getPropertyValue(key);
				if (!"class".equals(key)) {
					if (!StringUtil.isEmpty(value+"")) {
						sb.append(key);
						sb.append("=");
						sb.append(value);
					}						
					if (i < properties.length) {
						sb.append("&");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
