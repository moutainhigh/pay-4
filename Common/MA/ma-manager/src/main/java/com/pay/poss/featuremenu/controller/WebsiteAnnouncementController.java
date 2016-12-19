/**
 * 
 */
package com.pay.poss.featuremenu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.common.SessionUserUtils;
import com.pay.poss.featuremenu.dto.AnnouncementDto;
import com.pay.poss.featuremenu.model.Announcement;
import com.pay.poss.featuremenu.service.AnnouncementService;
import com.pay.poss.security.model.SessionUserHolder;

/**
 * @Description website公告管理
 * @project 	poss-membermanager
 * @file 		WebsiteAnnouncementController.java 
 * @note		<br>
 * @develop		JDK1.6 + SpringSource 2.3.2
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-11-6		tianqing_wang			Create
 */
public class WebsiteAnnouncementController extends MultiActionController {
	private Log log = LogFactory.getLog(WebsiteAnnouncementController.class);
	private AnnouncementService announcementService;
	private String announcementSearchView;
	private String announcementSearchListView;
	private String announcementEditView;
	private String announcementCreateView;
	private String announcementSortView;
	
	
	public ModelAndView index(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return new ModelAndView(announcementSearchView);
    }
	
	//公告列表
	@SuppressWarnings("unchecked")
	public ModelAndView announcementSearchList(HttpServletRequest request,
            HttpServletResponse response,AnnouncementDto announcementDto) throws Exception {
		Map paramMap = new HashMap(4);
		paramMap.put("subject", announcementDto.getSubject());
		paramMap.put("message", announcementDto.getMessage());
		Page<AnnouncementDto> page = PageUtils.getPage(request);
		List<AnnouncementDto> announcemenList = announcementService.findAnnouncementByCondition(paramMap, page);
        //List<FeatureDto> featureList = featureService.queryAllFeature();
		page.setResult(announcemenList);
        return new ModelAndView(announcementSearchListView)
                    .addObject("page",page);  
    }
	
	//删除公告
	@SuppressWarnings("unchecked")
	public ModelAndView deleteAnnouncement(HttpServletRequest request,
            HttpServletResponse response,AnnouncementDto announcementDto) throws Exception {
		Map paramMap = new HashMap(1);
		paramMap.put("announcementId", announcementDto.getAnnouncementId());
		announcementService.deleteAnnouncement(paramMap);
        return new ModelAndView(announcementSearchView);
    }
	//新增公告
	@SuppressWarnings("unecked")
	public ModelAndView createAnnouncementView(HttpServletRequest request,
            HttpServletResponse response,AnnouncementDto announcementDto) throws Exception {
		AnnouncementDto result = announcementService.findAnnouncementById(announcementDto);
		return new ModelAndView(announcementCreateView)
	    			.addObject("result",result);
    }
	
	//新增公告保存
	@SuppressWarnings("unecked")
	public ModelAndView createSaveAnnouncement(HttpServletRequest request,
            HttpServletResponse response,AnnouncementDto announcementDto) throws Exception {
		SessionUserHolder user = SessionUserUtils.getUserInfo(request);
		Announcement  anouncement = new Announcement();
		anouncement.setDisplaysort(announcementDto.getDisplaysort());
		anouncement.setSubject(announcementDto.getSubject());
		anouncement.setMessage(announcementDto.getMessage());
		//如果有announcementId进行修改操作，如果无进行创建操作
		if(null != announcementDto.getAnnouncementId()){
			//如果session失效则不进行插入操作
			if(user!=null){
				announcementDto.setAuthor(user.getUsername());
				announcementService.updateAnnouncement(announcementDto);
			}
		}else{
			//如果session失效则不进行插入操作
			if(user!=null){
				anouncement.setAuthor(user.getUsername());
				announcementService.createAnnouncement(anouncement);
			}
		}
        return new ModelAndView(announcementCreateView)
        			.addObject("result",announcementDto);	
    }
	
	
	//排序显示页面
	public ModelAndView announcementSortView(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
		List<AnnouncementDto> announcemenList = announcementService.findAnnouncementByCondition(new HashMap(2));
        return new ModelAndView(announcementSortView)
        	.addObject("announcemenList",announcemenList);
     }

	//调整排序后保存
	 public ModelAndView doSorting(HttpServletRequest request,
	            HttpServletResponse response)throws Exception{
	        List<AnnouncementDto> announcementDtoList=new ArrayList<AnnouncementDto>();
	       
	        //提交排序部分
	        String menuIds=request.getParameter("menuIdArry");
	        if(menuIds!=null &&  menuIds.length()>0){
	            String[] menuIdArray=menuIds.split(",");
	            int k=1;
	            AnnouncementDto md=null;
	            for(String s: menuIdArray){
	                md=new AnnouncementDto();
	                md.setAnnouncementId(Long.valueOf(s));
	                md.setDisplaysort(k);
	                k++;
	                announcementDtoList.add(md);
	            }
	            announcementService.updateSorting(announcementDtoList);
	        }
	        //提交完以后的页面刷新
	        List<AnnouncementDto> announcemenList = announcementService.findAnnouncementByCondition(new HashMap(2));
	        return new ModelAndView(announcementSortView)
	        	.addObject("announcemenList",announcemenList);
	    }
	
	
	
	
	
	
	

	public String getAnnouncementSearchView() {
		return announcementSearchView;
	}
	public void setAnnouncementSearchView(String announcementSearchView) {
		this.announcementSearchView = announcementSearchView;
	}
	public String getAnnouncementSearchListView() {
		return announcementSearchListView;
	}
	public void setAnnouncementSearchListView(String announcementSearchListView) {
		this.announcementSearchListView = announcementSearchListView;
	}
	public String getAnnouncementEditView() {
		return announcementEditView;
	}
	public void setAnnouncementEditView(String announcementEditView) {
		this.announcementEditView = announcementEditView;
	}
	public String getAnnouncementCreateView() {
		return announcementCreateView;
	}
	public void setAnnouncementCreateView(String announcementCreateView) {
		this.announcementCreateView = announcementCreateView;
	}
	public String getAnnouncementSortView() {
		return announcementSortView;
	}
	public void setAnnouncementSortView(String announcementSortView) {
		this.announcementSortView = announcementSortView;
	}
	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}
	
	
}
