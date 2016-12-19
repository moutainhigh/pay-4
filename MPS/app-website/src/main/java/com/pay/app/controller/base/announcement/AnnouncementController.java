/**
 *  File: AnnouncementController.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-13   terry_ma     Create
 *
 */
package com.pay.app.controller.base.announcement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.dto.AnnouncementParamDTO;
import com.pay.app.dto.BaseDTO;
import com.pay.app.service.announcement.AnnouncementService;

/**
 * 
 */
public class AnnouncementController extends MultiActionController {

	private String toView;
	private AnnouncementService announcementService;

	@SuppressWarnings("rawtypes")
	@Override
	protected ModelAndView handleRequestInternal(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		int totalCount = 0;
		int pager_offset = 1;
		int page_size = 20;
		totalCount = announcementService.countAnnouncement();
		try{
			pager_offset = request.getParameter("pager_offset") == null ? 1
					: Integer.parseInt(request.getParameter("pager_offset"));
		}catch(NumberFormatException e){
			pager_offset = 1;
		}
		PageUtil pu = new PageUtil(pager_offset, page_size, totalCount);
		int beginNum = pu.getStartIndex();
		int endNum = beginNum+page_size;
		//BaseDTO bd = new BaseDTO();
		//added by PengJiangbo
		Long memberCode = Long.valueOf(SessionHelper.getMemeberCodeBySession()) ;
		AnnouncementParamDTO bd = new AnnouncementParamDTO() ;
		bd.setBeginNum(beginNum);
		bd.setEndNum(endNum);
		bd.setMemberCode(memberCode);
		List list = announcementService.queryAnnouncementByPage(bd);
		
		return new ModelAndView(toView).addObject("list", list).addObject("pu",pu);
	}

	public void setToView(final String toView) {
		this.toView = toView;
	}

	public void setAnnouncementService(final AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}
	
}