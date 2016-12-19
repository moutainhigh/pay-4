package com.pay.poss.base.env.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.security.providers.encoding.PasswordEncoder;

import com.pay.poss.base.common.Constants;
import com.pay.poss.base.env.data.IDataManager;
import com.pay.poss.base.env.data.IDataService;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.schedule.task.TaskManager;

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

	public static IDataManager getDataMgr() throws PossUntxException {
		if (context.containsBean(IDataManager.BEAN_ID) == false) {
			throw new PossUntxException("必须定义IDataManager服务 [" + IDataManager.BEAN_ID + "]", ExceptionCodeEnum.CONTEXT_EXCEPTION);
		}
		return (IDataManager) context.getBean(IDataManager.BEAN_ID);
	}

	public static PasswordEncoder getPasswordEncoder() throws PossUntxException {
		if (context.containsBean(Constants.PASSWORD_ENCODER) == false) {
			throw new PossUntxException("必须定义PasswordEncoder服务", ExceptionCodeEnum.CONTEXT_EXCEPTION);
		}
		return (PasswordEncoder) context.getBean(Constants.PASSWORD_ENCODER);
	}

	public static TaskManager getTaskManager() {
		if (context.containsBean(Constants.TASKMGR_SERVICE) == false) {
			logger.error("必须定义TaskManager服务 [" + Constants.TASKMGR_SERVICE + "]");
			return null;
		}
		return (TaskManager) context.getBean(Constants.TASKMGR_SERVICE);
	}

	public static IDataService getDataService() {
		if (context.containsBean(IDataService.BEAN_ID) == false) {
			logger.error("必须定义DataService服务 [" + IDataService.BEAN_ID + "]");
			return null;
		}
		return (IDataService) context.getBean(IDataService.BEAN_ID);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		context = beanFactory;
	}
}
