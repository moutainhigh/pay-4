/**
 *  File: CenterHelpController.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-10-27   single_zhang     Create
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
public class ProblemSolvingController extends MultiActionController {
	
	private String issuesExplained;
	
	private String listOfIssuesView;
	
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String step = request.getParameter("step");
		return new ModelAndView(issuesExplained).addObject("step",step);
	}
	
	public ModelAndView listofIssues(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String step = request.getParameter("step");
		return new ModelAndView(listOfIssuesView).addObject("step",step);
	}

	public void setIssuesExplained(String issuesExplained) {
		this.issuesExplained = issuesExplained;
	}

	public void setListOfIssuesView(String listOfIssuesView) {
		this.listOfIssuesView = listOfIssuesView;
	}
}
