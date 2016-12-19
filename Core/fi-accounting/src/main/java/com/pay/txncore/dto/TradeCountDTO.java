package com.pay.txncore.dto;


/**
 * 交易统计
 * @author peiyu.yang
 *
 */
public class TradeCountDTO {
	
	/**
	 * 交易总数
	 */
	private int total=0;
	
	/**
	 * 成功数
	 * status=4
	 */
	private int sucAmount=0;
	
	/**
	 * 失败笔数
	 * status=5
	 */
	private int failAmount=0;
	
	/**
	 * 支付中笔数
	 * status=20
	 */
	private int payAmount=0;
	
	/**
	 * 已退款笔数
	 * status=3
	 */
	private int refundAmount=0;
	
	/**
	 * 未付款
	 * status 为 0
	 */
	private int unpayAmount=0;
	
	/**
	 * 会员号
	 */
	private String partnerId;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getSucAmount() {
		return sucAmount;
	}

	public void setSucAmount(int sucAmount) {
		this.sucAmount = sucAmount;
	}

	public int getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(int failAmount) {
		this.failAmount = failAmount;
	}

	public int getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}

	public int getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(int refundAmount) {
		this.refundAmount = refundAmount;
	}

	public int getUnpayAmount() {
		return unpayAmount;
	}

	public void setUnpayAmount(int unpayAmount) {
		this.unpayAmount = unpayAmount;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	@Override
	public String toString() {
		return "TradeCountDTO [total=" + total + ", sucAmount=" + sucAmount
				+ ", failAmount=" + failAmount + ", payAmount=" + payAmount
				+ ", refundAmount=" + refundAmount + ", unpayAmount="
				+ unpayAmount + ", partnerId=" + partnerId + "]";
	}
}
