 /** @Description 
 * @project 	poss-refund
 * @file 		WebQueryRefundDTO.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-25		sunsea.li		Create 
*/
package com.pay.poss.refund.model;

import java.util.Date;
import java.util.List;

import com.pay.inf.model.BaseObject;

/**
 * <p>充退管理查询参数DTO</p>
 * @author sunsea.li
 * @param <T>
 * @since 2010-8-25
 * @see 
 */
public class WebQueryRefundDTO extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	private static final String CHARGE_REFUND = "1";
	private static final String DEAL_REFUND = "2";
	/**
	 * 只用于参数传递,属性不需要序列化
	 */
	private String account ;//账户名
	private String rechargeSeq ;//网关退款流水号
	private Date startTime;//起始日期
	private Date endTime ;//终止日期
	private String rechargeStatus;//充值状态
	private String bankSeq;//银行流水号
	private String rechargeChannel;//充值渠道
	
	private String refundSeq ;//充退流水号
	private String refundStatus;//充退状态
	
	private String batchNum ;//批次号
	private String batchName;//批次名称
	private String fileStatus;//文件状态。1：已生成 2：已下载3：已导入 4:已确认导入
	private String channelCode;//渠道编码
    private String busiType;//业务类型	1:充退 2:提现
    private String bankCode;//银行编码
    private String fileType;//文件类型（内部、外部）
    private String fileKy;//文件主键
    private String batchType;//批次业务类型。200002：提现    200001：充退
	private String busiFlag;//0:默认值
									    /*1:完全匹配
									    2:完成匹配失败
									    3:完成匹配交易进行中
									    4:不匹配交易*/
	
	private List<String> fileKys; //文件主键
    
    private String memberNo;	//会员号
    private String memberType;	//会员类型
    private String accountType;	//账户类型
    private String bizStatus;		//交易状态
    private String auditGrade;	//审核等级
    private String liquidateFlag;	//是否清结算废除
    private List<String> workflowKys; //工作流实例ID
    private String userId;	//登录用户账号
    private String userName;	//登录用户姓名
    private String auditStatus;		//审核状态
    private String reAuditStatus;	//复核状态
    private String handleStatus;	//处理状态
    private String handleDatas;		//处理数据
    private String handleType;		//处理类型
    private String assigner;		//操作员
    private String queryType;		//查询类型
    
    private Long seqId;//对账结果表主键
    private String handleUser;
    private Integer workOrderStatus;
    private List<String> status;
    private List<String> oldStatus;
    private String operator;
    private String applyReason;
    /**
     * 工单表Ky
     */
    private String workorderKy;
    
    private String auditRefundRemark;//退款失败原因
    
    private String merchantOrderId;
    
	/**
	 * @return the merchantOrderId
	 */
	public String getMerchantOrderId() {
		return merchantOrderId;
	}
	/**
	 * @param merchantOrderId the merchantOrderId to set
	 */
	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}
	public String getAuditRefundRemark() {
		return auditRefundRemark;
	}
	public void setAuditRefundRemark(String auditRefundRemark) {
		this.auditRefundRemark = auditRefundRemark;
	}
	/**
	 * @return the workorderKy
	 */
	public String getWorkorderKy() {
		return workorderKy;
	}
	/**
	 * @param workorderKy the workorderKy to set
	 */
	public void setWorkorderKy(String workorderKy) {
		this.workorderKy = workorderKy;
	}
	public String getApplyReason() {
		return applyReason;
	}
	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public List<String> getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(List<String> oldStatus) {
		this.oldStatus = oldStatus;
	}
	public List<String> getStatus() {
		return status;
	}
	public void setStatus(List<String> status) {
		this.status = status;
	}
	/**
	 * @return the workOrderStatus
	 */
	public Integer getWorkOrderStatus() {
		return workOrderStatus;
	}
	/**
	 * @param workOrderStatus the workOrderStatus to set
	 */
	public void setWorkOrderStatus(Integer workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}
	/**
	 * @return the handleUser
	 */
	public String getHandleUser() {
		return handleUser;
	}
	/**
	 * @param handleUser the handleUser to set
	 */
	public void setHandleUser(String handleUser) {
		this.handleUser = handleUser;
	}
	public Long getSeqId() {
		return seqId;
	}
	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}
	public List<String> getFileKys() {
		return fileKys;
	}
	public void setFileKys(List<String> fileKys) {
		this.fileKys = fileKys;
	}
	public String getBatchType() {
		return batchType;
	}
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	public String getFileKy() {
		return fileKy;
	}
	public void setFileKy(String fileKy) {
		this.fileKy = fileKy;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
    public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getAssigner() {
		return assigner;
	}
	public void setAssigner(String assigner) {
		this.assigner = assigner;
	}
	public String getHandleType() {
		return handleType;
	}
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}	
    
    public String getHandleDatas() {
		return handleDatas;
	}
	public void setHandleDatas(String handleDatas) {
		this.handleDatas = handleDatas;
	}
	public String getHandleStatus() {
		return handleStatus;
	}
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	public String getReAuditStatus() {
		return reAuditStatus;
	}
	public void setReAuditStatus(String reAuditStatus) {
		this.reAuditStatus = reAuditStatus;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<String> getWorkflowKys() {
		return workflowKys;
	}
	public void setWorkflowKys(List<String> workflowKys) {
		this.workflowKys = workflowKys;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBizStatus() {
		return bizStatus;
	}
	public void setBizStatus(String bizStatus) {
		this.bizStatus = bizStatus;
	}
	public String getAuditGrade() {
		return auditGrade;
	}
	public void setAuditGrade(String auditGrade) {
		this.auditGrade = auditGrade;
	}
	public String getLiquidateFlag() {
		return liquidateFlag;
	}
	public void setLiquidateFlag(String liquidateFlag) {
		this.liquidateFlag = liquidateFlag;
	}   
    
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getRechargeSeq() {
		return rechargeSeq;
	}
	public void setRechargeSeq(String rechargeSeq) {
		this.rechargeSeq = rechargeSeq;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getRechargeStatus() {
		return rechargeStatus;
	}
	public void setRechargeStatus(String rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}
	public String getBankSeq() {
		return bankSeq;
	}
	public void setBankSeq(String bankSeq) {
		this.bankSeq = bankSeq;
	}
	public String getRechargeChannel() {
		return rechargeChannel;
	}
	public void setRechargeChannel(String rechargeChannel) {
		this.rechargeChannel = rechargeChannel;
	}
	public String getRefundSeq() {
		return refundSeq;
	}
	public void setRefundSeq(String refundSeq) {
		this.refundSeq = refundSeq;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBusiFlag() {
		return busiFlag;
	}
	public void setBusiFlag(String busiFlag) {
		this.busiFlag = busiFlag;
	}
}
