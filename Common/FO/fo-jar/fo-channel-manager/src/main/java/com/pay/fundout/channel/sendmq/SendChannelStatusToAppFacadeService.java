package com.pay.fundout.channel.sendmq;

import java.util.HashMap;

/**
 * 
 * <p>出款渠道对应的目的行维护发送MQ通知APP接口</p>
 * @author Volcano.Wu
 * @since 2010-12-10
 * @see
 */
public interface SendChannelStatusToAppFacadeService {

	/**
	 * String:bankCode(银行机构号)
	 * String:bankName（银行名称）
     * String:flag（是否有效,1-有效，0-无效）
	 * @param params
	 */
	public void sendMq2App(HashMap<String,String> params);
}
