/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.common.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.RequestLocal;
import com.pay.app.base.session.SessionHelper;


/**
 * @author JINHAHA
 *
 */
public class LoginUtil {
	
	public static final String login_num_session = "loginNumSession";
    
    public static void setLoginErrorNumLog(String memberCode,String operatorId,String loginIp){
        
        LoginSession loginSession=getLoginNumSession();
        if(loginSession == null){
            loginSession= new LoginSession();
            setLoginNumSession(loginSession);
        }
        if(StringUtils.isNotBlank(memberCode)){
            Integer failNum = (Integer)loginSession.getLoginFailNum().get("member"+StringUtils.trim(memberCode));
            int num = 0;
            if(failNum != null){
                num = failNum;
            }
            loginSession.getLoginFailNum().put("member"+StringUtils.trim(memberCode), num + 1);
        } 
        if(StringUtils.isNotBlank(operatorId)){
            Integer failNum = (Integer)loginSession.getLoginFailNum().get("operator"+StringUtils.trim(operatorId));
            int num = 0;
            if(failNum != null){
                num = failNum;
            }
            loginSession.getLoginFailNum().put("operator"+StringUtils.trim(operatorId), num + 1);
        } 
        if(StringUtils.isNotBlank(loginIp)){
            loginSession.getLoginFailNum().put(StringUtils.trim(loginIp), 1);
        }
    }
    public static int getLoginFailNum(String memberCode,String operatorId,String loginIp){
        LoginSession loginSession=getLoginNumSession();
        if(loginSession != null){
            if(StringUtils.isNotBlank(memberCode)){
                Integer failNum = (Integer)loginSession.getLoginFailNum().get("member"+StringUtils.trim(memberCode));
                if(failNum != null){
                    return failNum;
                }
            } else if(StringUtils.isNotBlank(operatorId)){
                Integer failNum = (Integer)loginSession.getLoginFailNum().get("operator"+StringUtils.trim(operatorId));
                if(failNum != null){
                    return failNum;
                }
            } else if(StringUtils.isNotBlank(loginIp)){
                Integer failNum = (Integer)loginSession.getLoginFailNum().get(StringUtils.trim(loginIp));
                if(failNum != null){
                    return failNum;
                }
            }
        }
        return 0;
    }
    public static void clearLoginFailNum(String memberCode,String operatorId,String loginIp){
    	LoginSession loginSession=getLoginNumSession();
        if(loginSession != null){
        	if(StringUtils.isNotBlank(memberCode)){
                loginSession.getLoginFailNum().put("member"+StringUtils.trim(memberCode), new Integer(0));
            } 
        	if(StringUtils.isNotBlank(operatorId)){
                loginSession.getLoginFailNum().put("operator"+StringUtils.trim(operatorId), new Integer(0));
            } 
        	if(StringUtils.isNotBlank(loginIp)){
                loginSession.getLoginFailNum().put(StringUtils.trim(loginIp), new Integer(0));
            }
        }
    }
    
    
    public static void setLoginNumSession(LoginSession loginSession){
		HttpServletRequest request=RequestLocal.getRequest();
		if(null!=loginSession){
			request.getSession().setAttribute(login_num_session, loginSession);
		}
	}
    
    
    /**
     * 登录计数器
     * @return
     */
    public static LoginSession getLoginNumSession(){
		HttpServletRequest request=RequestLocal.getRequest();
		return request.getSession().getAttribute(login_num_session)==null?null
				:(LoginSession)request.getSession().getAttribute(login_num_session);
	}
	
}
