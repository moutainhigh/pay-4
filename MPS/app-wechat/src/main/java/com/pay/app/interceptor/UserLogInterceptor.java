/*
 * Copyright © 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;

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

import com.pay.app.base.api.annotation.PutAppLog;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.base.common.enums.SecurityLvlEnum;
import com.pay.base.dto.IndividualInfoDto;
import com.pay.base.dto.MemberDto;
import com.pay.base.dto.UserLogDTO;
import com.pay.base.model.Operator;
import com.pay.base.service.individual.IndividualInfoService;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.base.service.user.UserLogService;
import com.pay.util.IPUtil;
import com.pay.util.WebUtil;

/**
 * 会员操作记录拦截器
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-19 上午11:57:36
 * 
 */
public class UserLogInterceptor extends HandlerInterceptorAdapter {

	private static final Log logger = LogFactory
			.getLog(UserLogInterceptor.class);
	private static final String SET_METHOD_PREFIX = "set";
	private static final String GET_METHOD_PREFIX = "get";
	private static final String TO_STRING_METHOD = "toString";
	private static final String CLONE_METHOD = "clone";
	private final static String ORGIN_FROM_SECURITY="security";
	private MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver();

	private UserLogService userLogService;
	/** 会员服务 */
    private MemberService         memberService;
    /** 个人会员信息 */
    private IndividualInfoService individualInfoService;
    /** 操作员服务 */
    private OperatorServiceFacade operatorServiceFacade;
    
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		String path = request.getRequestURI();
		if (logger.isDebugEnabled()) {
			logger
					.debug("UserLogInterceptor  will start  intercept ..... requestPath is ["
							+ path + "]");
		}

		String methodName = methodNameResolver.getHandlerMethodName(request);
		// String methodName=getHandlerMethodName(request);
		String className = handler.getClass().getName();

		Class targetClass = Class.forName(className);
		Method[] method = targetClass.getMethods();
		Method hasNoProxyMethod = convertMethodName(request, method,
				methodName, handler);

