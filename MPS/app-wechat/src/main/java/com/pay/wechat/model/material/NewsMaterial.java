/**
 * 
 */
package com.pay.wechat.model.material;

/**
 * 图文素材类
 * @author davis at 2016-07-05
 *
 */
public class NewsMaterial {

	//图文消息素材id
	private String media_id ;
	//这篇图文消息素材的最后更新时间
	private long update_time ;
	//图文素材对象
	private NewsContent content ;
	
	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

	public NewsContent getContent() {
		return content;
	}

	public void setContent(NewsContent content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "NewsMaterial [media_id=" + media_id + ", update_time=" + update_time + ", content=" + content + "]";
	}
	
}
