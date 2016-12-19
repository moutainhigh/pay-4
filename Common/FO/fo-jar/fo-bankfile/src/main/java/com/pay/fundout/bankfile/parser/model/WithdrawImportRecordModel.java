/**
 *  File: WithdrawImportRecordModel.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-11      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.bankfile.parser.model;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author Jason_wang
 *
 */
public class WithdrawImportRecordModel  extends BaseObject {
	private String batchNum;
	private Long fileKy;
	private String bankName;
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	private String bankCode;
	private Integer status;
	/**
	 * @return the batchNum
	 */
	public String getBatchNum() {
		return batchNum;
	}

	/**
	 * @param batchNum the batchNum to set
	 */
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	/**
	 * @return the fileKy
	 */
	public Long getFileKy() {
		return fileKy;
	}

	/**
	 * @param fileKy the fileKy to set
	 */
	public void setFileKy(Long fileKy) {
		this.fileKy = fileKy;
	}

	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the gFileKy
	 */
	public Long getgFileKy() {
		return gFileKy;
	}

	/**
	 * @param gFileKy the gFileKy to set
	 */
	public void setgFileKy(Long gFileKy) {
		this.gFileKy = gFileKy;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3724901714161006250L;
	/**
	 * 银行导入文件KY(主键)
	 */
    private Long importKy;
   
    public Long getImportKy() {
		return importKy;
	}

	public void setImportKy(Long importKy) {
		this.importKy = importKy;
	}

	public String getBankSeq() {
		return bankSeq;
	}

	public void setBankSeq(String bankSeq) {
		this.bankSeq = bankSeq;
	}

	public BigDecimal getBankAmount() {
		return bankAmount;
	}

	public void setBankAmount(BigDecimal bankAmount) {
		this.bankAmount = bankAmount;
	}


	public Date getBusiTime() {
		return busiTime;
	}

	public void setBusiTime(Date busiTime) {
		this.busiTime = busiTime;
	}

	/**
	 * @return the bankStatus
	 */
	public Integer getBankStatus() {
		return bankStatus;
	}

	/**
	 * @return the orderSeq
	 */
	public Long getOrderSeq() {
		return orderSeq;
	}

	public String getBankAcctName() {
		return bankAcctName;
	}

	public void setBankAcctName(String bankAcctName) {
		this.bankAcctName = bankAcctName;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankRemark() {
		return bankRemark;
	}

	public void setBankRemark(String bankRemark) {
		this.bankRemark = bankRemark;
	}

	public String getBankFailureReason() {
		return bankFailureReason;
	}

	public void setBankFailureReason(String bankFailureReason) {
		this.bankFailureReason = bankFailureReason;
	}

	/**
     * 银行返回流水
     */
    private String bankSeq;
    /**
     * 银行交易金额
     */
    private BigDecimal bankAmount;
    /**
     * 银行状态
     */
    private Integer bankStatus;
    /**
     * 银行交易时间
     */
    private Date busiTime;

    /**
     * 订单流水号
     */
    private Long orderSeq;
    
    /**
	 * @param bankStatus the bankStatus to set
	 */
	public void setBankStatus(Integer bankStatus) {
		this.bankStatus = bankStatus;
	}

	/**
	 * @param orderSeq the orderSeq to set
	 */
	public void setOrderSeq(Long orderSeq) {
		this.orderSeq = orderSeq;
	}

	private String bankAcctName;
    
    private String bankAcct;
    
    private String bankBranch;
    
    private String bankRemark;
    
    private String bankFailureReason;
    /**
     * 分类
     */
    private Integer category;

	/**
	 * @return the category
	 */
	public Integer getCategory() {
		return category;
	}
	
	private Long gFileKy;//

	public Long getGFileKy() {
		return gFileKy;
	}

	public void setGFileKy(Long gFileKy) {
		this.gFileKy = gFileKy;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}
    
}
