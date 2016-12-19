package com.pay.fundout.channel.dao.channel;

import java.util.List;

import com.pay.fundout.channel.model.channel.ChannelObject;
import com.pay.fundout.channel.model.channel.ChannelRequest;
import com.pay.inf.dao.BaseDAO;

public interface ChannelDAO extends BaseDAO<ChannelObject> {

	public List<ChannelObject> findBySelective(ChannelRequest request);
}
