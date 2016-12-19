/**
 * 
 */
package com.pay.fo.order.model.batchpayment;

/**
 * @author NEW
 *
 */
public class BatchPaymentTotalInfo {
	
	/**
	 * 批量订单号
	 */
	private Long parentOrderId;
	/**
	 * 失败的笔数
	 */
	private Integer failCount;

	/**
	 * 成功的笔数
	 */
	private Integer successCount;
	
	/**
	 * 失败的金额
	 */
	private Long failAmount;
	
	/**
	 * 成功的金额
	 */
	private Long successAmount;
	
	/**
	 * 成功的手续费
	 */
	private Long successFee;

	/**
	 * @return the parentOrderId
	 */
	public Long getParentOrderId() {
		return parentOrderId;
	}

	/**
	 * @param parentOrderId the parentOrderId to set
	 */
	public void setParentOrderId(Long parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

	/**
	 * @return the failCount
	 */
	public Integer getFailCount() {
		return failCount;
	}

	/**
	 * @param failCount the failCount to set
	 */
	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	/**
	 * @return the successCount
	 */
	public Integer getSuccessCount() {
		return successCount;
	}

	/**
	 * @param successCount the successCount to set
	 */
	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	/**
	 * @return the failAmount
	 */
	public Long getFailAmount() {
		return failAmount;
	}

	/**
	 * @param failAmount the failAmount to set
	 */
	public void setFailAmount(Long failAmount) {
		this.failAmount = failAmount;
	}

	/**
	 * @return the successAmount
	 */
	public Long getSuccessAmount() {
		return successAmount;
	}

	/**
	 * @param successAmount the successAmount to set
	 */
	public void setSuccessAmount(Long successAmount) {
		this.successAmount = successAmount;
	}

	/**
	 * @return the successFee
	 */
	public Long getSuccessFee() {
		return successFee;
	}

	/**
	 * @param successFee the successFee to set
	 */
	public void setSuccessFee(Long successFee) {
		this.successFee = successFee;
	}
	
	
}
