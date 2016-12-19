/**
 * 
 */
package com.pay.txncore.dto.refund;

import java.util.List;

/**
 * 退款查询结果DTO
 * @author huhb
 *
 */
public class RefundOrderQueryResultDTO {
	
	private int count;
	
	private String refundTotalAmount;
	
	private int pageSize;
	
	private int page;
	
	private List<RefundOrderDetailDTO>  orderDetails;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getRefundTotalAmount() {
		return refundTotalAmount;
	}

	public void setRefundTotalAmount(String refundTotalAmount) {
		this.refundTotalAmount = refundTotalAmount;
	}

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

	public List<RefundOrderDetailDTO> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<RefundOrderDetailDTO> orderDetails) {
		this.orderDetails = orderDetails;
	}

	@Override
	public String toString() {
		return "RefundOrderQueryResultDTO [count=" + count + ", orderDetails="
				+ orderDetails + ", page=" + page + ", pageSize=" + pageSize
				+ ", refundTotalAmount=" + refundTotalAmount + "]";
	}
	
}
