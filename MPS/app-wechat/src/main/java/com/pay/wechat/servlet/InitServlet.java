package com.pay.wechat.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.wechat.thread.MaterialThread;
import com.pay.wechat.thread.TokenThread;


/**
 * 初始化servlet
 * 
 * @author PengJiangbo
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(InitServlet.class);

	public void init() throws ServletException {
		//获取web.xml中配置的参数信息
    	TokenThread.appid = getInitParameter("appid") ;
    	TokenThread.appsecret = getInitParameter("appsecret") ;
    	
    	log.info("微信公众平台接入第三方唯一凭证：{}:", TokenThread.appid) ;
    	log.info("微信公众平台接入第三方唯一密钥：{}:", TokenThread.appsecret);
    	
    	//未配置appid、appsecret时给出提示
    	if("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)){
    		log.error("appid和appsecret获取失败，请排除错误！");
    	}else {
			//启动定时获取access_token的线程
    		new Thread(new TokenThread()).start() ;
			//启动定时获取永久性素材列表对象的线程add by davis at 2016-07-11
			new Thread(new MaterialThread()).start() ;
		}
	}
}