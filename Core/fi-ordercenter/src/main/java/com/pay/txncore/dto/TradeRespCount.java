package com.pay.txncore.dto;


/**
 * 交易返回消息统计
 * @author peiyu.yang
 *
 */
public class TradeRespCount {
	
	/**
	 * 数量
	 */
	private int total=0;
	
	/**
	 * 会员号
	 */
	private String partnerId;
	
	/**
	 * 返回编码
	 */
	private String respCode;
	
	/**
	 * 返回消息
	 */
	private String respMsg;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	@Override
	public String toString() {
		return "TradeRespCount [total=" + total + ", partnerId=" + partnerId
				+ ", respCode=" + respCode + ", respMsg=" + respMsg + "]";
	}
}