		// System.out.println("UserLogInterceptor hasNoProxyMethod  requestPath is ["+path+"]");;
		// 如果没有匹配的方法名 不记录日志。
		if (hasNoProxyMethod == null) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("UserLogInterceptor hasNoProxyMethod is null ..... requestPath is ["
								+ path + "]");
			}
			return;
		}

		// 方法的annotation没有包含PutAppLog annotation.不记录日志。
		if (!hasNoProxyMethod.isAnnotationPresent(PutAppLog.class)) {
			return;
		}

		Annotation[] methodAnnotation = hasNoProxyMethod.getAnnotations();

		LoginSession loginSession = SessionHelper.getLoginSession();

		String memberCode = "";
		// 登录名
		String loginName = "";
		// 真实姓名
		String verifyName = "";
		// 操作员ID
		Long operatorExtId = null;
		// 登录级别
		int securityLvl = SecurityLvlEnum.LOCK.getValue();

		if (loginSession != null
				&& StringUtils.isNotBlank(loginSession.getMemberCode())) {
			memberCode = loginSession.getMemberCode();
			loginName = loginSession.getLoginName();
			verifyName = loginSession.getVerifyName();
			operatorExtId = loginSession.getOperatorExtId();
			securityLvl = loginSession.getSecurityLvl();
		} else {
			memberCode = (String) request.getSession().getAttribute(
					"memberCode");
			loginName = (String) request.getSession().getAttribute("loginName");
			verifyName = (String) request.getSession().getAttribute(
					"verifyName");
		}
		if (StringUtils.isNotBlank(memberCode)) {
			// System.out.println("UserLogInterceptor hasNoProxyMethod  getMemberCode is ["+loginSession.getMemberCode()+"]");;

			// 判断方法上面是否有这个ANNOTATION
			for (int i = 0; i < methodAnnotation.length; i++) {
				if (methodAnnotation[i].annotationType() == PutAppLog.class) {
					PutAppLog putAppLog = ((PutAppLog) methodAnnotation[i]);
					int rightVal = putAppLog.logType();
					if (logger.isInfoEnabled()) {
						logger.info("PutAppLog logType is:" + rightVal);
					}
					if(rightVal == CutsConstants.USER_LOG_REGISTERSUCCESS){
						//注册成功记录一条日志信息
						Object ob = request.getAttribute("isRegisterSuccess");
						if(ob!=null){
							// 保存日志
							this.saveUserLog(request, rightVal, memberCode, loginName,
									verifyName, operatorExtId, securityLvl);
						}
						return;
					}
					// 保存日志
					this.saveUserLog(request, rightVal, memberCode, loginName,
							verifyName, operatorExtId, securityLvl);
					return ;
				}
			}
		} else {
			// 判断方法上面是否有这个ANNOTATION
			for (int i = 0; i < methodAnnotation.length; i++) {
				if (methodAnnotation[i].annotationType() == PutAppLog.class) {
					PutAppLog putAppLog = ((PutAppLog) methodAnnotation[i]);
					int rightVal = putAppLog.logType();
					if (logger.isInfoEnabled()) {
						logger.info("PutAppLog logType is:" + rightVal);
					}
					// 保存登录失败日志
					if(rightVal == CutsConstants.USER_LOG_LOGIN){
						loginName = request.getParameter("loginName")==null?"":request.getParameter("loginName").trim();
						String operatorName = request.getParameter("operatorName")==null?"":request.getParameter("operatorName").trim();
						MemberDto memberDto = memberService.findMemberByLoginName(StringUtils.lowerCase(loginName));
						if(memberDto != null){
							memberCode = String.valueOf(memberDto.getMemberCode());
							if(StringUtils.isNotBlank(operatorName)){
								Operator operator = operatorServiceFacade.getByIdentityMemCode(operatorName, memberDto
							            .getMemberCode());
								if(operator != null){
									verifyName = operator.getName();
									operatorExtId = operator.getOperatorId();
									// 记录登录失败日志
									this.saveUserLog(request, CutsConstants.USER_LOG_LOGIN_FAILED, memberCode, loginName,
											verifyName, operatorExtId, securityLvl);
									return;
								}
							} else {
								IndividualInfoDto individualInfo = individualInfoService
		                         .queryIndividualInfoByMemberCode(memberDto.getMemberCode());
								if(individualInfo != null){
									verifyName = individualInfo.getName();
									// 记录登录失败日志
									this.saveUserLog(request, CutsConstants.USER_LOG_LOGIN_FAILED, memberCode, loginName,
											verifyName, operatorExtId, securityLvl);
									return;
								}
							}
						}
						if (logger.isInfoEnabled()) {
							logger.info("PutAppLog logType is:" + rightVal+",save USER_LOG_LOGIN_FAILED Error!loginName="+loginName);
						}
						return ;
					}
				}
			}
		}

	}

	/**
	 * 保存会员操作记录
	 *
	 * @param request
	 * @param logType
	 * @param memberCode
	 * @param loginName
	 * @param verifyName
	 * @param operatorExtId
	 * @param securityLvl
	 */
	private void saveUserLog(HttpServletRequest request, int logType,
			String memberCode, String loginName, String verifyName,
			Long operatorExtId, int securityLvl) {
		if (logType < 0) {
			logger.warn("保存会员操作记录失败,memberCode=" + memberCode + ",logType="
					+ logType);
			return;
		}
		// 获取memberCode
		if (logger.isDebugEnabled()) {
			logger.debug("UserLogInterceptor saveUserLog memberCode:"
					+ memberCode);
			;
		}

		UserLogDTO userLogDTO = new UserLogDTO();
		if (operatorExtId != null && operatorExtId > 0) {
			userLogDTO.setOperatorId(operatorExtId);
		} else {
			userLogDTO.setOperatorId(0L);
		}
		userLogDTO.setMemberCode(Long.valueOf(memberCode));
		String url = request.getRequestURI().replace(request.getContextPath(),
				"");
		userLogDTO.setActionUrl(url);
		String hasSecurity = request.getParameter("hasSecurity")==null?"":request.getParameter("hasSecurity");
		// 检查登录类型:普通登录、安全登录、口令卡登录
		if (logType == CutsConstants.USER_LOG_LOGIN) {
			if(ORGIN_FROM_SECURITY.equals(hasSecurity)){// 安全登录
				userLogDTO.setLogType(CutsConstants.USER_LOG_SECURE_LOGIN);
			}else if(securityLvl == SecurityLvlEnum.MAXTRIX.getValue()){ // 口令卡登录
				userLogDTO.setLogType(CutsConstants.USER_LOG_CARD_LOGIN);
			}else{ // 普通登录
				userLogDTO.setLogType(CutsConstants.USER_LOG_LOGIN);
			}
		}else{
		    userLogDTO.setLogType(logType);
		}
		userLogDTO.setLoginDate(new Timestamp(new Date().getTime()));
		String clientIp = WebUtil.getIp(request);
		if (StringUtils.isNotBlank(clientIp)) {
			userLogDTO.setLoginIp(clientIp);
		} else {
			String ip = "";
			try {
				ip = IPUtil.getLocalAddr();
			} catch (Exception e) {
				logger.error("UserLogFilter IPUtil getLocalAddr Error.", e);
			}
			String[] ipchr = null;
			if (null != ip && ip.indexOf("/") > 0) {
				ipchr = ip.toString().split("/");
			}
			if (logger.isInfoEnabled()) {
				logger.info("UserLogInterceptor -------------IP:" + ipchr[1]);
			}
			userLogDTO.setLoginIp(ipchr[1]);
		}
		userLogDTO.setLoginName(loginName);
		userLogDTO.setName(verifyName);
		// 浏览器设置默认值 IE7
		String agent = request.getHeader("User-Agent");
		if (StringUtils.isEmpty(agent)) {
			agent = "IE7";
		} else {
			agent = agent.length() > 100 ? agent.substring(0, 100) : agent;
		}
		userLogDTO.setBrowserVer(agent);
		userLogService.create(userLogDTO);
	}

	public String getHandlerMethodName(HttpServletRequest request)
			throws NoSuchRequestHandlingMethodException {
		// 取得servlet Path
		String pathName = request.getServletPath();
		// 去掉路径中最后一个/之前的内容
		pathName = pathName.substring(pathName.lastIndexOf("/") + 1);
		// 去掉路径中.之后的内容
		pathName = pathName.substring(0, pathName.indexOf("."));
		// 返回方法的名称
		return pathName;
	}

	/**
	 * 拿到方法名的Method对象
	 * 
	 * @param request
	 * @param method
	 * @param methodName
	 * @param handler
	 * @return
	 */
	private Method convertMethodName(HttpServletRequest request,
			Method[] method, String methodName, Object handler) {
		Method hasNoProxyMethod = null;
		try {
			for (Method m : method) {
				if (m.getName().startsWith(SET_METHOD_PREFIX)
						|| m.getName().startsWith(GET_METHOD_PREFIX)
						|| m.getName().startsWith(TO_STRING_METHOD)
						|| m.getName().equals(CLONE_METHOD)) {
					continue;
				}
				// System.out.println(m.getName());
				if (m.getName().equals(methodName)) {
					Class[] tmpCs = m.getParameterTypes();
					hasNoProxyMethod = handler.getClass().getMethod(methodName,
							tmpCs);
					break;
				}
			}

			if (hasNoProxyMethod == null) {
				if (handler instanceof MultiActionController) {
					MultiActionController maControl = (MultiActionController) handler;
					MethodNameResolver pmrResolver = maControl
							.getMethodNameResolver();
					methodName = pmrResolver.getHandlerMethodName(request);
					for (Method m : method) {
						if (m.getName().startsWith(SET_METHOD_PREFIX)
								|| m.getName().startsWith(GET_METHOD_PREFIX)
								|| m.getName().startsWith(TO_STRING_METHOD)
								|| m.getName().equals(CLONE_METHOD)) {
							continue;
						}
						if (m.getName().equals(methodName)) {
							Class[] tmpCs = m.getParameterTypes();
							hasNoProxyMethod = handler.getClass().getMethod(
									methodName, tmpCs);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(
					"identityFeatureInterceptor hasNoProxyMethod throws error",
					e);
		}

		return hasNoProxyMethod;
	}

	public void setUserLogService(UserLogService userLogService) {
		this.userLogService = userLogService;
	}

	public UserLogService getUserLogService() {
		return userLogService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setIndividualInfoService(IndividualInfoService individualInfoService) {
		this.individualInfoService = individualInfoService;
	}

	public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}
	
}
