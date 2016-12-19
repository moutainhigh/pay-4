/**
 * 
 */
package com.pay.wechat.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.wechat.model.AccessToken;
import com.pay.wechat.service.accesstoken.BasicAccessTokenService;
import com.pay.wechat.util.WeiXinUtil;


/**
 * 定时获取access_token，获取－>定时获取更新以保证每次获取到的access_token值都是有效的
 * @author PengJiangbo
 *
 */
public class TokenThread extends MultiActionController implements Runnable {

	private static Logger log = LoggerFactory.getLogger(TokenThread.class) ;
	
	ApplicationContext act = new ClassPathXmlApplicationContext("classpath*:context/**/*.xml") ;
	BasicAccessTokenService basicAccessTokenService = (BasicAccessTokenService) act.getBean("app-basicAccessTokenService") ;
	
	//第三方用户唯一凭证
	public static String appid = "" ;
	//第三方用户唯一密钥
	public static String appsecret = "" ;
	public static AccessToken accessToken = null ;
	
	public void run() {

		while(true){
			//调用微信公众平台接口获取access_toke
			try {
				accessToken = WeiXinUtil.getAccessToken(appid, appsecret) ;
				if(null != accessToken){
					log.info("获取assess_token成功，有效期:{},token:{}", accessToken.getExpiresIn(), accessToken.getToken());
					//保存进库
					try {
						this.basicAccessTokenService.insertOrUpdate(accessToken.getToken());
					} catch (Exception e) {
						log.info(e.getMessage());
					} 
					//休眠7000秒
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
				}else{
					//如果accessToken为null,60秒后重新获取
					Thread.sleep(60 * 1000);
				}
			} catch (Exception e) {
				log.info("{}", e.getMessage());
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					log.error("{}", e1);
				}
			}
		}
	}
	
	//---------------------setter----------
	
	
}
