package com.pay.base.common.annotation;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Around;
import org.springframework.aop.support.StaticMethodMatcherPointcut;



/**
 * 权限角色访问资源的切入点
 * @author zengjin
 * @date 2010-10-06
 */
public class AppFeaturePointCut extends StaticMethodMatcherPointcut{
    private static final Log logger = LogFactory.getLog(AppFeaturePointCut.class);
    private static final String SET_METHOD_PREFIX = "set";
    private static final String GET_METHOD_PREFIX = "get";
    private static final String TO_STRING_METHOD="toString";
    private static final String CLONE_METHOD="clone";
    
    //@Around("execution(* com.pay.app.controller.*.*(..))")
    @Override
    @SuppressWarnings("unchecked")
    public boolean matches(Method method, Class targetClass) {
        logger.debug("init  AppFeaturePointCut ######################"+method.getName());
       
        // 对set,get方法不进行权限拦截
        if (method.getName().startsWith(SET_METHOD_PREFIX) || method.getName().startsWith(GET_METHOD_PREFIX) || method.getName().startsWith(TO_STRING_METHOD) ||method.getName().equals(CLONE_METHOD)) {
            return false;
        }
        System.out.println("init  AppFeaturePointCut ######################"+method.getName());
//        if(targetClass.isAnnotationPresent(HasFeature.class)){
//            return true;
//        }
        try {
            // 判断调用方法是否需要权限控制
            Method hasNoProxyMethod=targetClass.getMethod(method.getName(), method.getParameterTypes());
            
            if(hasNoProxyMethod==null)return false;
            
            return hasNoProxyMethod.isAnnotationPresent(HasRight.class);
        } catch (Exception e) {
            logger.error("AppFeaturePointCut throws error :", e);
        }
        return false;
    }
}
