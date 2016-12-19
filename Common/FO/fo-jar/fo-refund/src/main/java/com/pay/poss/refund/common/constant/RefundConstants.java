 /** @Description 
 * @project 	poss-refund
 * @file 		RefundConstants.java 
 * Copyright © 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-25		sunsea.li		Create 
*/
package com.pay.poss.refund.common.constant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.pay.poss.refund.model.WebQueryRefundDTO;

/**
 * <p>充退管理相关常量的定义</p>
 * @author sunsea.li
 * @since 2010-8-25
 * @see
 */
public abstract class RefundConstants {
	/**
	 * other Constants Definition
	 */
	public static final String FILE_TYPE_UPLOAD = "filePath";
	
	/**
	 * sqlmapKey definition
	 */
	public static final String REFUND_KEY = "REFUND.";
	public static final String QUERY_TRADEORDERNO = REFUND_KEY+"queryTradeOrderNo";
	public static final String CREATE_REFUNDORDERM =REFUND_KEY + "createRefundOrderM";
	public static final String CREATE_REFUNDORDERD =REFUND_KEY + "createRefundOrderD";
	public static final String CREATE_REFUNDWORKORDER =REFUND_KEY + "createRefundWorkorder";
	public static final String UPDATE_REFUNDORDERM_BY_ACCEPTKY =REFUND_KEY + "updateRefundOrderMByAcceptKy";
	public static final String UPDATE_REFUNDORDERD_BY_DETAILKY =REFUND_KEY + "updateRefundOrderDByDetailKy";
	public static final String QUERY_REFUNDORDERM_BY_CONDITION =REFUND_KEY + "queryRefundOrderMByCondition";
	public static final String QUERY_REFUNDORDERDETAIL_BY_ACCEPTKY =REFUND_KEY + "queryRefundOrderDetailByAcceptKy";
	
	public static final String CREATE_REFUNDBANLANCECHECK =REFUND_KEY + "createRefundBanlanceCheck";
	public static final String QUERY_REFUNDBANLANCECHECK =REFUND_KEY + "queryRefundBanlanceCheck";
	public static final String UPDATE_REFUNDBANLANCECHECK =REFUND_KEY + "updateRefundBanlanceCheck";
	public static final String UPDATE_OR_CEATE_REFUNDBANLANCECHECK =REFUND_KEY + "updateOrCeateRefundBanlanceCheck";
	
	public static final String CREATE_REFUNDIMPORTFILE =REFUND_KEY + "createRefundImportFile";
	public static final String CREATE_REFUNDIMPORTRECORD =REFUND_KEY + "createRefundImportRecord";
	public static final String CALL_RECONCILEPROC =REFUND_KEY + "callReconcileProc";
	public static final String QUERY_RESULTSTATISTICS =REFUND_KEY + "queryResultStatistics";
	public static final String QUERY_REFUNDRECONCILERESULTLIST =REFUND_KEY + "queryrefundReconcileResultList";
	public static final String UPDATE_REFUNDRECONCILERESULT =REFUND_KEY + "updateRefundReconcileResult";
	public static final String QUERY_REFUNDIMPORTFILE =REFUND_KEY + "queryRefundImportFile";
	public static final String QUERY_BATCHFILEINFO =REFUND_KEY + "queryBatchFileInfo";
	public static final String UPDATE_BATCHFILEINFO =REFUND_KEY + "updateBatchFileInfo";
	public static final String DELETE_IMPORTEDFILE =REFUND_KEY + "deleteImportedFile";
	public static final String DELETE_IMPORTEDRECORD =REFUND_KEY + "deleteImportedRecord";
	public static final String DELETE_IMPORTEDRESULT =REFUND_KEY + "deleteImportedResult";
	public static final String QUERY_NEEDACCOUNTINGLIST =REFUND_KEY + "queryNeedAccountingList";
	public static final String QUERY_NEEDACCOUNTINGINFO =REFUND_KEY + "queryNeedAccountingInfo";
	public static final String UPDATE_DSTATUSAFTERACCOUNTING =REFUND_KEY + "updateDstatusAfterAccounting";
	public static final String UPDATE_MSTATUSWHENTASK =REFUND_KEY + "updateMstatusWhenTask";
	public static final String UPDATE_WORKORDERSTATUSWHENTASK =REFUND_KEY + "updateWorkorderStatusWhenTask";

