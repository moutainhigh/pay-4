package com.pay.poss.base.exception.enums;
/**
 * @Description 异常代码枚举
 * @project 	poss-base
 * @file 		ExceptionCodeEnum.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-7-24		Rick.Lv			Create
 */
public enum ExceptionCodeEnum {

	UN_KNOWN_EXCEPTION("0001", "未知异常"),
	UN_DEFINE_EXCEPTIONCODE("9001", "未定义异常编码"),
	ILLEGAL_PARAMETER("0002", "参数错误"),
	DATA_ACCESS_EXCEPTION("0003", "数据访问错误"),
	NOT_FOUND_EXCEPTION("0004", "记录不存在"),
	NOT_FOUND_FILE("0005","找不到文件"),
	UNSUPPORTED_ENCODING("0006","不支持的编码类型"),
	IO_EXCEPTION("0007","IO流操作异常"),
	CONTEXT_EXCEPTION("0008","Context异常"),
	TASK_EXCEPTION("0009","Task异常"),
	SEND_WORKFLOW_AUDIT_EXCEPTION("0010","提交审核工作流异常"),
	SEND_WORKFLOW_SECOND_AUDIT_EXCEPTION("0020","提交复核工作流异常"),
	WORK_ORDER_AUDIT_STATUS_EXCEPTION("0030","审核状态异常"),
	SEND_WORKFLOW_LIQUIDATE_EXCEPTION("0040","结算工作流异常"),
	START_WORKFLOW_EXCEPTION("0050","工作流启动异常"),
	ALLOCATE_WORKFLOW_EXCEPTION("0060","工作流任务分配异常"),
	WITHDRAW_RESULT_MANUAL_PROCESS_EXCEPTION("0070","手工处理提现结果异常"),
	EXPORT_ERROR("0080","导出EXCEL异常"), 
	ACCTOUNTING_PROCESS_EXCEPTION("0090","记账异常"),
	CRATE_MASSPAYTOBANK_DETAILORDER_EXCEPTION("0100","生成批量付款到银行明细订单异常");
	
	
	private final String code;
    private final String description;
    
    /**
     * 私有构造方法
     * @param code
     * @param description
     */
    private ExceptionCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     * @param code
     * @return
     */
    public static ExceptionCodeEnum valueof(String code) {
        for (ExceptionCodeEnum exceptionCode : values()) {
            if (exceptionCode.getCode().equals(code)) {
                return exceptionCode;
            }
        }
        return UN_DEFINE_EXCEPTIONCODE;
    }
}
