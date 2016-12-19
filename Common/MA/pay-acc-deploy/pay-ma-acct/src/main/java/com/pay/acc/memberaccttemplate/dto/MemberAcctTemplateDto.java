/**
 * 
 */
package com.pay.acc.memberaccttemplate.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chaoyue
 * 
 */
public class MemberAcctTemplateDto implements Serializable {

	/**
     */
	private Integer matId;

	/**
     */
	private String templateName;

	/**
     */
	private Integer acctType;

	private Integer needProtect;

	/**
     */
	private Integer allowDeposit;

	/**
     */
	private Integer allowWithdrawal;

	/**
     */
	private Integer allowTransferIn;

	/**
     */
	private Integer allowTransferOut;

	/**
     */
	private Integer allowIn;

	/**
     */
	private Integer allowOut;

	private Integer acctLevel;

	/**
     */
	private String description;

	/**
     */
	private Integer frozen;

	/**
     */
	private Integer defRecAcct;

	/**
     */
	private String curCode;

	/**
     */
	private Integer scene;

	/**
     */
	private Date createDate;

	/**
     */
	private Date updateDate;

	/**
     */
	private Integer bearInterest;

	/**
     */
	private Integer payAble;

	/**
     */
	private Integer allowOverdraft;

	/**
     */
	private Integer memberType;

	/**
     */
	private String subjectCode;

	/**
     */
	private Integer status;

	private Integer balanceBy;

	private Integer managerable;

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public Integer getMatId() {
		return matId;
	}

	public void setMatId(Integer matId) {
		this.matId = matId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Integer getAcctType() {
		return acctType;
	}

	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
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

	public Integer getScene() {
		return scene;
	}

	public void setScene(Integer scene) {
		this.scene = scene;
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

	public Integer getBearInterest() {
		return bearInterest;
	}

	public void setBearInterest(Integer bearInterest) {
		this.bearInterest = bearInterest;
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

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

}
