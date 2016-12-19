package com.pay.txncore.dto;

import java.util.Date;

import com.pay.inf.model.BaseObject;

public class ReconcileImportRecordBatchDto extends BaseObject{
	/***
	 * 批次号
	 */
	private String bacthNo;
	/**
	 * 申请笔数
	 */
	private Integer applyCount;
	/**
	 * 成功笔数
	 */
	private Integer successCount;
	/***
	 * 是否已对账
	 */
	private Integer status;
	/***
	 * 创建时间
	 */
	private Date createDate;
	/***
	 * 渠道
	 */
	private String orgCode;
	/***
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 操作人
	 */
	private String operator;


	public void setBacthNo(String bacthNo) {
		this.bacthNo = bacthNo;
	}

	public Integer getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(Integer applyCount) {
		this.applyCount = applyCount;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBacthNo() {
		return bacthNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
