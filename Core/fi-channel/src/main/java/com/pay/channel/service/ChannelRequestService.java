/**
 * 
 */
package com.pay.channel.service;

import com.pay.channel.model.ChannelRequest;

/**
 * @author chaoyue
 *
 */
public interface ChannelRequestService {

	/**
	 * 
	 * @param request
	 * @return
	 */
	Long createChannelRequest(ChannelRequest request);

	/**
	 * 根据充值协议号查询资金机构请求历史记录
	 * 
	 * @param depositProtocolNo
	 * @return
	 */
	ChannelRequest findByDepositProtocolNo(Long depositProtocolNo);
}
