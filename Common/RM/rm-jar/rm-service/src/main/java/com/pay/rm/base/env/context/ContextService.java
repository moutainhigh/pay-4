package com.pay.rm.base.env.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class ContextService implements BeanFactoryAware {
	private static Log logger = LogFactory.getLog(ContextService.class);
	private static BeanFactory context;

	public static Object getBean(String beanId) {
		try {
			return context.getBean(beanId);
		} catch (Exception e) {
			logger.error("找不到指定名称bean [" + beanId + "]");
			return null;
		}

	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		context = beanFactory;
	}
}
