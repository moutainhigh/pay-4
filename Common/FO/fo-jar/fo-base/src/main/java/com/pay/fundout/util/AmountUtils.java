/**
 * 
 */
package com.pay.fundout.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author NEW
 *
 */
public class AmountUtils {
	
	private final static String AMOUNT_PATTERN = "^(0(\\.\\d{0,2})?|([1-9]+[0]*)+(\\.\\d{0,2})?)$";
	/**
	 * 验证金额是否合法
	 * @param amountStr
	 * @return
	 */
	public static boolean checkAmount(String amountStr){
		boolean ret = false;
		try {
			Pattern p = Pattern.compile(AMOUNT_PATTERN);
			Matcher m = p.matcher(amountStr);
			ret = m.matches();
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
	
	public static Long toLongAmount(String amountStr){
		if(checkAmount(amountStr)){
			return new BigDecimal(amountStr).multiply(new BigDecimal(1000)).longValue();
		}
		return 0L;
	}
	
	
	/**
	 * 金额转BigDecimal
	 * @param amount
	 * @return
	 */
	public static BigDecimal toBigDecimalAmount(Long amount){
		Long tmpAmount = amount;
		if (tmpAmount == null) {
			tmpAmount = 0L;
		}
		return new BigDecimal(tmpAmount).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP);

	}
	
	public static String numberFormat(BigDecimal amount){
		 NumberFormat formatter   =   new   DecimalFormat( "#,###,##0.00"); 
		 if(amount==null){
			 return "NULL";
		 }
		 return formatter.format(amount.doubleValue());
	}
	
	public static String numberFormat(Long amount){
		 NumberFormat formatter   =   new   DecimalFormat( "#,###,##0.00"); 
		 if(amount==null){
			 return "NULL";
		 }
		 
		 BigDecimal tmp = toBigDecimalAmount(amount);
		 
		 return formatter.format(tmp.doubleValue());
	}
	
}
