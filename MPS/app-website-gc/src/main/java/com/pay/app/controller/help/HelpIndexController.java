/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.controller.help;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.service.announcement.AnnouncementService;

/**
 * 帮助中心首页
 * @author fjl
 * @date 2011-5-18
 */
public class HelpIndexController extends MultiActionController {
	private String index;
	
	private AnnouncementService announcementService;

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int topnum=8;
		List list = announcementService.queryTopAnnouncement(topnum);
		
		return new ModelAndView(index).addObject("ggls",list);
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @param announcementService the announcementService to set
	 */
	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}
	
	
}
