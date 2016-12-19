/** @Description 
 * @project 	poss-base
 * @file 		Constants.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-7-28		Henry.Zeng			Create 
 */
package com.pay.fundout.withdraw.common.util;

/**
 * <p>
 * 公共常量定义
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-7-28
 * @see
 */
public class WithDrawConstants {
	public static final String FILE_SERVICE_NAME_SPANCE = "wdfileservice.";
	
	public static final String FAILURE_INVAILD_BATCH_INFO = "该批次不处于可废除状态,不能废除";
	
	public static final String SCUESS_INVAILD_BATCH_INFO = "该批次已经废除";
	
	public static final String FAILURE_GENERATE_BATCH_FILE ="手工生成批次文件失败";
	
	/**
	 * 风控审核标记
	 */
	 public static final String  RISK_AUDIT = "1"; 
	 
	 /**
	  * 风控复核标记
	  */
	 public static final String RISK_SECOND_AUDIT = "2";
	 
	 /**
	  * 审核节点
	  */
	 public static final String  AUDIT_NODE = "audit"; 
	 
	 /**
	  * 提交到审核节点
	  */
	 //public static final String  ACTION_AUDIT_NODE = "to_audit"; 
	 
	 /**
	  * 滞留
	  */
	 public static final String  DELAY = "tempTask";
	 /**
	  * TO滞留
	  */
	 public static final String  TO_DELAY = "to_tempTask";
	 
	 
	 /**
	  * 复核节点
	  */
	 public static final String SECOND_AUDIT_NODE = "reAudit";
	 
	 /**
	  * 风控任务分配节点
	  */
	 public static final String TASK_ALLOCATE_NODE = "allocate";
	 
	 /**
	  * 结算节点
	  */
	 public static final String LIQUIDATE_NODE = "liquidate";
	 
	 /**
	  * 进行中数据查询节点
	  */
	 public static final String PROCESSING_NODE = "processing";
	 
	 /**
	  * 手工审核节点
	  */
	 public static final String MANUAL_FIRST_AUDIT_NODE = "manualFirstAudit";
	 
	 /**
	  * 手工复核节点
	  */
	 public static final String MANUAL_SECOND_AUDIT_NODE = "manualSecondAudit";
	 
	 /**
	  * 审核分配任务
	  */
	 public static final String TASK_ASSIGN_AUDIT_NODE = "assignTask";
	 
	 
	 /**
	  * 复核分配任务
	  */
	 public static final String TASK_REASSIGN_REAUDIT_NODE = "reAssignTask";
	 /**
	  * 审核通过
	  */
	 public static final int AUDIT_SUCCESS = 1;
	 
	 /**
	  * 审核拒绝
	  */
	 public static final int AUDIT_REJECT = 2;
	 
	 /**
	  * 审核滞留
	  */
	 public static final int AUDIT_WAIT = 3;
	 
	 /**
	  * 初始状态
	  */
	 public static final int AUDIT_INIT = 0;
	 
	 /**
	  * 复核通过
	  */
	 public static final int SECOND_AUDIT_SUCCESS = 4;
	 
	 /**
	  * 复核拒绝
	  */
	 public static final int SECOND_AUDIT_REJECT = 5;
	 
	 /**
	  * 清算拒绝
	  */
	 public static final int LIQUIDATE_AUDIT_REJECT = 6;
	 
	 /**
	  * 工作流名称
	  */
	 public static final String PROCESS_NAME = "withdraw";
	 
	 /**
	  * 审核通过标示
	  */
	 public static final int FIRST_AUDIT_SUCCESS = 1;
	 
	 /**
	  * 审核拒绝标示
	  */
	 public static final int FIRST_AUDIT_REJECT = 2;
	 
	 /**
	  * 审核滞留标示
	  */
	 public static final int FIRST_AUDIT_WAIT = 3;
	 
	 /**
	  * 工作流审核同意
	  */
	 public static final int FIRST_AUDIT_HANDLE_AGREE = 0;
	 
	 /**
	  * 工作流审核拒绝 
	  */
	 public static final int FIRST_AUDIT_HANDLE_REJECT = 1;
	 
	 /**
	  * 流程跳转
	  */
	 public static final int SKIP_WORKFLOW = 9;
	 
	 /**
	  * 复核同意状态
	  */
	 public static int SECOND_AUDIT_AGREE = 0;
	 
	 /**
	  * 复核退回状态
	  */
	 public static int SECOND_AUDIT_BACK = 1;
	 
	 /**
	  * 结算退回
	  */
	 public static final int LIQUIDATE_BACK = 1;
	 
