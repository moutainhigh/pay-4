/**
 * 
 */
package com.pay.wechat.model.material;

import java.util.List;

/**
 * 图文素材内容类
 * @author davis at 2016-07-05
 *
 */
public class NewsContent {
	//这篇图文消息素材的创建时间
	private long create_time ;
	//这篇图文消息素材的最后更新时间
	private long update_time ;
	//多图文内容
	private List<NewsInfo> news_item ;

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

	public List<NewsInfo> getNews_item() {
		return news_item;
	}

	public void setNews_item(List<NewsInfo> news_item) {
		this.news_item = news_item;
	}

	@Override
	public String toString() {
		return "NewsContent [news_item=" + news_item + "]";
	}
	
}
