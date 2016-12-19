 /** @Description 
 * @project 	poss-withdraw
 * @file 		WithDrawUtil.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-9		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pay.fundout.withdraw.dto.fileservice.WebQueryWithDrawDTO;


/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-9-9
 * @see 
 */
public class WithDrawUtil {
	public final static Map<String,WebQueryWithDrawDTO> entryReconcilePage (){
		final WebQueryWithDrawDTO webQueryWithDrawDTO = new WebQueryWithDrawDTO();
		webQueryWithDrawDTO.setStartTime(new Date());
		webQueryWithDrawDTO.setEndTime(new Date());
		final Map<String,WebQueryWithDrawDTO> map = new HashMap<String, WebQueryWithDrawDTO>();
		map.put("webQueryWithDrawDTO", webQueryWithDrawDTO);
		return map;
	}
	
	/**
	 * 转换银行账号(显示前六后四，中间的用*代替)
	 * @param bankAcct
	 * @return
	 */
	public static String transfBankAcct(String bankAcct){
		if(StringUtils.isEmpty(bankAcct)){
			return "";
		}else if(bankAcct.length() < 10){
			return bankAcct;
		}else{
			StringBuffer sb = new StringBuffer();
			int len = bankAcct.length();
			sb.append(bankAcct.substring(0,6));
			for(int i = 0; i < (len - 10);i++){
				sb.append("*");
			}
			sb.append(bankAcct.substring(len-4,len));
			return sb.toString();
		}		
	}
}
