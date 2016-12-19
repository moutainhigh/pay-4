package com.pay.app.controller.base.login;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.pay.app.filter.AppFilterCommon;


/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-7-19 下午06:41:29
 * 登出相关控制
 */
public class OutAppController implements Controller {
    private static final Log log = LogFactory.getLog(OutAppController.class);
	private String outapp;
	
	public void setOutapp(String outapp) {
		this.outapp = outapp;
	}
	/**
	 *outing , url:website/ftl/login
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AppFilterCommon.removePaySession(request);
		String mtype=request.getParameter("mtype")==null?"1":request.getParameter("mtype");
		return new ModelAndView(outapp).addObject("mtype",mtype);
	}
}
