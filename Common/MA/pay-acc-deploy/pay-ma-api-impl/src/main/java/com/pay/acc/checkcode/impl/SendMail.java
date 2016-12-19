package com.pay.acc.checkcode.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.comm.Resource;
import com.pay.jms.notification.request.EmailNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.UUIDUtil;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-7-26 下午12:03:17 邮件服务
 */
public class SendMail {
	private final Log log = LogFactory.getLog(SendMail.class);
	private JmsSender jmsSender;
	// 邮件发送人地址
	private String emailFromAddress = Resource.EMAILFROMADDRESS;

	public JmsSender getJmsSender() {
		return jmsSender;
	}

	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}
	
	/**
	 * @return the emailFromAddress
	 */
	public String getEmailFromAddress() {
		return emailFromAddress;
	}

	/**
	 * @param emailFromAddress
	 *            the emailFromAddress to set
	 */
	public void setEmailFromAddress(String emailFromAddress) {
		this.emailFromAddress = emailFromAddress;
	}

	/**
	 * 
	 * @param userId
	 *            收件人名称(LOGINNAME)
	 * @param recAddress
	 *            收件人列表
	 * @param subject
	 *            邮件主题
	 * @param url
	 *            推送URL
	 * @param templateId
	 *            模板ID
	 * @return
	 */
	public String sendMail(String userId, List<String> recAddress, String subject, String url, long templateId,String checkCode) {
		Map<String, String> data = new HashMap<String, String>();
//		String checkCode = this.checkCode();// 验证码
		EmailNotifyRequest request = new EmailNotifyRequest();
		request.setFromAddress(getEmailFromAddress());
		request.setRecAddress(recAddress);
		request.setTemplateId(templateId);// 模板ID
		request.setSubject(subject);
		data.put("userId", userId);
		data.put("url", url + checkCode);
		request.setData(data);
		try{
			log.info("appwesite==========NotifyRequest:"+request+"#####["+request.getFromAddress()+"]");
			jmsSender.send(request);
		}catch(Exception e){
			log.error("jms send error.",e);
		}finally{
			return checkCode;
		}
	}
	
	/**
	 * @param recAddress 收件人地址
	 * @param subject 主题
	 * @param templateId 模板ID
	 * @param data 邮件中的数据
	 * @return
	 */
	public boolean sendMail(List<String> recAddress, String subject, long templateId ,Map<String, String> data) {
		if(data == null){
			data = new HashMap<String, String>();
		}

		EmailNotifyRequest request = new EmailNotifyRequest();
		request.setFromAddress(getEmailFromAddress());
		request.setRecAddress(recAddress);
		request.setTemplateId(templateId);
		request.setSubject(subject);
		
		request.setData(data);
		try{
			log.info("appwesite==========NotifyRequest:"+request+"#####["+request.getFromAddress()+"]");
			jmsSender.send(request);
			return true;
		}catch(Exception e){
			log.error("jms send error.",e);
			return false;
		}
	}

	/**
	 * @param userId
	 *            收件人名称(LOGINNAME)
	 * @param recAddress
	 *            收件人列表
	 * @param subject
	 *            邮件主题
	 * @param url
	 *            推送URL
	 * @param templateId
	 *            模板ID
	 * @param attachmentName
	 *            附件名称
	 * @param content
	 *            附件
	 * @return
	 * @throws IOException 
	 */
	public String sendMail(String userId, List<String> recAddress, String subject, String url, long templateId, String attachmentName, InputStream content,String checkCode) throws IOException {
		Map<String, String> data = new HashMap<String, String>();
//		String checkCode = this.checkCode();// 验证码
		EmailNotifyRequest request = new EmailNotifyRequest();
		request.setFromAddress(getEmailFromAddress());
		request.setRecAddress(recAddress);
		request.setTemplateId(templateId);// 模板ID
		request.setSubject(subject);
		request.addAttachment(attachmentName, content);
		data.put("userId", userId);
		data.put("url", url + checkCode);
		request.setData(data);
		try{
			jmsSender.send(request);
		}catch(Exception e){
			log.error("jms send error.",e);
		}finally{
			return checkCode;
		}
	}

	/**
	 * 
	 * @param userId
	 *            收件人名称
	 * @param recAddress
	 *            收件人列表
	 * @param subject
	 *            邮件主题
	 * @param url
	 *            推送URL
	 * @param templateId
	 *            模板ID
	 * @param checkCode
	 *            数据库未过期的验证码
	 * @return
	 */
	public String resendMail(String userId, List<String> recAddress, String subject, String url, long templateId, String checkCode) {
		Map<String, String> data = new HashMap<String, String>();
		EmailNotifyRequest request = new EmailNotifyRequest();
		request.setFromAddress(getEmailFromAddress());
		request.setRecAddress(recAddress);
		request.setTemplateId(templateId);// 模板ID
		request.setSubject(subject);
		data.put("userId", userId);
		data.put("url", url + checkCode);
		request.setData(data);
		try{
			jmsSender.send(request);
		}catch(Exception e){
			log.error("jms send error.",e);
		}finally{
			return checkCode;
		}
	}

	/**
	 * 获取36位随机验证码
	 * 
	 * @return
	 */
	private String checkCode() {
		return UUIDUtil.uuid();
	}

}
