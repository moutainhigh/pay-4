/**
 * 
 */
package com.pay.wechat.main;

import com.pay.wechat.model.AccessToken;
import com.pay.wechat.util.WeiXinUtil;


/**
 * 自定义菜单查询
 * @author PengJiangbo
 *
 */
public class MenuQuery {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//第三方用户唯一凭证   
		//使用的是测试接口  账号(权限接口范围更广)
		String appid = "wx2026588e61f19035" ;			
		String appsecret = "e85c278032bce703597d4541256bfa35" ;
		AccessToken accessToken = WeiXinUtil.getAccessToken(appid, appsecret) ;
		
		//查询自定义菜单
		if(null != accessToken){
			String str = WeiXinUtil.queryMenu(accessToken.getToken()) ;
			System.out.println(str);
		}
		
	}

}
