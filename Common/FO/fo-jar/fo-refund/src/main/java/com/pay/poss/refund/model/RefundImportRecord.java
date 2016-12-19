 /** @Description 
 * @project 	poss-refund
 * @file 		RefundImportRecord.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-14		sunsea.li		Create 
*/
package com.pay.poss.refund.model;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.inf.model.BaseObject;

/**对应银行状态文件记录表信息
 * @author sunsea_li
 *
 */
public class RefundImportRecord extends BaseObject {
	private static final long serialVersionUID = -8980842346934705289L;
	private Long fileKy;//关联导入的文件号
	private String batchNum;//批次号（冗余）
    private String bankCode;//银行编号（冗余）
    private Long orderSeq;//银行返回流水（订单明细流水）
    private BigDecimal bankAmount;//银行交易金额
    private String bankAcctName;//收款人
    private String bankSeq;//银行交易流水
    private String bankRemark;//银行备注
    private Integer bankStatus;//银行状态
    private Date busiTime;//银行交易时间
    private Integer status;//1：默认值  0：重复无效
	public Long getFileKy() {
		return fileKy;
	}
	public void setFileKy(Long fileKy) {
		this.fileKy = fileKy;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}