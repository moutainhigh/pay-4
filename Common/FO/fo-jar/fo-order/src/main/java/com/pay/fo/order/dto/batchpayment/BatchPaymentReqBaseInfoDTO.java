package com.pay.fo.order.dto.batchpayment;

import java.util.Date;
import java.util.List;

public class BatchPaymentReqBaseInfoDTO {
	/**
	 * 请求流水号
	 */
	private Long requestSeq;

	/**
	 * 请求类型
	 */
	private Integer requestType;

	/**
	 * 业务批次号
	 */
	private String businessBatchNo;

	/**
	 * 付款方名称
	 */
	private String payerName;

	/**
	 * 付款方登录标识
	 */
	private String payerLoginName;

	/**
	 * 付款方会员号
	 */
	private Long payerMemberCode;

	/**
	 * 付款方会员类型
	 */
	private Integer payerMemberType;

	/**
	 * 付款方账号
	 */
	private String payerAcctCode;

	/**
	 * 付款方账号类型
	 */
	private Integer payerAcctType;

	/**
	 * 创建者
	 */
	private String creator;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 更新日期
	 */
	private Date updateDate;

	/**
	 * 审核状态
	 */
	private Integer status;

	/**
	 * 审核员
	 */
	private String auditor;

	/**
	 * 请求付款金额
	 */
	private Long requestAmount;

	/**
	 * 请求付款次数
	 */
	private Integer requestCount;

	/**
	 * 有效金额
	 */
	private Long validAmount;

	/**
	 * 有效笔数
	 */
	private Integer validCount;

	/**
	 * 是否是付款方付手续费
	 */
	private Integer isPayerPayFee;

	/**
	 * 手续费
	 */
	private Long fee;

	/**
	 * 总手续费
	 */
	private Long totalFee;

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

	/**
	 * 请求来源
	 */
	private String requestSrc;

	/**
	 * 审核备注
	 */
	private String auditRemark;

	/**
	 * 请求明细信息
	 */
	private List<RequestDetail> requestDetails;

	/**
	 * 错误信息
	 */
	private String errorMsg;
	/**
	 * 处理类型 0 默认 1 定期
	 */
	private Integer processType;
	/**
	 * 执行时间
	 */
	private Date excuteDate;
	
	private String payerCurrencyCode;
	
	public Long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
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
	
	private Integer payeeAcctType ;
	
	private String payeeCurrencyCode ;
	
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
	 * @return the requestType
	 */
	public Integer getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType
	 *            the requestType to set
	 */
	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the businessBatchNo
	 */
	public String getBusinessBatchNo() {
		return businessBatchNo;
	}

	/**
	 * @param businessBatchNo
	 *            the businessBatchNo to set
	 */
	public void setBusinessBatchNo(String businessBatchNo) {
		this.businessBatchNo = businessBatchNo;
	}

	/**
	 * @return the payerName
	 */
	public String getPayerName() {
		return payerName;
	}

	/**
	 * @param payerName
	 *            the payerName to set
	 */
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	/**
	 * @return the payerLoginName
	 */
	public String getPayerLoginName() {
		return payerLoginName;
	}

	/**
	 * @param payerLoginName
	 *            the payerLoginName to set
	 */
	public void setPayerLoginName(String payerLoginName) {
		this.payerLoginName = payerLoginName;
	}

	/**
	 * @return the payerMemberCode
	 */
	public Long getPayerMemberCode() {
		return payerMemberCode;
	}

	/**
	 * @param payerMemberCode
	 *            the payerMemberCode to set
	 */
	public void setPayerMemberCode(Long payerMemberCode) {
		this.payerMemberCode = payerMemberCode;
	}

	/**
	 * @return the payerMemberType
	 */
	public Integer getPayerMemberType() {
		return payerMemberType;
	}

	/**
	 * @param payerMemberType
	 *            the payerMemberType to set
	 */
	public void setPayerMemberType(Integer payerMemberType) {
		this.payerMemberType = payerMemberType;
	}

	/**
	 * @return the payerAcctCode
	 */
	public String getPayerAcctCode() {
		return payerAcctCode;
	}

