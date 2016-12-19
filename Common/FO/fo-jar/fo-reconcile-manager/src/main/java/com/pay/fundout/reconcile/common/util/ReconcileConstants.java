/**
 *  File: ReconcileConstants.java
 *  Description:对账常量定义
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-17    jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.reconcile.common.util;

public class ReconcileConstants {

    public static final int RECONCILE_FILE_STATUS_1 = 1;	//上传成功
    public static final int RECONCILE_FILE_STATUS_2 = 2;	//上传失败
    public static final int RECONCILE_FILE_STATUS_3 = 3;	//解析成功
    public static final int RECONCILE_FILE_STATUS_4 = 4;	//解析失败
    public static final int RECONCILE_FILE_STATUS_5 = 5;	//导入成功
    public static final int RECONCILE_FILE_STATUS_6 = 6;	//导入失败
    public static final int RECONCILE_FILE_STATUS_9 = 9;	//已废除
    
    public static final String WITHDRAW_BUSI_TYPE = "0";	//业务类型
    public static final String START_TIME = " 00:00:00";
    public static final String END_TIME = " 23:59:59";
    
    public static final String PARAMETER_SEPARATOR_1 = "##";	//分隔符
    public static final String PARAMETER_SEPARATOR_2 = ",";
    public static final String RECONCILE_PROCEDURE_STAUTS_SUCCESS = "01";
    public static final String RECONCILE_PROCEDURE_STAUTS_FAILED = "00";
    
    public static final int DECIMAL_DIGIT_DEFAULT = 0;		//保留小数点的位数
}
