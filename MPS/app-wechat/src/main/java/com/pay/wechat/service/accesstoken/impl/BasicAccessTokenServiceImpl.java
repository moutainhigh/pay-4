/**
 * 
 */
package com.pay.wechat.service.accesstoken.impl;

import com.pay.wechat.dao.accesstoken.BasicAccessTokenDao;
import com.pay.wechat.service.accesstoken.BasicAccessTokenService;

/**
 * @author PengJiangbo
 *
 */
public class BasicAccessTokenServiceImpl implements BasicAccessTokenService {

	//注入
	private BasicAccessTokenDao basicAccessTokenDao ;
	
	public void setBasicAccessTokenDao(BasicAccessTokenDao basicAccessTokenDao) {
		this.basicAccessTokenDao = basicAccessTokenDao;
	}

	@Override
	public void insertOrUpdate(String accessToken) {
		this.basicAccessTokenDao.insertOrUpdate(accessToken);
	}

}
