/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.poss.featuremenu.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.pay.poss.featuremenu.dto.AnnouncementDto;
import com.pay.poss.featuremenu.model.Announcement;





/**
 * website公告管理
 * @author tianqing_wang
 */
public interface AnnouncementDAO {
    
    /**
     * 公告列表
     * @param paramMap
     * @returnList<AnnouncementDto>
     */
    public List<AnnouncementDto> findAnnouncementByCondition(Map paramMap);
    
    /**
     * 公告列表count
     * @param paramMap
     * @returnList<AnnouncementDto>
     */
    public Integer countAnnouncementByCondition(Map paramMap);
    
    /**
     * 删除公告
     * @param paramMap
     */
	public void deleteAnnouncement(Map paramMap) ;
	
	 /**
     * 修改公告
     * @param paramMap
     */
	public void updateAnnouncement(AnnouncementDto announcementDto)  ;
	 /**
     * 添加公告
     * @param Announcement
     */
	public Long createAnnouncement(Announcement Announcement);

	/**
     * 由id活动公告对象
     * @param announcementDto
     */
	public AnnouncementDto findAnnouncementById(AnnouncementDto announcementDto);
	
	
	public void batchUpdate(final List<AnnouncementDto> mList) throws DataAccessException;
	    
}
