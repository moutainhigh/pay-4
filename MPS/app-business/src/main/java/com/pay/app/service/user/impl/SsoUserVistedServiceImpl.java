package com.pay.app.service.user.impl;

import java.util.Date;

import com.pay.app.dao.user.SsoUserVistedDAO;
import com.pay.app.model.Ssouservisted;
import com.pay.app.service.user.SsoUserVistedService;
import com.pay.inf.dao.BaseDAO;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-8-20 下午04:26:11
 */
public class SsoUserVistedServiceImpl implements SsoUserVistedService {

	private final int vistedstatus = 1;

	private BaseDAO mainDao;

	public void setMainDao(BaseDAO mainDao) {
		this.mainDao = mainDao;
	}

	@SuppressWarnings("unchecked")
	public Class getDtoClass() {
		return Ssouservisted.class;
	}

	@Override
	public int querySsoUserVistedByUserId(String userId) {
		int num = 0;
		num = ((SsoUserVistedDAO) mainDao).querySsoUserVistedByUserId(userId);
		return num;
	}

	@Override
	public Ssouservisted saveSsoUserVisted(String userId) {
		Ssouservisted ssouservisted = new Ssouservisted();
		ssouservisted.setUserId(userId);
		ssouservisted.setVistedtime(new Date());
		ssouservisted.setVistedstatus(vistedstatus);
		Ssouservisted model = (Ssouservisted) mainDao.create(ssouservisted);
		return model;
	}

}
