/**
 *  File: NotifyChargeRequest.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-9      zliner      Changes
 *  
 *
 */
package com.pay.poss.dto.withdraw.notify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.jms.notification.request.NotifyRequest;

/**
 * 发送通知请求
 * @author zliner
 *
 */
public class NotifyTargetRequest extends NotifyRequest  {
	//序列号 
	private static final long serialVersionUID = 368070855572725565L;
	//发送邮件，短信或通知的类型 件、短信和HTTP取值分别为EMAIL(0),SMS(10),HTTP(20)
	private int notifyType;
	//mobile发送短信的手机列表
	private List<String> mobiles = new ArrayList<String>();
	//发送HTTP请求的地址列表
	private List<String> urlList;
	//发送eamil的时候的接收者列表
	private List<String> recAddress = new ArrayList<String>();
	//发送eamil的时候的者地址
	private String fromAddress;
	//发送eamil的时候的抄送地址列表
	private List<String> ccAddress = new ArrayList<String>();
	//发送eamil的时候的邮件主题
	private String subject;
	//发送eamil的时候的邮件附件
	private Map<String, byte[]> attachments = new HashMap<String, byte[]>();
	public int getNotifyType() {
		return notifyType;
	}
	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}
	public List<String> getMobiles() {
		return mobiles;
	}
	public void setMobiles(List<String> mobiles) {
		this.mobiles = mobiles;
	}
	public List<String> getRecAddress() {
		return recAddress;
	}
	public void setRecAddress(List<String> recAddress) {
		this.recAddress = recAddress;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public List<String> getCcAddress() {
		return ccAddress;
	}
	public void setCcAddress(List<String> ccAddress) {
		this.ccAddress = ccAddress;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Map<String, byte[]> getAttachments() {
		return attachments;
	}
	public void setAttachments(Map<String, byte[]> attachments) {
		this.attachments = attachments;
	}
	public List<String> getUrlList() {
		return urlList;
	}
	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}
}
