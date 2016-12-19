/**
 * 
 */
package com.pay.wechat.service.accesstoken;

/**
 * @author PengJiangbo
 *
 */
public interface BasicAccessTokenService {

	/**
	 * access_token插入或新增
	 * @param accessToken
	 */
	public void insertOrUpdate(String accessToken) ;
	
}
