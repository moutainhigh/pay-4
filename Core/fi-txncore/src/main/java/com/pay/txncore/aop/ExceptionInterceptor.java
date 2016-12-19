package com.pay.txncore.aop;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.jms.notification.request.EmailNotifyRequest;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.sender.JmsSender;
/**
 * 异常处理拦截器
 * @author zhaoyang
 *
 */
public class ExceptionInterceptor {

	private static Logger logger = LoggerFactory.getLogger(ExceptionInterceptor.class);
	
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	
	private JmsSender jmsSender;
	
	private String errormsgReceiver;

	@Pointcut("execution(* com.pay.txncore.service.preauth.impl.PreAuth2ServiceImpl.*(..))")
	private void preAuth2ServiceMethod(){}
	
	@Pointcut("execution(* com.pay.txncore.service.impl.PaymentServiceImpl.pay*(..))")
	private void paymentServiceMethod(){}
	
	@Around("preAuth2ServiceMethod() or paymentServiceMethod()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
        Object object = null;
        try{
            object = pjp.proceed();
        }catch (Exception e){
            if(e instanceof BusinessException){
                throw e;
            }else{
                this.sendEmail(e);
            }
        }
        return object;
    }
	
	 /**通用的邮件发送方法
		 * @param addressee 收件人列表
		 * @param request 邮件通知请求对象
		 * @param templateId 邮件通知模板ID
		 */
		public void sendEmail(Exception e){
			long templateId = 500L;
			if(StringUtils.isBlank(errormsgReceiver)){
				logger.error("错误信息接收者的邮件配置信息为空，请检查");
				return;
			}
			String[] receivers = errormsgReceiver.split(",");
//			List<String> recAddress = new ArrayList<String>();
//	 		recAddress.add("yang.zhao@ipaylinks.com");
			EmailNotifyRequest request = new EmailNotifyRequest();
			request.setRecAddress(Arrays.asList(receivers));// 收件人列表
			request.setType(RequestType.EMAIL);
			request.setTemplateId(templateId);// INF.notify_template表中的模板ID，一定要跟这个值一样。 XXX
			Map<String, String> data = new HashMap<String, String>();
			data.put("content", this.getEmailContent(e));
			request.setData(data);
			jmsSender.sendMsg(request);
		}
		
		private String getEmailContent(Exception e){
			StringBuilder sb = new StringBuilder("发生时间：");
			sb.append(df.format(new Date()));
			sb.append(System.getProperty("line.separator"));
			sb.append("错误内容：");
			sb.append(System.getProperty("line.separator"));
			sb.append(e);
			return sb.toString();
		}
	
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	public String getErrormsgReceiver() {
		return errormsgReceiver;
	}

	public void setErrormsgReceiver(String errormsgReceiver) {
		this.errormsgReceiver = errormsgReceiver;
	}
	
	public static void main(String[] args){
		try{
			int i = 4/0;
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println("---------------------");
			System.out.println(e.getStackTrace()[0]);
			System.out.println("---------------------");
			System.out.println(e);
			logger.error("exception is {}", e);
		}
	}
}
