/**
 * 
 */
package com.pay.acc.acctattrib.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 * 
 */
public class AcctAttribDto implements Serializable {

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
	private String payPwd;
	private Date createDate;
	private Date updateDate;
	private Integer acctLevel;
	private Integer balanceBy;
	private Integer payAble;
	private Integer allowOverdraft;
	private Integer needProtect;
	private Integer managerable;
	private Integer acctType;
	private Integer bearInterest;
	private Long memberAcctCode;
	private String subjectCode;
	private boolean isPossFrozen;// 后台冻结
	private boolean isPossAllowIn;// 后台止入
	private boolean isPossAllowOut;// 后台止出
	private Integer allowAdvanceMoney;// 是否垫资 0否，1是

	public Integer getAllowAdvanceMoney() {
		return allowAdvanceMoney;
	}

	public void setAllowAdvanceMoney(Integer allowAdvanceMoney) {
		this.allowAdvanceMoney = allowAdvanceMoney;
	}

	public boolean isPossFrozen() {
		return isPossFrozen;
	}

	public void setPossFrozen(boolean isPossFrozen) {
		this.isPossFrozen = isPossFrozen;
	}

	public boolean isPossAllowIn() {
		return isPossAllowIn;
	}

	public void setPossAllowIn(boolean isPossAllowIn) {
		this.isPossAllowIn = isPossAllowIn;
	}

	public boolean isPossAllowOut() {
		return isPossAllowOut;
	}

	public void setPossAllowOut(boolean isPossAllowOut) {
		this.isPossAllowOut = isPossAllowOut;
	}

	/**
	 * @return the acctCode
	 */
	public String getAcctCode() {
		return acctCode;
	}

	/**
	 * @param acctCode
	 *            the acctCode to set
	 */
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	/**
	 * @return the allowDeposit
	 */
	public Integer getAllowDeposit() {
		return allowDeposit;
	}

	/**
	 * @param allowDeposit
	 *            the allowDeposit to set
	 */
	public void setAllowDeposit(Integer allowDeposit) {
		this.allowDeposit = allowDeposit;
	}

	/**
	 * @return the allowWithdrawal
	 */
	public Integer getAllowWithdrawal() {
		return allowWithdrawal;
	}

	/**
	 * @param allowWithdrawal
	 *            the allowWithdrawal to set
	 */
	public void setAllowWithdrawal(Integer allowWithdrawal) {
		this.allowWithdrawal = allowWithdrawal;
	}

	/**
	 * @return the allowTransferIn
	 */
	public Integer getAllowTransferIn() {
		return allowTransferIn;
	}

	/**
	 * @param allowTransferIn
	 *            the allowTransferIn to set
	 */
	public void setAllowTransferIn(Integer allowTransferIn) {
		this.allowTransferIn = allowTransferIn;
	}

	/**
	 * @return the allowTransferOut
	 */
	public Integer getAllowTransferOut() {
		return allowTransferOut;
	}

	/**
	 * @param allowTransferOut
	 *            the allowTransferOut to set
	 */
	public void setAllowTransferOut(Integer allowTransferOut) {
		this.allowTransferOut = allowTransferOut;
	}

	/**
	 * @return the allowIn
	 */
	public Integer getAllowIn() {
		return allowIn;
	}

	/**
	 * @param allowIn
	 *            the allowIn to set
	 */
	public void setAllowIn(Integer allowIn) {
		this.allowIn = allowIn;
	}

	/**
	 * @return the allowOut
	 */
	public Integer getAllowOut() {
		return allowOut;
	}

