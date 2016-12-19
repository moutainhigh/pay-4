/**
 *  <p>File: RefundOnlineDTO.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.dto.fi;

import java.util.Date;

public class RefundOnlineDTO {

    
	/**
	 * 主键
	 */
	private long refundOnlineId;
    
	/**
	 * REFUND_ORDER_INFO主键
	 */
	private long refundOrderInfoId;
    
	/**
	 * PAY_ONLINE中主键
	 */
	private long payOnlineDetailId;
    
	/**
	 * 退款金额
	 */
	private long refundAmount;
    
	/**
	 * 退款类型,1:充退,2:退款
	 */
	private int refundType;
    
	/**
	 * 退款状态,0:创建,1:退款中,2:退款成功,3:退款失败
	 */
	private int status;
    
	/**
	 * 错误代码
	 */
	private String errorCode;
    
	/**
	 * 创建时间
	 */
	private Date createDate;
    
	/**
	 * 记录修改时间
	 */
	private Date updateDate;
	
	// 收入，支出标识
	private int fifo;

	public int getFifo() {
		return fifo;
	}

	public void setFifo(int fifo) {
		this.fifo = fifo;
	}

	/**
	 * @return the refundOnlineId
	 */
	public long getRefundOnlineId() {
		return refundOnlineId;
	}

	/**
	 * @param refundOnlineId the refundOnlineId to set
	 */
	public void setRefundOnlineId(long refundOnlineId) {
		this.refundOnlineId = refundOnlineId;
	}

	/**
	 * @return the refundOrderInfoId
	 */
	public long getRefundOrderInfoId() {
		return refundOrderInfoId;
	}

	/**
	 * @param refundOrderInfoId the refundOrderInfoId to set
	 */
	public void setRefundOrderInfoId(long refundOrderInfoId) {
		this.refundOrderInfoId = refundOrderInfoId;
	}
	
	/**
	 * @return the payOnlineDetailId
	 */
	public long getPayOnlineDetailId() {
		return payOnlineDetailId;
	}

	/**
	 * @param payOnlineDetailId the payOnlineDetailId to set
	 */
	public void setPayOnlineDetailId(long payOnlineDetailId) {
		this.payOnlineDetailId = payOnlineDetailId;
	}

	/**
	 * @return the refundAmount
	 */
	public long getRefundAmount() {
		return refundAmount;
	}

	/**
	 * @param refundAmount the refundAmount to set
	 */
	public void setRefundAmount(long refundAmount) {
		this.refundAmount = refundAmount;
	}

	/**
	 * @return the refundType
	 */
	public int getRefundType() {
		return refundType;
	}

	/**
	 * @param refundType the refundType to set
	 */
	public void setRefundType(int refundType) {
		this.refundType = refundType;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RefundOnline [createDate=" + createDate + ", errorCode="
				+ errorCode + ", payOnlineDetailId=" + payOnlineDetailId
				+ ", refundAmount=" + refundAmount + ", refundOnlineId="
				+ refundOnlineId + ", refundOrderInfoId=" + refundOrderInfoId
				+ ", refundType=" + refundType + ", status=" + status
				+ ", updateDate=" + updateDate + "]";
	}
}
