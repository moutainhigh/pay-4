package com.pay.acc.translog.model;

import java.util.Date;

public class FlushesLog {
	private Long id;
	private String flushesOrderId;// 冲正交易流水号
	private String orderId;// 原交易流水号
	private Integer dealCode;//
	private Integer dealType;// 交易类型
	private Long amount;
	private Integer status;// 状态
	private Date createDate;
	private Date updateDate;// 更新时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFlushesOrderId() {
		return flushesOrderId;
	}

	public void setFlushesOrderId(String flushesOrderId) {
		this.flushesOrderId = flushesOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getDealCode() {
		return dealCode;
	}

	public void setDealCode(Integer dealCode) {
		this.dealCode = dealCode;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getDealType() {
		return dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}
}
