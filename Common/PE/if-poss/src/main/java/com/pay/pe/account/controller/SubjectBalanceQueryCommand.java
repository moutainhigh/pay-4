package com.pay.pe.account.controller;

import java.io.Serializable;




public class SubjectBalanceQueryCommand implements Serializable{

	private String acctCode = null;
	private String beginDate = null;
	private String endDate = null;
	private String subjectLevel=null;//科目级别
	private String subjectLevelName=null;//科目级别名称


	public String getSubjectLevelName() {
		return subjectLevelName;
	}

	public void setSubjectLevelName(String subjectLevelName) {
		this.subjectLevelName = subjectLevelName;
	}

	public String getSubjectLevel() {
		return subjectLevel;
	}

	public void setSubjectLevel(String subjectLevel) {
		this.subjectLevel = subjectLevel;
	}

	/**
	 * @return the acctCode
	 */
	public String getAcctCode() {
		return acctCode;
	}

	/**
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param acctCode the acctCode to set
	 */
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
