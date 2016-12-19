 /** @Description 
 * @project 	poss-base
 * @file 		CacheManager.java 
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-25		Henry.Zeng			Create 
*/
package com.pay.poss.base.dataprovide;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;


/**
 * <p>缓存管理器</p>
 * @author Henry.Zeng
 * @since 2010-8-25
 * @see 
 */
public final class DataProvideManager implements BeanFactoryAware{

	
	private static BeanFactory beanFactory ;
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		DataProvideManager.beanFactory = beanFactory;
	}
	/**
	 * 获取缓存对象
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public static DataProvideObject getDataProvideObject(){
		DataProvideObject cacheObject = (DataProvideObject) beanFactory.getBean("dataProvideObject");
		return cacheObject;
	}
	
	
}
