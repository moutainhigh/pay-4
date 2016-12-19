/**
 * 
 */
package com.pay.acc.chargeupaop.jmsmessage;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public interface JmsMessageService {
	
	/**
	 * 发送记账信息
	 * @param chargupMessage
	 */
	public void sendChargeUpMessage(Serializable chargupMessage);

}
