package com.pay.app.service.messagebox;

/**
 * @author jim_chen
 * @version 2010-10-9
 */
public interface MessageBoxService {

	/**
	 * 发送站内信
	 * 
	 * @param title
	 *            标题
	 * @param receiveMemberCode
	 *            发送会员
	 * @param content
	 *            内容
	 */
	void sendMessageBox(String title, String receiveMemberCode, String content);
}
