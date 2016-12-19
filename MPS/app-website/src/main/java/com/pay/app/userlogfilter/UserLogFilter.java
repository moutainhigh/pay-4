package com.pay.app.userlogfilter;

/**
 *  File: UserLogFilter.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-9   lihua     Create
 *
 */

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.userlogurl.UserLogURL;
import com.pay.base.dto.UserLogDTO;
import com.pay.base.service.user.UserLogService;
import com.pay.util.IPUtil;
import com.pay.util.WebUtil;

/**
 * 
 */
public class UserLogFilter implements Filter {

	private static UserLogService userLogService;

	private static final Log logger = LogFactory.getLog(UserLogFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub

		LoginSession loginSession;
		UserLogURL userLogURL = new UserLogURL();
		HttpServletRequest res = (HttpServletRequest) request;
		String ip = "";
		loginSession = SessionHelper.getLoginSession();
		// 校验是否成功登录并memberCode有值。
		if (loginSession == null || StringUtils.isBlank(loginSession.getMemberCode())) {
			chain.doFilter(request, response);
			return;
		}
		// 获取memberCode
		String memberCode = loginSession.getMemberCode();
		logger.info("memberCode:" + memberCode);
		try {
			ip = IPUtil.getLocalAddr();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("UserLogFilter", e);
		}
		String[] ipchr = null;
		if (null != ip && ip.indexOf("/") > 0) {
			ipchr = ip.toString().split("/");
		}
		logger.info("-------------IP:" + ipchr[1]);
		String url = res.getRequestURL().substring(
				res.getRequestURL().lastIndexOf("/"),
				res.getRequestURL().length());
		logger.info("-------------URL:" + url);
		String action = userLogURL.getAction(url) == null ? "" : userLogURL
				.getAction(url);
		String logType = userLogURL.getLogType(url) == null ? "" : userLogURL
				.getLogType(url);
		UserLogDTO userLogDTO = new UserLogDTO();
		if (loginSession != null && loginSession.getOperatorId() != null
				&& loginSession.getOperatorId() > 0) {
			userLogDTO.setOperatorId(loginSession.getOperatorId());
		} else {
			userLogDTO.setOperatorId(0L);
		}
		if(StringUtils.isBlank(logType)){
			logger.warn("URL:" + url+"not find LogType!");
			chain.doFilter(request, response);
			return;
		}
		userLogDTO.setMemberCode(Long.valueOf(memberCode));
		userLogDTO.setActionUrl(action);
		userLogDTO.setLogType(new Integer(logType));
		userLogDTO.setLoginDate(new Timestamp(new Date().getTime()));
		String clientIp= WebUtil.getClientIP(res);
		if (StringUtils.isNotBlank(clientIp)) {
			logger.info("clientIp-----IP:"+clientIp);
			userLogDTO.setLoginIp(clientIp);
		} else {
			userLogDTO.setLoginIp(ipchr[1]);
		}
		userLogDTO.setLoginName(loginSession.getLoginName());
		userLogDTO.setName(loginSession.getVerifyName());
		// 浏览器设置默认值 IE7
		String agent = res.getHeader("User-Agent");
		if (StringUtils.isEmpty(agent)) {
			agent = "IE7";
		} else {
			agent = agent.length() > 100 ? agent.substring(0, 100) : agent;
		}
		userLogDTO.setBrowserVer(agent);
		userLogService.create(userLogDTO);

		chain.doFilter(request, response);
	}

	public void setUserLogService(UserLogService userLogService) {
		this.userLogService = userLogService;
	}

	public UserLogService getUserLogService() {
		return userLogService;
	}

}
