/**
 * 
 */
package com.pay.fi.chain.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * @author PengJiangbo
 *
 */
public class PayLinkUtil {

	/**
	 * 生成payLinkName[基本路径,上下文]
	 * @param request
	 * @return
	 */
	public static String generatePayLinkName(HttpServletRequest request){
		if(null != request){
			int port = request.getServerPort() ;
			StringBuilder sb = new StringBuilder() ;
			sb.append(request.getScheme()) ;
			sb.append("://") ;
			sb.append(request.getServerName()) ;
			if(port != 80 && port != 443){
				sb.append(":").append(port) ;
			}
			String path = request.getContextPath() ;
			sb.append(path) ;
			return sb.toString() ;
		}else{
			return null ;
		}
	}
	
	/**
	 * 生成失效时间
	 * @param invalidTimeLong
	 * @return
	 */
	public static Timestamp genrateInvalidTime(int invalidTimeLong){
		Calendar calendar = Calendar.getInstance() ;
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + invalidTimeLong);
		Date invalidDate = calendar.getTime() ;
		Timestamp invalidTime = new Timestamp(invalidDate.getTime()) ;
		return invalidTime ;
	}
	/**
	 * 日期转格式化字符串
	 * @param date
	 * @return
	 */
	public static String date2Str(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		String strDate = sdf.format(date) ;
		return strDate ;
	}
	
}
