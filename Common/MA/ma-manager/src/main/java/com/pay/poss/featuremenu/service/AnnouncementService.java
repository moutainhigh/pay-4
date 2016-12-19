/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.poss.featuremenu.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.featuremenu.dto.AnnouncementDto;
import com.pay.poss.featuremenu.model.Announcement;

/**
 * website公告管理
 * @author tianqing_wang
 */
public interface AnnouncementService {

	/**
     * 查询公告
     * @param paramMap
     * @param page
     * @return List<AnnouncementDto>
     */
	public List<AnnouncementDto> findAnnouncementByCondition(Map paramMap,Page page) ;
	
	/**
	 * 排序
     * @param paramMap
     * @return List<AnnouncementDto>
     */
	public List<AnnouncementDto> findAnnouncementByCondition(Map paramMap) ;
	
	/**
     * 删除公告告
     * @param paramMap
     */
	public void deleteAnnouncement(Map paramMap) ;
	
	/**
     * 添加公告
     * @param Announcement
     * @return Long
     */
	public Long createAnnouncement(Announcement nnouncement);
	
	/**
     * 对公告排序
     * @param List<AnnouncementDto> mList
     */
	 public void updateSorting(final List<AnnouncementDto> mList);
	 
	 /**
	  * 对公告排序
      * @param announcementDto
      */
	 public void updateAnnouncement(AnnouncementDto announcementDto);
	 
	 /**
	  * 获取公告对象
      * @param announcementDto
      */
	 public AnnouncementDto findAnnouncementById(AnnouncementDto announcementDto);
}
