/**
 *  File: AnnouncementServiceImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-13   terry_ma     Create
 *
 */
package com.pay.app.service.announcement.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pay.app.dao.announcement.AnnouncementDAO;
import com.pay.app.dto.AnnouncementDTO;
import com.pay.app.dto.BaseDTO;
import com.pay.app.model.Announcement;
import com.pay.app.service.announcement.AnnouncementService;
import com.pay.inf.service.impl.BaseServiceImpl;
import com.pay.util.BeanConvertUtil;

/**
 * 
 */
public class AnnouncementServiceImpl extends BaseServiceImpl implements
		AnnouncementService {

	@Override
	public List<AnnouncementDTO> queryAnnouncementByPage(BaseDTO bd) {
		List<Announcement> topAnnountcement = ((AnnouncementDAO) mainDao)
				.queryAnnouncementByPage(bd);
		List<AnnouncementDTO> listdto = new ArrayList<AnnouncementDTO>();
		for (Announcement announcement : topAnnountcement) {
			AnnouncementDTO dto = BeanConvertUtil.convert(
					AnnouncementDTO.class, announcement);
			listdto.add(dto);
		}
		return listdto;
	}

	@Override
	public Integer countAnnouncement() {
		return ((AnnouncementDAO) mainDao).countAnnouncement();
	}

	@Override
	public List<AnnouncementDTO> queryTopAnnouncement(Integer topnum) {
		List<Announcement> topAnnountcement = ((AnnouncementDAO) mainDao)
				.queryTopAnnouncement(topnum);
		List<AnnouncementDTO> listdto = new ArrayList<AnnouncementDTO>();
		for (Announcement announcement : topAnnountcement) {
			AnnouncementDTO dto = BeanConvertUtil.convert(
					AnnouncementDTO.class, announcement);
			listdto.add(dto);
		}
		return listdto;
	}

	@Override
	protected Class getModelClass() {
		return Announcement.class;
	}

	@Override
	protected Class getDtoClass() {
		return AnnouncementDTO.class;
	}

	@Override
	public Integer countAnnouncement(Map<String, Object> hMap) {
		return ((AnnouncementDAO) mainDao).countAnnouncement(hMap) ;
	}

	@Override
	public void create(String sqlId, Object object) {
		((AnnouncementDAO) mainDao).create(sqlId, object) ;
		
	}

}
