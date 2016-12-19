/**
 * 
 */
package com.pay.batchpay.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 简单的日期时间工具类
 * @author PengJiangbo
 *
 */
public class SimpleDateUtil {
	
	/**
	 * 获取时间戳
	 * @param date
	 * @return
	 */
	public static String getTimeStamp(Date date){
		String timeStamp = "" ;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss") ;
			timeStamp = sdf.format(date) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeStamp ;
	}
	
	public static Date stringToDate(String str){
		Date date = null ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
		try {
			date = sdf.parse(str) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date ;
	}
	
	public static void main(String[] args) {
		System.out.println(stringToDate("2015-09-06"));
	}
	
	public static String yyyyMMddFormat(Date date){
		String str = "" ;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
			str = sdf.format(date) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str ;
	}
}