	//add jason_wang
	public static final String QUERY_ASSING_TASK_INFO = REFUND_KEY + "queryAssignTaskInfo";
	public static final String QUERY_REFUND_DOWNLOAD_INFO = REFUND_KEY + "queryAssignTaskDownloadInfo";
	public static final String QUERY_ASSING_TASK_INFO_1 = REFUND_KEY + "queryAssignTaskInfo1";
	public static final String UPDATE_REFUND_STATUS_INFO = REFUND_KEY + "updateRefundStatusInfo";
	public static final String QUERY_REFUND_STATUS_INFO = REFUND_KEY + "updateRefundStatusInfo";
	public static final String STAT_REFUND_INFO = REFUND_KEY + "statRefundInfo";
	public static final String QUERY_REFUND_WORK_OPERATION_LOG = REFUND_KEY + "queryRefundWorkOperationLog";
	public static final String UPDATE_REFUND_WORK_OPERATION_LOG = REFUND_KEY + "updateRefundWorkOperationLog";
	public static final String UPDATE_REFUND_WORK_ORDER_STATUS = REFUND_KEY + "updateRefundWorkOrderStatus";
	public static final String UPDATE_REFUND_ORDER_M_STATUS = REFUND_KEY + "updateRefundOrderMStatus";
	public static final String UPDATE_REFUND_ORDER_D_STATUS = REFUND_KEY + "updateRefundOrderDStatus";
	public static final String REFUND_WORKFLOW_NAME = "refund";							//充退流程名称
	public static final String REFUND_WORKFLOW_NODE_ASSIGN_TASK = "assignTask";			//充退流程-任务分配
	public static final String REFUND_WORKFLOW_NODE_AUDIT = "audit";					//充退流程-审核
	public static final String REFUND_WORKFLOW_NODE_TEMPAUDIT = "tempTask";			//充退流程-审核滞留
	public static final String REFUND_WORKFLOW_NODE_REASSIGN_TASK = "reAssignTask";		//充退流程-任务分配
	public static final String REFUND_WORKFLOW_NODE_REAUDIT = "reAudit";				//充退流程-复核
	public static final String REFUND_WORKFLOW_NODE_LIQUIDATE = "liquidate";			//充退流程-清算
	public static final String STR_SPLIT_SEPARATOR = ",";								//任务分配信息字符分隔符
	public static final String STR_SPLIT_SEPARATOR_W = "##";							//工作流批注信息分隔符
	public static final String REFUND_WORKFLOW_TRANSITION_TO_REASSIGNTASK = "to_reAssignTask";			//至分配节点
	public static final String REFUND_WORKFLOW_TRANSITION_TO_TEMPTASK = "to_tempTask";			//至审核滞留
	public static final String REFUND_WORKFLOW_TRANSITION_TO_END = "flowEnd";	//流程结束
	
	public static final String REFUND_STATUS_0 = "0";		//工单初始状态
	public static final String REFUND_STATUS_1 = "1";		//审核通过
	public static final String REFUND_STATUS_2 = "2";		//审核拒绝
	public static final String REFUND_STATUS_3 = "3";		//审核滞留
	public static final String REFUND_STATUS_4 = "4";		//复核通过
	public static final String REFUND_STATUS_5 = "5";		//复核拒绝
	public static final String REFUND_STATUS_6 = "6";		//清算拒绝
	public static final String REFUND_STATUS_7 = "7";		//完成
	public static final String REFUND_STATUS_8 = "8";		//审核拒绝 原因拒付
	public static final String REFUND_STATUS_LIQUIDATE_ROLLBACK = "rollback";	//清算退回
	
	public static final String HANDLE_STATUS_CODE_0 = "0";	//代表操作通过
	public static final String HANDLE_STATUS_CODE_1 = "1";	//代表操作拒绝
	public static final String HANDLE_STATUS_CODE_2 = "2";	//代笔滞留
	
