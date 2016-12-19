 /** @Description 
 * @project 	poss-refund
 * @file 		RefundImportRecord.java 
 * Copyright © 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-14		Jason_wang		Create 
*/
package com.pay.fundout.bankfile.parser.model;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.inf.model.BaseObject;

/**对应银行状态文件记录表信息
 * @author Jason_wang
 *
 */
public class RefundImportRecordModel extends BaseObject {
	private static final long serialVersionUID = -8980842346934705289L;
    private Long orderSeq;//银行返回流水（订单明细流水）
    private BigDecimal bankAmount;//银行交易金额
    private String bankAcctName;//收款人
    private String bankSeq;//银行交易流水
    private String bankRemark;//银行备注
    private Integer bankStatus;//银行状态
    private Date busiTime;//银行交易时间
	private String batchNum;
	private Long fileKy;
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
	public Long getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(Long orderSeq) {
		this.orderSeq = orderSeq;
	}
	public BigDecimal getBankAmount() {
		return bankAmount;
	}
	public void setBankAmount(BigDecimal bankAmount) {
		this.bankAmount = bankAmount;
	}
	public String getBankAcctName() {
		return bankAcctName;
	}
	public void setBankAcctName(String bankAcctName) {
		this.bankAcctName = bankAcctName;
	}
	public String getBankSeq() {
		return bankSeq;
	}
	public void setBankSeq(String bankSeq) {
		this.bankSeq = bankSeq;
	}
	public String getBankRemark() {
		return bankRemark;
	}
	public void setBankRemark(String bankRemark) {
		this.bankRemark = bankRemark;
	}
	public Integer getBankStatus() {
		return bankStatus;
	}
	public void setBankStatus(Integer bankStatus) {
		this.bankStatus = bankStatus;
	}
	public Date getBusiTime() {
		return busiTime;
	}
	public void setBusiTime(Date busiTime) {
		this.busiTime = busiTime;
	}
}