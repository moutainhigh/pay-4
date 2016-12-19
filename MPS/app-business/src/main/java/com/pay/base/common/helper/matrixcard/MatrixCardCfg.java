package com.pay.base.common.helper.matrixcard;

import java.io.Serializable;

/**
 * @author jim_chen
 * @version 2010-9-15
 */
public class MatrixCardCfg implements Serializable {

	private static final long serialVersionUID = 3061872159322123827L;

//	// 同一ip一天100张
//	public int reqIpLimit = 100;	
//
//	// 同一张卡最高使用数次
//	public int matrixCardUserLimit = 1000;

	// 同一session1分钟内不能重发
	public static final int REQ_SESSION_EXP_LIMIT = 1;

	// 5次验证上限
	public static int VFY_RETRY_LIMIT = 5;

	// token有效时间
	public static int VFY_TOKEN_EXPIRED = 10;

	// 绑定新卡有效期30天
	public final static int BIND_DATE_LINE = 30;

	// 口令卡行数
	public final static int ROWS = 7;

	// 口令卡列数
	public final static int COLS = 10;
	
}
