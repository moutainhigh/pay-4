package com.pay.app.service.user;

import com.pay.app.model.Ssouservisted;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-8-20 下午04:25:27
 * SSO访问记录服务
 */
public interface SsoUserVistedService {
	
	/**
	 * 保存SSO访问
	 * @param userId
	 * @return 
	 */
	public Ssouservisted saveSsoUserVisted(String userId);
	
	/**
	 * 根据userId查询
	 * @param userId
	 * @return
	 */
	public int querySsoUserVistedByUserId(String userId);
}
