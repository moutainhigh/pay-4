/**
 * 
 */
package com.pay.txncore.crosspay.model;

import java.util.Date;

/**
 * @author chaoyue
 * 
 */
public class PartnerClearCycle {

	private Long id;
	private Long partnerId;
	private Date nextClearDate;// NEXT_CLEAR_DATE
	private Long withdrawableAmount;// WITHDRAWABLE_AMOUNT
	private Integer status;
	private String remark;
	private Date createDate;
	private Date updateDate;
	private String extInfo1;// EXT_INFO_1
	private String extInfo2;// EXT_INFO_2

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Date getNextClearDate() {
		return nextClearDate;
	}

	public void setNextClearDate(Date nextClearDate) {
		this.nextClearDate = nextClearDate;
	}

	public Long getWithdrawableAmount() {
		return withdrawableAmount;
	}

	public void setWithdrawableAmount(Long withdrawableAmount) {
		this.withdrawableAmount = withdrawableAmount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getExtInfo1() {
		return extInfo1;
	}

	public void setExtInfo1(String extInfo1) {
		this.extInfo1 = extInfo1;
	}

	public String getExtInfo2() {
		return extInfo2;
	}

	public void setExtInfo2(String extInfo2) {
		this.extInfo2 = extInfo2;
	}

}
