/**
 * 
 */
package com.pay.wechat.util;

/**
 * @author PengJiangbo
 *
 */
public class Oauth2AccessToken {
	private static String access_token = "" ;

	public static String getAccess_token() {
		return access_token;
	}

	public static void setAccess_token(String access_token) {
		Oauth2AccessToken.access_token = access_token;
	}
	
	
}
