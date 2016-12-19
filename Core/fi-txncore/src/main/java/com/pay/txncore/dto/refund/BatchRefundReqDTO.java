package com.pay.txncore.dto.refund;

import java.util.Date;
import java.util.List;

public class BatchRefundReqDTO {
	/**
	 * 批次流水
	 */
	private Long batchRefundNo;
	
	/**
	 * 商户ID
	 */
	private String partnerId;
	
	/**
	 * 申请操作员
	 */
	private String applyOperator;
	
	/**
	 * 复核操作员
	 */
	private String auditOperator;
	
	/**
	 * 申请总笔数
	 */
	private Long total;
	
	/**
	 * 申请总金额
	 */
	private String totalAmount;
	
	/**
	 * 申请时间
	 */
	private Date applyDate;
	
	/**
	 * 错误消息
	 */
	private String errorMsg;
	
	/**
	 * 是否验证通过
	 */
	private boolean isValid = true;
	
	/**
	 * 上传文件流水
	 */
	private Long fileNo; 
	
	private String status;
	
	private String actionStatus;
	
	private String fileName;
	
	private Date auditDate;
	
	/**
	 * 请求明细信息
	 */
	private List<BatchReqDetailDTO> requestDetails;

	
	public int getItemsCount(){
		return getRequestDetails().size();
	}
	
	public int getErrorCount(){
		int errorCount = 0 ;
		for(BatchReqDetailDTO detail:getRequestDetails()){
			if(!detail.isValid())
				errorCount ++;
		}
		return errorCount;
	}
	
	public int getSuccessCount(){
		int successCount = 0 ;
		for(BatchReqDetailDTO detail:getRequestDetails()){
			if(detail.isValid())
				successCount ++;
		}
		return successCount;
	}
	
	public Long getOrderTotalAmount(){
		Long amount = 0L;
		for(BatchReqDetailDTO detail:getRequestDetails()){
			if(detail.getRefundAmount() != null){
				amount += detail.getRefundAmount() ;
			}
		}
		return amount;
	}
	
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getBatchRefundNo() {
		return batchRefundNo;
	}

	public void setBatchRefundNo(Long batchRefundNo) {
		this.batchRefundNo = batchRefundNo;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getApplyOperator() {
		return applyOperator;
	}

	public void setApplyOperator(String applyOperator) {
		this.applyOperator = applyOperator;
	}

	public String getAuditOperator() {
		return auditOperator;
	}

	public void setAuditOperator(String auditOperator) {
		this.auditOperator = auditOperator;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public List<BatchReqDetailDTO> getRequestDetails() {
		return requestDetails;
	}

	public void setRequestDetails(List<BatchReqDetailDTO> requestDetails) {
		this.requestDetails = requestDetails;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public Long getFileNo() {
		return fileNo;
	}

	public void setFileNo(Long fileNo) {
		this.fileNo = fileNo;
	}
	
	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	@Override
	public String toString() {
		return "BatchRefundReqDTO [actionStatus=" + actionStatus
				+ ", applyDate=" + applyDate + ", applyOperator="
				+ applyOperator + ", auditDate=" + auditDate
				+ ", auditOperator=" + auditOperator + ", batchRefundNo="
				+ batchRefundNo + ", errorMsg=" + errorMsg + ", fileName="
				+ fileName + ", fileNo=" + fileNo + ", isValid=" + isValid
				+ ", partnerId=" + partnerId + ", requestDetails="
				+ requestDetails + ", status=" + status + ", total=" + total
				+ ", totalAmount=" + totalAmount + "]";
	}

}
