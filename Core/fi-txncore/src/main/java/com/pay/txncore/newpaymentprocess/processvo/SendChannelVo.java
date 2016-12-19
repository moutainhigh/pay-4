package com.pay.txncore.newpaymentprocess.processvo;

import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/24.
 */
public class SendChannelVo {
    private String orgMerchantCode;//我们在渠道的商户号
    private String orgKey;//我们在机构号的密钥
    private String orderAmount;//送到渠道的订单金额
    private String currencyCode;//送到渠道的币种
    private Map<String, String> channel2front;//渠道送给前置的必要参数，如收银台等;
    private String goodNams;//订单名称
    private String sellerName;//用户打钱显示的卖家账户
    private String channelOrderNo;//渠道订单ID；
    private String preServerUrl;//送给的渠道前缀
    private String orgCode;
    

    public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getPreServerUrl() {
        return preServerUrl;
    }

    public void setPreServerUrl(String preServerUrl) {
        this.preServerUrl = preServerUrl;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getGoodNams() {
        return goodNams;
    }

    public void setGoodNams(String goodNams) {
        this.goodNams = goodNams;
    }

    public String getOrgMerchantCode() {
        return orgMerchantCode;
    }

    public void setOrgMerchantCode(String orgMerchantCode) {
        this.orgMerchantCode = orgMerchantCode;
    }

    public String getOrgKey() {
        return orgKey;
    }

    public void setOrgKey(String orgKey) {
        this.orgKey = orgKey;
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

    public Map<String, String> getChannel2front() {
        return channel2front;
    }

    public void setChannel2front(Map<String, String> channel2front) {
        this.channel2front = channel2front;
    }

    public String getChannelOrderNo() {
        return channelOrderNo;
    }

    public void setChannelOrderNo(String channelOrderNo) {
        this.channelOrderNo = channelOrderNo;
    }
}
