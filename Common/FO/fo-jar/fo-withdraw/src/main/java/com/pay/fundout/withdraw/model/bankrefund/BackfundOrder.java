package com.pay.fundout.withdraw.model.bankrefund;

import java.io.Serializable;
import java.util.Date;

public class BackfundOrder implements Serializable {
	private String backfundOrderId;
	private String innerOrderId;
	private String outerOrderId;
	private int status;
	private Date updateTime;
	private Date createTime;
	private long amount;
	public String getBackfundOrderId() {
		return backfundOrderId;
	}
	public void setBackfundOrderId(String backfundOrderId) {
		this.backfundOrderId = backfundOrderId;
	}
	public String getInnerOrderId() {
		return innerOrderId;
	}
	public void setInnerOrderId(String innerOrderId) {
		this.innerOrderId = innerOrderId;
	}
	public String getOuterOrderId() {
		return outerOrderId;
	}
	public void setOuterOrderId(String outerOrderId) {
		this.outerOrderId = outerOrderId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
}
