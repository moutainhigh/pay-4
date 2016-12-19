/**
 *  File: AnnouncementDAO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-13   terry_ma     Create
 *
 */
package com.pay.app.dao.announcement;

import java.util.List;
import java.util.Map;

import com.pay.app.dto.BaseDTO;
import com.pay.app.model.Announcement;
import com.pay.inf.dao.BaseDAO;

/**
 * 
 */
public interface AnnouncementDAO extends BaseDAO {
	/**
	 * 分页查询
	 * 
	 * @param messageReceiveDto
	 * @return
	 */
	List<Announcement> queryAnnouncementByPage(BaseDTO bd);

	/**
	 * 统计广告的总条数
	 * 
	 * @return
	 */
	Integer countAnnouncement();

	/**
	 * 根据topNum查询top n 条广告信息
	 * 
	 * @param messageReceiveDto
	 * @return
	 */
	List<Announcement> queryTopAnnouncement(Integer topnum);
	
	/**
	 * 统计广告的总条数
	 * 
	 * @return
	 */
	Integer countAnnouncement(Map<String, Object> hMap);

}
