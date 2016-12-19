package com.pay.app.controller.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ActivityAreaController extends MultiActionController{
	private String activityAreaViewPrefix;
	private String activityAreaIndex;
	public ModelAndView activityArea(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageView = ServletRequestUtils.getStringParameter(request, "view",activityAreaIndex);
		return new ModelAndView(activityAreaViewPrefix+pageView).addObject("view",pageView);
	}
	

	public void setActivityAreaViewPrefix(String activityAreaViewPrefix) {
		this.activityAreaViewPrefix = activityAreaViewPrefix;
	}

	public void setActivityAreaIndex(String activityAreaIndex) {
		this.activityAreaIndex = activityAreaIndex;
	}

}
