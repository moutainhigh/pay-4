/**
 *  File: LogUtil.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-22      zliner      Changes
 *  
 *
 */
package com.pay.poss.base.util.log;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.util.StringUtil;

/**
 * @author zliner
 *
 */
public class LogUtil {
	/**
	 * 发生时间 组别 业务操作简单描述 业务操作状态 业务标识 附加业务字段 异常信息 错误代码 错误信息 |2010-01-28
	 * 12:01:23|FO|withdraworder
	 * checkPwd|Start|3644122|merchantId=100224999701
	 * |(没有异常信息空)|(没有错误代码为空)|(没有错误信息为空)|
	 */
	private static final MessageFormat LOG_INFO_FORMAT = new MessageFormat(
			"{0} - |{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}");

	/**
	 * 日志的时间格式.
	 */
	private static final DateFormat LOG_INFO_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 组别.
	 */
	private static final String TEAM = "FO";

	/**
	 * 记录日志.
	 * 
	 * @param clazz
	 *            要记录日志的类
	 * @param bizDesc
	 *            业务操作简单描述
	 * @param status
	 *            业务操作状态
	 * @param identity
	 *            业务标识
	 * @param additionalQueryString
	 *            附加业务字段
	 */
	public static final void info(Class clazz, String bizDesc, OPSTATUS status,
			String identity, String additionalQueryString ) {
		try {
			String stackTrace = getLineInfo(new Throwable().getStackTrace()[1]);
			// 针对某类记录日志
			doLog(LEVEL.INFO, clazz, stackTrace, bizDesc, status, identity,
					additionalQueryString, null, null, null);
		} catch (Exception ex) {
			// 捕获记录日志异常，不影响业务
		}
	}
	
	/**
     * return current java file name and code line name
     * @return String
     */
    private static final String getLineInfo(StackTraceElement ste4) {
        return (ste4.getClassName()+"."+ste4.getMethodName() + ":" + (ste4.getLineNumber()));
    }

	/**
	 * 记录日志.
	 * 
	 * @param clazz
	 *            要记录日志的类
	 * @param bizDesc
	 *            业务操作简单描述
	 * @param status
	 *            业务操作状态
	 * @param identity
	 *            业务标识
	 * @param additionalQueryString
	 *            附加业务字段
	 * @param exceptionMsg
	 *            异常信息
	 * @param failCode
	 *            错误代码
	 * @param failMsg
	 *            错误信息
	 */
	public static final void debug(Class clazz, String bizDesc,
			OPSTATUS status, String identity, String additionalQueryString,
			String exceptionMsg, String failCode, String failMsg) {
		try {
			String stackTrace = getLineInfo(new Throwable().getStackTrace()[1]);
			// 针对某类记录日志
			doLog(LEVEL.DEBUG, clazz, stackTrace, bizDesc, status, identity,
					additionalQueryString, exceptionMsg, failCode, failMsg);
		} catch (Exception ex) {
			// 捕获记录日志异常，不影响业务
		}
	}

	/**
	 * 记录日志.
	 * 
	 * @param clazz
	 *            要记录日志的类
	 * @param bizDesc
	 *            业务操作简单描述
	 * @param status
	 *            业务操作状态
	 * @param identity
	 *            业务标识
	 * @param additionalQueryString
	 *            附加业务字段
	 * @param exceptionMsg
	 *            异常信息
	 * @param failCode
	 *            错误代码
	 * @param failMsg
	 *            错误信息
	 */
	public static final void warn(Class clazz, String bizDesc, OPSTATUS status,
			String identity, String additionalQueryString, String exceptionMsg,
			String failCode, String failMsg) {
		try {
			String stackTrace = getLineInfo(new Throwable().getStackTrace()[1]);
			// 针对某类记录日志
			doLog(LEVEL.WARN, clazz, stackTrace, bizDesc, status, identity,
					additionalQueryString, exceptionMsg, failCode, failMsg);
		} catch (Exception ex) {
			// 捕获记录日志异常，不影响业务
		}
	}

