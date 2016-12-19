/** @Description 
 * @project 	poss-base
 * @file 		Constants.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-7-28		Henry.Zeng			Create 
 */
package com.pay.poss.base.common;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 公共常量定义
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-7-28
 * @see
 */
public class Constants {
	public static final String PASSWORD_ENCODER = "POSS.PASSWORDENCODER";
	public static final String TASKMGR_SERVICE = "POSS.TASKMANAGER";

	public final static String COL_SPLIT = ":";
	public final static String ROW_SPLIT = ";";

	// 状态
	public final static int STATUS_VALID = 1;
	public final static int STATUS_UNVALID = 0;

	// 文件类型
	public final static int FILE_TYPE_INNER_M = 11;// 内部概要文件
	public final static int FILE_TYPE_INNER_D = 12;// 内部详细文件
	public final static int FILE_TYPE_OUTER_M = 21;// 外部概要文件
	public final static int FILE_TYPE_OUTER_D = 22;// 外部详细文件

	// 批次类型
	public final static int BATCH_BUILD_AUTO = 1;// 自动构建批次
	public final static int BATCH_BUILD_MANUAL = 2;// 手工构建批次
	public final static int BATCH_REBUILD = 3;// 重新构建批次
	public final static int BATCH_FILE_BUILD = 4;// 构建批次文件
	public final static int BATCH_ALERT_BUILD = 5;// 预警批次

	// 业务类型
	public final static String TXN_TYPE_REFUND = "200001"; // 充退
	public final static String TXN_TYPE_WITHDRAW = "200002"; // 提现
	public final static String TXN_TYPE_RECONCILE = "800001"; // 对账

	public final static String TASK_TYPE_REFUND = "200001"; // 充退
	public final static String TASK_TYPE_WITHDRAW = "200002"; // 提现

	public final static int BATCH_RULE_SYSTEM = 1;// 系统规则
	public final static int BATCH_RULE_MANUAL = 2;// 手工规则

	// 交易渠道
	public final static int TXN_CHANNEL_WEB = 1;
	public final static int TXN_CHANNEL_WAP = 2;
	public final static int TXN_CHANNEL_PHONE = 3;

	// 流程节点编号
	public final static String WF_NODE_SHANGCHUANG = "WF_SC"; // 上传节点
	public final static String WF_NODE_SHENHE = "WF_SHENHE"; // 审核节点
	public final static String WF_NODE_FUHE = "WF_FUHE"; // 复核节点
	public final static String WF_NODE_QINGSUAN = "WF_QINGSUAN"; // 清算节点
	public final static String WF_NODE_FENPEI = "WF_FENPEI"; // 分配节点
	public final static String WF_NODE_ZHILIU = "WF_ZHILIU"; // 滞留节点

	// 订单状态
	public final static int ORDER_STATUS_INIT = 101; // 订单初始
	public final static int ORDER_STATUS_SUCC = 111; // 订单成功
	public final static int ORDER_STATUS_FAIL = 112; // 订单失败

	public final static String SYSTEM_MAIL = "pay@pay.com";

	// 复核状态码及描述
	public final static Map<Integer, String> REVIEW_STATUS = new HashMap<Integer, String>();
	static {
		REVIEW_STATUS.put(1, "未复核");
		REVIEW_STATUS.put(2, "复核通过");
		REVIEW_STATUS.put(3, "复核拒绝");
	}
}
