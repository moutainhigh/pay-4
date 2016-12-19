/**
 * 
 */
package com.pay.fi.fill.model;

/**
 * 补单明细记录
 * @author PengJiangbo
 *
 */
public class FillRecordInfo {
	
	/** 明细记录编号[主键] */
	private Long reqRecordNo ;
	/** 关联：申请批次号 */
    private Long reqBatchNo ;
    /** 渠道订单号 */
    private Long channelOrderNo ;
    /** 机构端流水号 */
    private String returnNo ;
    /** 支付金额 */
    private Long amount ;
    /** 支付币种 */
    private String currencyCode ;
    /** 授权码 */
    private String authorization ;
    /** 失败原因  */
    private String failReason ;
    /** 明细记录状态 */
    private int recordStatus ;
    
	
	public Long getReqRecordNo() {
		return reqRecordNo;
	}

	public void setReqRecordNo(Long reqRecordNo) {
		this.reqRecordNo = reqRecordNo;
	}

	public Long getReqBatchNo() {
		return reqBatchNo;
	}

	public void setReqBatchNo(Long reqBatchNo) {
		this.reqBatchNo = reqBatchNo;
	}

	public Long getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(Long channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public String getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	
	public int getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(int recordStatus) {
		this.recordStatus = recordStatus;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result
				+ ((authorization == null) ? 0 : authorization.hashCode());
		result = prime * result
				+ ((channelOrderNo == null) ? 0 : channelOrderNo.hashCode());
		result = prime * result
				+ ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result
				+ ((returnNo == null) ? 0 : returnNo.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FillRecordInfo other = (FillRecordInfo) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (authorization == null) {
			if (other.authorization != null)
				return false;
		} else if (!authorization.equals(other.authorization))
			return false;
		if (channelOrderNo == null) {
			if (other.channelOrderNo != null)
				return false;
		} else if (!channelOrderNo.equals(other.channelOrderNo))
			return false;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (returnNo == null) {
			if (other.returnNo != null)
				return false;
		} else if (!returnNo.equals(other.returnNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FillRecordInfo [reqRecordNo=" + reqRecordNo + ", reqBatchNo="
				+ reqBatchNo + ", channelOrderNo=" + channelOrderNo
				+ ", returnNo=" + returnNo + ", amount=" + amount
				+ ", currencyCode=" + currencyCode + ", authorization="
				+ authorization + ", failReason=" + failReason
				+ ", recordStatus=" + recordStatus + "]";
	}

}
