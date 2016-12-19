/**
 * 
 */
package com.pay.channel.service.impl;

import com.pay.channel.dao.ChannelResponseDAO;
import com.pay.channel.model.ChannelResponse;
import com.pay.channel.service.ChannelResponseService;

/**
 * @author chaoyue
 *
 */
public class ChannelResponseServiceImpl implements ChannelResponseService {

	private ChannelResponseDAO channelResponseDAO;

	public void setChannelResponseDAO(ChannelResponseDAO channelResponseDAO) {
		this.channelResponseDAO = channelResponseDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.ChannelResponseService#createChannelResponse(
	 * com.pay.channel.model.ChannelResponse)
	 */
	@Override
	public Long createChannelResponse(ChannelResponse response) {
		return (Long) channelResponseDAO.create(response);
	}

}
