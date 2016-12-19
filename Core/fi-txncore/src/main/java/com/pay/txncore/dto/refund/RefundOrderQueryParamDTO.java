/**
 * 
 */
package com.pay.txncore.dto.refund;

/**
 * 单笔退款订单查询DTO
 * @author huhb
 *
 */
public class RefundOrderQueryParamDTO {
	
	private String fromTime;
	
	private String toTime;
	
	private int status;
	
	private String orderId;
	
	private String partnerId;
	
	private int page;
	
	private int pageSize;
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "RefundOrderQueryParamDTO [fromTime=" + fromTime + ", orderId="
				+ orderId + ", page=" + page + ", partnerId=" + partnerId
				+ ", status=" + status + ", toTime=" + toTime + "]";
	}

}
