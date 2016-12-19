/**
 * 
 */
package com.pay.wechat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.wechat.dao.questionbind.QuestionBindDao;
import com.pay.wechat.dao.sysusermapper.SysUserMapperDao;
import com.pay.wechat.message.resp.Article;
import com.pay.wechat.message.resp.BaseMessage;
import com.pay.wechat.message.resp.NewsMessage;
import com.pay.wechat.message.resp.TextMessage;
import com.pay.wechat.model.material.NewsInfo;
import com.pay.wechat.service.CoreService;
import com.pay.wechat.thread.MaterialThread;
import com.pay.wechat.util.MessageUtil;

/**
 * @author PengJiangbo
 * 核心业务处理类实现
 */
public class CoreServiceImpl implements CoreService {

	private static final Log logger = LogFactory.getLog(CoreServiceImpl.class) ;
	
	//注入
	private SysUserMapperDao sysUserMapperDao ;
	private QuestionBindDao questionBindDao ;
	
	public void setSysUserMapperDao(SysUserMapperDao sysUserMapperDao) {
		this.sysUserMapperDao = sysUserMapperDao;
	}

	public void setQuestionBindDao(QuestionBindDao questionBindDao) {
		this.questionBindDao = questionBindDao;
	}


	/**
	 * 处理微信发来的请求
	 * @param reqeust
	 */
	public String processRequest(HttpServletRequest request) {
		
		String respMessage = null ;
		
		//解析xml请求
		try {
			//默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！" ;
			Map<String, String> requestMap = MessageUtil.parseXml(request) ;
			//发送方账号
			String fromUserName = requestMap.get("FromUserName") ;
			//System.out.println("fromUserName:" + fromUserName);
			//公众号
			String toUserName = requestMap.get("ToUserName") ;
			
			//消息类型
			String msgType = requestMap.get("MsgType") ;
			
			//List<Article> articleList = new ArrayList<Article>() ;
			
			//NewsMessage  newsMessage = null ;
			//默认回复文本消息
			TextMessage textMessage = new TextMessage() ;
			setRespBaseMsg(textMessage, toUserName, fromUserName, new Date().getTime(), MessageUtil.RESP_MESSAGE_TYPE_TEXT, 0);
			//文本消息 
			if(MessageUtil.REQ_MESSAGE_TYPE_TEXT.equalsIgnoreCase(msgType)){
				respContent = "您发送的是文本消息!" ;
			}
			//事件推送
            else if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equalsIgnoreCase(msgType)){
            	//事件类型
            	String eventType = requestMap.get("Event") ;
            	//订阅
            	if(MessageUtil.EVENT_TYPE_SUBSCRIBE.equalsIgnoreCase(eventType)){
            		//respContent = "您好，欢迎关注iPayLinks！/:rose" ;   //\ue402//回复文本消息
            		//modified by davis.guo at 2016-08-04
            		respContent = "您好！欢迎关注“起云汇”，期待与您真诚沟通。iPayLinks作为全球领先、安全、快捷、易用的专业第三方跨境收付款服务平台，我们为您提供基于跨境生态体系的增值金融产品与服务，是您的跨境金融专家。";
            	}
            	//取消订阅
            	else if(MessageUtil.EVENT_TYPE_UNSUBSCRIBE.equalsIgnoreCase(eventType)){
            		//取消订阅后，用户不再接收到公众号发送的消息，因此不需要回复消息
            		//取消订阅后，删除绑定消息，删除安全问题验证
            		int delSumResult = this.sysUserMapperDao.deleteSysUserMapperByMemberCode(fromUserName) ;
            		if(delSumResult > 0){
            			int delQbResult = this.questionBindDao.deletelQuestionBindByOpenID(fromUserName) ;
            			logger.info("--------->>>>>>>>>>>>delQbResult....>>" + delQbResult);
            		}
            	}
            	//自定义菜单单击事件
            	else if(MessageUtil.EVENT_TYPE_CLICK.equalsIgnoreCase(eventType)){
            		//获取素材总数 TODO 
        			ConcurrentMap<String, List<NewsInfo>> newsMap = MaterialThread.newsMap;
        			ConcurrentMap<String, String> menuItemMap = MaterialThread.menuItemMap;
            		if(newsMap != null && newsMap.size() > 0)
            		{
            			//事件key值，与创建自定义菜单时指定的key值相应
	            		String eventKey = requestMap.get("EventKey") ;
	            		//图文消息的设置请参考教程：http://www.cnblogs.com/mfryf/p/3598745.html
						//事件值定义参考MenuManager.getMenuByDavis()方法中的定义。
	
	            		String media_id = "";//可能存在多图文消息，所以使用图文消息素材id来作为菜单项的唯一标识。
	            		List<NewsInfo> newsItems = new ArrayList<NewsInfo>();
						List<Article> articleList = new ArrayList<Article>();
						NewsMessage newsMessage = new NewsMessage();
						setRespBaseMsg(newsMessage, toUserName, fromUserName, new Date().getTime(), MessageUtil.RESP_MESSAGE_TYPE_NEWS, 0);
						if(menuItemMap != null)
						{
    						if("key_11".equalsIgnoreCase(eventKey))//了解启赟->公司简介；
    						{
    							media_id = menuItemMap.get(MaterialThread.MENUITEM_NAME_11);
    						}
    						else if("key_12".equalsIgnoreCase(eventKey))//了解启赟->合作伙伴；
    						{
    							media_id = menuItemMap.get(MaterialThread.MENUITEM_NAME_12);
    						}
    						else if("key_13".equalsIgnoreCase(eventKey))//了解启赟->最新动态；
    						{
    							media_id = menuItemMap.get(MaterialThread.MENUITEM_NAME_13);
    						}
    						else if("key_21".equalsIgnoreCase(eventKey))//产品介绍->国际收付款业务；
    						{
    							media_id = menuItemMap.get(MaterialThread.MENUITEM_NAME_21);
    						}
    						else if("key_22".equalsIgnoreCase(eventKey))//产品介绍->供应链金融服务；
    						{
    							media_id = menuItemMap.get(MaterialThread.MENUITEM_NAME_22);
							}
    						else if("key_23".equalsIgnoreCase(eventKey))//产品介绍->风险管理服务；
    						{
    							media_id = menuItemMap.get(MaterialThread.MENUITEM_NAME_23);
    						}
    						else if("key_31".equalsIgnoreCase(eventKey))//商务中心->接入流程；
    						{
    							media_id = menuItemMap.get(MaterialThread.MENUITEM_NAME_31);
        					}
    						else if("key_32".equalsIgnoreCase(eventKey))//商务中心->常见问题；
    						{
    							media_id = menuItemMap.get(MaterialThread.MENUITEM_NAME_32);
        					}
    						else if("key_33".equalsIgnoreCase(eventKey))//商务中心->联系我们；
    						{
    							media_id = menuItemMap.get(MaterialThread.MENUITEM_NAME_33);
        					}
						}
						
						newsItems = newsMap.get(media_id);
						if(newsItems!=null)
						for(NewsInfo newsInfo : newsItems)
						{
							Article article = new Article();
							article.setTitle(newsInfo.getTitle());  
		                    article.setPicUrl(newsInfo.getThumb_url());
		                    article.setDescription(newsInfo.getDigest());
		                    article.setUrl(newsInfo.getUrl());
		                    articleList.add(article); 
						}
						
	                    // 设置图文消息个数  
	                    newsMessage.setArticleCount(articleList.size());  
	                    // 设置图文消息包括的图文集合  
	                    newsMessage.setArticles(articleList);  
	                    // 将图文消息对象转换成xml字符串  
	                    respMessage = MessageUtil.newsMessageToXml(newsMessage); 
            		}
                	return respMessage;
            	}
            }
			//图片消息
			else if(MessageUtil.REQ_MESSAGE_TYPE_IMAGE.equalsIgnoreCase(msgType)){
				respContent = "您发送的是图片消息!" ;
			}
			// 地理位置消息  
			else if(MessageUtil.REQ_MESSAGE_TYPE_LOCATION.equalsIgnoreCase(msgType)){
                respContent = "您发送的是地理位置消息！";  
            } 
			// 链接消息  
			else if(MessageUtil.REQ_MESSAGE_TYPE_LINK.equalsIgnoreCase(msgType)){
            	respContent = "您发送的是链接消息！";  
            }  
            // 音频消息  
			else if(MessageUtil.REQ_MESSAGE_TYPE_VOICE.equalsIgnoreCase(msgType)){
            	respContent = "您发送的是音频消息！";  
            }
			
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage) ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage ;
	}
	
	/***
	 * 设置响应的基础信息
	 * @author davis at 2016-07-04
	 * @param msg
	 */
	private void setRespBaseMsg(BaseMessage msg, String toUserName, String fromUserName, long now, String msgType, int flag)
	{
		msg.setFromUserName(toUserName);
		msg.setToUserName(fromUserName);
		msg.setCreateTime(now);
		msg.setMsgType(msgType);
		msg.setFuncFlag(flag);
	}

}
