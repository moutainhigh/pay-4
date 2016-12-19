/**
 *  File: AnnouncementDAOImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-13   terry_ma     Create
 *
 */
package com.pay.app.dao.announcement.impl;

import java.util.List;
import java.util.Map;

import com.pay.app.dao.announcement.AnnouncementDAO;
import com.pay.app.dto.BaseDTO;
import com.pay.app.model.Announcement;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 */
public class AnnouncementDAOImpl extends BaseDAOImpl implements AnnouncementDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Announcement> queryAnnouncementByPage(BaseDTO bd) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("queryByPage"), bd);
	}

	@Override
	public Integer countAnnouncement() {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("count"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Announcement> queryTopAnnouncement(Integer topnum) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("queryTop"), topnum);
	}

	@Override
	public Integer countAnnouncement(Map<String, Object> hMap) {
		return (Integer) getSqlMapClientTemplate().queryForObject(namespace.concat("countByCriteria"), hMap) ;
	}

}