	/**
	 * 记录日志.
	 * 
	 * @param clazz
	 *            要记录日志的类
	 * @param bizDesc
	 *            业务操作简单描述
	 * @param status
	 *            业务操作状态
	 * @param identity
	 *            业务标识
	 * @param additionalQueryString
	 *            附加业务字段
	 * @param exceptionMsg
	 *            异常信息
	 * @param failCode
	 *            错误代码
	 * @param failMsg
	 *            错误信息
	 */
	public static final void error(Class clazz, String bizDesc,
			OPSTATUS status, String identity, String additionalQueryString,
			String exceptionMsg, String failCode, String failMsg) {
		try {
			String stackTrace = getLineInfo(new Throwable().getStackTrace()[1]);
			// 针对某类记录日志
			doLog(LEVEL.ERROR, clazz, stackTrace, bizDesc, status, identity,
					additionalQueryString, exceptionMsg, failCode, failMsg);
		} catch (Exception ex) {
			// 捕获记录日志异常，不影响业务
		}
	}

	/**
	 * 执行日志记录.
	 * 
	 * @param clazz
	 *            要记录日志的类
	 * @param bizDesc
	 *            业务操作简单描述
	 * @param status
	 *            业务操作状态
	 * @param identity
	 *            业务标识
	 * @param additionalQueryString
	 *            附加业务字段
	 * @param exceptionMsg
	 *            异常信息
	 * @param failCode
	 *            错误代码
	 * @param failMsg
	 *            错误信息
	 */
	private static final void doLog(LEVEL level, Class clazz, String stackTrace, String bizDesc,
			OPSTATUS status, String identity, String additionalQueryString,
			String exceptionMsg, String failCode, String failMsg) {
		if (clazz == null) {
			return;
		}
		String dateTime = LOG_INFO_DATE_FORMAT.format(new Date());
		log(level, clazz, stackTrace, dateTime, TEAM, bizDesc, status.getValue(), identity,
				additionalQueryString, exceptionMsg, failCode, failMsg);
	}

	/**
	 * 记录日志.
	 * 
	 * @param clazz
	 *            要记录日志的类
	 * @param params
	 *            日志的参数
	 */
	private static final void log(LEVEL level, Class clazz, String... params) {
		String message = getLogMessage(params);
		Log loggerContext = LogFactory.getLog(clazz);
		System.out.println("message is:" + message);
		if(level.getValue() == LEVEL.INFO.getValue()) {
			loggerContext.info(message);
		}else if (level.getValue() == LEVEL.ERROR.getValue()) {
			loggerContext.error(message);
		}
	}

	/**
	 * 根据日志参数得到日志记录的内容.
	 * 
	 * @param params
	 *            日志参数
	 * @return 日志记录的内容
	 */
	private static final String getLogMessage(String... params) {
		// 构造日志参数
		String[] args = buildLogArgs(params);
		// 构造日志内容
		String message = buildLogMessage(args);
		return message;
	}

	/**
	 * 构造日志参数,null被转换为"".
	 * 
	 * @param params
	 *            原始日志参数
	 * @return 日志参数，null被转换为""
	 */
	private static final String[] buildLogArgs(String... params) {
		String[] args = null;
		if (params != null && params.length > 0) {
			int paramsSize = params.length;
			args = new String[params.length];
			for (int i = 0; i < paramsSize; i += 1) {
				args[i] = StringUtil.null2String(params[i]);
			}
		}
		return args;
	}

	/**
	 * 构造日志内容.
	 * 
	 * @param args
	 *            日志内容的参数
	 * @return 返回日志内容
	 */
	private static final String buildLogMessage(String... args) {
		return LOG_INFO_FORMAT.format(args);
	}
	public static void main(String[] args) {
		LogUtil.info(OPSTATUS.class,"通知商户处理中关于信用卡还款及转账业务通知处理", OPSTATUS.START, "20010013246664564","order_seqid=30000&status=111");
		LogUtil.error(OPSTATUS.class,"通知商户处理中关于信用卡还款及转账业务通知处理", OPSTATUS.EXCEPTION, "20010013246664564","order_seqid=30000&status=111", "java.lang.NullPointException:XXXXXX", "1003", "订单不存在");
		System.out.println();
	}
}
