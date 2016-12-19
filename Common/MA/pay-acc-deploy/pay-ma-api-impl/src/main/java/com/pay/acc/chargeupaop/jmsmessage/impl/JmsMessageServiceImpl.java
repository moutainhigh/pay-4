package com.pay.acc.chargeupaop.jmsmessage.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.chargeupaop.jmsmessage.JmsMessageService;
import com.pay.jms.sender.JmsSender;

public class JmsMessageServiceImpl implements JmsMessageService {

	private String mqChargeUpName;

	private JmsSender jmsSender;

	private Log log = LogFactory.getLog(JmsMessageServiceImpl.class);

	@Override
	public void sendChargeUpMessage(Serializable chargupMessage) {
		if (chargupMessage == null) {
			return;
		}
		if (log.isInfoEnabled()) {
			log.info("######################通知记账开始#############################");
			log.info("\n传入的记账信息为：[" + chargupMessage.toString() + "]");
			log.info("\n向队列:[" + this.mqChargeUpName + "]发送记账信息");
		}
		try {
			this.jmsSender.send(this.mqChargeUpName, chargupMessage);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("记账失败", e);
			}
		}
		if(log.isInfoEnabled()){
			log.info("######################通知记账结束#############################");
		}

	}

	/**
	 * @param mqChargeUpName
	 *            the mqChargeUpName to set
	 */
	public void setMqChargeUpName(String mqChargeUpName) {
		this.mqChargeUpName = mqChargeUpName;
	}

	/**
	 * @param jmsSender
	 *            the jmsSender to set
	 */
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

}
