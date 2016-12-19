package com.pay.notify.dao;

import java.util.Map;

import com.pay.notify.model.NotifyTemplate;

public interface NotifyTemplateDAO {
	
	public void saveNotifyTemplate(NotifyTemplate entry);
	
	NotifyTemplate findTemplateById(long id);
	
	NotifyTemplate findTemplate(Map<String,Object> params);
	
}
