package com.pay.app.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.jms.notification.request.SMSNotifyRequest;
import com.pay.jms.sender.JmsSender;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-8-12 下午05:01:32
 * 短信服务
 */
public class SendSms {
	
	private JmsSender jmsSender;
	
	public JmsSender getJmsSender() {
		return jmsSender;
	}
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	/**
	 * 推送短信到指定手机
	 * @param userId     收信人名称
	 * @param mobiles    手机号码列表
	 * @param templateId 模板ID
	 * @return String    验证码
	 */
	public String sendSms(String userId,List<String> mobiles,long templateId,String checkCode){
		Map<String, String> map = new HashMap<String, String>();
		map.put("abc", "验证码是:"+checkCode);
		sendSms(mobiles, templateId, map);
		return checkCode;
	}
	
	/**
	 * 发送信息
	 * @param mobiles 手机 号码列表
	 * @param templateId 短信模板
	 * @param data map 
	 * @return
	 */
	public boolean sendSms(List<String> mobiles, long templateId, Map<String,String> data) {
		try {
			SMSNotifyRequest request = new SMSNotifyRequest();
			request.setMobiles(mobiles);
			request.setTemplateId(templateId);
			
			if(data == null){
				data = new HashMap<String,String>();
			}
			request.setData(data);
			jmsSender.send(request);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
//	public static void main(String [] args){
//		SendSms s = new SendSms();
//		for(int i=0;i<600;i++){
//			System.out.println(s.checkCode());
//		}
//	}
}