	/**
	 * @param allowOut
	 *            the allowOut to set
	 */
	public void setAllowOut(Integer allowOut) {
		this.allowOut = allowOut;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the frozen
	 */
	public Integer getFrozen() {
		return frozen;
	}

	/**
	 * @param frozen
	 *            the frozen to set
	 */
	public void setFrozen(Integer frozen) {
		this.frozen = frozen;
	}

	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode
	 *            the memberCode to set
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the defRecAcct
	 */
	public Integer getDefRecAcct() {
		return defRecAcct;
	}

	/**
	 * @param defRecAcct
	 *            the defRecAcct to set
	 */
	public void setDefRecAcct(Integer defRecAcct) {
		this.defRecAcct = defRecAcct;
	}

	/**
	 * @return the curCode
	 */
	public String getCurCode() {
		return curCode;
	}

	/**
	 * @param curCode
	 *            the curCode to set
	 */
	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	/**
	 * @return the payPwd
	 */
	public String getPayPwd() {
		return payPwd;
	}

	/**
	 * @param payPwd
	 *            the payPwd to set
	 */
	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the acctLevel
	 */
	public Integer getAcctLevel() {
		return acctLevel;
	}

	/**
	 * @param acctLevel
	 *            the acctLevel to set
	 */
	public void setAcctLevel(Integer acctLevel) {
		this.acctLevel = acctLevel;
	}

	/**
	 * @return the balanceBy
	 */
	public Integer getBalanceBy() {
		return balanceBy;
	}

	/**
	 * @param balanceBy
	 *            the balanceBy to set
	 */
	public void setBalanceBy(Integer balanceBy) {
		this.balanceBy = balanceBy;
	}

	/**
	 * @return the payAble
	 */
	public Integer getPayAble() {
		return payAble;
	}

	/**
	 * @param payAble
	 *            the payAble to set
	 */
	public void setPayAble(Integer payAble) {
		this.payAble = payAble;
	}

	/**
	 * @return the allowOverdraft
	 */
	public Integer getAllowOverdraft() {
		return allowOverdraft;
	}

	/**
	 * @param allowOverdraft
	 *            the allowOverdraft to set
	 */
	public void setAllowOverdraft(Integer allowOverdraft) {
		this.allowOverdraft = allowOverdraft;
	}

	/**
	 * @return the needProtect
	 */
	public Integer getNeedProtect() {
		return needProtect;
	}

	/**
	 * @param needProtect
	 *            the needProtect to set
	 */
	public void setNeedProtect(Integer needProtect) {
		this.needProtect = needProtect;
	}

	/**
	 * @return the managerable
	 */
	public Integer getManagerable() {
		return managerable;
	}

	/**
	 * @param managerable
	 *            the managerable to set
	 */
	public void setManagerable(Integer managerable) {
		this.managerable = managerable;
	}

	/**
	 * @return the bearInterest
	 */
	public Integer getBearInterest() {
		return bearInterest;
	}

	/**
	 * @param bearInterest
	 *            the bearInterest to set
	 */
	public void setBearInterest(Integer bearInterest) {
		this.bearInterest = bearInterest;
	}

	/**
	 * @return the memberAcctCode
	 */
	public Long getMemberAcctCode() {
		return memberAcctCode;
	}

	/**
	 * @param memberAcctCode
	 *            the memberAcctCode to set
	 */
	public void setMemberAcctCode(Long memberAcctCode) {
		this.memberAcctCode = memberAcctCode;
	}

	/**
	 * @return the subjectCode
	 */
	public String getSubjectCode() {
		return subjectCode;
	}

	/**
	 * @param subjectCode
	 *            the subjectCode to set
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public Integer getAcctType() {
		return acctType;
	}

	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}

	@Override
	public String toString() {
		return "AcctAttribDto [acctCode=" + acctCode + ", allowDeposit=" + allowDeposit + ", allowWithdrawal="
				+ allowWithdrawal + ", allowTransferIn=" + allowTransferIn + ", allowTransferOut=" + allowTransferOut
				+ ", allowIn=" + allowIn + ", allowOut=" + allowOut + ", description=" + description + ", frozen="
				+ frozen + ", memberCode=" + memberCode + ", defRecAcct=" + defRecAcct + ", curCode=" + curCode
				+ ", payPwd=" + payPwd + ", createDate=" + createDate + ", updateDate=" + updateDate + ", acctLevel="
				+ acctLevel + ", balanceBy=" + balanceBy + ", payAble=" + payAble + ", allowOverdraft=" + allowOverdraft
				+ ", needProtect=" + needProtect + ", managerable=" + managerable + ", acctType=" + acctType
				+ ", bearInterest=" + bearInterest + ", memberAcctCode=" + memberAcctCode + ", subjectCode="
				+ subjectCode + ", isPossFrozen=" + isPossFrozen + ", isPossAllowIn=" + isPossAllowIn
				+ ", isPossAllowOut=" + isPossAllowOut + ", allowAdvanceMoney=" + allowAdvanceMoney + "]";
	}
	

}
