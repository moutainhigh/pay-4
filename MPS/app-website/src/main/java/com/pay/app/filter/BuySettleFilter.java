package com.pay.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.pay.app.base.session.LoginSession;

public class BuySettleFilter implements Filter{
    
    private static  final String loginOutUrl="/logout.htm?mtype=2";
    
    
    @Override
    public void destroy() {
        
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        LoginSession loginSession = (LoginSession) request.getSession().getAttribute("userSession");
        if(loginSession!=null){
        	filterChain.doFilter(servletRequest, servletResponse);
        }else{
        	request.getRequestDispatcher(loginOutUrl).forward(request, servletResponse);
        }
        return;
    }
    
    
    @Override
    public void init(FilterConfig arg0) throws ServletException {
    
    }

}
