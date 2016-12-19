package com.pay.fundout.bankfile.parser.helper;

/**		
 *  @author lIWEI
 *  @Date 2011-5-25
 *  @Description
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 */
public class BusinessSupport {
	/**银行处理成功
	 * @param bankStatus
	 * @return
	 */
	public static boolean isBankStatusSuccess(String bankStatus){
		if(null == bankStatus){
			return false;
		}
		if(bankStatus.contains("成功")){
			return true;
		}
		return false;
	}
	
	/**银行处理失败
	 * @param bankStatus
	 * @return
	 */
	public static boolean isBankStatusFail(String bankStatus){
		if(null == bankStatus){
			return false;
		}
		if(bankStatus.contains("失败") 
				|| bankStatus.contains("拒绝") 
				|| bankStatus.contains("退票")
				|| bankStatus.contains("退回")
				|| bankStatus.contains("错误")
			){
			return true;
		}
		return false;
	}

}
