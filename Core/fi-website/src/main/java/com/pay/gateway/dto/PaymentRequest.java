package com.pay.gateway.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/20.
 */
public class PaymentRequest {
    /**
     * 渠道ID  通用使用
     */
    private String orgCode;

    /**订单号码*/
    private String tradeOrderNo;

    /**商户订单ID*/
    private String orderId;
    private String mapData;
	

	public String getMapData() {
		return mapData;
	}

	public void setMapData(String mapData) {
		this.mapData = mapData;
	}

	/**会员号*/
    private String partnerId;
    /**货物名称*/
    private String goodsName;
    /**货物描述*/
    private String goodsDesc;
    /**卖家名称*/
    private String sellerName;
    /**订单金额*/
    private String orderAmount;
    /**交易币种*/
    private String currencyCode;
    /**交易终端*/
    private String orderTerminal;
    /**语言*/
    private String language;
    /**终端ID*/
    private String deviceFingerprintId;
    /**商户授权网站*/
    private String siteId;
    /**卡设定*/
    private String cardLimit;
    /**备注信息*/
    private String remark;
    /**返回的*/
    private String returnUrl;
    private String noticeUrl;
    /**用户商户注册ID*/
    private String registerUserId;
    /**交易IP*/
    private String customerIP;
    /**input数据集合*/
    private Map<String,String> data;
    /**结算币种*/
    private String settlementCurrencyCode;

    public String getSettlementCurrencyCode() {
        return settlementCurrencyCode;
    }

    public void setSettlementCurrencyCode(String settlementCurrencyCode) {
        this.settlementCurrencyCode = settlementCurrencyCode;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTradeOrderNo() {
        return tradeOrderNo;
    }

    public void setTradeOrderNo(String tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
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

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getOrderTerminal() {
        return orderTerminal;
    }

    public void setOrderTerminal(String orderTerminal) {
        this.orderTerminal = orderTerminal;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDeviceFingerprintId() {
        return deviceFingerprintId;
    }

    public void setDeviceFingerprintId(String deviceFingerprintId) {
        this.deviceFingerprintId = deviceFingerprintId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(String cardLimit) {
        this.cardLimit = cardLimit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(String registerUserId) {
        this.registerUserId = registerUserId;
    }

    public String getCustomerIP() {
        return customerIP;
    }

    public void setCustomerIP(String customerIP) {
        this.customerIP = customerIP;
    }

    public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	@Override
	public String toString() {
		return "PaymentRequest [orgCode=" + orgCode + ", tradeOrderNo=" + tradeOrderNo + ", orderId=" + orderId
				+ ", mapData=" + mapData + ", partnerId=" + partnerId + ", goodsName=" + goodsName + ", goodsDesc="
				+ goodsDesc + ", sellerName=" + sellerName + ", orderAmount=" + orderAmount + ", currencyCode="
				+ currencyCode + ", orderTerminal=" + orderTerminal + ", language=" + language
				+ ", deviceFingerprintId=" + deviceFingerprintId + ", siteId=" + siteId + ", cardLimit=" + cardLimit
				+ ", remark=" + remark + ", returnUrl=" + returnUrl + ", noticeUrl=" + noticeUrl + ", registerUserId="
				+ registerUserId + ", customerIP=" + customerIP + ", data=" + data + "]";
	}
	
}