	public static final int REFUND_HANDLE_STATUS_101 = 101;	//充退处理中
	public static final int REFUND_HANDLE_STATUS_102 = 102;	//人工审核成功
	public static final int REFUND_HANDLE_STATUS_103 = 103;	//人工审核失败
	public static final int REFUND_HANDLE_STATUS_111 = 111;	//充退成功
	public static final int REFUND_HANDLE_STATUS_112 = 112;	//充退失败
	
	public static final int RES_WORKFLOW_DEFUALT_POSITION = 0;	//默认优先级
	public static final int RES_WORKFLOW_DEFUALT_STATUS = 1;	//默认状态 -有效
	//end jason_wang
	
	/**
	 * 一些常量的定义
	 */
	/**
	 * 批次文件的状态的定义
	 */
	public static final String FILE_STATUS_NOTCREATE = "1";			//1：文件未生成
	public static final String FILE_STATUS_CREATED = "2";			//2：文件已经生成
	public static final String FILE_STATUS_DOWNLOADED = "3";		//3：已下载
	public static final String FILE_STATUS_IMPORTED = "4";			//4：已导入
	public static final String FILE_STATUS_IMPORT_CONFIRMED = "5";	//5：已确认导入
	public static final String FILE_STATUS_DROPED = "6";			//6: 批次文件已废除  
	public static final String FILE_STATUS_HAND_DISPOSE = "7";		//7: 已手工处理
	
	/**
	 * 批次的状态的定义
	 */
	public static final String BATCH_STATUS_NOTCREATE = "1";		//1：批次未生成
	public static final String BATCH_STATUS_CREATED = "2";			//2：批次已生成
	public static final String BATCH_STATUS_DROPED = "3";			//3：已废除批次文件
	public static final String BATCH_STATUS_FILECREATED = "4";		//4：已出批次文件
	public static final String BATCH_STATUS_FELENOTCREATED = "5";	//5：未出批次文件
	
	/**
	 * 生成的文件的类型的定义
	 */
	public static final String FILE_TYPE_BATCH_BUSI = "11";			//11：内部概要文件（批次汇总概要文件）
	public static final String FILE_TYPE_BATCH = "12";				//12：内部详细文件
	public static final String FILE_TYPE_BANK_BUSI = "21";			//21：外部概要文件（提交银行概要文件）
	public static final String FILE_TYPE_BANK = "22";				//22：外部详细文件
	
	public static final String BATCH_TYPE_WITHDRAW = "200002";		//200002:提现
	public static final String BATCH_TYPE_REFUND = "200001";		//200001：充退
	
	public static final String UPLOAD_SUCCESS = "1"; 				//上传成功
	public static final String UPLOAD_FAILURE = "2"; 				//上传失败
	public static final String PARSER_SUCCESS = "3"; 				//解析成功
	public static final String PARSER_FAILURE  = "4"; 				//解析失败
	public static final String IMPORT_DB_SUCCESS = "5"; 			//导入数据库成功
	public static final String IMPORT_DB_FAILURE = "6"; 			//导入数据库失败
	public static final String RECONCILE_SUCCESS = "7"; 			//对账完成
	public static final String RECONCILE_FAILURE = "8"; 			//对账失败
	public static final String WATI_AUDIT = "1";					//待审核（申请中）

	public static final String BANK_MANY = "200"; 					//银行多
	public static final String SYS_MANY = "210"; 					//支付多
	public static final String MATCH = "100"; 						//平账
	public static final String NO_MATCH = "220"; 					//完全不匹配
	
	public final static Map<String,WebQueryRefundDTO> entryRefundPage (){
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		webQueryRefundDTO.setStartTime(new Date());
		webQueryRefundDTO.setEndTime(new Date());
		final Map<String,WebQueryRefundDTO> map = new HashMap<String, WebQueryRefundDTO>();
		map.put("webQueryRefundDTO", webQueryRefundDTO);
		return map;
	}
}
