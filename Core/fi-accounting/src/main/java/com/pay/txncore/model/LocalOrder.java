/**
 * LocalOrderDTO.java 
 * 集团支付
 */
package com.pay.txncore.model;

import java.util.Date;

/**
 * 本地订单对象，用于转换为查询参数使用
 * latest modified time : 2011-8-24 下午4:57:16
 * @author bigknife
 */
public class LocalOrder {
	/**
	 * 送往银行的协议号
	 */
	private String depositProtocolNo;
	/**
	 * 银行生成的订单号
	 */
	private String bankOrderId;
	/**
	 * 订单时间
	 */
	private Date transDate;
	
	/**
	 * @return the depositProtocolNo
	 */
	public String getDepositProtocolNo() {
		return depositProtocolNo;
	}
	/**
	 * @param depositProtocolNo the depositProtocolNo to set
	 */
	public void setDepositProtocolNo(String depositProtocolNo) {
		this.depositProtocolNo = depositProtocolNo;
	}
	/**
	 * @return the bankOrderId
	 */
	public String getBankOrderId() {
		return bankOrderId;
	}
	
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	/**
	 * @param bankOrderId the bankOrderId to set
	 */
	public void setBankOrderId(String bankOrderId) {
		this.bankOrderId = bankOrderId;
	}
	
	
	
}
