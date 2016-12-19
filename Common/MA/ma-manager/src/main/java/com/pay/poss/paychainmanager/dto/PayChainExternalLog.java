/**
 *Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.poss.paychainmanager.dto;

/**
 * @Title: PayChainPayInfo.java
 * @Package com.pay.poss.paychainmanager.dto
 * @Description: 支付链付款信息
 * @author cf
 * @date 2011-9-28 下午01:50:45
 * @version V1.0
 */
public class PayChainExternalLog {
	private String 		orderNo;   //订单号
	private String 		cardNo;	//易卡充值填卡号；支付链填支付链编号
	private Integer 	processStatus;//处理状态 0-未处理 1-成功 2-失败 3-已退款 4-退款中
	private String 		amount;		//交易金额
	private String 		payerName;	//付款方名称
	private String 		gatewayNo;	//网关支付流水号
	private String 		payerContact;//付款人联系方式
	private String 		updateDate; //付款成功时间
	private String 		remark;		//备注
	private Integer 	pageStartRow;// 起始行
	private Integer 	pageEndRow;// 结束行
	
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public Integer getPageStartRow() {
		return pageStartRow;
	}
	public void setPageStartRow(Integer pageStartRow) {
		this.pageStartRow = pageStartRow;
	}
	public Integer getPageEndRow() {
		return pageEndRow;
	}
	public void setPageEndRow(Integer pageEndRow) {
		this.pageEndRow = pageEndRow;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getGatewayNo() {
		return gatewayNo;
	}
	public void setGatewayNo(String gatewayNo) {
		this.gatewayNo = gatewayNo;
	}
	public String getPayerContact() {
		return payerContact;
	}
	public void setPayerContact(String payerContact) {
		this.payerContact = payerContact;
	}

	
}
