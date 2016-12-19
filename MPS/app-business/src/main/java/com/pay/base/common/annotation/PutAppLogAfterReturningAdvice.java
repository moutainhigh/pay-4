package com.pay.base.common.annotation;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;

import com.pay.util.CheckUtil;



public class PutAppLogAfterReturningAdvice implements AfterReturningAdvice{
    private static final Log logger = LogFactory.getLog(PutAppLogAfterReturningAdvice.class);

//    @Autowired
//	private UserLogService userLogService;
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
		//Annotation[] methodAnnotation = target.getClass().getAnnotations();
		//Annotation[] methodAnnotation = AnnotationUtils.getAnnotations(method);

		 Annotation[] methodAnnotation = hasNoProxyMethod.getAnnotations();
		// 判断方法上面是否有这个ANNOTATION
		/*for (int i = 0; i < methodAnnotation.length; i++) {
			if (methodAnnotation[i].annotationType() == PutAppLog.class) {
			    PutAppLog putAppLog = ((PutAppLog) methodAnnotation[i]);
				
				if(!putAppLog.isClosed()){
					HttpServletRequest request = (HttpServletRequest)RequestLocal.getRequest();
		            String referer = request.getHeader("Referer");
		            if (StringUtils.isEmpty(request.getHeader("Referer"))) {
		                referer = "";
		            }
		            String agent = request.getHeader("User-Agent");
		            if (StringUtils.isEmpty(agent)) {
		                agent = "";
		            } else {
		                agent = agent.length() > 220 ? agent.substring(0, 220) : agent;
		            }
		            String ip = getClientIP(request);
		            if (StringUtils.isEmpty(ip)) {
		                ip = "";
		            }
		            String cookie = request.getHeader("Cookie");
		            if (StringUtils.isEmpty(cookie)) {
		                cookie = "";
		            } else if (cookie.length() > 512) {
		                cookie = cookie.substring(0, 500);
		            }
		            
		            System.out.println("will insert logs ###########################"+putAppLog.logType());
//		            UserLogDTO userLogDTO = new UserLogDTO();
//		           
//		            LoginSession loginSession = (LoginSession) request.getSession().getAttribute("userSession");
//		            if(loginSession!=null){
//		                if(loginSession.getOperatorId()!=null)
//		                        userLogDTO.setOperatorId(0L);
//	                    userLogDTO.setMemberCode(Long.valueOf(loginSession.getMemberCode()));
//	                    userLogDTO.setActionUrl(requestURI(request));
//	                    userLogDTO.setLogType(putAppLog.logType());
//	                    userLogDTO.setLoginDate(new Timestamp(new Date().getTime()));
//	                    userLogDTO.setLoginIp(transferIp2Str(ip));
//	                    userLogDTO.setLoginName(loginSession.getLoginName());
//	                    userLogDTO.setName(loginSession.getVerifyName());
//	                    userLogDTO.setBrowserVer(agent);
//	                    userLogService.save(userLogDTO);
//		            }
		        }else{
					return;
				}
				
			}
		}*/
		}catch(Exception e){
		    logger.error("PutAppLogAfterReturningAdvice afterReturning throws error :"+e);
		}
//      判断类上面是否有这个ANNOTATION
//		for (int i = 0; i < clsAnnotation.length; i++) {
//			
//		}
		
		
	}
	
	private static String getClientIP(HttpServletRequest request) {
        // 如果使用了反向代理软件,而且有多级反向代理的话,会通过,进行分隔
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isNotEmpty(ip) && StringUtils.indexOf(ip, ',') != -1) {
            ip = StringUtils.split(ip, ',')[0];
            if (ip.equalsIgnoreCase("unknown")) {
                ip = StringUtils.split(ip, ',')[1];
            }
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            // WebLogic Plug-In Enabled
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    private static String transferIp2Str(String ip) {
	        if (StringUtils.isEmpty(ip))
	            return "";
	        if (ip.length() > 15 || ip.indexOf(":") != -1)
	            return "000000000000";
	        StringBuilder sbuilder = new StringBuilder();
	        String[] array = StringUtils.split(ip, ".");
	        int len = 0;
	        for (String str : array) {
	            len = str.length();
	            while (len < 3) {
	                sbuilder.append("0");
	                len++;
	            }
	            sbuilder.append(str);
	        }
	        return sbuilder.toString();
	    }
    
    private static String requestURI(HttpServletRequest request) throws UnsupportedEncodingException {
        String url =request.getRequestURI().replace(request.getContextPath(), "");
       
        StringBuffer queryString=new StringBuffer("");
        //StringBuffer url_buffer = request.getRequestURL(); 
        Map map=request.getParameterMap();
        Iterator it = map.keySet().iterator();
        String key="";
        String keyValue="";
        while (it.hasNext()) {
            key= (String)it.next();
            keyValue=map.get(key) ==null?"":((String[])map.get(key))[0];
            if("".equals(keyValue)){
                continue;
            }
            
            if(CheckUtil.isContainsChinese(keyValue)){
                try {
                  keyValue= URLEncoder.encode(keyValue,"utf-8");
              } catch (UnsupportedEncodingException e) {
                  logger.error("PutAppLogAfterReturningAdvice URLEncoder.encode "+keyValue+" throws error :"+e);
              }
            }
                
            queryString.append(key);
            queryString.append("=");
            queryString.append(keyValue);
            queryString.append("&");
        }
        String fullPath ="";
        if(queryString.toString().endsWith("&")){
            fullPath= url +"?"+ queryString.substring(0, queryString.length() - 1);
        }else
            fullPath= url +"?"+ queryString;    
  
        if(StringUtils.isEmpty(queryString.toString())){
            fullPath=url;
        }
        // fullPath =  new String(fullPath.getBytes("iso-8859-1"),"utf-8");
    
          return fullPath;
  }

}
