/**
 * 
 */
package com.pay.wechat.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.wechat.model.AccessToken;
import com.pay.wechat.model.JsapiTicket;
import com.pay.wechat.model.Menu;
import com.pay.wechat.model.OAuth2Resp;
import com.pay.wechat.model.UserInfo;
import com.pay.wechat.model.material.BatchMaterialParam;
import com.pay.wechat.model.material.Materialcount;
import com.pay.wechat.model.material.NewsContent;
import com.pay.wechat.model.material.NewsInfo;
import com.pay.wechat.model.material.NewsMaterial;
import com.pay.wechat.model.material.NewsMaterialList;
import com.pay.wechat.thread.TokenThread;

/**
 * 公众平台通用接口工具类
 * 
 * @author PengJiangbo
 *
 */
public class WeiXinUtil {

	private static Logger log = LoggerFactory.getLogger(WeiXinUtil.class);

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 菜单创建（POST） 限100（次/天）
	public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	// 菜单查询(GET)
	public final static String menu_query_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	// 获取微信服务器IP地址(GET)
	public final static String ip_list_url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";

	// 通过code换取网页授权access_token
	public final static String oauth2_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	//拉取用户信息url
	public final static String snsapi_userinfo_url ="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN" ;
	
	//发送客服消息
	public final static String custom_send_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN" ;
	
	//获取jsapi_ticket
	public final static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi" ;
	
	//发送模板消息
	public final static String template_message_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN" ;

	//获取素材总数
	public final static String get_materialcount_url = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN" ;
	
	//获取素材列表
	public final static String batchget_material_url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN" ;
	
	/**
	 * 发起https请求，并响应结果
	 * 
	 * @param requstUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式(GET、POST)
	 * @param outputStr
	 *            提交的数据
	 * @return
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer sb = new StringBuffer();

		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false); 
//	      	httpUrlConn.setRequestProperty("Accept-Charset", "UTF-8");  
			httpUrlConn.setRequestProperty("contentType", "UTF-8");  //add by davis at 2016-07-06
			// 设置请求方式（GET/POSt）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));//URLEncoder.encode(outputStr, "UTF-8");
				outputStream.close();
			}

			// 将返回输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");//add by davis at 2016-07-06
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				sb.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();

			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(sb.toString());

		} catch (ConnectException ce) {
			log.error("微信服务器连接超时！");
		} catch (Exception e) {
			log.error("https请求错误：{}", e);
		}
		return jsonObject;
	}

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = access_token_url.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败error:{}errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 创建菜单，只需要手动执行一次就可以了
	 * 
	 * @param menu菜单实例
	 * @param accessToken
	 *            有效的accessToken
	 * @return 0表示成功，其他值表示失败
	 * 
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// 拼装菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();

		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败errcode{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}

		return result;
	}

	/**
	 * 删除当前菜单
	 * @author davis at 2016-07-04
	 * @Title: deleteMenu
	 * @Description: 删除当前Menu
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static int deleteMenu(String accessToken)
	{
		int result = 0;

		//删除菜单的url请求
		String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + accessToken;

		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "GET", null);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("删除菜单失败errcode{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}

		return result;
	}

	/**
	 * 查询自定义菜单
	 * 
	 * @param accessToken
	 *            有效的accessToken
	 * @return
	 */
	public static String queryMenu(String accessToken) {

		JSONObject jsonObject = null;
		// 拼装菜单查询的url
		String url = menu_query_url.replace("ACCESS_TOKEN", accessToken);
		// 调用接口查询菜单
		jsonObject = httpRequest(url, "GET", null);

		return jsonObject.toString();

	}

	/**
	 * 获取微信服务器ip列表
	 * 
	 * @param accessToken
	 * @return
	 */
	public static String getIpList(String accessToken) {
		JSONObject jsonObject = null;
		String url = ip_list_url.replace("ip_list_url", accessToken);
		// 调用接口获取微信服务器ip列表
		jsonObject = httpRequest(url, "GET", null);
		return jsonObject.toString();
	}

