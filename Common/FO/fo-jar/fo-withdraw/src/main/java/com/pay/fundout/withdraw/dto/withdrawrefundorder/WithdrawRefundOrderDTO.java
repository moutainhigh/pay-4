/**
 *
 * jack.liu_liu
 *2010.9.18
 **/
package com.pay.fundout.withdraw.dto.withdrawrefundorder;

import java.util.Date;

import com.pay.poss.dto.withdraw.orderhandlermanage.BaseOrderDTO;

/**
 * withdraw_refund_order
 */

public class WithdrawRefundOrderDTO extends BaseOrderDTO implements java.io.Serializable {

	private String payerAcctCode;
	private java.util.Date creationDate;
	private Integer status;
	private String payeeAcctCode;
	private Long withdrawOrderId;
	private Long sequenceId;
	private String comments;
	private Date updateDate;

	public String getPayerAcctCode() {
		return payerAcctCode;
	}

	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}

	public java.util.Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	/* 吕宏修改:统一用父类订单状态 */
	public Integer getStatus() {
		return getInnerStatus();
	}

	public void setStatus(Integer status) {
		setInnerStatus(status);
	}

	public String getPayeeAcctCode() {
		return payeeAcctCode;
	}

	public void setPayeeAcctCode(String payeeAcctCode) {
		this.payeeAcctCode = payeeAcctCode;
	}

	public Long getWithdrawOrderId() {
		return withdrawOrderId;
	}

	public void setWithdrawOrderId(Long withdrawOrderId) {
		this.withdrawOrderId = withdrawOrderId;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}