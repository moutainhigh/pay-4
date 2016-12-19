package com.pay.fundout.channel.dao.channel.impl;

import java.util.List;

import com.pay.fundout.channel.dao.channel.ChannelDAO;
import com.pay.fundout.channel.model.channel.ChannelObject;
import com.pay.fundout.channel.model.channel.ChannelRequest;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class ChannelDAOImpl extends BaseDAOImpl<ChannelObject> implements
		ChannelDAO {

	@Override
	public List<ChannelObject> findBySelective(ChannelRequest request) {
		return this.findByQuery("channel.getChannelList", request);
	}
}
