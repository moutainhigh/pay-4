/**
 * 
 */
package com.pay.txncore.newpaymentprocess.processvo;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 威富通【微信｜支付宝】返回生成二维码、WAP继续支付的参数
 * @author Jiangbo.Peng
 *
 */
public class WftBackVo {

    private String responseCode;//返回code
    private String responseDesc;//返回的描述
    private String codeImgUrl ;//微信扫码支付、支付宝扫码支付时的二维码图片链接
    //微信WAP支付的唤起手机微信支付url地址,当为支付宝js支付时是一个自行唤起支付宝钱宝的原生支付信息
    private String payInfo ;
    private String payUrl ;//支付宝js支付直接请求支付宝支付的链接
    private String outTradeNo ;//我们在微富通的商户订单号，对应ipaylinks系统的渠道订单号
    private String totalFee ;//订单总金额
    private String body ;//商品描述
	
	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}
	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	/**
	 * @return the responseDesc
	 */
	public String getResponseDesc() {
		return responseDesc;
	}
	/**
	 * @param responseDesc the responseDesc to set
	 */
	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}
	/**
	 * @return the codeImgUrl
	 */
	public String getCodeImgUrl() {
		return codeImgUrl;
	}
	/**
	 * @param codeImgUrl the codeImgUrl to set
	 */
	public void setCodeImgUrl(String codeImgUrl) {
		this.codeImgUrl = codeImgUrl;
	}
	/**
	 * @return the payInfo
	 */
	public String getPayInfo() {
		return payInfo;
	}
	/**
	 * @param payInfo the payInfo to set
	 */
	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}
	/**
	 * @return the outTradeNo
	 */
	public String getOutTradeNo() {
		return outTradeNo;
	}
	/**
	 * @param outTradeNo the outTradeNo to set
	 */
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	/**
	 * @return the totalFee
	 */
	public String getTotalFee() {
		return totalFee;
	}
	/**
	 * @param totalFee the totalFee to set
	 */
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

    /**
	 * @return the payUrl
	 */
	public String getPayUrl() {
		return payUrl;
	}
	/**
	 * @param payUrl the payUrl to set
	 */
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	return ToStringBuilder.reflectionToString(this) ;
    }
    
}
