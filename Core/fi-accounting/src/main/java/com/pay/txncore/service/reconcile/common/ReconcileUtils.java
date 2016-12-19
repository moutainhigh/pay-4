 /** @Description 
 * @project 	fo-reconcile-manager
 * @file 		ReconcileUtils.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-9		Henry.Zeng			Create 
*/
package com.pay.txncore.service.reconcile.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>对账工具类</p>
 * @author Henry.Zeng
 * @since 2010-10-9
 * @see 
 */
public final class ReconcileUtils {
	
	private final static transient Log log = LogFactory.getLog(ReconcileUtils.class);
	
	/**
	 * 把字符date转型成TimeStamp
	 * @param date
	 * @return
	 */
	public final static Date string2Timestamp(String date) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		dateFormat.setLenient(false);
		Date timeDate = null;
		try{
			if(StringUtils.isNotEmpty(date)){
				timeDate = dateFormat.parse(date);
				return timeDate;
			}
			return null;
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return null;
		}
		
	}
}
