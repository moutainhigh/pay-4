package com.pay.fundout.withdraw.common.util;

/**		
 *  @author lIWEI
 *  @Date 2011-4-23
 *  @Description
 *  @Copyright Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
public class FundoutConstants {
	/*
	 * 批次文件状态
	*/
	public static final int FILE_NOT_CREAT = 1;				//文件未生成
	public static final int FILE_CREATED = 2;				//文件已生成
	public static final int FILE_DOWNLOADED = 3;			//已下载
	public static final int FILE_RESULT_IMPORTED = 4;		//已导入结果文件
	public static final int FILE_IMPORT_CONFIRMED = 5;		//已确认导入
	public static final int FILE_DROPED = 6;				//批次文件已废除
	public static final int FILE_HAS_HANDPRCEEDED = 7;		//已手工处理
	public static final int FILE_REVIEWED = 8;				//已确认复核
	public static final int FILE_REVIEW_IMPORTED = 9;		//已导入复核文件
}
