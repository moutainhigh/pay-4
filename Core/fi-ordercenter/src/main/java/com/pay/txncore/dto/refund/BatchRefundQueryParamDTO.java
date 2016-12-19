package com.pay.txncore.dto.refund;

public class BatchRefundQueryParamDTO {
	
	private String startTime;
	
	private String endTime;
	
	private int status;
	
	private String batchNo;
	
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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
		return "BatchRefundQueryParamDTO [batchNo=" + batchNo + ", endTime="
				+ endTime + ", page=" + page + ", pageSize=" + pageSize
				+ ", partnerId=" + partnerId + ", startTime=" + startTime
				+ ", status=" + status + "]";
	}
}
