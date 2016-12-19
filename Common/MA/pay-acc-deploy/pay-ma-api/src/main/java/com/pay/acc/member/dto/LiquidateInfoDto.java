package com.pay.acc.member.dto;

import java.io.Serializable;
import java.util.Date;

public class LiquidateInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long liquidateId;
	private Long memberCode;
	private String bankName;
	private String bankAcct;
	private String acctName;
	private Date createDate;
	private Date updateDate;
	// private Integer accountMode;
	private Long province;
	private Long city;
	private String bankId;
	private String bankAddress;
	private Integer status = 0;
	private Long branchBankId;// 分行Id
	private String bigBankName;
	
	private Integer auditStatus ;

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getLiquidateId() {
		return liquidateId;
	}

	public void setLiquidateId(Long liquidateId) {
		this.liquidateId = liquidateId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
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

	public Long getProvince() {
		return province;
	}

	public void setProvince(Long province) {
		this.province = province;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public Long getBranchBankId() {
		return branchBankId;
	}

	public void setBranchBankId(Long branchBankId) {
		this.branchBankId = branchBankId;
	}

	public String getBigBankName() {
		return bigBankName;
	}

	public void setBigBankName(String bigBankName) {
		this.bigBankName = bigBankName;
	}

	@Override
	public String toString() {
		return "LiquidateInfo [" + "acctName=" + acctName + ", bankAcct="
				+ bankAcct + ", bankAddress=" + bankAddress + ", bankId="
				+ bankId + ", bankName=" + bankName + ", city=" + city
				+ ", createDate=" + createDate + ", liquidateId=" + liquidateId
				+ ", memberCode=" + memberCode + ", province=" + province
				+ ", updateDate=" + updateDate + ", branchBankId="
				+ branchBankId + "]";
	}
}