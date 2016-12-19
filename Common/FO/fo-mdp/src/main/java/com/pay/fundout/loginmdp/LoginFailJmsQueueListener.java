package com.pay.fundout.loginmdp;

import java.util.Date;
import java.util.HashMap;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.dto.autorisk.LoginFailLogDto;
import com.pay.fundout.withdraw.service.autorisk.CheckLogService;

/**
 * website登录失败监听器，主要目的是用来记录登录失败累计次数，为出款自动过风控服务
 * @author meng.li
 *
 */
public class LoginFailJmsQueueListener implements MessageListener {
	
	private static final String MEMBER_CODE = "memberCode";
	
	private static final String ERROR_TIME = "errorTime";

	private static final Log log = LogFactory.getLog(LoginFailJmsQueueListener.class);
	
	private CheckLogService checkLogService;
	
	public void setCheckLogService(CheckLogService checkLogService){
		this.checkLogService = checkLogService;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		try {
			if (message instanceof ActiveMQObjectMessage) {
				ActiveMQObjectMessage msg = (ActiveMQObjectMessage) message;
				HashMap<String, Object> request = (HashMap<String, Object>)msg.getObject();
				Long memberCode = (Long)request.get(MEMBER_CODE);
				Date errorTime = (Date)request.get(ERROR_TIME);
				LoginFailLogDto dto = new LoginFailLogDto();
				dto.setMemberCode(memberCode);
				dto.setErrorTime(errorTime);
				checkLogService.createLoginFailLog(dto);
			}
		} catch (Exception e) {
			log.error("记录登录失败次数异常", e);
			e.printStackTrace();
		}
	}

}
