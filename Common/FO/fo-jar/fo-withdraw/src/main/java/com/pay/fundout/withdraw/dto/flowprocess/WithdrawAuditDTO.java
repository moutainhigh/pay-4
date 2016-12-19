/**
 *  File: WithdrawAuditDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.flowprocess;

import java.util.Date;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.inf.model.BaseObject;
import com.pay.util.DateUtil;

/**
 * @author Jonathen Ni
 * @since 2010-9-14
 */
public class WithdrawAuditDTO extends BaseObject {

	private static final long serialVersionUID = 1L;

	/** 交易备注 **/
	private String orderRemarks;

	/** 优先级.值越大越优先.默认为5 **/
	private Short prioritys;

	private String prioritysStr;

	/** 申请人会员类型。默认个人会员 **/
	private Long memberType;

	/**
	 * 会员类别 1:个人会员;2:企业会员 3:卖家会员
	 */
	private String memberTypeStr;

	/** 会员号 **/
	private Long memberCode;

	private String memberCodeStr;

	/** 帐户类型。默认人民币账户 **/
	private Long memberAccType;

	private String memberAccTypeStr;

	/** 收款人名称 **/
	private String accountName;

	/** 主键 **/
	private Long sequenceId;

	private String sequenceIdStr;

	/** 开户支行名称 **/
	private String bankBranch;

	/** 银行账户 **/
	private String bankAcct;

	/** 提现金额 **/
	private Long amount;

	private String amountStr;

	/** 开户行所在省份 **/
	private Short bankProvince;

	private String bankProvinceStr;

	/** 开户行所在城市 **/
	private Short bankCity;

	private String bankCityStr;

	/** 创建时间 **/
	private Date createTime;

	private String createTimeStr;

	private String riskLeveCode;// 商户风控等级

	private String accountMode;// 结算周期

	private Integer isLoaning;// 是否垫资

	/** 前台复核提交时间 **/
	private Date webAuditTime;

	/** 出款银行编号 **/
	private String withdrawBankCode;

	/** 业务批次号 **/
	private String batchNo;

	/** 滞留原因 **/
	private String failReason;

	//-------------------add - batch_paymentorder:orderId
	private Long corderId ;
	
	private Long parentOrderId;
		
	/**
	 * @return the failReason
	 */
	public String getFailReason() {
		return failReason;
	}

	/**
	 * @param failReason
	 *            the failReason to set
	 */
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	/**
	 * @return the withdrawBankCode
	 */
	public String getWithdrawBankCode() {
		return withdrawBankCode;
	}

	/**
	 * @param withdrawBankCode
	 *            the withdrawBankCode to set
	 */
	public void setWithdrawBankCode(String withdrawBankCode) {
		this.withdrawBankCode = withdrawBankCode;
	}

	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo
	 *            the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return the webAuditTime
	 */
	public Date getWebAuditTime() {
		return webAuditTime;
	}

	/**
	 * @param webAuditTime
	 *            the webAuditTime to set
	 */
	public void setWebAuditTime(Date webAuditTime) {
		this.webAuditTime = webAuditTime;
	}

	/**
	 * @return the riskLeveCode
	 */
	public String getRiskLeveCode() {
		return riskLeveCode;
	}

	public Integer getIsLoaning() {
		return isLoaning;
	}

	public void setIsLoaning(Integer isLoaning) {
		this.isLoaning = isLoaning;
	}

	/**
	 * @param riskLeveCode
	 *            the riskLeveCode to set
	 */
	public void setRiskLeveCode(String riskLeveCode) {
		this.riskLeveCode = riskLeveCode;
	}

	/**
	 * @return the accountMode
	 */
	public String getAccountMode() {
		return accountMode;
	}

	/**
	 * @param accountMode
	 *            the accountMode to set
	 */
	public void setAccountMode(String accountMode) {
		this.accountMode = accountMode;
	}

	/**
	 * 状态 0：工单初始 1：审核通过 2：审核拒绝 3：审核滞留 4：复核通过 5：复核拒绝 6：清算拒绝 7：工单生成批次成功 8：人工初审成功
	 * 9：人工初审失败 10：工单失败 11：完成
	 */
	private Integer status;

	private String statusStr;

	/**
	 * 工单主键
	 */
	private Long workOrderky;

	/**
	 * 工作流实例Id
	 */
	private String workflowKy;

	/**
	 * 人工初审失败原因
	 */
	private String auditFailReason;

	/**
	 * 人工复核退回原因
	 */
	private String reAuditBackReason;

	/**
	 * 批次状态 0：未出批次 1：已出批次 2：批次废除
	 */
	private Integer batchStatus;

