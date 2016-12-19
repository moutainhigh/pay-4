package com.pay.notify.dao.impl;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.pay.notify.dao.NotifyTemplateDAO;
import com.pay.notify.model.NotifyTemplate;

public class NotifyTemplateDAOImpl extends SqlMapClientDaoSupport implements
		NotifyTemplateDAO {
	//protected String namespace = "notifyTemplate";
	public NotifyTemplate findTemplateById(long id) {
		return (NotifyTemplate) getSqlMapClientTemplate().queryForObject(
				"findTemplateById", id);//namespace.concat("findTemplateById")
	}
	
	public NotifyTemplate findTemplate(Map<String,Object> params) {
		return (NotifyTemplate) getSqlMapClientTemplate().queryForObject(
				"findTemplate", params);
	}

	@Override
	public void saveNotifyTemplate(NotifyTemplate entry) {
		getSqlMapClientTemplate().insert("save", entry);
	}
}
