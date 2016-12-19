 /** @Description 
 * @project 	poss-refund
 * @file 		RefundReconcileResult.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-14		sunsea.li		Create 
*/
package com.pay.poss.refund.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**对应提现对账结果信息
 * @author sunsea_li
 *
 */
public class RefundReconcileResult extends BaseObject {
	private static final long serialVersionUID = 6449532918194739346L;
	private Long fileKy;//关联导入的文件号（入参）
    private Date cutTime;//日期时间。默认当前时间戳
    private String batchNum;//批次号（冗余，入参）
    private String bankCode;//银行编号（冗余，入参）
    private String leftBackSeq;//银行返回流水(充退明细流水)
    private String leftBankSeq;//银行交易流水（银行自身流水）
    private Long leftAmount;//银行交易金额
    private Integer leftStatus;//银行状态
    private Date leftTime;//银行交易时间
    private Long rightSeqM;//支付系统充退主流水
    private Long rightSeqD;//支付系统充退明细流水
    private String rightMemberName;//充退会员姓名
    private Date rightTime;//支付订单生成时间
    private Long rightAmount;//支付系统充退明细交易金额
    private Integer busiFlag;//0:默认值
							    /*1:完全匹配
							    2:完成匹配失败
							    3:完成匹配交易进行中
							    4:不匹配交易*/

    private Integer adjustStatus;//1:未调账，默认值 2：已调账
    private Integer status;//1：默认值  0：重复无效
    private Long seqId;//主键
    private String leftMemberName;//银行方收款人名称
    
	public Long getSeqId() {
		return seqId;
	}
	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}
	public String getLeftMemberName() {
		return leftMemberName;
	}
	public void setLeftMemberName(String leftMemberName) {
		this.leftMemberName = leftMemberName;
	}
	public Long getFileKy() {
		return fileKy;
	}
	public void setFileKy(Long fileKy) {
		this.fileKy = fileKy;
	}
	public Date getCutTime() {
		return cutTime;
	}
	public void setCutTime(Date cutTime) {
		this.cutTime = cutTime;
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
	public String getLeftBackSeq() {
		return leftBackSeq;
	}
	public void setLeftBackSeq(String leftBackSeq) {
		this.leftBackSeq = leftBackSeq;
	}
	public String getLeftBankSeq() {
		return leftBankSeq;
	}
	public void setLeftBankSeq(String leftBankSeq) {
		this.leftBankSeq = leftBankSeq;
	}
	public Integer getLeftStatus() {
		return leftStatus;
	}
	public void setLeftStatus(Integer leftStatus) {
		this.leftStatus = leftStatus;
	}
	public Date getLeftTime() {
		return leftTime;
	}
	public void setLeftTime(Date leftTime) {
		this.leftTime = leftTime;
	}
	public Long getRightSeqM() {
		return rightSeqM;
	}
	public void setRightSeqM(Long rightSeqM) {
		this.rightSeqM = rightSeqM;
	}
	public Long getRightSeqD() {
		return rightSeqD;
	}
	public void setRightSeqD(Long rightSeqD) {
		this.rightSeqD = rightSeqD;
	}
	public String getRightMemberName() {
		return rightMemberName;
	}
	public void setRightMemberName(String rightMemberName) {
		this.rightMemberName = rightMemberName;
	}
	public Date getRightTime() {
		return rightTime;
	}
	public void setRightTime(Date rightTime) {
		this.rightTime = rightTime;
	}
	public Integer getBusiFlag() {
		return busiFlag;
	}
	public void setBusiFlag(Integer busiFlag) {
		this.busiFlag = busiFlag;
	}
	public Integer getAdjustStatus() {
		return adjustStatus;
	}
	public void setAdjustStatus(Integer adjustStatus) {
		this.adjustStatus = adjustStatus;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getLeftAmount() {
		return leftAmount;
	}
	public void setLeftAmount(Long leftAmount) {
		this.leftAmount = leftAmount;
	}
	public Long getRightAmount() {
		return rightAmount;
	}
	public void setRightAmount(Long rightAmount) {
		this.rightAmount = rightAmount;
	}
}