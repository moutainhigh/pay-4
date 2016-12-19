/**
 *  File: OperatorlogDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-01      Jonathen.Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.operatorlog;

import java.util.Date;

/**
 * 提现流程本地日志类
 * @author Jonathen.Ni
 *
 */
public class WithdrawWFOpLogDTO {
	//序号
	
	private long Id;
	
	/**操作员*/
	private String operator;
	
	/**节点名称*/
	private String node;
	
	/**操作状态*/
	private String state;
	
	/**创建时间*/
	private Date createDate;
	
	/**交易备注*/
	private String remark;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