	private String batchStatusStr;

	/**
	 * 提现银行名称,关联到银行基础表
	 */
	private String bankKyStr;

	/**
	 * 提现银ID,关联到银行基础表
	 */
	private String bankKy;

	private Long busiType;

	private String busiTypeStr;

	/**
	 * 会员名称
	 */
	private String memberName;

	/**
	 * 付款人 (应欧航要求，增加付款人，从MA接口读取)
	 */
	private String payer;

	private String auditUser;

	private String reAuditUser;

	public String getCreateTimeStr() {
		if (null == createTime) {
			return "";
		} else {
			return DateUtil.dateToStr(createTime, "yyyy-MM-dd HH:mm:ss");
		}
	}

	public String getAmountStr() {
		if (null == amount) {
			return "0.00";
		} else {
			return String.valueOf(amount.longValue() / 1000.0);
		}
	}

	public String getSequenceIdStr() {
		if (null == sequenceId) {
			return "";
		} else {
			return sequenceId.toString();
		}
	}

	public String getMemberCodeStr() {
		if (null == memberCode) {
			return "";
		} else {
			return memberCode.toString();
		}
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getReAuditUser() {
		return reAuditUser;
	}

	public void setReAuditUser(String reAuditUser) {
		this.reAuditUser = reAuditUser;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getBusiTypeStr() {
		switch (busiType.intValue()) {
		case 0:
			busiTypeStr = "提现";
			break;
		case 1:
			busiTypeStr = "批量出款";
			break;
		case 2:
			busiTypeStr = "信用卡还款";
			break;
		case 3:
			busiTypeStr = "付款到银行卡";
			break;
		case 4:
			busiTypeStr = "批量付款到银行";
			break;
		}
		return busiTypeStr;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public Long getBusiType() {
		return busiType;
	}

	public void setBusiType(Long busiType) {
		this.busiType = busiType;
	}

	/**
	 * @return the bankKy
	 */
	public String getBankKy() {
		return bankKy;
	}

	/**
	 * @return the workflowKy
	 */
	public String getWorkflowKy() {
		return workflowKy;
	}

	/**
	 * @param workflowKy
	 *            the workflowKy to set
	 */
	public void setWorkflowKy(String workflowKy) {
		this.workflowKy = workflowKy;
	}

	/**
	 * @return the workOrderky
	 */
	public Long getWorkOrderky() {
		return workOrderky;
	}

	/**
	 * @param workOrderky
	 *            the workOrderky to set
	 */
	public void setWorkOrderky(Long workOrderky) {
		this.workOrderky = workOrderky;
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
	 * @return the memberAccType
	 */
	public Long getMemberAccType() {
		return memberAccType;
	}

	/**
	 * @param memberAccType
	 *            the memberAccType to set
	 */
	public void setMemberAccType(Long memberAccType) {
		this.memberAccType = memberAccType;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the sequenceId
	 */
	public Long getSequenceId() {
		return sequenceId;
	}

	/**
	 * @param sequenceId
	 *            the sequenceId to set
	 */
	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	/**
	 * @return the bankBranch
	 */
	public String getBankBranch() {
		return bankBranch;
	}

	/**
	 * @param bankBranch
	 *            the bankBranch to set
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * @return the bankAcct
	 */
	public String getBankAcct() {
		return bankAcct;
	}

	/**
	 * @param bankAcct
	 *            the bankAcct to set
	 */
	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	/**
	 * @return the amount
	 */
	public Long getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 * @return the bankProvince
	 */
	public Short getBankProvince() {
		return bankProvince;
	}

	/**
	 * @param bankProvince
	 *            the bankProvince to set
	 */
	public void setBankProvince(Short bankProvince) {
		this.bankProvince = bankProvince;
	}

	/**
	 * @return the bankCity
	 */
	public Short getBankCity() {
		return bankCity;
	}

	/**
	 * @param bankCity
	 *            the bankCity to set
	 */
	public void setBankCity(Short bankCity) {
		this.bankCity = bankCity;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the bankProvinceStr
	 */
	public String getBankProvinceStr() {
		return bankProvinceStr;
	}

	/**
	 * @param bankProvinceStr
	 *            the bankProvinceStr to set
	 */
	public void setBankProvinceStr(String bankProvinceStr) {
		this.bankProvinceStr = bankProvinceStr;
	}

	/**
	 * @return the bankCityStr
	 */
	public String getBankCityStr() {
		return bankCityStr;
	}

	/**
	 * @param bankCityStr
	 *            the bankCityStr to set
	 */
	public void setBankCityStr(String bankCityStr) {
		this.bankCityStr = bankCityStr;
	}

	/**
	 * @return the orderRemarks
	 */
	public String getOrderRemarks() {
		return orderRemarks;
	}

	/**
	 * @param orderRemarks
	 *            the orderRemarks to set
	 */
	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	/**
	 * @return the prioritys
	 */
	public Short getPrioritys() {
		return prioritys;
	}

	/**
	 * @param prioritys
	 *            the prioritys to set
	 */
	public void setPrioritys(Short prioritys) {
		this.prioritys = prioritys;
	}

	/**
	 * @return the memberType
	 */
	public Long getMemberType() {
		return memberType;
	}

	/**
	 * @param memberType
	 *            the memberType to set
	 */
	public void setMemberType(Long memberType) {
		this.memberType = memberType;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the memberTypeStr
	 */
	public String getMemberTypeStr() {
		if (memberType == 1)
			memberTypeStr = "个人";
		else if (memberType == 2)
			memberTypeStr = "企业";
		return memberTypeStr;
	}

	/**
	 * @return the memberAccTypeStr
	 */
	public String getMemberAccTypeStr() {
		return AcctTypeEnum.getAcctNameByCode(memberAccType.intValue());
	}

	/**
	 * @return the prioritysStr
	 */
	public String getPrioritysStr() {
		if (prioritys == 5)
			prioritysStr = "正常";
		else
			prioritysStr = "优先";
		return prioritysStr;
	}

	/**
	 * 0：工单初始 1：审核通过2：审核拒绝3：审核滞留4：复核通过5：复核拒绝6：清算拒绝7：工单生成批次成功 8：人工初审成功 9：人工初审失败
	 * 10：工单失败 11：完成 12：工单确认成功
	 */
	/**
	 * @return the statusStr
	 */
	public String getStatusStr() {
		switch (status.intValue()) {
		case 0:
			statusStr = "初始";
			break;
		case 1:
			statusStr = "审核通过";
			break;
		case 2:
			statusStr = "审核拒绝";
			break;
		case 3:
			statusStr = "审核滞留";
			break;
		case 4:
			statusStr = "复核通过";
			break;
		case 5:
			statusStr = "复核拒绝";
			break;
		case 6:
			statusStr = "清算拒绝";
			break;
		case 7:
			statusStr = "工单生成批次完成";
			break;
		case 8:
			statusStr = "人工审核成功";
			break;
		case 9:
			statusStr = "人工初审失败";
			break;
		case 10:
			statusStr = "工单失败 ";
			break;
		case 11:
			statusStr = "完成";
			break;
		case 12:
			statusStr = "工单确认成功";
			break;
		default:
			statusStr = "人工审核失败";
			break;
		}
		return statusStr;
	}

	/**
	 * @return the auditFailReason
	 */
	public String getAuditFailReason() {
		return auditFailReason;
	}

	/**
	 * @param auditFailReason
	 *            the auditFailReason to set
	 */
	public void setAuditFailReason(String auditFailReason) {
		this.auditFailReason = auditFailReason;
	}

	/**
	 * @return the reAuditBackReason
	 */
	public String getReAuditBackReason() {
		return reAuditBackReason;
	}

	/**
	 * @param reAuditBackReason
	 *            the reAuditBackReason to set
	 */
	public void setReAuditBackReason(String reAuditBackReason) {
		this.reAuditBackReason = reAuditBackReason;
	}

	/**
	 * @return the batchStatus
	 */
	public Integer getBatchStatus() {
		return batchStatus;
	}

	/**
	 * @param batchStatus
	 *            the batchStatus to set
	 */
	public void setBatchStatus(Integer batchStatus) {
		this.batchStatus = batchStatus;
	}

	/**
	 * @return the batchStatusStr
	 */
	public String getBatchStatusStr() {
		if (this.batchStatus == 1)
			batchStatusStr = "已归档";
		else
			batchStatusStr = "未归档";
		return batchStatusStr;
	}

	/**
	 * @return the bankKyStr
	 */
	public String getBankKyStr() {
		return bankKyStr;
	}

	/**
	 * @param bankKyStr
	 *            the bankKyStr to set
	 */
	public void setBankKyStr(String bankKyStr) {
		this.bankKyStr = bankKyStr;
	}

	/**
	 * @param bankKy
	 *            the bankKy to set
	 */
	public void setBankKy(String bankKy) {
		this.bankKy = bankKy;
	}

	public Long getCorderId() {
		return corderId;
	}

	public void setCorderId(Long corderId) {
		this.corderId = corderId;
	}

	public Long getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(Long parentOrderId) {
		this.parentOrderId = parentOrderId;
	}
	
}
