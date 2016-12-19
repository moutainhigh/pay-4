package com.pay.base.common.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;



public class AppFeatureCheckAdvice implements AfterReturningAdvice{
    private static final Log logger = LogFactory.getLog(PutAppLogAfterReturningAdvice.class);

//  @Autowired
//private UserLogService userLogService;
//
  @Override
  public void afterReturning(Object returnValue, Method method,
          Object[] args, Object target) throws Throwable {
      System.out.println("init PutAppLogAfterReturningAdvice #################"+method.getName());
      
      Method hasNoProxyMethod=null;
      try {
          hasNoProxyMethod=target.getClass().getMethod(method.getName(), method.getParameterTypes());
      } catch (Exception e) {
          e.printStackTrace();
          return;
      }
      
      try{
     

       Annotation[] methodAnnotation = hasNoProxyMethod.getAnnotations();
      // 判断方法上面是否有这个ANNOTATION
      for (int i = 0; i < methodAnnotation.length; i++) {
          if (methodAnnotation[i].annotationType() == HasRight.class) {
              HasRight hasFeature = ((HasRight) methodAnnotation[i]);
              
              if(!hasFeature.isClosed()){
                  System.out.println("you have features   ###########################"+hasFeature.value());
              }else{
                  return;
              }
              
          }
      }
      }catch(Exception e){
          logger.error("PutAppLogAfterReturningAdvice afterReturning throws error :"+e);
      }
  }
}
