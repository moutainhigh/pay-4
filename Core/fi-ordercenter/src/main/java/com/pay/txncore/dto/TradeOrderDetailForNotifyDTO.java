package com.pay.txncore.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 网关订单详情，邮件通知 
 * @author Davis.guo
 * @date 2016-09-03
 */
public class TradeOrderDetailForNotifyDTO implements Serializable{

	private static final long serialVersionUID = 1525321148234352426L;
	/**网站**/
	private String extOrderInfo;
	/**商户订单**/
	private String orderId;
	/**网关订单**/
	private String tradeOrderNo;
	/**交易时间**/
	private String createDate;
	/**交易状态信息**/
	private String statusMsg;
	/**交易金额信息**/
	private String amountMsg;
	/**持卡人**/
	private String cardholderMame;
	
	public String getExtOrderInfo() {
		return extOrderInfo;
	}

	public void setExtOrderInfo(String extOrderInfo) {
		this.extOrderInfo = extOrderInfo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getAmountMsg() {
		return amountMsg;
	}

	public void setAmountMsg(String amountMsg) {
		this.amountMsg = amountMsg;
	}

	public String getCardholderMame() {
		return cardholderMame;
	}

	public void setCardholderMame(String cardholderMame) {
		this.cardholderMame = cardholderMame;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
