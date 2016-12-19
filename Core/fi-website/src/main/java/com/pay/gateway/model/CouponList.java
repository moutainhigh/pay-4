/**
 * 
 */
package com.pay.gateway.model;

import java.util.Date;

/**
 * @author chaoyue
 *
 */
public class CouponList {

	private Long id;
	private String couponNumber;
	private Date effectDate;
	private Date expireDate;// EXPIRE_DATE
	private Date createDate;
	private Long value;
	private Long minOrderAmount;
	private Integer status;
	private String scene;
	private String orgCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public Long getMinOrderAmount() {
		return minOrderAmount;
	}

	public void setMinOrderAmount(Long minOrderAmount) {
		this.minOrderAmount = minOrderAmount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}
