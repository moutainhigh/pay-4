/**
 *  File: AnnouncementService.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-13   terry_ma     Create
 *
 */
package com.pay.app.service.announcement;

import java.util.List;
import java.util.Map;

import com.pay.app.dto.AnnouncementDTO;
import com.pay.app.dto.BaseDTO;
import com.pay.inf.service.BaseService;

/**
 * 
 */
public interface AnnouncementService extends BaseService{
	/**
	 * 分页查询
	 * 
	 * @param messageReceiveDto
	 * @return
	 */
	List<AnnouncementDTO> queryAnnouncementByPage(BaseDTO bd);

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
	List<AnnouncementDTO> queryTopAnnouncement(Integer topnum);
	
	/**
	 * 统计广告的总条数
	 * 
	 * @return
	 */
	Integer countAnnouncement(Map<String, Object> hMap);
	
	/**
	 * 创建
	 * @param sqId
	 * @param object
	 */
	void create(String sqlId, Object object) ;
}
