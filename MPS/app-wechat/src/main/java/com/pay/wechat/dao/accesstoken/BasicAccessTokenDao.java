/**
 * 
 */
package com.pay.wechat.dao.accesstoken;

/**
 * 基础access_tokenDAO
 * @author PengJiangbo
 *
 */
public interface BasicAccessTokenDao {

	/**
	 * access_token插入或新增
	 * @param accessToken
	 */
	public void insertOrUpdate(String accessToken) ;
	
}
