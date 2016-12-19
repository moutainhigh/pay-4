/**
 * 
 */
package com.pay.batchpay.util;

import java.util.Random;

/**
 * 简单的字符串操作工具类
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
	
	/**
	 * 产生指定长度[长度为0以上]的随机数
	 * @param n
	 * @return
	 */
	public static String random(int n) {
        if (n < 1 || n > 10) {
            throw new IllegalArgumentException("cannot random " + n + " bit number");
        }
        Random ran = new Random();
        if (n == 1) {
            return String.valueOf(ran.nextInt(10));
        }
        int bitField = 0;
        char[] chs = new char[n];
        for (int i = 0; i < n; i++) {
            while(true) {
                int k = ran.nextInt(10);
                if( (bitField & (1 << k)) == 0) {
                    bitField |= 1 << k;
                    chs[i] = (char)(k + '0');
                    break;
                }
            }
        }
        return new String(chs);
    }
	
}
