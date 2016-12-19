package com.pay.fo.order.dto.batchpayment;

import java.util.Date;

public class BatchPaymentToAcctReqDetailDTO extends RequestDetail {

	/**
	 * 明细流水号
	 */
	private Long detailSeq;
	/**
	 * 请求流水号
	 */
	private Long requestSeq;
	/**
	 * 商户流水号
	 */
	private String foreignOrderId;
	/**
	 * 收款方账户（收款方登录标识）
	 */
	private String PayeeLoginName;
	/**
	 * 收款方名称
	 */
	private String payeeName;
	/**
	 * 请求付款金额
	 */
	private String requestAmount;
	/**
	 * 付款金额
	 */
	private Long paymentAmount;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 验证状态
	 */
	private Integer validateStatus;
	/**
	 * 验证错误信息
	 */
	private String errorMsg;
	/**
	 * 是否已生成订单
	 */
	private Integer orderStatus;

	/**
	 * 订单状态描述
	 */
	private String orderStatusDesc;

	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 更新日期
	 */
	private Date updateDate;

	/**
	 * 收款方手续费
	 */
	private Long payeeFee;

	/**
	 * 付款方手续费
	 */
	private Long payerFee;

	/**
	 * 实际付款金额
	 */
	private Long realpayAmount;

	/**
	 * 实际出款金额
	 */
	private Long realoutAmount;

	public Long getRealpayAmount() {
		return realpayAmount;
	}

	public void setRealpayAmount(Long realpayAmount) {
		this.realpayAmount = realpayAmount;
	}

	public Long getRealoutAmount() {
		return realoutAmount;
	}

	public void setRealoutAmount(Long realoutAmount) {
		this.realoutAmount = realoutAmount;
	}

	public Long getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(Long payeeFee) {
		this.payeeFee = payeeFee;
	}

	public Long getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Long payerFee) {
		this.payerFee = payerFee;
	}

	/**
	 * @return the detailSeq
	 */
	public Long getDetailSeq() {
		return detailSeq;
	}

	/**
	 * @param detailSeq
	 *            the detailSeq to set
	 */
	public void setDetailSeq(Long detailSeq) {
		this.detailSeq = detailSeq;
	}

	/**
	 * @return the requestSeq
	 */
	public Long getRequestSeq() {
		return requestSeq;
	}

	/**
	 * @param requestSeq
	 *            the requestSeq to set
	 */
	public void setRequestSeq(Long requestSeq) {
		this.requestSeq = requestSeq;
	}

	/**
	 * @return the foreignOrderId
	 */
	public String getForeignOrderId() {
		return foreignOrderId;
	}

	/**
	 * @param foreignOrderId
	 *            the foreignOrderId to set
	 */
	public void setForeignOrderId(String foreignOrderId) {
		this.foreignOrderId = foreignOrderId;
	}

	/**
	 * @return the payeeLoginName
	 */
	public String getPayeeLoginName() {
		return PayeeLoginName;
	}

	/**
	 * @param payeeLoginName
	 *            the payeeLoginName to set
	 */
	public void setPayeeLoginName(String payeeLoginName) {
		PayeeLoginName = payeeLoginName;
	}

	/**
	 * @return the payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}

	/**
	 * @param payeeName
	 *            the payeeName to set
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	/**
	 * @return the requestAmount
	 */
	public String getRequestAmount() {
		return requestAmount;
	}

	/**
	 * @param requestAmount
	 *            the requestAmount to set
	 */
	public void setRequestAmount(String requestAmount) {
		this.requestAmount = requestAmount;
	}

	/**
	 * @return the paymentAmount
	 */
	public Long getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @param paymentAmount
	 *            the paymentAmount to set
	 */
	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the validateStatus
	 */
	public Integer getValidateStatus() {
		return validateStatus;
	}

	/**
	 * @param validateStatus
	 *            the validateStatus to set
	 */
	public void setValidateStatus(Integer validateStatus) {
		this.validateStatus = validateStatus;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg
	 *            the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @return the orderStatus
	 */
	public Integer getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus
	 *            the orderStatus to set
	 */
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
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
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the orderStatusDesc
	 */
	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}

	/**
	 * @param orderStatusDesc
	 *            the orderStatusDesc to set
	 */
	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}

}