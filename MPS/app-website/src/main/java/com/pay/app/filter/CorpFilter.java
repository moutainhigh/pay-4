package com.pay.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.common.enums.OrginEnum;
import com.pay.app.common.helper.AppConf;

public class CorpFilter implements Filter{
    
    private static final Log logger = LogFactory.getLog(CorpFilter.class);
    private static  final String loginOutUrl="/outapp.htm?mtype=2";
    private static final String corploginOutUrl="/logout.htm?mtype=2";
    
    static final String findPaypasswordUrl = "/corp/tofoundpaypasswordbyemailpage.htm";
    
    @Override
    public void destroy() {
        
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String forwadrUrl=loginOutUrl;
        
        String requestPath=request.getRequestURI();
        if(requestPath.indexOf(findPaypasswordUrl) != -1 || requestPath.indexOf("/corp/images/logo_yt.png") != -1){
        	filterChain.doFilter(new XSSHttpServletRequestWrapper(
    				(HttpServletRequest) servletRequest), servletResponse);
            return;
        }
        
        /*****************判断用户是否登录而且为正常 用户********************/
        if(AppFilterCommon.isCorpNormalUser(request)){
            if(!AppFilterCommon.isSignature()){//判断用户是否登录验签是否正确
                RequestDispatcher rd=servletRequest.getRequestDispatcher(forwadrUrl);
                logger.info("corpFilter writer the log  [signature  is false will forward :"+corploginOutUrl+"]");
                rd.forward(servletRequest, servletResponse);
                return;
            }
            
            if(!AppFilterCommon.isNormalStatusUser(request)){//判断用户状态是否正常
            	RequestDispatcher rd=request.getRequestDispatcher("/error.htm?method=illegal");
            	logger.info("corpFilter writer the log  [status  is error ]");
            	rd.forward(servletRequest, servletResponse);
            	return ;
            }
            
            int result=AppFilterCommon.isHaveCertFeature();
            if(result==AppFilterCommon.nofeature){
            	RequestDispatcher rd=request.getRequestDispatcher("/error.htm?method=noFeature");
            	rd.forward(servletRequest, servletResponse);
            	return ;
            }else if(result==AppFilterCommon.nocert){
            	RequestDispatcher rd=request.getRequestDispatcher("/error.htm?method=noCert");
            	rd.forward(servletRequest, servletResponse);
            	return ;
            }
            	
//            if(!AppFilterCommon.isHaveFeature()){
//            	RequestDispatcher rd=request.getRequestDispatcher("/error.htm?method=noFeature");
//                rd.forward(servletRequest, servletResponse);
//                return ;
//            }
            
           
            //AppFilterCommon.makeMenuSelect(request);
            logger.info("corpFilter writer the log  user login success [memberCode:"+request.getSession().getAttribute("memberCode")+"]");
            //filterChain.doFilter(new XSSHttpServletRequestWrapper(request), servletResponse);
            filterChain.doFilter(new XSSHttpServletRequestWrapper(
    				(HttpServletRequest) servletRequest), servletResponse);
            return;
        } 
        
        /*****************记录登录前请求的url********************/
        AppFilterCommon.setCallBackUri(request,AppConf.defaultCorpCallBack,OrginEnum.CORP_LOCAL.getValue(),2);
        /*****************跳登录页********************/
        RequestDispatcher rd=servletRequest.getRequestDispatcher(forwadrUrl);
        logger.info("corpFilter writer the log  [no session  will forward :"+forwadrUrl+"]");
        rd.forward(servletRequest, servletResponse);

        return;
    }
    
    
    @Override
    public void init(FilterConfig arg0) throws ServletException {
    
    }

}
