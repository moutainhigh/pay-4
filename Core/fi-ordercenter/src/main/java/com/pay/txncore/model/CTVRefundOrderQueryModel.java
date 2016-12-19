package com.pay.txncore.model;

/* author	  :sch
 * create date:2016-06-16
 * 数据结构有点像  com.pay.file.parser.dto.GatewayReconciliationParserMode
 * 这个代码 还有一份在  com.pay.poss.dto.CTVRefundOrderQueryModel;
 */


public class CTVRefundOrderQueryModel {
	
	//(1) 用来查找退款订单的两个字段，各个渠道可能不一样 
	private String channelOrderNo;		//渠道订单号 111xxxxxx
	
	private String refundRequestId; // 46xxxxxxxxxxxxxx  退款订单请求Id 
	
	//(2) 退款订单本身的一些信息 
	// 交易日期
	private String dealDate; // 20160406

	// 交易时间
	private String dealTime; // 184513

	private String cardNo; // 卡号 4645560811049424 ，或者 4645*******4924

	// 交易金额 退款金额
	private String refundAmount; // 1129.90 -1129.90
	
	// 结算金额
	private String settAmount; // 9.79 -9.79
	// 手续费
	private String transFee; // 0.02 -1.23
	
	//(3) 辅助查找信息 
	private String orgCode; // 机构编码，这个字段不是从文件中读取的，而是额外赋值进去的

	private String queryIndex; // 如果是一个数组，则这个是数组中的下标 和返回结果一一对应
	
	//(4) 这是查询结果
	private String refundOrderNo;

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getQueryIndex() {
		return queryIndex;
	}

	public void setQueryIndex(String queryIndex) {
		this.queryIndex = queryIndex;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public String getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String getDealDate() {
		return dealDate;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getDealTime() {
		return dealTime;
	}

	// 交易时间
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setRefundRequestId(String refundRequestId) {
		this.refundRequestId = refundRequestId;
	}

	public String getRefundRequestId() {
		return refundRequestId;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setSettAmount(String settAmount) {
		this.settAmount = settAmount;
	}

	public String getSettAmount() {
		return settAmount;
	}

	public void setTransFee(String transFee) {
		this.transFee = transFee;
	}

	public String getTransFee() {
		return transFee;
	}

	public String toString() {

		return "CTVRefundOrderQueryModel  [channelOrderNo=" + channelOrderNo
				+ ", dealDate=" + dealDate + ", dealTime=" + dealTime
				+ ", cardNo=" + cardNo + ", refundRequestId=" + refundRequestId
				+ ", refundAmount=" + refundAmount + ", queryIndex="
				+ queryIndex + "]";
	}
	
}
