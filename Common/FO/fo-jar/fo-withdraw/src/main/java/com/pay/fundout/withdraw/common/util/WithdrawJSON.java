/** @Description 
 * @project 	poss-withdraw
 * @file 		JSON.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-12-01		Jonathen.Ni			Create 
 */
package com.pay.fundout.withdraw.common.util;

/**
 * <p>
 * 提现流程处理异常的JSON提示类
 * </p>
 * 
 * @author Jonathen.Ni
 * 
 */
public class WithdrawJSON {
	/**
	 * 操作是否成功
	 */
	private boolean isSuccess;

	/**
	 * 有异常的交易流水编号
	 */
	private String sequenceId;

	/**
	 *可能的异常原因
	 */
	private String reason;

	public static WithdrawJSON JsonBuilder() {
		return new WithdrawJSON();
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{isSuccess:").append(this.isSuccess).append(",sequenceId:'")
				.append(this.sequenceId).append("',reason:'").append(this.reason)
				.append("'}");
		return sb.toString();
	}
	
	public static void main(String[] args){
		String str = "111,222,333,444,";
		System.out.println(str.substring(0, str.length()-1));
	}
}
