/**
 * 
 */
package com.pay.fundout.withdraw.dto.paytobank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 付款到银行查询结果
 * @author zliner
 *
 */
public class PayToBankQueryResult implements Serializable {
	//序号
	private static final long serialVersionUID = -6079011704865256604L;
	private String dealId;
	private Date creationDate;
	private Date endDate;
	private Integer memberAcctType;
	private Integer businessType;
	private String accountName;
	//开户省份
	private Short bankProvince;
	//开户行所在城市
	private Short bankCity;
	//银行卡卡号
	private String bankCardNo;
	//开户行支行
	private String bankBranch;
	//付款方
	private String payerName;
	//收款方
	private String payeeName;
	//手续费
	private BigDecimal fee;
	//付款至银行卡金额,以分为单位
	private Long amount;
	//交易状态
	private Integer status;
	//备注
	private String remarks;
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getMemberAcctType() {
		return memberAcctType;
	}
	public void setMemberAcctType(Integer memberAcctType) {
		this.memberAcctType = memberAcctType;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Short getBankProvince() {
		return bankProvince;
	}
	public void setBankProvince(Short bankProvince) {
		this.bankProvince = bankProvince;
	}
	public Short getBankCity() {
		return bankCity;
	}
	public void setBankCity(Short bankCity) {
		this.bankCity = bankCity;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
		
}
