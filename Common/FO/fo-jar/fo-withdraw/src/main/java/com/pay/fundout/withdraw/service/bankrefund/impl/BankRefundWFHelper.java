/**
 *  File: BankRefundWFHelper.java
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
public class BankRefundWFHelper {
	
	public final static String PROCESS_NAME="withdrawrefundment";
	
	 /**
	  * 审核节点
	  */
	 public static final String  AUDIT_NODE = "audit"; 
	 
	 
	 /**
	  * 审核通过
	  */
	 public static final String AUDIT_SUCCESS = "success";
	 
	 /**
	  * 审核拒绝
	  */
	 public static final String AUDIT_REJECT = "reject";

	 
	 /**
	  * 用户登录名
	  */
	 public static final String WF_USERID = "userId";
	 
	 /**
	  * 当前节点名
	  */
	 public static final String WF_NODENAME = "nodeName";
	 
	 /**
	  * 流程名称
	  */
	 public static final String WF_PROCESSKEY = "processKey";
	 
	 /**
	  * 指定授权人
	  */
	 public static final String WF_ASSIGNER = "assigner";
	 
	 /**
	  * 批注
	  */
	 public static final String WF_TASKMESSAGE = "taskMessage";
	 
	 /**
	  * 工作流程实例ID
	  */
	 public static final String WF_PROCESSINSTANCEID = "processInstanceId";
	 
	 /**
	  * 上一个节点操作 人
	  */
	 public static final String WF_PREVIOUSUSER = "previousUser";
}
