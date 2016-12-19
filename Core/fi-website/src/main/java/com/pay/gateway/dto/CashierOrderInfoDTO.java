/**
 * 
 */
package com.pay.gateway.dto;

/**
 * @author huhb
 *
 */
public class CashierOrderInfoDTO {
	
	private String orderID;
	
	private String orderAmount;
	
	private String sellerName;
	
	private String goodsName;
	
	private Long goodsCount;
	
	private String partnerID;
	
	private String orderTime;

	private Long tradeOrderNo;
	
	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
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

	public String getPartnerID() {
		return partnerID;
	}

	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	@Override
	public String toString() {
		return "CashierOrderInfoDTO [goodsCount=" + goodsCount + ", goodsName="
				+ goodsName + ", orderAmount=" + orderAmount + ", orderID="
				+ orderID + ", orderTime=" + orderTime + ", partnerID="
				+ partnerID + ", sellerName=" + sellerName + ", tradeOrderNo="
				+ tradeOrderNo + "]";
	}

}
