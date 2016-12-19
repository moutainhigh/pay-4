//package com.pay.wechat.test;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Properties;
//
//import net.sf.json.JSONObject;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.pay.util.MD5Util;
//import com.pay.wechat.model.AccessToken;
//import com.pay.wechat.model.CustomMessageText;
//import com.pay.wechat.model.Text;
//import com.pay.wechat.thread.TokenThread;
//import com.pay.wechat.util.LoadPropertiesUtil;
//import com.pay.wechat.util.WeiXinUtil;
//
//public class JTest {
//	String appid = "";
//	String appsecret = "";
//	@Before
//	public void setUp() throws Exception {
//		System.out.println("================做一些数据初始化的操作开始==========================");
//		appid = "wx5c2672970ee835e8";//wx5c2672970ee835e8
//		appsecret = "8e0fe9e93eb73de40f1c8a4886da70cd"; //8e0fe9e93eb73de40f1c8a4886da70cd
//		System.out.println("================做一些数据初始化的操作结束==========================");
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void testMD5Util() {
//		String md5Hex = MD5Util.md5Hex("asdffdsa") ;
//		System.out.println(md5Hex);
//	}
//	
//	@Test
//	public void testSendCustomMessage(){
//		//,\"text":{\"content\": \"Hello World22222\"}
//		String message = "{\"touser\": \"oGA6Et9UiJ4VRMDLXexOd9Kh4_3M\",\"msgtype\": \"text\",\"text\":{\"content\": \"哈哈-->>>>Hello World22222\"}}" ;
//		
//		JSONObject jsonObject = JSONObject.fromObject(message) ;
//		System.out.println("========>>>" + jsonObject);
//		
//		System.out.println(TokenThread.appid);
//		AccessToken accessToken = WeiXinUtil.getAccessToken(this.appid, this.appsecret) ;
//		System.out.println(accessToken);
//		
//		int sendCustomMessage = WeiXinUtil.sendCustomMessage(message, accessToken.getToken()) ;
//		System.out.println("sendCustomMessage:" + sendCustomMessage);
//	}
//	
//	//@Test
//	public void testProperties(){
//		String touser = "" ;
//		Properties props = new Properties() ;
//		
//		InputStream is = this.getClass().getResourceAsStream("wechatsendmessage.properties") ;
//		
//		try {
//			props.load(is);
//			touser = props.getProperty("touser") ;
//			System.out.println(touser);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} ;
//		
//	}
//	
//	//@Test
//	public void testJson(){
//		Text text = new Text() ;
//		text.setContent("Hey boy, I love you , 对，就是你");
//		CustomMessageText cmt = new CustomMessageText() ;
//		cmt.setTouser(LoadPropertiesUtil.getTouser());
//		cmt.setMsgtype("text");
//		cmt.setText(text);
//		String jsontStr = JSONObject.fromObject(cmt).toString() ;
//		System.out.println(jsontStr);
//	}
//	
//	//@Test
//	public void testTakeUserInfo(){
//		AccessToken accessToken = WeiXinUtil.getAccessToken(appid, appsecret) ;
//		System.out.println(accessToken.getToken());
//		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
//		url = url.replace("ACCESS_TOKEN", accessToken.getToken()).replace("OPENID", "oGA6Et9UiJ4VRMDLXexOd9Kh4_3M") ;
//		
//		JSONObject jsonObject = WeiXinUtil.httpRequest(url, "GET", null) ;
//		System.out.println(jsonObject.toString());
//		//UserInfo userInfo = WeiXinUtil.getUserInfo(accessToken.getToken(), "oGA6Et9UiJ4VRMDLXexOd9Kh4_3M") ;
//		//System.out.println(userInfo);
//	}
//	
//	@Test
//	public void testTemplateMessage(){
//		AccessToken accessToken = WeiXinUtil.getAccessToken(appid, appsecret) ;
//		//String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN" ;
//		//url = url.replace("ACCESS_TOKEN", accessToken.getToken()) ;
//		
//		String message = "{\"touser\":\"oGA6Et9UiJ4VRMDLXexOd9Kh4_3M\",\"template_id\":\"YrGwmiOFBa6gHai_f6OObTz11CwycowVpsInFVME2tE\",\"url\":\"http://www.baidu.com\",\"topcolor\":\"#FF0000\",\"data\":{\"first\":{\"value\":\"您好，您提交的信息披露已被采纳并公布!\",\"color\":\"#173177\"},\"keyword1\":{\"value\":\"巧克力\",\"color\":\"#173177\"},\"keyword2\":{\"value\":\"39.8元\",\"color\":\"#173177\"},\"keyword3\":{\"value\":\"33249.8元\",\"color\":\"#173177\"},\"remark\":{\"value\":\"欢迎再次购买！\",\"color\":\"#173177\"}}}" ;
//		
//		JSONObject jsonObject = JSONObject.fromObject(message) ;
//		System.out.println(jsonObject);
//		
//		int sendTemplateMessage = WeiXinUtil.sendTemplateMessage(message, accessToken.getToken()) ;
//		System.out.println(sendTemplateMessage);
//	}
//	
//	@Test
//	public void testD(){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
//		
//		String format = sdf.format(new Date()) ;
//		
//		System.out.println(format);
//	}
//	
//}
