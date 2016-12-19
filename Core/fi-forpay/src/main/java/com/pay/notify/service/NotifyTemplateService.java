package com.pay.notify.service;

import java.util.Map;

import com.pay.notify.model.NotifyTemplate;

public interface NotifyTemplateService {
	
	public void saveNotifyTemplate(NotifyTemplate entry);

	NotifyTemplate findTemplateById(long id);
	
	NotifyTemplate findTemplate(Map<String,Object> params);
}
