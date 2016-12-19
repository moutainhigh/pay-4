package com.pay.acc.service.member;

import java.util.HashMap;

/**
 * website登录失败发消息到fo-mdp接口，仅此用途，主要是解决ma登录失败满三次会锁定账户以及解锁后清零情况
 * 此处会记录详细登录失败次数信息
 * @author meng.li
 *
 */
public interface NotifyFacadeService {
	
	/**
	 * 发送消息处理  
	 * @param request 发送消息内容
	 */
	void sendRequest(String queueName, HashMap<String, Object> request);
	
}
