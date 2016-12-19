package com.pay.app.controller.base.info;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.SessionHelper;
import com.pay.app.dto.AnnouncementDTO;
import com.pay.app.service.announcement.AnnouncementService;

public class InformationController extends MultiActionController{

	private String individView;
	private String merchantsView;
	private String infoDetailView;
	private String selfServiceView;
	private String infoAnnouncementView;

	private AnnouncementService announcementService;
	                  
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		return new ModelAndView("redirect:/corp/announcement.htm");
	}
	
	public ModelAndView individInfo(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		return new ModelAndView(individView);
	}
	
	@SuppressWarnings("rawtypes")
	public ModelAndView infoAnnouncement(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String aId = request.getParameter("id");
		Long id=0L;
		try {
			id = Long.valueOf(aId);
		} catch (Exception e) {
			id=0L;	
		}
		AnnouncementDTO announcement = (AnnouncementDTO) announcementService.findById(id);
		try {
			String memberCode = SessionHelper.getMemeberCodeBySession() ;
			Map<String, Object> queryRelationMap = new HashMap<String, Object>() ;
			queryRelationMap.put("memberCode", memberCode) ;
			queryRelationMap.put("announcementId", aId) ;
			List list = this.announcementService.findByCriteria("findRelation", queryRelationMap) ;
			if(org.apache.commons.collections.CollectionUtils.isEmpty(list)){
				Map<String, Object> relationMap = new HashMap<String, Object>() ;
				relationMap.put("memberCode", memberCode) ;
				relationMap.put("announcementId", aId) ;
				relationMap.put("createTime", new Timestamp(System.currentTimeMillis())) ;
				this.announcementService.create("createRealtion", relationMap);
			}else{
				if(logger.isInfoEnabled()){
					logger.info("公告与商户关联关系与存在，不重复创建！！");
				}
			}
		} catch (Exception e) {
			logger.error("保存公告商户关联关系失败：" + e);
		}
		return new ModelAndView(infoAnnouncementView).addObject("announcement",announcement);
	}
	
	public ModelAndView merchantsInfo(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		return new ModelAndView(merchantsView);
	}
	
	public ModelAndView infoDetail(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		return new ModelAndView(infoDetailView);
	}
	
	public ModelAndView selfService(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		return new ModelAndView(selfServiceView);
	}
	
	public void setIndividView(final String individView) {
		this.individView = individView;
	}

	public void setMerchantsView(final String merchantsView) {
		this.merchantsView = merchantsView;
	}

	public void setInfoDetailView(final String infoDetailView) {
		this.infoDetailView = infoDetailView;
	}

	public void setSelfServiceView(final String selfServiceView) {
		this.selfServiceView = selfServiceView;
	}

	public void setInfoAnnouncementView(final String infoAnnouncementView) {
		this.infoAnnouncementView = infoAnnouncementView;
	}

	
	public void setAnnouncementService(final AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

}
