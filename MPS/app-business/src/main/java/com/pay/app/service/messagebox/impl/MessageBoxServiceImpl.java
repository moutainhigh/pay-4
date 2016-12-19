package com.pay.app.service.messagebox.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.jms.sender.JmsSender;
import com.pay.app.service.messagebox.MessageBoxService;

/**
 * @author jim_chen
 * @version 2010-10-9
 */
public class MessageBoxServiceImpl implements MessageBoxService {

	private JmsSender jmsSender;
	private final Log log = LogFactory.getLog(MessageBoxServiceImpl.class);

	public JmsSender getJmsSender() {
		return jmsSender;
	}

	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	@Override
	public void sendMessageBox(String title, String receiveMemberCode, String content) {
		try {
			if (StringUtils.isNotEmpty(title) && StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(receiveMemberCode)) {
				jmsSender.send(title, receiveMemberCode, content);
			}
		}
		catch (Exception e) {
			log.error("send messagebox error.The detail [" + e + "]");
		}

	}

}
