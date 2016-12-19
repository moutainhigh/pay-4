package com.pay.base.common.annotation;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

//import com.pay.app.base.api.annotation.PutAppLog;


/**
 * 检查用户行为日志的切入点
 * @author zengjin
 * @date  2010-10-06
 */
public class PutAppLogPointCut extends StaticMethodMatcherPointcut{
    private static final Log logger = LogFactory.getLog(PutAppLogPointCut.class);
	private static final String SET_METHOD_PREFIX = "set";
	private static final String GET_METHOD_PREFIX = "get";
	private static final String TO_STRING_METHOD="toString";
	private static final String CLONE_METHOD="clone";
	@Override
	@SuppressWarnings("unchecked")
	public boolean matches(Method method, Class targetClass) {
		// 对set,get方法不插入日志
		if (method.getName().startsWith(SET_METHOD_PREFIX) || method.getName().startsWith(GET_METHOD_PREFIX) || method.getName().startsWith(TO_STRING_METHOD) ||method.getName().equals(CLONE_METHOD)) {
			return false;
		}
		System.out.println("init PutAppLogPointCut ###########################"+method.getName());
		try {

			Method hasNoProxyMethod=targetClass.getMethod(method.getName(), method.getParameterTypes());
			
			if(hasNoProxyMethod==null)return false;
			
//			return hasNoProxyMethod.isAnnotationPresent(PutAppLog.class);
		} catch (Exception e) {
			logger.error("PutAppLogPointCut matches throws error:",e);
		}
		return false;
	}

}
