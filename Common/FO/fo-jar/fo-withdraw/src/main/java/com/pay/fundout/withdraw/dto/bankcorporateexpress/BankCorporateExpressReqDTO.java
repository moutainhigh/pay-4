package com.pay.fundout.withdraw.dto.bankcorporateexpress;

import java.util.Date;

import com.pay.inf.model.BaseObject;

public class BankCorporateExpressReqDTO extends BaseObject {

	private static final long serialVersionUID = -8841807481110063391L;
	private Date startDate; // 交易时间
	private Date endDate;
	private String tradeType;// 交易类型
	private String sequenceId; // 交易流水号
	private String memberCode; // 会员号
	private String memberName; // 会员名称
	private Integer status; // 交易状态
	private String bankKy; // 出款渠道

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBankKy() {
		return bankKy;
	}

	public void setBankKy(String bankKy) {
		this.bankKy = bankKy;
	}

}