	 /**
	  * 结算拒绝
	  */
	 public static final int LIQUIDATE_REJECT = 0;
	 
	 /**
	  * 人工初审成功
	  */
	 public static final int MANUAL_FITST_AUDIT_SUCC = 1;
	 
	 /**
	  * 人工初审失败
	  */
	 
	 public static final int MANUAL_FITST_AUDIT_FAIL = 0;
	 

	 /**
	  * 人工复核同意
	  */
	 public static final int MANUAL_SECOND_AUDIT_AGREE = 1;
	 
	 /**
	  * 人工复核退回
	  */
	 
	 public static final int MANUAL_SECOND_AUDIT_BACK = 0;

	 
	 /**
	  * 复核同意
	  */
	 public static final String SECOND_AUDIT_AGREE_HANDLE_TYPE = "agree";
	 
	 /**
	  * 复核退回
	  */
	 public static final String SECOND_AUDIT_BACK_HANDLE_TYPE = "rollback";
	 
	 /**
	  * 结算退回
	  */
	 public static final String LIQUIDATE_AUDIT_BACK_HANDLE_TYPE = "rollback";
	 
	 /**
	  * 结算拒绝
	  */
	 public static final String LIQUIDATE_AUDIT_REJECT_HANDLE_TYPE = "flowEnd";
	 
	 /**
	  * 手工处理体现结果 人工审核节点
	  */
	 public static final String WITHDRAW_RESULT_PROCESS_MANUAL_AUDIT_NODE = "audit";  
	 
	 /**
	  * 手工处理体现结果 人工复核节点
	  */
	 public static final String WITHDRAW_RESULT_PROCESS_MANUAL_REAUDIT_NODE = "reAudit";  
	 
	 /**
	  * 手工处理体现结果 人工审核通过
	  */
	 public static final int WITHDRAW_RESULT_PROCESS_MANUAL_AUDIT_SUCCESS = 8;
	 
	 /**
	  * 手工处理体现结果 人工审核失败
	  */
	 public static final int WITHDRAW_RESULT_PROCESS_MANUAL_AUDIT_FAIL = 9;
	 
	 /**
	  * 工单生成批次成功 
	  */
	 public static final int GENERATE_BATCHNUM_SUCCESS = 7;
	 
	 /**
	  * 工单确认成功
	  */
	 public static final int CONFIRM_SUCCESS = 12;
	 
	 /**
	  * 工单失败
	  */
	 public static final int WORKORD_ERROR = 10;
	 
	 /**
	  * 未生成批次
	  */
	 public static final int BATCH_NUM_UNGENERATE = 0;
	 
	 /**
	  * 废除批次
	  */
	 public static final int BATCH_NUM_REVOKE = 2;
	 
	 /**
	  * 提现工作流任务分配角色代码
	  */
	 public static final String WITHDRAW_WORKFLOW_ROLECODE_ASSIGN = "assign001";
	 
	 /**
	  * 提现工作流审核角色代码
	  */
	 public static final String WITHDRAW_WORKFLOW_ROLECODE_AUDIT = "audit001";
	 
	 /**
	  * 提现工作流复核角色代码
	  */
	 public static final String WITHDRAW_WORKFLOW_ROLECODE_REAUDIT = "reAudit001";
	 
	 /**
	  * 提现工作流清结算角色代码
	  */
	 public static final String WITHDRAW_WORKFLOW_ROLECODE_LIQUIDATE = "liquidate001";

	 /**
	  * 提现批次文件状态-已导入
	  */
	 public static final int BATCH_FILE_STATUS_4 = 4;
	 /**
	  * 提现批次文件状态-已确认导入
	  */
	 public static final int BATCH_FILE_STATUS_5 = 5;
	 /**
	  * 提现批次文件状态-已复核
	  */
	 public static final int BATCH_FILE_STATUS_8 = 8;
	 /**
	  * 提现批次文件状态-已导入复核
	  */
	 public static final int BATCH_FILE_STATUS_9 = 9;
	 
	 /**
	  * 内部概要文件类型
	  */
	 public static final String BATCH_FILE_TYPE_11 = "11";
	 
	 /**
	  * 内部详细文件类型
	  */
	 public static final String BATCH_FILE_TYPE_12 = "12";
	 
	 /**
	  * 银行概要文件类型
	  */
	 public static final String BATCH_FILE_TYPE_21 = "21";
	 
	 /**
	  * 银行详细文件类型
	  */
	 public static final String BATCH_FILE_TYPE_22 = "22";
	 
	 /**
	  * 出款自动过分控等待风险审核处理状态
	  */
	 public static final int AUTO_RISK_TO_DEAL = 16;
}
