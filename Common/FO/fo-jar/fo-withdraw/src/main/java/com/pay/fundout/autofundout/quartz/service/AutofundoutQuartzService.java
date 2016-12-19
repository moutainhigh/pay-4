 /** @Description 
 * @project 	fo-withdraw
 * @file 		AutoTimeFundoutService.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-12-10		Henry.Zeng			Create 
*/
package com.pay.fundout.autofundout.quartz.service;
/**
 * <p>定期自动出款Servcie</p>
 * @author Henry.Zeng
 * @since 2010-12-10
 * @see 
 */
public interface AutofundoutQuartzService {
	
	/**
	 * 执行定期自动出款操作
	 */
	void executeStartQuartz() ;
	
	
}
