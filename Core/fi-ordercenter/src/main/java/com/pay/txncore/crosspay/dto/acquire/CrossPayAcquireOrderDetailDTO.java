/**
 * 
 */
package com.pay.txncore.crosspay.dto.acquire;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 在线收单订单明细内容
 * 
 * @author huhb
 *
 */
public class CrossPayAcquireOrderDetailDTO {

	/**
	 * 商户明细号
	 */
	@Size(max = 32)
	@NotNull
	private String orderID;

	/**
	 * 订单明细金额
	 */
	@Digits(integer = 18, fraction = 0)
	@Size(max = 18, min = 1)
	@NotNull
	private String orderAmount;

	/**
	 * 下单商户显示名
	 */
	@Size(max = 128)
	private String sellerName;

	/**
	 * 商品名称
	 */
	@Size(max = 256)
	private String goodsName;

	/**
	 * 商品数量
	 */
	private Long goodsCount;

	private String partnerID;

	private String serialID;

	private Long tradeBaseNo;

	private String payType;

	private String currency;

	private String directFlag;

	private Long tradeOrderNo;

	private String orderTime;

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getBorrowingMarked() {
		return borrowingMarked;
	}

	public void setBorrowingMarked(String borrowingMarked) {
		this.borrowingMarked = borrowingMarked;
	}

	private String borrowingMarked;

	public String getDirectFlag() {
		return directFlag;
	}

	public void setDirectFlag(String directFlag) {
		this.directFlag = directFlag;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPartnerID() {
		return partnerID;
	}

	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}

	public String getSerialID() {
		return serialID;
	}

	public void setSerialID(String serialID) {
		this.serialID = serialID;
	}

	public Long getTradeBaseNo() {
		return tradeBaseNo;
	}

	public void setTradeBaseNo(Long tradeBaseNo) {
		this.tradeBaseNo = tradeBaseNo;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Long getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Long goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	@Override
	public String toString() {
		return "CrossPayAcquireOrderDetailDTO [goodsCount=" + goodsCount
				+ ", goodsName=" + goodsName + ", orderAmount=" + orderAmount
				+ ", orderID=" + orderID + ", sellerName=" + sellerName + "]";
	}

}
