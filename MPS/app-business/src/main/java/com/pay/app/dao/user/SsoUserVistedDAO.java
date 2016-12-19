package com.pay.app.dao.user;
/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-8-20 下午05:21:13
 */
public interface SsoUserVistedDAO {
	/**
	 * 根据userId查询
	 * @param userId
	 * @return
	 */
	public int querySsoUserVistedByUserId(String userId);
}
