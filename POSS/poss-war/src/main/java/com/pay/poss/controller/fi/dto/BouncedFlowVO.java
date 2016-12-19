package com.pay.poss.controller.fi.dto;

import java.util.Date;

public class BouncedFlowVO {
	
	/**
	 * 拒付订单号
	 */
	private String orderId;
	/**
	 * 审批状态：0未处理 1已上传资料2已递交银行3申诉失败待复核4申诉失败待复核5申诉失败6申诉成功7不申诉
	 */
	private String status;
	/**
	 * 操作员
	 */
	private String operator;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 批次号
	 */
	private String batchNo;
	/**
	 * 创建时间
	 */
	private Date  createDate;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
