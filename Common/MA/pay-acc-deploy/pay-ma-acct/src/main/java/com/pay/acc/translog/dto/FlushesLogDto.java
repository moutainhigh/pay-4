package com.pay.acc.translog.dto;

import java.io.Serializable;
import java.util.Date;

public class FlushesLogDto implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	private Long id;
	private String flushesOrderId;// 冲正交易流水号
	private String orderId;// 原交易流水号
	private Long dealCode;//
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

	public Long getDealCode() {
		return dealCode;
	}

	public void setDealCode(Long dealCode) {
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
}
