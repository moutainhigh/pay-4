package com.pay.txncore.facade.dto;

import java.io.Serializable;
import java.util.Date;

public class AccountAttribDTO implements Serializable {

	private static final long serialVersionUID = -7916971423937922501L;
	private Long attribId;
	private String acctCode;
	private Integer allowDeposit;
	private Integer allowWithdrawal;
	private Integer allowTransferIn;
	private Integer allowTransferOut;
	private Integer allowIn;
	private Integer allowOut;
	private String description;
	private Integer frozen;
	private Long memberCode;
	private Integer defRecAcct;
	private String curCode;
	private Date createDate;
	private Date updateDate;
	private Integer acctLevel;
	private Integer balanceBy;
	private Integer payAble;
	private Integer allowOverdraft;
	private Integer needProtect;
	private Integer managerable;
	private Integer type;

	public Long getAttribId() {
		return attribId;
	}

	public void setAttribId(Long attribId) {
		this.attribId = attribId;
	}

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public Integer getAllowDeposit() {
		return allowDeposit;
	}

	public void setAllowDeposit(Integer allowDeposit) {
		this.allowDeposit = allowDeposit;
	}

	public Integer getAllowWithdrawal() {
		return allowWithdrawal;
	}

	public void setAllowWithdrawal(Integer allowWithdrawal) {
		this.allowWithdrawal = allowWithdrawal;
	}

	public Integer getAllowTransferIn() {
		return allowTransferIn;
	}

	public void setAllowTransferIn(Integer allowTransferIn) {
		this.allowTransferIn = allowTransferIn;
	}

	public Integer getAllowTransferOut() {
		return allowTransferOut;
	}

	public void setAllowTransferOut(Integer allowTransferOut) {
		this.allowTransferOut = allowTransferOut;
	}

	public Integer getAllowIn() {
		return allowIn;
	}

	public void setAllowIn(Integer allowIn) {
		this.allowIn = allowIn;
	}

	public Integer getAllowOut() {
		return allowOut;
	}

	public void setAllowOut(Integer allowOut) {
		this.allowOut = allowOut;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getFrozen() {
		return frozen;
	}

	public void setFrozen(Integer frozen) {
		this.frozen = frozen;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Integer getDefRecAcct() {
		return defRecAcct;
	}

	public void setDefRecAcct(Integer defRecAcct) {
		this.defRecAcct = defRecAcct;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
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

	public Integer getAcctLevel() {
		return acctLevel;
	}

	public void setAcctLevel(Integer acctLevel) {
		this.acctLevel = acctLevel;
	}

	public Integer getBalanceBy() {
		return balanceBy;
	}

	public void setBalanceBy(Integer balanceBy) {
		this.balanceBy = balanceBy;
	}

	public Integer getPayAble() {
		return payAble;
	}

	public void setPayAble(Integer payAble) {
		this.payAble = payAble;
	}

	public Integer getAllowOverdraft() {
		return allowOverdraft;
	}

	public void setAllowOverdraft(Integer allowOverdraft) {
		this.allowOverdraft = allowOverdraft;
	}

	public Integer getNeedProtect() {
		return needProtect;
	}

	public void setNeedProtect(Integer needProtect) {
		this.needProtect = needProtect;
	}

	public Integer getManagerable() {
		return managerable;
	}

	public void setManagerable(Integer managerable) {
		this.managerable = managerable;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "AccountAttribDTO [attribId=" + attribId + ", acctCode="
				+ acctCode + ", allowDeposit=" + allowDeposit
				+ ", allowWithdrawal=" + allowWithdrawal + ", allowTransferIn="
				+ allowTransferIn + ", allowTransferOut=" + allowTransferOut
				+ ", allowIn=" + allowIn + ", allowOut=" + allowOut
				+ ", description=" + description + ", frozen=" + frozen
				+ ", memberCode=" + memberCode + ", defRecAcct=" + defRecAcct
				+ ", curCode=" + curCode + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", acctLevel=" + acctLevel
				+ ", balanceBy=" + balanceBy + ", payAble=" + payAble
				+ ", allowOverdraft=" + allowOverdraft + ", needProtect="
				+ needProtect + ", managerable=" + managerable + ", type="
				+ type + "]";
	}

}
