/**
 * 
 */
package com.pay.channel.service.impl;

import com.pay.channel.dao.ChannelRequestDAO;
import com.pay.channel.model.ChannelRequest;
import com.pay.channel.service.ChannelRequestService;

/**
 * @author chaoyue
 *
 */
public class ChannelRequestServiceImpl implements ChannelRequestService {

	private ChannelRequestDAO channelRequestDAO;

	public void setChannelRequestDAO(ChannelRequestDAO channelRequestDAO) {
		this.channelRequestDAO = channelRequestDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.ChannelRequestService#createChannelRequest(com
	 * .pay.channel.model.ChannelRequest)
	 */
	@Override
	public Long createChannelRequest(ChannelRequest request) {
		return (Long) channelRequestDAO.create(request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.ChannelRequestService#findByDepositProtocolNo
	 * (java.lang.Long)
	 */
	@Override
	public ChannelRequest findByDepositProtocolNo(Long depositProtocolNo) {
		return channelRequestDAO.findByDepositProtocolNo(depositProtocolNo);
	}

}
