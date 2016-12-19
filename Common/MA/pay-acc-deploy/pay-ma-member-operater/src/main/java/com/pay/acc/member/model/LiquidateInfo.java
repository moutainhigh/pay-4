package com.pay.acc.member.model;

import java.io.Serializable;
import java.util.Date;

public class LiquidateInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long liquidateId;
	private Long memberCode;
	private String bankName;
	private String bankAcct;
	private String acctName;
	private Date createDate;
	private Date updateDate;
	private Integer accountMode;
	private Long province;
	private Long city;
	private String bankId;
	private String bankAddress;
	private Integer status = 0;
	private Long branchBankId;// 分行Id
	private String bigBankName;
	
	private String auditRemark ;
	private Integer auditStatus ;
	//added by Pengjiang
	//委托制授权书存放路径， 除动态配置部分外
	private String dbRelativePath ;

	/**
	 * @return the dbRelativePath
	 */
	public String getDbRelativePath() {
		return dbRelativePath;
	}

	public void setDbRelativePath(String dbRelativePath) {
		this.dbRelativePath = dbRelativePath;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public Long getLiquidateId() {
		return liquidateId;
	}

	public void setLiquidateId(final Long liquidateId) {
		this.liquidateId = liquidateId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(final Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(final String bankName) {
		this.bankName = bankName;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(final String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(final String acctName) {
		this.acctName = acctName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(final Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getAccountMode() {
		return accountMode;
	}

	public void setAccountMode(final Integer accountMode) {
		this.accountMode = accountMode;
	}

	public Long getProvince() {
		return province;
	}

	public void setProvince(final Long province) {
		this.province = province;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(final Long city) {
		this.city = city;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(final String bankId) {
		this.bankId = bankId;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(final String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public Long getBranchBankId() {
		return branchBankId;
	}

	public void setBranchBankId(final Long branchBankId) {
		this.branchBankId = branchBankId;
	}

	public String getBigBankName() {
		return bigBankName;
	}

	public void setBigBankName(final String bigBankName) {
		this.bigBankName = bigBankName;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Override
	public String toString() {
		return "LiquidateInfo [liquidateId=" + liquidateId + ", memberCode="
				+ memberCode + ", bankName=" + bankName + ", bankAcct="
				+ bankAcct + ", acctName=" + acctName + ", createDate="
				+ createDate + ", updateDate=" + updateDate + ", accountMode="
				+ accountMode + ", province=" + province + ", city=" + city
				+ ", bankId=" + bankId + ", bankAddress=" + bankAddress
				+ ", status=" + status + ", branchBankId=" + branchBankId
				+ ", bigBankName=" + bigBankName + ", auditRemark="
				+ auditRemark + ", auditStatus=" + auditStatus + "]";
	}

	
}