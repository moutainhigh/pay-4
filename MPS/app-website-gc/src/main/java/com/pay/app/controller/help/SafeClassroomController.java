/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.controller.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * 安全课堂
 * @author fjl
 *
 */
public class SafeClassroomController extends MultiActionController{
	
	private String cheatPage;
	private String fishingPage;
	private String trojanhorsePage;
	
	
	public ModelAndView cheat(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isapp = request.getParameter("isapp");
		if(StringUtils.isNotBlank(isapp)){
			if(!isapp.equals("1")){
				isapp = "0";
			}
		}else{
			isapp = "0";
		}
		return new ModelAndView(cheatPage).addObject("isapp", isapp);
	}
	
	public ModelAndView fishing(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isapp = request.getParameter("isapp");
		if(StringUtils.isNotBlank(isapp)){
			if(!isapp.equals("1")){
				isapp = "0";
			}
		}else{
			isapp = "0";
		}
		return new ModelAndView(fishingPage).addObject("isapp", isapp);
	}
	
	public ModelAndView trojanhorse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isapp = request.getParameter("isapp");
		if(StringUtils.isNotBlank(isapp)){
			if(!isapp.equals("1")){
				isapp = "0";
			}
		}else{
			isapp = "0";
		}
		return new ModelAndView(trojanhorsePage).addObject("isapp", isapp);
	}

	public void setCheatPage(String cheatPage) {
		this.cheatPage = cheatPage;
	}

	public void setFishingPage(String fishingPage) {
		this.fishingPage = fishingPage;
	}

	public void setTrojanhorsePage(String trojanhorsePage) {
		this.trojanhorsePage = trojanhorsePage;
	}
	
	
	
}
