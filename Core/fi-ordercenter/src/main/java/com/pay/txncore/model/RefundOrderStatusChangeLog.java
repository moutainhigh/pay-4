package com.pay.txncore.model;


public class RefundOrderStatusChangeLog implements java.io.Serializable {
	
	private Long refundStatusChangeNo;

	private Long refundOrderNo;

	private Long oldStatus;

	private Long newStatus;

	private String action;

	private Long tradeRefundDelta;

	private Long settlementAmountDelta;

	private java.util.Date createDate;

	private Long settlementModifyFlg;		//0 表示没有修改， 1 表示改过 SettlementFlg 2表示改过 AssureSettlentFlg 4=表示改过Amount  8 表示改过Date		
	
	private Long oldSettlementFlg;
	private Long newSettlementFlg;
	private Long oldAssureSettlementFlg;
	private Long newAssureSettlementFlg;
	private java.util.Date oldSettlementDate;
	private java.util.Date newSettlementDate;
	
	public Long getSettlementModifyFlg(){
		return settlementModifyFlg;
	}
	public void setSettlementModifyFlg(Long settlementModifyFlg){
		this.settlementModifyFlg = settlementModifyFlg;
	}
	
	public Long getOldSettlementFlg(){
		return oldSettlementFlg;
	}
	
	public void setOldSettlementFlg(Long oldSettlementFlg){
		this.oldSettlementFlg = oldSettlementFlg;
	}
	
	public Long getNewSettlementFlg(){
		return newSettlementFlg;
	}
	
	public void setNewSettlementFlg(Long newSettlementFlg){
		this.newSettlementFlg = newSettlementFlg;
	}
	
	public Long getOldAssureSettlementFlg(){
		return oldAssureSettlementFlg;
	}
	
	public void setOldAssureSettlementFlg(Long oldAssureSettlementFlg){
		this.oldAssureSettlementFlg = oldAssureSettlementFlg;
	}
	
	public Long getNewAssureSettlementFlg(){
		return newAssureSettlementFlg;
	}
	
	public void setNewAssureSettlementFlg(Long newAssureSettlementFlg){
		this.newAssureSettlementFlg = newAssureSettlementFlg;
	}
	
	public java.util.Date getOldSettlementDate() {
		return oldSettlementDate;
	}
	
	public void setOldSettlementDate(java.util.Date oldSettlementDate) {
		this.oldSettlementDate = oldSettlementDate;
	}
	
	public java.util.Date getNewSettlementDate() {
		return newSettlementDate;
	}
	
	public void setNewSettlementDate(java.util.Date newSettlementDate) {
		this.newSettlementDate = newSettlementDate;
	}		
	
	public Long getStatusChangeNo() {
		return refundStatusChangeNo;
	}

	public void setStatusChangeNo(Long refundStatusChangeNo) {
		this.refundStatusChangeNo = refundStatusChangeNo;
	}

	public Long getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(Long refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public Long getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Long oldStatus) {
		this.oldStatus = oldStatus;
	}

	public Long getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(Long newStatus) {
		this.newStatus = newStatus;
	}

	public Long getTradeRefundDelta() {
		return tradeRefundDelta;
	}

	public void setTradeRefundDelta(Long tradeRefundDelta) {
		this.tradeRefundDelta = tradeRefundDelta;
	}

	public Long getSettlementAmountDelta() {
		return settlementAmountDelta;
	}

	public void setSettlementAmountDelta(Long settlementAmountDelta) {
		this.settlementAmountDelta = settlementAmountDelta;
	}


	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}



	public String toString() {
			return "RefundOrderStatusChangeLog [refundOrderNo=" + refundOrderNo
					+ ", oldStatus=" + oldStatus
					+ ", newStatus=" + newStatus 
					+ ", action=" + action 
					+ ", tradeRefundDelta="+ tradeRefundDelta 
					+ ", settlementAmountDelta=" + settlementAmountDelta 
					+ ", createDate=" + createDate 
					+ ", settlementModifyFlg=" + settlementModifyFlg 
					+ ", oldSettlementFlg =" + oldSettlementFlg 
					+ ", newSettlementFlg =" + newSettlementFlg
					+ ", oldAssureSettlementFlg =" + oldAssureSettlementFlg 
					+ ", newAssureSettlementFlg = "+ newAssureSettlementFlg
					+ ", oldSettlementDate =" + oldSettlementDate
					+ ", newSettlementDate =" + newSettlementDate 				
					+"]";
		}
}
