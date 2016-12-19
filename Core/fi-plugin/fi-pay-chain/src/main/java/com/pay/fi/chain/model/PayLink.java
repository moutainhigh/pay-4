/**
 * 
 */
package com.pay.fi.chain.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 支付链
 * @author PengJiangbo
 *
 */
public class PayLink {
	
	/** 支付链ID[主键] */
	private Long paylinkNo ;
	/** 商户会员号[冗余] */
	private Long memberCode ;
	/** 支付链基本信息ID */
	private Long infoId ;
	/** 创建时间 */
	private Timestamp createTime ;
	/** 失效时间 */
	private Timestamp invalidTime ;
	/** 支付链名称 */
	private String payLinkName ;
	/** 状态0：生效1：失效 */
	private int status ;
	/** 商户logo路径 */
	private String logoPath ;
	/** 商户网址， logo上添加的网址 */
	private String merchantSite ;
	/** 是否显示支付公司logo, 0：显示1:不显示 */
	private int paymentLogoFlg ;
	/** 其它费用名 */
	private String otherFeeName ;
	/** 其它费用名 */
	private Long fee ;
	/** 失效时长 */
	private int invalidTimeLong ;
	/** 总费用（金额）*/
	private Long totalAmount ;
	/** 其他费用币种 */
	private String feeCurrencyCode ;
	/** 商品总金额 */
	private Long productAmount ;
	/**
	 * 支付链状态信息
	 */
	private String statusMsg ;
	
	//-----------------
	private String productName ;
	
	private String currencyCode ;
	
	//商品描述信息
	private String productNameDesc ;
	
	public Long getPaylinkNo() {
		return paylinkNo;
	}
	public void setPaylinkNo(final Long paylinkNo) {
		this.paylinkNo = paylinkNo;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(final Long memberCode) {
		this.memberCode = memberCode;
	}
	public Long getInfoId() {
		return infoId;
	}
	public void setInfoId(final Long infoId) {
		this.infoId = infoId;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(final Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getInvalidTime() {
		return invalidTime;
	}
	public void setInvalidTime(final Timestamp invalidTime) {
		this.invalidTime = invalidTime;
	}
	public String getPayLinkName() {
		return payLinkName;
	}
	public void setPayLinkName(final String payLinkName) {
		this.payLinkName = payLinkName;
	}
	public int getStatus() {
		return status;
	}
	/**
	 * 支付链接状态
	 * 状态信息[statusMsg]有值时，statusMsg为1时，置status为1，statusMsg为0时，置stauts为0
	 * 处理后，页面展示只根据status展示状态信息
	 * @param status
	 */
	public void setStatus(final int status) {
		String statusMsg2 = getStatusMsg();
		if(null != statusMsg2){
			if(statusMsg2.equals("1")){
				this.status = 1;
			}else{
				this.status = status ;
			}
		}else{
			this.status = status ;
		}
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(final String logoPath) {
		this.logoPath = logoPath;
	}
	public int getPaymentLogoFlg() {
		return paymentLogoFlg;
	}
	public void setPaymentLogoFlg(final int paymentLogoFlg) {
		this.paymentLogoFlg = paymentLogoFlg;
	}
	public String getOtherFeeName() {
		return otherFeeName;
	}
	public void setOtherFeeName(final String otherFeeName) {
		this.otherFeeName = otherFeeName;
	}
	public Long getFee() {
		return fee;
	}
	public void setFee(final Long fee) {
		this.fee = fee;
	}
	public int getInvalidTimeLong() {
		return invalidTimeLong;
	}
	public void setInvalidTimeLong(final int invalidTimeLong) {
		this.invalidTimeLong = invalidTimeLong;
	}
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(final Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public String getFeeCurrencyCode() {
		return feeCurrencyCode;
	}
	public void setFeeCurrencyCode(final String feeCurrencyCode) {
		this.feeCurrencyCode = feeCurrencyCode;
	}
	
	public Long getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(final Long productAmount) {
		this.productAmount = productAmount;
	}
	
	public String getStatusMsg() {
		return statusMsg;
	}
	/**
	 * 处理状态信息
	 * 当支付链接过期， 置状态信息statusMs为1，否则置为0
	 * @param invalidTime
	 */
	public void setStatusMsg(final Timestamp invalidTime) {
		long time = new Date().getTime() ;
		if(time > invalidTime.getTime()){
			this.statusMsg = "1" ;
		}else
			this.statusMsg = "0" ;
		
	}
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(final String productName) {
		this.productName = productName;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(final String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public String getMerchantSite() {
		return merchantSite;
	}
	public void setMerchantSite(final String merchantSite) {
		this.merchantSite = merchantSite;
	}
	
	public String getProductNameDesc() {
		return productNameDesc;
	}
	public void setProductNameDesc(String productNameDesc) {
		this.productNameDesc = productNameDesc;
	}
	@Override
	public String toString() {
		return "PayLink [paylinkNo=" + paylinkNo + ", memberCode=" + memberCode
				+ ", infoId=" + infoId + ", createTime=" + createTime
				+ ", invalidTime=" + invalidTime + ", payLinkName="
				+ payLinkName + ", status=" + status + ", logoPath=" + logoPath
				+ ", merchantSite=" + merchantSite + ", paymentLogoFlg="
				+ paymentLogoFlg + ", otherFeeName=" + otherFeeName + ", fee="
				+ fee + ", invalidTimeLong=" + invalidTimeLong
				+ ", totalAmount=" + totalAmount + ", feeCurrencyCode="
				+ feeCurrencyCode + ", productAmount=" + productAmount
				+ ", statusMsg=" + statusMsg + ", productName=" + productName
				+ ", currencyCode=" + currencyCode + ", productNameDesc="
				+ productNameDesc + "]";
	}
	
	
}
