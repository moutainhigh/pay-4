package com.pay.txncore.aop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fi.exception.BusinessException;
import com.pay.jms.notification.request.EmailNotifyRequest;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.sender.JmsSender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by cuber on 2016/10/8.
 */
@Component
@Aspect
public class TestInterceptor {
	
	private JmsSender jmsSender;
	
    @Pointcut("execution(* com.pay.txncore.aop.TestBean*.*(..))")
    private void testMethod(){}

//    @Pointcut("execution(* com.pay.txncore.aop.TestBean2.*(..))")
//    private void testMethod2(){}
    
    //or 或者 || 都可以
//    @Around("testMethod() or testMethod2()")
    @Around("testMethod()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("start");
        Object object = null;
        try{
        	Object[] obj = pjp.getArgs();
        	for(Object o : obj){
        		System.out.println(o);
        	}
//        	System.out.println(pjp.getArgs()[0]);
        	System.out.println("------------------------");
        	System.out.println("--1--"+pjp.getKind());
        	System.out.println("--2--"+pjp.getTarget());
        	System.out.println("--3--"+pjp.getClass());
        	System.out.println("--4--"+pjp.getSignature().getName());
        	System.out.println("--5--"+pjp.getSourceLocation());
        	Book book = (Book) pjp.getArgs()[0];
            object = pjp.proceed();
           /* com.pay.txncore.aop.Book@2242f64e
            ------------------------
            --1--method-execution
            --2--com.pay.txncore.aop.TestBean2@5a388c74
            --3--class org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint
            --4--void com.pay.txncore.aop.TestBean2.buyBook(Book)
            --5--org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint$SourceLocationImpl@29e965e9
			*/
        }catch (Exception e){
        	e.printStackTrace();
        	System.out.println(e.getLocalizedMessage());
        	System.out.println(e.getMessage());
        	System.out.println(e.getCause());
        	System.out.println(e.getClass());
        	System.out.println(e.getStackTrace()[0]);
        	System.out.println(e.getStackTrace()[0].getLineNumber());
            if(e instanceof BusinessException){
                throw e;
            }else{
                object = "1241243242";
                System.out.println(object);
//                this.sendEmail();
            }
        }
        System.out.println("end");
        return object;
    }
    
    
    /**通用的邮件发送方法
	 * @param addressee 收件人列表
	 * @param request 邮件通知请求对象
	 * @param templateId 邮件通知模板ID
	 */
	public void sendEmail(){
		long templateId = 500L;
		List<String> recAddress = new ArrayList<String>();
 		recAddress.add("yang.zhao@ipaylinks.com");
		EmailNotifyRequest request = new EmailNotifyRequest();
		request.setRecAddress(recAddress);// 收件人列表
		request.setType(RequestType.EMAIL);
		request.setTemplateId(templateId);// INF.notify_template表中的模板ID，一定要跟这个值一样。 XXX
		Map<String, String> data = new HashMap<String, String>();
		data.put("content", "hello!");
		request.setData(data);
		jmsSender.sendMsg(request);
	}
	
    
    
 
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	
	public static void main(String[] args){
		String[] arrs = new String[]{"1","3"};
		List<String> list = Arrays.asList(arrs);
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
	}
 	
}