	/**
	 * @param payerAcctCode
	 *            the payerAcctCode to set
	 */
	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}

	/**
	 * @return the payerAcctType
	 */
	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	/**
	 * @param payerAcctType
	 *            the payerAcctType to set
	 */
	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
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
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the auditor
	 */
	public String getAuditor() {
		return auditor;
	}

	/**
	 * @param auditor
	 *            the auditor to set
	 */
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	/**
	 * @return the requestAmount
	 */
	public Long getRequestAmount() {
		return requestAmount;
	}

	/**
	 * @param requestAmount
	 *            the requestAmount to set
	 */
	public void setRequestAmount(Long requestAmount) {
		this.requestAmount = requestAmount;
	}

	/**
	 * @return the requestCount
	 */
	public Integer getRequestCount() {
		return requestCount;
	}

	/**
	 * @param requestCount
	 *            the requestCount to set
	 */
	public void setRequestCount(Integer requestCount) {
		this.requestCount = requestCount;
	}

	/**
	 * @return the validAmount
	 */
	public Long getValidAmount() {
		return validAmount;
	}

	/**
	 * @param validAmount
	 *            the validAmount to set
	 */
	public void setValidAmount(Long validAmount) {
		this.validAmount = validAmount;
	}

	/**
	 * @return the validCount
	 */
	public Integer getValidCount() {
		return validCount;
	}

	/**
	 * @param validCount
	 *            the validCount to set
	 */
	public void setValidCount(Integer validCount) {
		this.validCount = validCount;
	}

	/**
	 * @return the isPayerPayFee
	 */
	public Integer getIsPayerPayFee() {
		return isPayerPayFee;
	}

	/**
	 * @param isPayerPayFee
	 *            the isPayerPayFee to set
	 */
	public void setIsPayerPayFee(Integer isPayerPayFee) {
		this.isPayerPayFee = isPayerPayFee;
	}

	/**
	 * @return the fee
	 */
	public Long getFee() {
		return fee;
	}

	/**
	 * @param fee
	 *            the fee to set
	 */
	public void setFee(Long fee) {
		this.fee = fee;
	}

	/**
	 * @return the realpayAmount
	 */
	public Long getRealpayAmount() {
		return realpayAmount;
	}

	/**
	 * @param realpayAmount
	 *            the realpayAmount to set
	 */
	public void setRealpayAmount(Long realpayAmount) {
		this.realpayAmount = realpayAmount;
	}

	/**
	 * @return the realoutAmount
	 */
	public Long getRealoutAmount() {
		return realoutAmount;
	}

	/**
	 * @param realoutAmount
	 *            the realoutAmount to set
	 */
	public void setRealoutAmount(Long realoutAmount) {
		this.realoutAmount = realoutAmount;
	}

	/**
	 * @return the requestSrc
	 */
	public String getRequestSrc() {
		return requestSrc;
	}

	/**
	 * @param requestSrc
	 *            the requestSrc to set
	 */
	public void setRequestSrc(String requestSrc) {
		this.requestSrc = requestSrc;
	}

	/**
	 * @return the auditRemark
	 */
	public String getAuditRemark() {
		return auditRemark;
	}

	/**
	 * @param auditRemark
	 *            the auditRemark to set
	 */
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	/**
	 * @return the requestDetails
	 */
	public List<RequestDetail> getRequestDetails() {
		return requestDetails;
	}

	/**
	 * @param requestDetails
	 *            the requestDetails to set
	 */
	public void setRequestDetails(List<RequestDetail> requestDetails) {
		this.requestDetails = requestDetails;
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

	public Integer getProcessType() {
		return processType;
	}

	public void setProcessType(Integer processType) {
		this.processType = processType;
	}

	public Date getExcuteDate() {
		return excuteDate;
	}

	public void setExcuteDate(Date excuteDate) {
		this.excuteDate = excuteDate;
	}
	
	public String getPayeeCurrencyCode() {
		return payeeCurrencyCode;
	}

	public void setPayeeCurrencyCode(String payeeCurrencyCode) {
		this.payeeCurrencyCode = payeeCurrencyCode;
	}

	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public String getPayerCurrencyCode() {
		return payerCurrencyCode;
	}

	public void setPayerCurrencyCode(String payerCurrencyCode) {
		this.payerCurrencyCode = payerCurrencyCode;
	}

	@Override
	public String toString() {
		return "BatchPaymentReqBaseInfoDTO [requestSeq=" + requestSeq
				+ ", requestType=" + requestType + ", businessBatchNo="
				+ businessBatchNo + ", payerName=" + payerName
				+ ", payerLoginName=" + payerLoginName + ", payerMemberCode="
				+ payerMemberCode + ", payerMemberType=" + payerMemberType
				+ ", payerAcctCode=" + payerAcctCode + ", payerAcctType="
				+ payerAcctType + ", creator=" + creator + ", createDate="
				+ createDate + ", updateDate=" + updateDate + ", status="
				+ status + ", auditor=" + auditor + ", requestAmount="
				+ requestAmount + ", requestCount=" + requestCount
				+ ", validAmount=" + validAmount + ", validCount=" + validCount
				+ ", isPayerPayFee=" + isPayerPayFee + ", fee=" + fee
				+ ", totalFee=" + totalFee + ", payeeFee=" + payeeFee
				+ ", payerFee=" + payerFee + ", realpayAmount=" + realpayAmount
				+ ", realoutAmount=" + realoutAmount + ", requestSrc="
				+ requestSrc + ", auditRemark=" + auditRemark
				+ ", requestDetails=" + requestDetails + ", errorMsg="
				+ errorMsg + ", processType=" + processType + ", excuteDate="
				+ excuteDate + ", payerCurrencyCode=" + payerCurrencyCode
				+ ", payeeAcctType=" + payeeAcctType + ", payeeCurrencyCode="
				+ payeeCurrencyCode + "]";
	}
	

}