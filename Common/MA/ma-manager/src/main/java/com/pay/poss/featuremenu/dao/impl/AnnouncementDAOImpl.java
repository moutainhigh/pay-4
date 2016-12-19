/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.poss.featuremenu.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.featuremenu.dao.AnnouncementDAO;
import com.pay.poss.featuremenu.dto.AnnouncementDto;
import com.pay.poss.featuremenu.model.Announcement;
import com.pay.util.BeanConvertUtil;

/**
 * website公告管理
 * 
 * @author tianqing_wang
 * 
 */
public class AnnouncementDAOImpl extends BaseDAOImpl implements AnnouncementDAO {

	@Override
	public List<AnnouncementDto> findAnnouncementByCondition(Map paramMap) {
		List<AnnouncementDto> AnnouncementList = this.getSqlMapClientTemplate()
				.queryForList(namespace.concat("findAnnouncementByCondition"),
						paramMap);
		;
		return AnnouncementList;
	}

	@Override
	public Integer countAnnouncementByCondition(Map paramMap) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countAnnouncementByCondition"), paramMap);
	}

	@Override
	public void deleteAnnouncement(Map paramMap) {
		this.getSqlMapClientTemplate().delete(
				namespace.concat("deleteAnnouncementById"), paramMap);
	}

	@Override
	public void updateAnnouncement(AnnouncementDto announcementDto) {
		this.getSqlMapClientTemplate().delete(namespace.concat("update"),
				BeanConvertUtil.convert(Announcement.class, announcementDto));
	}

	@Override
	public AnnouncementDto findAnnouncementById(AnnouncementDto announcementDto) {
		AnnouncementDto result = (AnnouncementDto) this
				.getSqlMapClientTemplate().queryForObject(
						namespace.concat("findAnnouncementById"),
						announcementDto);
		return result;
	}

	@Override
	public Long createAnnouncement(Announcement announcement) {
		return (Long) super.create(announcement);
	}

	public void batchUpdate(final List<AnnouncementDto> mList)
			throws DataAccessException {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				int batch = 0;
				// System.out.println(receiveList.size());
				for (AnnouncementDto tObject : mList) {
					// System.out.println(batch+"=="+tObject.getDelflag()+"=======================");
					executor.update(namespace.concat("updateSorting"),
							BeanConvertUtil
									.convert(Announcement.class, tObject));
					batch++;
					if (batch == 100) {
						executor.executeBatch();
						batch = 0;
					}
				}
				executor.executeBatch();
				return "";
			}
		});
	}

	public Class getModelClass() {
		return Announcement.class;
	}

}
