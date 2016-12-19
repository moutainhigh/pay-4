/**
 *  File: WithdrawResManualProcQuery.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-20      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.flowprocess;

import com.pay.inf.model.BaseObject;

/**
 * <p>手工处理提现结果查询DTO</p>
 * @author Jonathen ni
 * @since 2010-09-20
 */
public class WithdrawResManualProcQueryDTO extends BaseObject {
 	/**
	 * 交易流水号
	 */
	private String sequenceId;
 
     /**
      * 批次文件文件名
      */
     private String batchName;
     
     /**
      * 批次文件号
      */
     private String batchNum;
     
     /**
      * 银行名称
      */
     private String bankKy;
     
     private Long busiType;
     
     public Long getBusiType() {
		return busiType;
	}

	public void setBusiType(Long busiType) {
		this.busiType = busiType;
	}

	/**
      * 8：人工初审成功
      * 9：人工初审失败  
      */
     private String  status;

	/**
	 * @return the sequenceId
	 */
	public String getSequenceId() {
		return sequenceId;
	}

	/**
	 * @param sequenceId the sequenceId to set
	 */
	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	/**
	 * @return the batchName
	 */
	public String getBatchName() {
		return batchName;
	}

	/**
	 * @param batchName the batchName to set
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	public String getBankKy() {
		return bankKy;
	}

	public void setBankKy(String bankKy) {
		this.bankKy = bankKy;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
