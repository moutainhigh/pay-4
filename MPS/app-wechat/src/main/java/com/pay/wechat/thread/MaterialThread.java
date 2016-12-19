/**
 * 
 */
package com.pay.wechat.thread;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.wechat.model.material.BatchMaterialParam;
import com.pay.wechat.model.material.Materialcount;
import com.pay.wechat.model.material.NewsInfo;
import com.pay.wechat.model.material.NewsMaterial;
import com.pay.wechat.model.material.NewsMaterialList;
import com.pay.wechat.util.WeiXinUtil;


/**
 * 定时（10分钟）获取永久性素材列表对象并缓存起来，以防访问量大时服务器无法响应。
 * @author davis at 2016-07-11
 *
 */
public class MaterialThread extends MultiActionController implements Runnable {

	private static Logger log = LoggerFactory.getLogger(MaterialThread.class) ;
	public static final String MENUITEM_NAME_11 = "iPayLinks公司介绍";
	public static final String MENUITEM_NAME_12 = "合作伙伴";
	public static final String MENUITEM_NAME_13 = "最新动态";
	public static final String MENUITEM_NAME_21 = "国际收付款业务";
	public static final String MENUITEM_NAME_22 = "供应链金融服务";
	public static final String MENUITEM_NAME_23 = "风险管理服务";
	public static final String MENUITEM_NAME_31 = "跨境收单接入流程";
	public static final String MENUITEM_NAME_32 = "常见问题";
	public static final String MENUITEM_NAME_33 = "联系我们";
	
	//素材总数
	private static Materialcount materialcount = null;
	//永久性素材列表参数
	private static BatchMaterialParam bmParam = null;
	//永久性素材列表
	private static NewsMaterialList newsMaterialList = null;

	//永久性素材列表缓存
	public static ConcurrentMap<String, List<NewsInfo>> newsMap = new ConcurrentHashMap<String, List<NewsInfo>>();//mediaId与图文消息的映射
	public static ConcurrentMap<String, String> menuItemMap = new ConcurrentHashMap<String, String>();//菜单项与mediaId的映射
	
	public void run() {
		try {
			Thread.sleep(6 * 1000);
		} catch (InterruptedException e1) {
			log.error("{}", e1);
		}
		while(true){
			//调用微信公众平台接口,获取永久性素材列表
			try {
				//获取素材总数 TODO 
        		materialcount = WeiXinUtil.getMaterialcount();
        		//判断图文素材是否为空
        		if(materialcount != null && materialcount.getNewsCount() > 0)
        		{
        			//获取永久性素材列表
        			bmParam = new BatchMaterialParam("news", 0, materialcount.getNewsCount());
        			newsMaterialList = WeiXinUtil.getBatchMaterial(bmParam);
            		if(newsMaterialList != null)
            		{
        				newsMap.clear();
        				menuItemMap.clear();
            			String mediaId = "";//可能存在多图文消息，所以使用图文消息素材id来作为菜单项的唯一标识。add at 2016-07-11
            			for(NewsMaterial newsMaterial : newsMaterialList.getItem())
            			{
            				mediaId = newsMaterial.getMedia_id();
//            				logger.info("###########mediaId="+mediaId);
            				if(newsMaterial.getContent() != null)
            				{
            					List<NewsInfo> newsItems = newsMaterial.getContent().getNews_item();
                				if(newsItems != null)
                				{
                					String title = "";
	                				for(NewsInfo newsInfo : newsItems)
	                    			{
	                					title = newsInfo.getTitle();
	                					if(title==null || title.length()==0)continue;
	                					if(MENUITEM_NAME_11.equalsIgnoreCase(title)
	                							|| MENUITEM_NAME_12.equalsIgnoreCase(title)
	                							|| MENUITEM_NAME_13.equalsIgnoreCase(title)
	                							|| MENUITEM_NAME_21.equalsIgnoreCase(title)
	                							|| MENUITEM_NAME_22.equalsIgnoreCase(title)
	                							|| MENUITEM_NAME_23.equalsIgnoreCase(title)
	                							|| MENUITEM_NAME_31.equalsIgnoreCase(title)
	                							|| MENUITEM_NAME_32.equalsIgnoreCase(title)
	                							|| MENUITEM_NAME_33.equalsIgnoreCase(title)
	                							)
	                					{
			                				menuItemMap.put(title, mediaId);
			                				break;
	                					}
	                    			}
                					newsMap.put(mediaId, newsItems);
                				}
            				}
            			}
            		}
        		}
				if(newsMap != null){
					log.info("#获取永久性素材列表成功!");
					//休眠10分钟
					Thread.sleep(10 * 60 * 1000);//TODO
				}else{
					log.error("@@@@获取永久性素材列表失败！！!");
					//如果newsMaterialList为null,60秒后重新获取
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
