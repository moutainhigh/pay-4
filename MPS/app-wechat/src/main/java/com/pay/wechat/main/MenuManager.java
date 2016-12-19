/**
 * 
 */
package com.pay.wechat.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.wechat.model.AccessToken;
import com.pay.wechat.model.Button;
import com.pay.wechat.model.CommonButton;
//import com.pay.wechat.model.CommonButton;
import com.pay.wechat.model.ComplexButton;
import com.pay.wechat.model.Menu;
import com.pay.wechat.model.ViewButton;
import com.pay.wechat.thread.TokenThread;
import com.pay.wechat.util.WeiXinUtil;

/**
 * 菜单管理器类
 * 
 * @author PengJiangbo
 *
 */

public class MenuManager {

	//private static final String remoteAdd = "http://wx.tbars.cn" ;			//http://wx.tbars.cn		//http://qylinks.com
	private static final String remoteAdd = "http://wx.ipaylinks.com" ;			//http://wx.tbars.cn		//http://qylinks.com
	private static final String localAdd = "http://ipayWechat.tunnel.mobi" ;
	
	// private static Logger log = LoggerFactory.getLogger(MenuManager.class) ;
	private static Log log = LogFactory.getLog(MenuManager.class);

	//创建菜单只要在此手动执行一个就可以了，所以accessToken值无法从TokenThread.accessToken中获取。remark by davis at 2016-07-05
	public static void main(String[] args) {
		System.out.println("开始创建菜单！");
		// 第三方用户唯一凭证
//		String appid = "wx5c2672970ee835e8";
//		String appsecret = "8e0fe9e93eb73de40f1c8a4886da70cd";
		String appid = "wx5c2672970ee835e8";
		String appsecret = "8e0fe9e93eb73de40f1c8a4886da70cd";

		AccessToken accessToken = WeiXinUtil.getAccessToken(appid, appsecret);
		System.out.println(accessToken);
		log.info("获取accessToken....." + accessToken.toString());
		// 调用接口获取accessToke
		if (null != accessToken) {
			//创建菜单之前先删除菜单
			int result = WeiXinUtil.deleteMenu(accessToken.getToken());//add by davis at 2016-07-04
			if (0 == result) {
				log.info("删除菜单成功！");
				System.out.println("删除菜单成功！");
			}
			// 调用接口创建菜单
			result = WeiXinUtil.createMenu(getMenuByDavis(),//modifided by davis at 2016-07-04			//getMenuRem();				//getMenu
					accessToken.getToken());
			if (0 == result) {
				log.info("菜单创建成功！");
				System.out.println("菜单创建成功！");
			} else {
				log.error("菜单创建失败，错误码：" + result);
				System.out.println("菜单创建失败，错误码：" + result);
			}
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
//		ViewButton btn11 = new ViewButton();
//		btn11.setName("游客止步");
//		btn11.setType("view");
//		btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c2672970ee835e8&redirect_uri="
//				+ "http://ipayWechat.tunnel.mobi/wechat/toEmployeeBind.htm&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");

		ViewButton btn12 = new ViewButton();
		btn12.setName("招纳贤士");
		btn12.setType("view");
		btn12.setUrl("http://companyadc.51job.com/companyads/2015/sh/qibin0710_7696/index.htm");

		ViewButton btn13 = new ViewButton();
		btn13.setName("联姻伙伴");
		btn13.setType("view");
		btn13.setUrl( localAdd + "/wechat/toPartners.htm");

		ViewButton btn14 = new ViewButton();
		btn14.setName("我们是谁");
		btn14.setType("view");
		btn14.setUrl(localAdd + "/wechat/toCompanyIntroduction.htm");

		ViewButton btn21 = new ViewButton();
		btn21.setName("商户登录");
		btn21.setType("view");
		btn21.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c2672970ee835e8&redirect_uri="
				+ localAdd + "/wechat/index.htm&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");

		ViewButton btn22 = new ViewButton();
		btn22.setName("商户注册");
		btn22.setType("view");
		btn22.setUrl(localAdd + "/wechat/registerNav.htm?method=registerNav");
		
		/*
		 * CommonButton btn23 = new CommonButton(); btn23.setName("美女电台");
		 * btn23.setType("click"); btn23.setKey("23");
		 * 
		 * CommonButton btn24 = new CommonButton(); btn24.setName("人脸识别");
		 * btn24.setType("click"); btn24.setKey("24");
		 * 
		 * CommonButton btn25 = new CommonButton(); btn25.setName("聊天唠嗑");
		 * btn25.setType("click"); btn25.setKey("25");
		 */

		/*
		 * CommonButton btn31 = new CommonButton(); btn31.setName("Q友圈");
		 * btn31.setType("click"); btn31.setKey("31");
		 * 
		 * CommonButton btn32 = new CommonButton(); btn32.setName("电影排行榜");
		 * btn32.setType("click"); btn32.setKey("32");
		 * 
		 * CommonButton btn33 = new CommonButton(); btn33.setName("幽默笑话");
		 * btn33.setType("click"); btn33.setKey("33");
		 * 
		 * CommonButton btn34 = new CommonButton() ; btn34.setName("关于我们");
		 * btn34.setType("click"); btn34.setKey("34");
		 * 
		 * ViewButton btn35 = new ViewButton() ; btn35.setName("去逛百度");
		 * btn35.setType("view"); btn35.setUrl("http://www.baidu.com");
		 */

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("关于我们");
		mainBtn1.setSub_button(new Button[] { btn12, btn13, btn14 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("商户中心");
		mainBtn2.setSub_button(new Button[] { btn21, btn22 });

		// ComplexButton mainBtn3 = new ComplexButton();
		// mainBtn3.setName("更多体验");
		// mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33, btn34,
		// btn35 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2 });

		return menu;
	}

	private static Menu getMenuRem() {
//		ViewButton btn11 = new ViewButton();
//		btn11.setName("游客止步");
//		btn11.setType("view");
//		btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c2672970ee835e8&redirect_uri="
//				+ "http://ipayWechat.tunnel.mobi/wechat/toEmployeeBind.htm&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");

		ViewButton btn12 = new ViewButton();
		btn12.setName("招纳贤士");
		btn12.setType("view");
		btn12.setUrl("http://companyadc.51job.com/companyads/2015/sh/qibin0710_7696/index.htm");

		ViewButton btn13 = new ViewButton();
		btn13.setName("联姻伙伴");
		btn13.setType("view");
		btn13.setUrl( remoteAdd + "/wechat/toPartners.htm");

		ViewButton btn14 = new ViewButton();
		btn14.setName("我们是谁");
		btn14.setType("view");
		btn14.setUrl(remoteAdd + "/wechat/toCompanyIntroduction.htm");

		ViewButton btn21 = new ViewButton();
		btn21.setName("商户登录");
		btn21.setType("view");
		btn21.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c2672970ee835e8&redirect_uri="
				+ remoteAdd + "/wechat/index.htm&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");

		ViewButton btn22 = new ViewButton();
		btn22.setName("商户注册");
		btn22.setType("view");
		btn22.setUrl(remoteAdd + "/wechat/registerNav.htm?method=registerNav");
		
		/*
		 * CommonButton btn23 = new CommonButton(); btn23.setName("美女电台");
		 * btn23.setType("click"); btn23.setKey("23");
		 * 
		 * CommonButton btn24 = new CommonButton(); btn24.setName("人脸识别");
		 * btn24.setType("click"); btn24.setKey("24");
		 * 
		 * CommonButton btn25 = new CommonButton(); btn25.setName("聊天唠嗑");
		 * btn25.setType("click"); btn25.setKey("25");
		 */

		/*
		 * CommonButton btn31 = new CommonButton(); btn31.setName("Q友圈");
		 * btn31.setType("click"); btn31.setKey("31");
		 * 
		 * CommonButton btn32 = new CommonButton(); btn32.setName("电影排行榜");
		 * btn32.setType("click"); btn32.setKey("32");
		 * 
		 * CommonButton btn33 = new CommonButton(); btn33.setName("幽默笑话");
		 * btn33.setType("click"); btn33.setKey("33");
		 * 
		 * CommonButton btn34 = new CommonButton() ; btn34.setName("关于我们");
		 * btn34.setType("click"); btn34.setKey("34");
		 * 
		 * ViewButton btn35 = new ViewButton() ; btn35.setName("去逛百度");
		 * btn35.setType("view"); btn35.setUrl("http://www.baidu.com");
		 */

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("关于我们");
		mainBtn1.setSub_button(new Button[] { btn12, btn13, btn14 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("商户中心");
		mainBtn2.setSub_button(new Button[] { btn21, btn22 });

		// ComplexButton mainBtn3 = new ComplexButton();
		// mainBtn3.setName("更多体验");
		// mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33, btn34,
		// btn35 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2 });

		return menu;
	}
	
	/***
	 * 创建菜单，以响应图文消息
	 * @author davis at 2016-07-04
	 * 将最新动态指定官网的最新动态链接 modify by davis.guo at 2016-08-02
	 * @return
	 */
	private static Menu getMenuByDavis() {

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("了解启赟");
		CommonButton btn11 = new CommonButton("公司简介", "key_11");
		CommonButton btn12 = new CommonButton("合作伙伴", "key_12");
//		CommonButton btn13 = new CommonButton("最新动态", "key_13");//add by davis at 2016-07-11
		ViewButton btn13 = new ViewButton("最新动态", "http://www.ipaylinks.com/recentnews.html") ;
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("产品介绍");
		CommonButton btn21 = new CommonButton("国际收付款业务", "key_21");
		CommonButton btn22 = new CommonButton("供应链金融服务", "key_22");
		CommonButton btn23 = new CommonButton("风险管理服务", "key_23");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("商务中心");
		CommonButton btn31 = new CommonButton("接入流程", "key_31");
		CommonButton btn32 = new CommonButton("常见问题", "key_32");
		CommonButton btn33 = new CommonButton("联系我们", "key_33");
		mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });
		 

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}
