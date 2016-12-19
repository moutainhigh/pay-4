package com.pay.fundout.withdraw.dto.bsp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BspWithdrawAuditResultDTO implements Serializable {

	private static final long serialVersionUID = 3627919861639528302L;

	private String orderSeq; // 交易流水号
	private String orderType; // 业务类型
	private Date createDate; // 创建时间
	private String memberName; // 交易商名称
	private BigDecimal amount; // 金额
	private String status; // 状态

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
