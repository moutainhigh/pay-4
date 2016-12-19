/**
 * 
 */
package com.pay.wechat.model;

import java.util.Date;

/**
 * js-sdk接口中的jsapi_ticket
 * @author PengJiangbo
 *
 */
public class JsapiTicket {

	private int errcode ;
	
	private String errmsg ;
	
	private String ticket ;
	
	private long expires_in ;

	private Date createTime ;
	
	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}
	
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "JsapiTicket [errcode=" + errcode + ", errmsg=" + errmsg
				+ ", ticket=" + ticket + ", expires_in=" + expires_in
				+ ", createTime=" + createTime + "]";
	}

}
