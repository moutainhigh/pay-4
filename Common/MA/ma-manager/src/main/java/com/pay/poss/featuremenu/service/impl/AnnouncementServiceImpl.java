/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.poss.featuremenu.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.poss.featuremenu.dao.AnnouncementDAO;
import com.pay.poss.featuremenu.dto.AnnouncementDto;
import com.pay.poss.featuremenu.model.Announcement;
import com.pay.poss.featuremenu.service.AnnouncementService;

/**
 * website公告管理
 * 
 * @author tianqing_wang
 */
public class AnnouncementServiceImpl implements AnnouncementService {

	private static final Log logger = LogFactory
			.getLog(AnnouncementServiceImpl.class);

	private AnnouncementDAO announcementDAO;

	public List<AnnouncementDto> findAnnouncementByCondition(Map paramMap,
			Page page) {
		Integer pageStartRow;// 起始行
		Integer pageEndRow;// 结束行
		if (null == page) {
			return null;
		}
		Integer totalCount = announcementDAO
				.countAnnouncementByCondition(paramMap);
		page.setTotalCount(totalCount);
		if (totalCount == 0) {
			return null;
		}
		pageEndRow = page.getPageNo() * page.getPageSize();
		if ((page.getPageNo() - 1) == 0) {
			pageStartRow = 0;
		} else {
			pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
		}
		paramMap.put("pageStartRow", pageStartRow);
		paramMap.put("pageEndRow", pageEndRow);
		return announcementDAO.findAnnouncementByCondition(paramMap);
	}

	public List<AnnouncementDto> findAnnouncementByCondition(Map paramMap) {
		paramMap.put("pageStartRow", 0);
		paramMap.put("pageEndRow", 1000);
		return announcementDAO.findAnnouncementByCondition(paramMap);
	}

	public void deleteAnnouncement(Map paramMap) {
		announcementDAO.deleteAnnouncement(paramMap);
	}

	public Long createAnnouncement(Announcement announcement) {
		return announcementDAO.createAnnouncement(announcement);
	}

	public void updateSorting(final List<AnnouncementDto> mList) {
		announcementDAO.batchUpdate(mList);
	}

	public void updateAnnouncement(AnnouncementDto announcementDto) {
		announcementDAO.updateAnnouncement(announcementDto);
	}

	public AnnouncementDto findAnnouncementById(AnnouncementDto announcementDto) {
		return announcementDAO.findAnnouncementById(announcementDto);
	}

	public void setAnnouncementDAO(AnnouncementDAO announcementDAO) {
		this.announcementDAO = announcementDAO;
	}

	public Class getDtoClass() {
		return AnnouncementDto.class;
	}

}
