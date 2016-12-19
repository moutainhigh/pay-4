/**
 * 
 */
package com.pay.util;

/**
 * @author PengJiangbo
 *
 */
public class StringUtils {

	public static String isNotNull(String str){
		return str == null ? "" : str ;
	}
	
	public static int StringToInt(String str){
		if("".equals(isNotNull(str))){
			return 0 ;
		}
		return Integer.parseInt(isNotNull(str)) ;
	}
	
	public static long stringToLong(String str){
		if("".equals(isNotNull(str))){
			return 0l;
		}
		return Long.parseLong(isNotNull(str)) ;
	}
	
}
