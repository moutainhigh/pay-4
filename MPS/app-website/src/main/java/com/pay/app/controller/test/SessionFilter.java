/**
 *  File: SessionFilter.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-17   Terry Ma    Create
 *
 */
package com.pay.app.controller.test;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 */
public class SessionFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		req.getSession().setAttribute("username", "马超湖");
		req.getSession().setAttribute("userid", "1000");
		req.getSession().setAttribute("secondaryUsername", "machaohu");
		req.getSession().setAttribute("secondaryId", "machaohu");
		req.getSession().setAttribute("ticket", "aaa");
		req.getSession().setAttribute("castgc", "bbb");
		
		filterChain.doFilter(request, response); 
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
