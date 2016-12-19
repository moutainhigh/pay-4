package com.pay.app.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.pay.app.base.api.annotation.HasFeature;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.base.common.enums.SecurityLvlEnum;


/**
 *  File: IdentityInterceptor.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date     2010-9-14     
 *  Author   zengjin     
 *  Changes   
 *  Comment 会员身份权限拦截器
 */
public class IdentityFeatureInterceptor extends HandlerInterceptorAdapter{

	
    private static final Log logger = LogFactory.getLog(IdentityFeatureInterceptor.class);
    private static final String SET_METHOD_PREFIX = "set";
    private static final String GET_METHOD_PREFIX = "get";
    private static final String TO_STRING_METHOD="toString";
    private static final String CLONE_METHOD="clone";
    private static final String forwadrUrl="/error.htm?method=noFeature";
    private MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver(); 

    @SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {
	    
	    String path = request.getRequestURI(); 
	    if(logger.isDebugEnabled()){
	        logger.debug("IdentityFeatureInterceptor  will start  intercept ..... requestPath is ["+path+"]");
        }
	    
	    String methodName = methodNameResolver.getHandlerMethodName(request);  
	    //String methodName=getHandlerMethodName(request);
	    String className = handler.getClass().getName();   
	   
	  
        Class targetClass = Class.forName(className);  
        Method[] method = targetClass.getMethods();  
        Method hasNoProxyMethod=convertMethodName(request, method, methodName, handler);
        
     // 如果没有匹配的方法名 直接跳过不让访问
        if(hasNoProxyMethod==null){
//            RequestDispatcher rd=request.getRequestDispatcher(forwadrUrl);
//            rd.forward(request, response);
            return true;
        }
        
     // 在类的annotation或者方法的annotation没有包含HasFeature annotation.无需进行权限判断
        if (!handler.getClass().isAnnotationPresent(HasFeature.class) && !hasNoProxyMethod.isAnnotationPresent(HasFeature.class)) {
            return true;
        }
        
        Annotation[] methodAnnotation = hasNoProxyMethod.getAnnotations();
        Annotation[] clsAnnotation = handler.getClass().getAnnotations();
        
        int securityLvl=SecurityLvlEnum.LOCK.getValue();
        LoginSession loginSession = SessionHelper.getLoginSession();
     // 如果是企业用户不做权限控制
        if(loginSession!=null){
            if(SessionHelper.isCorpLogin()){
                return true;
            }
            if(loginSession.getSecurityLvl()>0){
                securityLvl=loginSession.getSecurityLvl();
            }
        }
        
        
        
        
        StringBuilder sBuilder = new StringBuilder();
        // 判断方法上面是否有这个ANNOTATION
        for (int i = 0; i < methodAnnotation.length; i++) {
            if (methodAnnotation[i].annotationType() == HasFeature.class) {
                HasFeature hasFeature = ((HasFeature) methodAnnotation[i]);
                int[] rightVal = hasFeature.value();
                if(!hasFeature.isClosed()){
                    sBuilder.append("method must hasFeature: [");
                    for (int j = 0; j < rightVal.length; j++) {
                        if (securityLvl==rightVal[j]) {
                            return true;
                        }
                        sBuilder.append(SecurityLvlEnum.getSecurityLvlEnum(rightVal[j]).getMemo()).append(",");
                    }
                    sBuilder.append("]");
                    logger.debug("warings :"+sBuilder);
                    RequestDispatcher rd=request.getRequestDispatcher(forwadrUrl);
                    rd.forward(request, response);
                    return false;
                }
                
            }
        }
        
     // 判断类上面是否有这个ANNOTATION
        for (int i = 0; i < clsAnnotation.length; i++) {
            if (clsAnnotation[i].annotationType() == HasFeature.class) {
                int[] rightVal = ((HasFeature) clsAnnotation[i]).value();
                sBuilder.append("class must HasFeature: [ ");
                for (int j = 0; j < rightVal.length; j++) {
                    // 用户有执行权限
                    if (securityLvl==rightVal[j]) {
                        return true;
                    }
                    sBuilder.append(SecurityLvlEnum.getSecurityLvlEnum(rightVal[j]).getMemo()).append(",");
                }
                sBuilder.append("]");
                logger.debug("warings  cls:"+sBuilder);
                RequestDispatcher rd=request.getRequestDispatcher(forwadrUrl);
                rd.forward(request, response);
                return false;
               
            }
        }
        
        
		return true;
	}

    public String getHandlerMethodName(HttpServletRequest request)   
               throws NoSuchRequestHandlingMethodException {   
           //取得servlet Path   
           String pathName = request.getServletPath();   
           //去掉路径中最后一个/之前的内容   
            pathName = pathName.substring(pathName.lastIndexOf("/") + 1);   
            //去掉路径中.之后的内容   
            pathName = pathName.substring(0,pathName.indexOf("."));   
            //返回方法的名称   
           return pathName;   
        }   
    
    /**
     * 拿到方法名的Method对象
     * @param request
     * @param method
     * @param methodName
     * @param handler
     * @return 
     */
    private Method  convertMethodName(HttpServletRequest request,Method[] method,String methodName,Object handler){
        Method hasNoProxyMethod=null;
        try {
            for(Method m:method){ 
                if (m.getName().startsWith(SET_METHOD_PREFIX) || m.getName().startsWith(GET_METHOD_PREFIX) || m.getName().startsWith(TO_STRING_METHOD) ||m.getName().equals(CLONE_METHOD)) {
                    continue;
                }
                //System.out.println(m.getName());
                if (m.getName().equals(methodName)) {  
                        Class[] tmpCs = m.getParameterTypes();  
                        hasNoProxyMethod= handler.getClass().getMethod(methodName, tmpCs);  
                        break;
                }  
            }
            
            if(hasNoProxyMethod==null){
              MultiActionController maControl = (MultiActionController) handler; 
              MethodNameResolver pmrResolver = maControl.getMethodNameResolver(); 
              methodName=pmrResolver.getHandlerMethodName(request);
              for(Method m:method){  
                  if (m.getName().startsWith(SET_METHOD_PREFIX) || m.getName().startsWith(GET_METHOD_PREFIX) || m.getName().startsWith(TO_STRING_METHOD) ||m.getName().equals(CLONE_METHOD)) {
                      continue;
                  }
                  if (m.getName().equals(methodName)) {  
                          Class[] tmpCs = m.getParameterTypes();  
                          hasNoProxyMethod= handler.getClass().getMethod(methodName, tmpCs);  
                          break;
                      }  
              }
            }
        } catch (Exception e) {
            logger.error("identityFeatureInterceptor hasNoProxyMethod throws error", e);
         }
        
         return hasNoProxyMethod;
    }
	
}
