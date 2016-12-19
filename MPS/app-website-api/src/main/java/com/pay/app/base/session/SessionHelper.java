/**
 *  File: SessionHelper.java
 *  Description:
 *  Copyright 2006-2010 woyo Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2010-7-22   terry_ma     Create
 *
 */
package com.pay.app.base.session;

import javax.servlet.http.HttpServletRequest;

import com.pay.app.base.api.common.enums.ScaleEnum;
import com.pay.app.base.api.common.enums.TokenEnum;
import com.pay.app.base.exception.LoginTimeOutException;
import com.pay.util.TokenUtil;



/**
 * 
 */
public class SessionHelper {

	public static final String user_session = "userSession";


	/**
	 * 获取用户登录信息
	 * @return
	 */
	public static LoginSession getLoginSession(){
		HttpServletRequest request=RequestLocal.getRequest();
		if (null != request.getSession().getAttribute(user_session)) {
			LoginSession loginSession = (LoginSession) request.getSession()
					.getAttribute(user_session);
			return loginSession;
		} 
		return null;
	}
	
	/**
	 * 获取用户登录信息
	 * @return
	 */
	public static LoginSession getLoginSession(HttpServletRequest request)throws LoginTimeOutException {
			if (null != request.getSession().getAttribute(user_session)) {
			LoginSession loginSession = (LoginSession) request.getSession()
					.getAttribute(user_session);
			return loginSession;
		} 
			throw new LoginTimeOutException();
	}
	
	/**
	 * 设置用户登录信息至Session
	 * @return
	 */
	
	public static void setLoginSession(LoginSession loginSession){
		HttpServletRequest request=RequestLocal.getRequest();
		if(null!=loginSession){
			request.getSession().setAttribute(user_session, loginSession);
		}
	}
	
	
	
	
	/**
	 * 获取当前登录用户的memberCode
	 * @return
	 */
	
	public static String getMemeberCodeBySession(){
		String memberCode=null;
		LoginSession loginSession=getLoginSession();
		if(loginSession!=null)
			return loginSession.getMemberCode();
		return memberCode;
	}
	
	/**
	 * 获取当前登录用户的operatorId
	 * @return
	 */
	
	public static Long getOperatorIdBySession(){
		Long operatorId=0L;
		LoginSession loginSession=getLoginSession();
		if(loginSession!=null)
			return loginSession.getOperatorId();
		return operatorId;
	}
	
	
	
	/**
	 * 防止重複提交設置Token
	 * @param tokenEnum
	 * @return
	 */
	public static String setToken(TokenEnum tokenEnum){
		if(tokenEnum==null)return null;
		String tokenValue=TokenUtil.getUUID();
		HttpServletRequest request=RequestLocal.getRequest();
		request.getSession().setAttribute(tokenEnum.getCode(), tokenValue);
		return tokenValue;
	}
	
	
	/**
	 * 验证Token是否正确
	 * @param tokenEnum
	 * @param compareToken
	 * @return
	 */
	public static boolean validateToken(TokenEnum tokenEnum,final String compareToken){
		HttpServletRequest request=RequestLocal.getRequest();
		String token=(String)request.getSession().getAttribute(tokenEnum.getCode());
		if(token==null || compareToken==null)
			return false;
		request.getSession().removeAttribute(tokenEnum.getCode());
		return (token.equals(compareToken));
	}
	
	public static void clearToken(TokenEnum tokenEnum){
		HttpServletRequest request=RequestLocal.getRequest();
		request.getSession().removeAttribute(tokenEnum.getCode());
	}
	
	public static boolean isCorpLogin(){
		LoginSession loginSession=SessionHelper.getLoginSession();
		return (loginSession.getScaleType()==ScaleEnum.ENTERPRICE.getValue()
				|| loginSession.getScaleType()==ScaleEnum.TRADING_CENTER.getValue());
	}
	
	
	public static boolean isCertUser(){
		LoginSession loginSession=SessionHelper.getLoginSession();
		if(null!=loginSession){
			return loginSession.isCertUser();
		}
		return false;
	}
	
	public static void setCertUser(){
		LoginSession loginSession=SessionHelper.getLoginSession();
		if(null!=loginSession){
			loginSession.setCertUser(true);
			SessionHelper.setLoginSession(loginSession);
		}
	}
	
	public static void removeCertUser(){
		LoginSession loginSession=SessionHelper.getLoginSession();
		if(null!=loginSession){
			loginSession.setCertUser(false);
			SessionHelper.setLoginSession(loginSession);
		}
	}
	
	public static boolean isLocalCertUser(){
		LoginSession loginSession=SessionHelper.getLoginSession();
		if(null!=loginSession){
			return loginSession.isLocalInstall();
		}
		return false;
	}
	
	public static void setLocalInstallCert(){
		LoginSession loginSession=SessionHelper.getLoginSession();
		loginSession.setLocalInstall(true);
		SessionHelper.setLoginSession(loginSession);
	}
	
	public static void removeLocalInstallCert(){
		LoginSession loginSession=SessionHelper.getLoginSession();
		loginSession.setLocalInstall(false);
		SessionHelper.setLoginSession(loginSession);
	}
}
