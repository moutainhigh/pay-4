/**
 * 
 */
package com.pay.wechat.model.template;

/**
 * 模板消息主体
 * @author PengJiangbo
 *
 */
public class TemplateMessageMain {

	private String touser ;
	
	private String template_id ;
	
	private String url ;
	
	private String topcolor ;
	
	private Data data ;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "TemplateMessageMain [touser=" + touser + ", template_id="
				+ template_id + ", url=" + url + ", topcolor=" + topcolor + "]";
	}
	
}