	/**
	 * 获取网页授权access_token
	 * 
	 * @param code
	 * @param url
	 * @return
	 */
	public static OAuth2Resp getOAuth2Resp(String code, String url) {
		OAuth2Resp oAuth2Resp = null;
		if (null == url)
			url = oauth2_url;
		if (null == code)
			return null;
		url = url.replace("APPID", TokenThread.appid)
				.replace("SECRET", TokenThread.appsecret).replace("CODE", code);

		JSONObject jsonObject = httpRequest(url, "GET", null);

		if (null != jsonObject) {
			try {
				oAuth2Resp = (OAuth2Resp) JSONObject.toBean(jsonObject,
						OAuth2Resp.class);
			} catch (Exception e) {
				oAuth2Resp = null;
				// 获取网页授权access_token失败
				log.error("获取OAuth2.0网页授权access_token失败，error:{},errMsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return oAuth2Resp;
	}
	
	/**
	 * 拉取用户信息(需scope为 snsapi_userinfo)
	 * @param accessToken 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同 
	 * @param openID
	 * @return
	 */
	public static UserInfo getUserInfo(String accessToken, String openID){
		
		UserInfo userInfo = null ;
		
		String url = snsapi_userinfo_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openID) ;
		
		//调用接口摘取用户信息
		JSONObject jsonObject = httpRequest(url, "GET", null) ;
		
		if(null != jsonObject){
			try {
				userInfo = new UserInfo() ;
				userInfo.setCity(jsonObject.getString("city"));
				userInfo.setCountry("country");
				//userInfo.setErrcode(jsonObject.getInt("errcode"));
				//userInfo.setErrmsg(jsonObject.getString("errmsg"));
				userInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
				userInfo.setNickname(jsonObject.getString("nickname"));
				userInfo.setOpenid(jsonObject.getString("openid"));
				userInfo.setProvince(jsonObject.getString("province"));
				userInfo.setSex(jsonObject.getString("sex"));
				//userInfo.setUnionid(jsonObject.getString("unionid"));
				
			} catch (Exception e) {
				userInfo = null ;
				log.error("拉取用户信息失败，error:{},errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return userInfo ;
	}
	/**
	 * 发送客服消息
	 * @param message
	 * @param accessToken
	 * @return
	 */
	public static int sendCustomMessage(String message, String accessToken){
		int resultCode = -1;	//默认非返回成功的值
		JSONObject jsonObject = null ;
		String url = custom_send_url.replace("ACCESS_TOKEN", accessToken) ;
		jsonObject = httpRequest(url, "POST", message) ;
		if(null != jsonObject){
			resultCode = jsonObject.getInt("errcode") ;
			if(resultCode == 0){
				log.info("---------->>>>>SEND CUSTOM MESAGE SUCCESS....");
			}
		}
		return resultCode ;
	}
	
	/**
	 * 获取jsapi_ticket
	 * @param url
	 * @param accessToken
	 * @return
	 */
	public static JsapiTicket get_jsapi_ticket(String url, String accessToken){
		JsapiTicket jsapiTicket = null ;
		String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", accessToken) ;
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null) ;
		//如果请求成功
		if(null != jsonObject){
			try {
				jsapiTicket = new JsapiTicket();
				jsapiTicket.setTicket(jsonObject.getString("ticket"));
				jsapiTicket.setExpires_in(jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				jsapiTicket = null;
				// 获取token失败
				log.error("获取token失败error:{}errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return jsapiTicket ;
	}
	
	public static int sendTemplateMessage(String templateMessage, String accessToken){
		int result = 0 ;
		
		String requestUrl = template_message_url.replace("ACCESS_TOKEN", accessToken) ;
		JSONObject jsonObject = httpRequest(requestUrl, "POST", templateMessage) ;
		if(null != jsonObject){
			result = jsonObject.getInt("errcode") ;
		}
		return result ;
	}

	/**
	 * 获取素材总数
	 * @author davis at 2016-07-05
	 * 
	 * @return Materialcount
	 */
	public static Materialcount getMaterialcount() {
		Materialcount materialcount = null;

		String requestUrl = get_materialcount_url.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				materialcount = new Materialcount();
				materialcount.setVoiceCount(jsonObject.getInt("voice_count"));
				materialcount.setVideoCount(jsonObject.getInt("video_count"));
				materialcount.setImageCount(jsonObject.getInt("image_count"));
				materialcount.setNewsCount(jsonObject.getInt("news_count"));
			} catch (Exception e) {
				materialcount = null;
				// 获取materialcount失败
				log.error("获取materialcount失败error:{}errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		
		return materialcount;
	}

//	private static JsonMapper mapper = JsonMapper.nonDefaultMapper();
	/**
	 * 获取永久性素材列表对象
	 * @author davis at 2016-07-05
	 * 
	 * @return NewsMaterialList
	 */
	public static NewsMaterialList getBatchMaterial(BatchMaterialParam bmParam) {
		NewsMaterialList newsMaterialList = null;

		String requestUrl = batchget_material_url.replace("ACCESS_TOKEN", TokenThread.accessToken.getToken());
		// 将获取永久性素材列表参数对象转成json字符串
		String jsonData = JSONObject.fromObject(bmParam).toString();
		JSONObject jsonObject = httpRequest(requestUrl, "POST", jsonData);
		// 如果请求成功
		if (null != jsonObject) {
//			System.out.println(jsonObject);
			try {
				Map<String, Class> classMap = new HashMap<String, Class>();  
		         classMap.put("item", NewsMaterial.class);  
		         classMap.put("content", NewsContent.class);    
		         classMap.put("news_item", NewsInfo.class);    
		         // 将JSON转换成NewsMaterialList
				newsMaterialList = (NewsMaterialList)JSONObject.toBean(jsonObject, NewsMaterialList.class, classMap);
//				newsMaterialList = mapper.fromJson(jsonObject.toString(), NewsMaterialList.class);	
			} catch (Exception e) {
				newsMaterialList =null;// 获取newsMaterialList失败
				log.error("获取newsMaterialList失败error:{}errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
//				e.printStackTrace();
			}
		}
		
		return newsMaterialList;
	}
}
