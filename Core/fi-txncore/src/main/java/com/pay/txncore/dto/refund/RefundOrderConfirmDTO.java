package com.pay.txncore.dto.refund;

import java.util.Date;

/**
 * 退款确认订单DTO
 */

public class RefundOrderConfirmDTO {
	private Long refundConfirmId;
	private Long refundOrderNo;
	private Date preStartTime;
	private Date createDate;
	private Date completeTime;
	private String status;
	private Integer conNum;
	
	public Long getRefundConfirmId() {
		return refundConfirmId;
	}
	public void setRefundConfirmId(Long refundConfirmId) {
		this.refundConfirmId = refundConfirmId;
	}
	public Long getRefundOrderNo() {
		return refundOrderNo;
	}
	public void setRefundOrderNo(Long refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}
	public Date getPreStartTime() {
		return preStartTime;
	}
	public void setPreStartTime(Date preStartTime) {
		this.preStartTime = preStartTime;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getConNum() {
		return conNum;
	}
	public void setConNum(Integer conNum) {
		this.conNum = conNum;
	}
	@Override
	public String toString() {
		return "RefundOrderConfirmDTO [refundConfirmId=" + refundConfirmId
				+ ", refundOrderNo=" + refundOrderNo 
				+ ", preStartTime=" + preStartTime
				+ ", createDate=" + createDate 
				+ ", completeTime=" + completeTime
				+ ", status=" + status
				+ ", conNum=" + conNum + "]";
	}

}