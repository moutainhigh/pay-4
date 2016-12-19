/**
 *  File: BankRefundWFParameter.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-1      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.bankrefund.impl;

/**
 * @author bill_peng
 *
 */
public class BankRefundWFParameter {
	/**
	 * 退款订单号
	 */
	private String orderId;
	/**
	 * 当前节点名称
	 */
	private String currentNode;
	/**
	 * 流程实例Id 
	 */
	private String wfInstanceId;
	/**
	 * 备注信息
	 */
	private String remark;
	/**
	 * 操作员
	 */
	private String operator;
	/**
	 * 操作类型
	 */
	private String option;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}

	public String getWfInstanceId() {
		return wfInstanceId;
	}

	public void setWfInstanceId(String wfInstanceId) {
		this.wfInstanceId = wfInstanceId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
	

}
