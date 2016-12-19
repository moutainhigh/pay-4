package com.pay.app.controller.base.login;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.util.RSAHelper;

public class LoginOutController  extends MultiActionController {
	
	private String logout;
	private String securityLogOut;
	private String enterpriceSecurityLogout;
	private String individualLogout;
	private String individualSecurityLogout;
	private String activePage;
	

    private String enterpriceLogout;

    
    public void setSecurityLogOut(String securityLogOut) {
		this.securityLogOut = securityLogOut;
	}
	public void setLogout(String logout) {
		this.logout = logout;
	}
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		String mtype=request.getParameter("mtype")==null?"1":request.getParameter("mtype");
		if(mtype.equals("2")){
			return new ModelAndView("redirect:/index.htm");
        }
		return new ModelAndView("redirect:/index.htm?method=indexApp");
		//return new ModelAndView(logout);
	}
	
	
	public void clear(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			response.setContentType("text/plain;charset=UTF-8");
	        response.setHeader("Cache-Control", "no-cache");
	    	request.getSession().invalidate();
		
	    	PrintWriter out = null;
	        out = response.getWriter();
	        boolean flag=false;
	        
	        out.print(flag);
	        out.flush();
	        out.close();
	}
	
	public ModelAndView security(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		String pkey=RSAHelper.public_key_String;
		return new ModelAndView(securityLogOut).addObject("pkey",pkey);
	}
	
	public ModelAndView out(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    	String mtype=request.getParameter("mtype")==null?"1":request.getParameter("mtype");
			//如果是企业用户
			if(mtype.equals("1")){
				String ln=request.getParameter("ln")==null?"":request.getParameter("ln");
				if(StringUtils.isNotBlank(ln)){
					request.getSession().setAttribute("hascode", "1");
			    	//激活时带认证码
					return new ModelAndView(activePage).addObject("memberType",mtype).addObject("ln",ln);
				}
				return new ModelAndView(individualSecurityLogout).addObject("memberType",mtype).addObject("ln",ln);
				
			}
			return new ModelAndView(enterpriceSecurityLogout).addObject("memberType",mtype);
	}
	
	public void setIndividualLogout(String individualLogout) {
        this.individualLogout = individualLogout;
    }
    public void setEnterpriceLogout(String enterpriceLogout) {
        this.enterpriceLogout = enterpriceLogout;
    }
	public void setEnterpriceSecurityLogout(String enterpriceSecurityLogout) {
		this.enterpriceSecurityLogout = enterpriceSecurityLogout;
	}
	public void setIndividualSecurityLogout(String individualSecurityLogout) {
		this.individualSecurityLogout = individualSecurityLogout;
	}
	public void setActivePage(String activePage) {
		this.activePage = activePage;
	}
    
}
