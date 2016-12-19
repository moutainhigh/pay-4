package com.pay.notify.service.impl;

import java.util.Map;

import com.pay.notify.dao.NotifyTemplateDAO;
import com.pay.notify.model.NotifyTemplate;
import com.pay.notify.service.NotifyTemplateService;

public class NotifyTemplateServiceImpl implements NotifyTemplateService{

	private NotifyTemplateDAO notifyTemplateDAO;

	public void setNotifyTemplateDAO(NotifyTemplateDAO notifyTemplateDAO) {
		this.notifyTemplateDAO = notifyTemplateDAO;
	}
	//-------------------------------
	public NotifyTemplate findTemplateById(long id) {
		return notifyTemplateDAO.findTemplateById(id);
	}
	
	public NotifyTemplate findTemplate(Map<String,Object> params) {
		return notifyTemplateDAO.findTemplate(params);
	}

	@Override
	public void saveNotifyTemplate(NotifyTemplate entry) {
		notifyTemplateDAO.saveNotifyTemplate(entry);
	}

	
}
