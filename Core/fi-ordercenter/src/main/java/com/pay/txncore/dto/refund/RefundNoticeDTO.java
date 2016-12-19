package com.pay.txncore.dto.refund;

public class RefundNoticeDTO {
	
	/**
	 * 退款流水订单号
	 */
	private Long refundOrderNo;
	
	/**
	 * 退款金额
	 */
	private Long refundAmount;
	
	
	/**
	 * 手续费
	 */
	private Long fee;
	
	/**
	 * 退款状态
	 * 	1：成功
	 *  0：失败
	 */
	private int status;
	
	
	private String resultCode;
	
	/**
	 * 充退订单流水
	 */
	private Long depositBackNo;
	
	
	
	
	public Long getDepositBackNo() {
		return depositBackNo;
	}

	public void setDepositBackNo(Long depositBackNo) {
		this.depositBackNo = depositBackNo;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public Long getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(Long refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RefundNoticeDTO [depositBackNo=" + depositBackNo + ", fee="
				+ fee + ", refundAmount=" + refundAmount + ", refundOrderNo="
				+ refundOrderNo + ", resultCode=" + resultCode + ", status="
				+ status + "]";
	}
	
	
	

}
