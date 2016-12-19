package com.pay.app.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;
import org.tuckey.web.filters.urlrewrite.UrlRewriteWrappedResponse;
import org.tuckey.web.filters.urlrewrite.UrlRewriter;
public class UrlFilter extends  UrlRewriteFilter {

	private static final Log logger = LogFactory.getLog(UrlFilter.class);


	// 只能处理所以有get请求，POST请求请先调用方法转成标准格式
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {	
		final HttpServletRequest hsRequest = (HttpServletRequest) request;
		final HttpServletResponse hsResponse = (HttpServletResponse) response;
		String type = hsRequest.getMethod();
		if ("get".equalsIgnoreCase(type)) {
			StringBuffer url = new StringBuffer(hsRequest.getRequestURI());
			String par = hsRequest.getQueryString();
			if (StringUtils.isNotBlank(par)) {
				url.append("?").append(par);
				UrlRewriter urlRewriter = getUrlRewriter(request, response, chain);
				UrlRewriteWrappedResponse urlRewriteWrappedResponse = new UrlRewriteWrappedResponse(hsResponse, hsRequest, urlRewriter);
				String encodeUrl= urlRewriteWrappedResponse.encodeURL(url.toString());
				if(encodeUrl.equalsIgnoreCase(url.toString())){
					chain.doFilter(hsRequest, urlRewriteWrappedResponse);
				}else{
					logger.debug("UrlFilter the log  [sendRedirect url :" + encodeUrl+ "]");
					urlRewriteWrappedResponse.sendRedirect(java.net.URLDecoder.decode(encodeUrl,"UTF-8"));
					return;
				}	
			}
			else {
				chain.doFilter(hsRequest, response);
			}
		}
		else {
			chain.doFilter(hsRequest, response);
		}

	}



}
