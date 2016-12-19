
package com.pay.pe.model;

import java.sql.Timestamp;



public class AccountingExceptionLog {

	private Long sequenceId;
	
	// 0-Other、1-Order、2-Deal、3-Accounting
	private Integer type;
	
	private String dealId;
	
	private String orderId;
	
	private String message;
	
	private Timestamp creationDate;

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Integer getExceptionType() {
		return type;
	}

	public void setExceptionType(Integer exceptionType) {
		this.type = exceptionType;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getCreationDtae() {
		return creationDate;
	}

	public void setCreationDtae(Timestamp creationDtae) {
		this.creationDate = creationDtae;
	}
}
