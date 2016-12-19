package com.pay.gateway.dto;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Created by cuber.huang on 2016/7/21.
 */
public class PaymentResponse {
    /**商户订单*/
    private String orderId;
    /**商户号*/
    private String partnerId;
    /**网关订单号*/
    private String tradeOrderNo;
    /**处理订单*/
    private String dealDate;
    /**交易币种*/
    private String currencyCode;
    /**语言*/
    private String language;
    /**错误信息*/
    private String errorMsg;
    /**返回商户URL*/
    private String returnUrl;
    /**错误CODE*/
    private String resultCode;
    /**错误信息*/
    private String resultMsg;
    /**商家显示名称*/
    private String sellerName;
    /**订单名称*/
    private String goodsName;
    /**订单描述*/
    private String goodsDesc;
    /**交易终端*/
    private String orderTerminal;
    /**支付金额*/
    private String orderAmount;

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        if(StringUtils.isNumeric(orderAmount) && StringUtils.isNotBlank(orderAmount)){
            BigDecimal orderAmountBigDecimal = new BigDecimal(orderAmount);
            orderAmountBigDecimal = orderAmountBigDecimal.divide(new BigDecimal(100),2,BigDecimal.ROUND_FLOOR);
            this.orderAmount = orderAmountBigDecimal.toString();
        }
    }

    public String getOrderTerminal() {
        return orderTerminal;
    }

    public void setOrderTerminal(String orderTerminal) {
        this.orderTerminal = orderTerminal;
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

    public String getTradeOrderNo() {
        return tradeOrderNo;
    }

    public void setTradeOrderNo(String tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    public String getDealDate() {
        return dealDate;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
