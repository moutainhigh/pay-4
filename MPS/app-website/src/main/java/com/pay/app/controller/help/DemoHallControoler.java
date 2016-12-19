/**
 *  File: CenterHelpController.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-10-28   single_zhang     Create
 *
 */
package com.pay.app.controller.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


/**
 * 帮助中心问题解决
 * @author Administrator
 *
 */
public class DemoHallControoler extends MultiActionController {
	
	private String demohall;
	
	private String halldetails;
	
	private String safeHelp;
	
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(demohall);
	}
	
	public ModelAndView detalis(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(halldetails);
	}
	
	public ModelAndView safehelp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(safeHelp);
	}


	public void setDemohall(String demohall) {
		this.demohall = demohall;
	}

	public void setHalldetails(String halldetails) {
		this.halldetails = halldetails;
	}

	public void setSafeHelp(String safeHelp) {
		this.safeHelp = safeHelp;
	}
	
}
