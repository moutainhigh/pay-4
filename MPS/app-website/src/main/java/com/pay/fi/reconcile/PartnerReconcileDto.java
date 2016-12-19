package com.pay.fi.reconcile;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Bobby Guo
 * @date 2016/1/21
 */
public class PartnerReconcileDto implements Serializable {

	private static final long serialVersionUID = -9107364339502397521L;
	
	public static String[] columnHeader = {"日期","交易流水号", "商户订单号", "明细类型", "交易币种",
		"交易金额","汇率","清算金额","交易手续费","单笔处理费","授信服务费","入账币种","保证金账户入账金额","基本账户入账金额"};

	public static String[] properties = {"reconcileDate","tradeOrderNo", "orderId",
		"transType", "transCurrencyCode", "transAmount","rate","settlementAmount","fee"
		,"fixedFee","charge","currencyCode","assureAmount","amount"};


	/** 交易流水号 **/
	private String tradeOrderNo;
	/** 商户订单号 **/
	private String orderId;
	/** 交易类型 **/
	private String transType;
	/** 交易币种 **/
	private String transCurrencyCode;
	/** 交易金额 **/
	private String transAmount;
	/** 汇率 **/
	private String rate;
	/** 交易手续费 **/
	private String fee;
	/** 单笔处理费 **/
	private String fixedFee;
	/** 退款手续费 **/
	private String refundFee;
	/** 保证金 **/
	private String assureAmount;
	/** 入账币种 **/
	private String currencyCode;
	/** 入账金额 **/
	private String amount;
	/** 日期 **/
	private String reconcileDate;
	/** 清算金额 **/
	private String settlementAmount;
	/** 清算金额 **/
	private String charge;
	
	public String getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransCurrencyCode() {
		return transCurrencyCode;
	}

	public void setTransCurrencyCode(String transCurrencyCode) {
		this.transCurrencyCode = transCurrencyCode;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getFixedFee() {
		return fixedFee;
	}

	public void setFixedFee(String fixedFee) {
		this.fixedFee = fixedFee;
	}

	public String getAssureAmount() {
		return assureAmount;
	}

	public void setAssureAmount(String assureAmount) {
		this.assureAmount = assureAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReconcileDate() {
		return reconcileDate;
	}

	public void setReconcileDate(String reconcileDate) {
		this.reconcileDate = reconcileDate;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

}
