/**
 * 
 */
package com.pay.wechat.model;

/**
 * 客服消息基类
 * @author PengJiangbo
 *
 */
public class CustomMessageText {

	//微信用户唯一标识
	private String touser ;
	
	//消息类型
	private String msgtype ;
	//文本类型
	private Text text ;
	
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	
	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "CustomMessageText [touser=" + touser + ", msgtype=" + msgtype
				+ ", text=" + text + "]";
	}
	
}

