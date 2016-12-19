package com.pay.txncore.model;

public class OrderQueryParam {
	
	private String startTime;
	private String endTime;
	private String status;
	private String orderId;
	private String partnerId;
	private int page;
	private int pageSize;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "OrderQueryParam [endTime=" + endTime + ", orderId=" + orderId
				+ ", page=" + page + ", pageSize=" + pageSize + ", partnerId="
				+ partnerId + ", startTime=" + startTime + ", status=" + status
				+ "]";
	}
}
