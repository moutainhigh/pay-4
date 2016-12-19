/**
 * 
 */
package com.pay.wechat.message.req;

/**
 * 请求消息的基类（普通用户->公众账号）
 * @author PengJiangbo
 *
 */
public class BaseMessage {
	
	//开发者微信号
	private String ToUserName ;
	//发送方微信号（一个openID）
	private String FromUserName ;
	//消息创建时间（整形）
	private Long CreateTime ;
	//消息类型（text/image/location/link）
	private String  MsgType ;
	//消息id,64位整形
	private Long MsgId ;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public Long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public Long getMsgId() {
		return MsgId;
	}
	public void setMsgId(Long msgId) {
		MsgId = msgId;
	}
	
	
}
