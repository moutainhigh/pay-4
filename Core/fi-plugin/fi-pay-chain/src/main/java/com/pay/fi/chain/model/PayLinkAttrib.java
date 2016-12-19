/**
 * 
 */
package com.pay.fi.chain.model;

/**
 * 支付链商品属性
 * @author PengJiangbo
 *
 */
public class PayLinkAttrib {
	
	/** 属性ID */
	private Long attribId ;
	/** 支付链ID[关联pay_link] */
	private Long payLinkNo ;
	/** 商品名称 */
	private String productName ;
	/** 商品规格 */
	private String productSpec ;
	/** 商品数量 */
	private Long productNum ;
	/** 商品价格 */
	private Long price ;
	/** 交易币种 */
	private String currencyCode ;
	/** 商品展示网址 */
	private String site ;
	/** 商品总金额 */
	//private Long productAmount ;
	
	/** 克隆页面展示，不作他用 */
	private String currencyCodeDes ;
	public Long getPayLinkNo() {
		return payLinkNo;
	}
	public void setPayLinkNo(Long payLinkNo) {
		this.payLinkNo = payLinkNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductSpec() {
		return productSpec;
	}
	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}
	public Long getProductNum() {
		return productNum;
	}
	public void setProductNum(Long productNum) {
		this.productNum = productNum;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	
	public Long getAttribId() {
		return attribId;
	}
	public void setAttribId(Long attribId) {
		this.attribId = attribId;
	}
	
	public String getCurrencyCodeDes() {
		return currencyCodeDes;
	}
	public void setCurrencyCodeDes(String currencyCodeDes) {
		this.currencyCodeDes = currencyCodeDes;
	}
	@Override
	public String toString() {
		return "PayLinkAttrib [attribId=" + attribId + ", payLinkNo="
				+ payLinkNo + ", productName=" + productName + ", productSpec="
				+ productSpec + ", productNum=" + productNum + ", price="
				+ price + ", currencyCode=" + currencyCode + ", site=" + site
				+ ", currencyCodeDes=" + currencyCodeDes + "]";
	}
	
}
