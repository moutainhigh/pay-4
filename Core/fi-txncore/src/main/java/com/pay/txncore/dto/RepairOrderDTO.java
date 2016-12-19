package com.pay.txncore.dto;

import java.util.Date;

/**
 * 逆向补单
 * @author qiyun10
 *
 */
public class RepairOrderDTO {
	private Long id;
	private String channelOrderNo;
	private String refNo;
	private String authCode;
	private String paymentOrderNo;
	private String tradeOrderNo;
	
	//1.渠道掉单，2.商户掉单，3.系统掉单
	private String repairType;
	private String operator;
	private String memberCode;
	private Date createDate;
	private Date updateDate;
    private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRepairType() {
		return repairType;
	}

	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	public String getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	@Override
	public String toString() {
		return "RepairOrderDTO [id=" + id + ", channelOrderNo="
				+ channelOrderNo + ", refNo=" + refNo + ", authCode="
				+ authCode + ", paymentOrderNo=" + paymentOrderNo
				+ ", tradeOrderNo=" + tradeOrderNo + ", repairType="
				+ repairType + ", operator=" + operator + ", memberCode="
				+ memberCode + ", createDate=" + createDate + ", updateDate="
				+ updateDate + ", status=" + status + "]";
	}
}
