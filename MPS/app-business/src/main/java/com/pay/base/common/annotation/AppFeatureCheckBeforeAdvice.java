package com.pay.base.common.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.MethodBeforeAdvice;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.base.common.enums.SecurityLvlEnum;



public class AppFeatureCheckBeforeAdvice implements MethodBeforeAdvice {
    
  
    
    private static final Log logger = LogFactory.getLog(AppFeatureCheckBeforeAdvice.class);
    
    @Override
    public void before(Method method, Object[] args, Object target) {

        logger.debug("init  AppFeatureCheckBeforeAdvice ######################"+method.getName());
        System.out.println("init  AppFeatureCheckBeforeAdvice ######################"+method.getName());
        // 对set,get方法不进行权限拦截
       
        Method hasNoProxyMethod=null;
        try {
            hasNoProxyMethod=target.getClass().getMethod(method.getName(), method.getParameterTypes());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        // 在类的annotation或者方法的annotation没有包含HasFeature annotation.无需进行权限判断
        if (!target.getClass().isAnnotationPresent(HasRight.class) && !hasNoProxyMethod.isAnnotationPresent(HasRight.class)) {
            return;
        }
        int securityLvl=SecurityLvlEnum.LOCK.getValue();
        LoginSession loginSession = SessionHelper.getLoginSession();
        if(loginSession!=null && loginSession.getSecurityLvl()>0){
            securityLvl=loginSession.getSecurityLvl();
        }

        Annotation[] clsAnnotation = target.getClass().getAnnotations();
        Annotation[] methodAnnotation = hasNoProxyMethod.getAnnotations();
        StringBuilder sBuilder = new StringBuilder();
        // 判断方法上面是否有这个ANNOTATION
        for (int i = 0; i < methodAnnotation.length; i++) {
            if (methodAnnotation[i].annotationType() == HasRight.class) {
                int[] rightVal = ((HasRight) methodAnnotation[i]).value();
                sBuilder.append("method must hasFeature: [");
                for (int j = 0; j < rightVal.length; j++) {
                    if (securityLvl==rightVal[j]) {
                        return;
                    }
                    sBuilder.append(rightVal[j]).append(",");
                }
                //throw new NoPermissionException(sBuilder.toString());
                logger.debug("first ="+sBuilder.toString());
                System.out.println("first ="+sBuilder.toString());
            }
        }
        // 判断类上面是否有这个ANNOTATION
        for (int i = 0; i < clsAnnotation.length; i++) {
            if (clsAnnotation[i].annotationType() == HasRight.class) {
                int[] rightVal = ((HasRight) clsAnnotation[i]).value();
                sBuilder.append("class must hasRight: [ ");
                for (int j = 0; j < rightVal.length; j++) {
                    // 用户有执行权限
                    if (securityLvl==rightVal[j]) {
                        return;
                    }
                    sBuilder.append(rightVal[j]).append(",");
                }
                logger.debug("second ="+sBuilder.toString());
                System.out.println("second ="+sBuilder.toString());
            }
        }
        
        
    }
}
