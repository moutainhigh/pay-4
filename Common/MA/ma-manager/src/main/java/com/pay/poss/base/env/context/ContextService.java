package com.pay.poss.base.env.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.providers.encoding.PasswordEncoder;

import com.pay.poss.base.common.Constants;
import com.pay.poss.base.env.data.IDataManager;
import com.pay.poss.base.env.data.IDataService;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.schedule.task.TaskManager;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;

public class ContextService {
	private Log logger = LogFactory.getLog(ContextService.class);
	private static ContextService instance = new ContextService();
	private ApplicationContext context;

	private ContextService() {
	}

	public static ContextService getInstance() {
		return instance;
	}

	public Object getBean(String beanId) {
		if (context == null) {
			logger.error("ApplicationContext必须不为空");
			return null;
		}

		return context.getBean(beanId);
	}

	public IDataManager getDataMgr() throws PossUntxException {
		if (context == null) {
			throw new PossUntxException("ApplicationContext必须不为空", ExceptionCodeEnum.CONTEXT_EXCEPTION);
		}
		if (context.containsBean(IDataManager.BEAN_ID) == false) {
			throw new PossUntxException("必须定义IDataManager服务 [" + IDataManager.BEAN_ID + "]", ExceptionCodeEnum.CONTEXT_EXCEPTION);
		}
		return (IDataManager) context.getBean(IDataManager.BEAN_ID);
	}

	public PasswordEncoder getPasswordEncoder() throws PossUntxException {
		if (context == null) {
			throw new PossUntxException("ApplicationContext必须不为空", ExceptionCodeEnum.CONTEXT_EXCEPTION);
		}
		if (context.containsBean(Constants.PASSWORD_ENCODER) == false) {
			throw new PossUntxException("必须定义PasswordEncoder服务", ExceptionCodeEnum.CONTEXT_EXCEPTION);
		}
		return (PasswordEncoder) context.getBean(Constants.PASSWORD_ENCODER);
	}

	public TaskManager getTaskManager() {
		if (context == null) {
			logger.error("ApplicationContext必须不为空");
			return null;
		}
		if (context.containsBean(Constants.TASKMGR_SERVICE) == false) {
			logger.error("必须定义TaskManager服务 [" + Constants.TASKMGR_SERVICE + "]");
			return null;
		}
		return (TaskManager) context.getBean(Constants.TASKMGR_SERVICE);
	}

	public IDataService getDataService() {
		if (context == null) {
			logger.error("ApplicationContext必须不为空");
			return null;
		}
		if (context.containsBean(IDataService.BEAN_ID) == false) {
			logger.error("必须定义DataService服务 [" + IDataService.BEAN_ID + "]");
			return null;
		}
		return (IDataService) context.getBean(IDataService.BEAN_ID);
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext ctx) {
		context = ctx;
	}
}
