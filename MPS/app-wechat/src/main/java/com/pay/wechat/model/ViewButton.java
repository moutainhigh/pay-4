/**
 * 
 */
package com.pay.wechat.model;

/**
 * View类型的菜单
 * @author PengJiangbo
 *
 */
public class ViewButton extends Button{
		//添加构造函数 add by davis at 2016-08-02
		public ViewButton(){}
		public ViewButton(String name, String url)
		{
			this(name, "view", url);
		}
		public ViewButton(String name, String type, String url)
		{
			this.setName(name);
			this.type = type;
			this.url = url;
		}
		
		private String type ;
		private String url ;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		@Override
		public String toString() {
			return "ViewButton [type=" + type + ", url=" + url + "]";
		}

}
