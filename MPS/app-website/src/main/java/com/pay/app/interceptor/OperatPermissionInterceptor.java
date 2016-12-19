/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.app.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.base.model.PowersModel;
import com.pay.base.service.featuremenu.MemberFeatureService;

/**
 * 操作员权限控制拦截器
 * @author zhi.wang
 * @version $Id: OperatPermissionInterceptor.java, v 0.1 2010-11-2 下午06:40:57 zhi.wang Exp $
 */
public class OperatPermissionInterceptor extends HandlerInterceptorAdapter{
	private static final Log logger = LogFactory.getLog(OperatPermissionInterceptor.class);
    private static final String SET_METHOD_PREFIX = "set";
    private static final String GET_METHOD_PREFIX = "get";
    private static final String TO_STRING_METHOD="toString";
    private static final String CLONE_METHOD="clone";
    private static final String forwadrUrl="/error.htm?method=noFeature";
    /** 分隔符 */
    private static final String SEPARATOR = "|";
    private MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver(); 

    private MemberFeatureService memberFeatureService;
    @SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {
	    
    	String path = request.getRequestURI(); 
	    if(logger.isDebugEnabled()){
	        logger.debug("OperatPermissionInterceptor  will start  intercept ..... requestPath is ["+path+"]");
        }
	    
	    Long operatorId = null;
        LoginSession loginSession = SessionHelper.getLoginSession();
        if (loginSession != null && loginSession.getMemberCode() != null) {
            if (SessionHelper.isCorpLogin() ) {
                if (loginSession.getOperatorId() != null && loginSession.getOperatorId() > 0) {
                	operatorId = loginSession.getOperatorId();
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
        	if(logger.isInfoEnabled()){
    	        logger.info("OperatPermissionInterceptor loginSession is null!");
            }
        	return true;
        }
            
	    String methodName = methodNameResolver.getHandlerMethodName(request);  
	    //String methodName=getHandlerMethodName(request);
	    String className = handler.getClass().getName();   
	   
	  
        Class targetClass = Class.forName(className);  
        Method[] method = targetClass.getMethods();  
        Method hasNoProxyMethod=convertMethodName(request, method, methodName, handler);
        
        // 如果没有匹配的方法名 。
        if(hasNoProxyMethod==null){
        	if(logger.isDebugEnabled()){
    	        logger.debug("OperatPermissionInterceptor hasNoProxyMethod is null ..... requestPath is ["+path+"]");
    	    }
        	return true;
        }
        
     // 方法的annotation没有包含OperatorPermission annotation.不记录日志。
        // 在类的annotation或者方法的annotation没有包含OperatorPermission annotation.无需进行权限判断
        if (!handler.getClass().isAnnotationPresent(OperatorPermission.class) && !hasNoProxyMethod.isAnnotationPresent(OperatorPermission.class)) {
        	if(logger.isDebugEnabled()){
    	        logger.debug("OperatPermissionInterceptor not has OperatorPermission Annotation");
    	    }
        	return true;
        }
        Annotation[] clsAnnotation = handler.getClass().getAnnotations();
        Annotation[] methodAnnotation = hasNoProxyMethod.getAnnotations();
        
        
     // 判断类上面是否有这个ANNOTATION
        for (int i = 0; i < clsAnnotation.length; i++) {
            if (clsAnnotation[i].annotationType() == OperatorPermission.class) {
            	OperatorPermission putAppLog = ((OperatorPermission) clsAnnotation[i]);
            	 String rightVal = putAppLog.operatPermission();
            	 if(logger.isInfoEnabled()){
                 	logger.info("PutAppLog logType is:"+rightVal);
                 }
                 // 校验操作员权限
                 if(!this.checkPermissions(operatorId, rightVal)){
                 	// 校验操作员权限不通过
                 	 RequestDispatcher rd=request.getRequestDispatcher(forwadrUrl);
                      rd.forward(request, response);
                      return false;
                 }
               
            }
        }
        
        
        // 判断方法上面是否有这个ANNOTATION
        for (int i = 0; i < methodAnnotation.length; i++) {
            if (methodAnnotation[i].annotationType() == OperatorPermission.class) {
            	OperatorPermission putAppLog = ((OperatorPermission) methodAnnotation[i]);
                String rightVal = putAppLog.operatPermission();
                if(logger.isInfoEnabled()){
                	logger.info("PutAppLog logType is:"+rightVal);
                }
                // 校验操作员权限
                if(!this.checkPermissions(operatorId, rightVal)){
                	// 校验操作员权限不通过
                	 RequestDispatcher rd=request.getRequestDispatcher(forwadrUrl);
                     rd.forward(request, response);
                     return false;
                }
            }
        }
        return true;
	}

    /**
     * 校验操作员是否有权限访问当前所请求的URL <br>
     * 
     * @param operatorId 操作员ID
     * @param permVal 权限值
     * @return
     */
    public boolean checkPermissions(Long operatorId,String permVal) {
        
    	List<PowersModel> menuList = memberFeatureService.getOperatorMenu(operatorId);
        if (menuList != null && menuList.size() > 0) {
            
            for (PowersModel powersModel : menuList) {
                // 子菜单
                List<PowersModel> list = powersModel.getChildlist();
                if (list != null && list.size() > 0) {
                    for (PowersModel powersModel2 : list) {
                    	String[] permissionVal2 = StringUtils.split(powersModel2.getMenuUrl(), SEPARATOR);
                    	if(permissionVal2 != null && permissionVal2.length > 0)
                    		for (String str : permissionVal2) {
                    			if (powersModel2.getStatus()== 1 && StringUtils.equals(str.trim(), permVal)) {
                                    return true;
                                }
							}
                		
                        List<PowersModel> list2 = powersModel2.getChildlist();
                        if(list2 != null && list2.size() >0){
                        	for (PowersModel powersModel3 : list2) {
                        		String[] permissionVal3 = StringUtils.split(powersModel3.getMenuUrl(), SEPARATOR);
                        		if(permissionVal3 != null && permissionVal3.length > 0){
                        			for (String str : permissionVal3) {
		                        		if (powersModel3.getStatus()== 1 && StringUtils.equals(str.trim(), permVal)) {
		                                    return true;
		                                }
                        			}
                        		}
                        		
                        		 List<PowersModel> list3 = powersModel3.getChildlist();
                        		 if(list3!=null && list3.size()>0){
                        			 for(PowersModel powerModel4:list3){
                        				 String[] permissionVal4 = StringUtils.split(powerModel4.getMenuUrl(), SEPARATOR);
                        				 if(permissionVal4 != null && permissionVal4.length > 0){
                                 			for (String str : permissionVal4) {
         		                        		if (powerModel4.getStatus()== 1 && StringUtils.equals(str.trim(), permVal)) {
         		                                    return true;
         		                                }
                                 			}
                                 		}
                        			 }
                        		 }
							}
                        }
                    }
                } else {
                    // 父菜单
                	String[] permissionVal = StringUtils.split(powersModel.getMenuUrl(), SEPARATOR);
            		if(permissionVal != null && permissionVal.length > 0){
            			for (String str : permissionVal) {
                    		if (powersModel.getStatus()== 1 && StringUtils.equals(str.trim(), permVal)) {
                                return true;
                            }
            			}
            		}
                }
            }
        } 
        logger.warn("校验操作员是否有权限访问当前所请求的URL不通过！operatorId=" + operatorId + ",permVal="
                    + permVal);
        return false;
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
            
            if(hasNoProxyMethod==null && handler instanceof MultiActionController){
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

	public void setMemberFeatureService(MemberFeatureService memberFeatureService) {
		this.memberFeatureService = memberFeatureService;
	}
    
}
