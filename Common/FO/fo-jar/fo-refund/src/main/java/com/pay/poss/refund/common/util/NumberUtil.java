package com.pay.poss.refund.common.util;

import java.text.DecimalFormat;

/**提供数字处理常用方法
 * @author 李威
 * 20101103
 *
 */
public class NumberUtil {
	/**格式化一个double值，如保留小数位等
	 * @param adouble
	 * @param pattern:如"##0.00"表示保留两位小数
	 * @return
	 */
	public static double formatDouble(double adouble,String pattern){
		double returnDouble;
		DecimalFormat df = new DecimalFormat(pattern);
		String doubleString = df.format(adouble);
		returnDouble = Double.parseDouble(doubleString);
		return returnDouble;
	}
	
	/**格式化一个double值，如保留小数位等
	 * @param adouble
	 * @param pattern:如"##0.00"表示保留两位小数
	 * @return
	 */
	public static String formatDoubleToString(double adouble,String pattern){
		DecimalFormat df = new DecimalFormat(pattern);
		String doubleString = df.format(adouble);
		return doubleString;
	}
}
