/**
 *  <p>File: BnakFileStringUtil.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.common.util;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

import com.pay.util.StringUtil;


/**
 * <p>去掉空格方法</p>
 * @since 2011-4-29
 * @see 
 */
public final class BankFileUtil {
	/**
	 * 去掉字符串所有空格,包括中间的空格
	 * @param str
	 * @return
	 */
	public static String trimAllWhitespace(String str){
		if(StringUtil.isEmpty(str)){
			return "";
		}else{
			return StringUtils.trimAllWhitespace(str);
		}
	}
	
	/**
	 * 去掉前后空格
	 * @param str
	 * @return
	 */
	public static String trimWhitespace(String str){
		if(StringUtil.isEmpty(str)){
			return "";
		}else{
			return StringUtils.trimWhitespace(str);
		}
	}
	
	/**
	 * 两数相除，保留两位小数(不四舍五入)
	 * @param source
	 * @param temp
	 * @return
	 */
	public static BigDecimal divideNum(BigDecimal source,BigDecimal temp){
		if(null == source || null == temp){
			return new BigDecimal(0);
		}
		return source.divide(temp,2,BigDecimal.ROUND_DOWN);
	}
	
	/**
	 * 两数相除，不保留两位小数(不四舍五入)
	 * @param source
	 * @param temp
	 * @return
	 */
	public static BigDecimal divideBig(BigDecimal source,BigDecimal temp){
		if(null == source || null == temp){
			return new BigDecimal(0);
		}
		return source.divide(temp,BigDecimal.ROUND_DOWN);
	}
	
}
